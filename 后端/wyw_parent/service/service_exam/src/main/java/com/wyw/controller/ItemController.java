package com.wyw.controller;

import com.wyw.entity.ApiResult;
import com.wyw.entity.PaperManage;
import com.wyw.service.PaperService;
import com.wyw.service.impl.FillQuestionServiceImpl;
import com.wyw.service.impl.JudgeQuestionServiceImpl;
import com.wyw.service.impl.MultiQuestionServiceImpl;
import com.wyw.util.ApiResultHandler;
import com.wyw.entity.vo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ItemController {

    @Autowired
    MultiQuestionServiceImpl multiQuestionService;

    @Autowired
    FillQuestionServiceImpl fillQuestionService;

    @Autowired
    JudgeQuestionServiceImpl judgeQuestionService;

    @Autowired
    PaperService paperService;



    @PostMapping("/item")
    public ApiResult ItemController(@RequestBody Item item) {
        // 选择题
        Integer changeNumber = item.getChangeNumber();
        // 填空题
        Integer fillNumber = item.getFillNumber();
        // 判断题
        Integer judgeNumber = item.getJudgeNumber();
        //出卷id
        Integer paperId = item.getPaperId();

        // 选择题数据库获取
        List<Integer>  changeNumbers = multiQuestionService.findBySubject(item.getSubject(), changeNumber);
        if(changeNumbers==null){
            return ApiResultHandler.buildApiResult(20001,"选择题数据库获取失败",null);
        }
        for (Integer number : changeNumbers) {
            PaperManage paperManage = new PaperManage(paperId,1,number);
            int index = paperService.add(paperManage);
            if(index==0)
                return ApiResultHandler.buildApiResult(20001,"选择题组卷保存失败",null);
        }

        // 填空题
        List<Integer> fills = fillQuestionService.findBySubject(item.getSubject(), fillNumber);
        if(fills==null)
            return ApiResultHandler.buildApiResult(20001,"填空题数据库获取失败",null);
        for (Integer fillNum : fills) {
            PaperManage paperManage = new PaperManage(paperId,2,fillNum);
            int index = paperService.add(paperManage);
            if(index==0)
                return ApiResultHandler.buildApiResult(20001,"填空题题组卷保存失败",null);
        }
        // 判断题
        List<Integer> judges = judgeQuestionService.findBySubject(item.getSubject(), judgeNumber);
        if(fills==null)
            return ApiResultHandler.buildApiResult(20001,"判断题数据库获取失败",null);
        for (Integer judge : judges) {
            PaperManage paperManage = new PaperManage(paperId,3,judge);
            int index = paperService.add(paperManage);
            if(index==0)
                return ApiResultHandler.buildApiResult(20001,"判断题题组卷保存失败",null);
        }


          return ApiResultHandler.buildApiResult(20000,"试卷组卷成功",null);
    }
}

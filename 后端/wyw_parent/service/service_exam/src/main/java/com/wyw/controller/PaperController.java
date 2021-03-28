package com.wyw.controller;

import com.wyw.entity.*;
import com.wyw.service.impl.FillQuestionServiceImpl;
import com.wyw.service.impl.JudgeQuestionServiceImpl;
import com.wyw.service.impl.MultiQuestionServiceImpl;
import com.wyw.service.impl.PaperServiceImpl;
import com.wyw.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/examservice/paper")
//@CrossOrigin
public class PaperController {

    @Autowired
    private PaperServiceImpl paperService;

    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @Autowired
    private FillQuestionServiceImpl fillQuestionService;
    @GetMapping("/papers")
    public ApiResult<PaperManage> findAll() {
       ApiResult res =  ApiResultHandler.buildApiResult(20000,"请求成功",paperService.findAll());
       return  res;
    }

    @GetMapping("/paper/{paperId}")
    public Map<Integer, List<?>> findById(@PathVariable("paperId") Integer paperId) {
        //选择题题库 1
        List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(paperId);
        //填空题题库 2
        List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(paperId);
        //判断题题库 3
        List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(paperId);
        Map<Integer, List<?>> map = new HashMap<>();
        map.put(1,multiQuestionRes);
        map.put(2,fillQuestionsRes);
        map.put(3,judgeQuestionRes);
        return  map;
    }

    @PostMapping("/paperManage")
    public ApiResult add(@RequestBody PaperManage paperManage) {
        int res = paperService.add(paperManage);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(20000,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(20001,"添加失败",res);
    }
}

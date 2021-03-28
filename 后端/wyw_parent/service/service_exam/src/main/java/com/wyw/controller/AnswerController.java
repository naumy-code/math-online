package com.wyw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.entity.ApiResult;
import com.wyw.entity.FillQuestion;
import com.wyw.entity.JudgeQuestion;
import com.wyw.entity.MultiQuestion;
import com.wyw.service.impl.AnswerServiceImpl;
import com.wyw.util.ApiResultHandler;
import com.wyw.entity.vo.AnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * //@CrossOrigin
 * @author Administrator
 */
@RestController
@RequestMapping("/examservice/answer")
public class AnswerController {

    @Autowired
    private AnswerServiceImpl answerService;

    /**
     * 分页查询题目信息
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/answers/{page}/{size}")
    public ApiResult findAllQuestion(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
       Page<AnswerVO> answerVOPage = new Page<>(page,size);
       IPage<AnswerVO> answerVOIPage = answerService.findAll(answerVOPage);
       return ApiResultHandler.buildApiResult(20000,"查询所有题库",answerVOIPage);
    }

    /**
     * 根据题目类型和ID删除题目信息
     * @param id
     * @param questionType
     * @return
     */
    @DeleteMapping("/exam/{questionId}/{questionType}")
    public ApiResult deleteById(@PathVariable("questionId") Integer id,@PathVariable("questionType") String questionType){
        if (questionType.equals("选择题")){
            int res = answerService.deleteMultiQuestion(id);
            return ApiResultHandler.buildApiResult(20000,"删除成功",res);
        }
        if (questionType.equals("填空题")){
            int res = answerService.deletefillquestion(id);
            return ApiResultHandler.buildApiResult(20000,"删除成功",res);
        }
        if (questionType.equals("判断题")){
            int res = answerService.deleteJudgequestion(id);
            return ApiResultHandler.buildApiResult(20000,"删除成功",res);
        }
        int res = 1;
        return ApiResultHandler.buildApiResult(20000,"删除成功",res);
    }

    /**
     * 通过问题ID和问题的类型查询试题信息
     * @param id
     * @param questionType
     * @return
     */
    @GetMapping("/examInfo/{questionId}/{questionType}")
    public ApiResult getQuestionInfoById(@PathVariable("questionId") Integer id,@PathVariable("questionType") String questionType){
        if (questionType.equals("判断题")){
            JudgeQuestion judgeQuestion = answerService.findById(id);
            return ApiResultHandler.buildApiResult(20000,"请求成功！",judgeQuestion);
        }
        if (questionType.equals("选择题")){
            MultiQuestion multiQuestion = answerService.findmultiQuestionById(id);
            return ApiResultHandler.buildApiResult(20000,"请求成功！",multiQuestion);
        }
        if (questionType.equals("填空题")){
            FillQuestion fillQuestion = answerService.findfillQuestionById(id);
            return ApiResultHandler.buildApiResult(20000,"请求成功！",fillQuestion);
        }
        return null;
    }


}

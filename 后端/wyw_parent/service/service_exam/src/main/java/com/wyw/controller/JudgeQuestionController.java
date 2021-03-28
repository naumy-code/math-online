package com.wyw.controller;

import com.wyw.entity.ApiResult;
import com.wyw.entity.JudgeQuestion;
import com.wyw.service.impl.JudgeQuestionServiceImpl;
import com.wyw.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/examservice/judgeQuestion")
//@CrossOrigin
public class JudgeQuestionController {

    @Autowired
    private JudgeQuestionServiceImpl judgeQuestionService;

    /**
     * 添加判断题信息
     * @param judgeQuestion
     * @return
     */
    @PostMapping("/addJudgeQuestion")
    public ApiResult add(@RequestBody JudgeQuestion judgeQuestion) {
        int res = judgeQuestionService.add(judgeQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(20000,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(20001,"添加失败",res);
    }

    @GetMapping("/judgeQuestionId")
    public ApiResult findOnlyQuestionId() {
        JudgeQuestion res = judgeQuestionService.findOnlyQuestionId();
        return  ApiResultHandler.buildApiResult(20000,"查询成功",res);
    }
}

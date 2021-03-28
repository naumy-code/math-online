package com.wyw.controller;

import com.wyw.entity.ApiResult;
import com.wyw.entity.MultiQuestion;
import com.wyw.service.impl.MultiQuestionServiceImpl;
import com.wyw.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/examservice/multiQuestion")
//@CrossOrigin
public class MultiQuestionController {

    @Autowired
    private MultiQuestionServiceImpl multiQuestionService;

    @GetMapping("/multiQuestionId")
    public ApiResult findOnlyQuestion() {
        MultiQuestion res = multiQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(20000,"查询成功",res);
    }

    /**
     * 添加选择题信息
     * @param multiQuestion
     * @return
     */
    @PostMapping("/addMultiQuestion")
    public ApiResult add(@RequestBody MultiQuestion multiQuestion) {
        int res = multiQuestionService.add(multiQuestion);
        if (res != 0) {

            return ApiResultHandler.buildApiResult(20000,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"添加失败",res);
    }
}

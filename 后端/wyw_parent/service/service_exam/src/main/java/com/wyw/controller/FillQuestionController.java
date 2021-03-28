package com.wyw.controller;

import com.wyw.entity.ApiResult;
import com.wyw.entity.FillQuestion;
import com.wyw.service.impl.FillQuestionServiceImpl;
import com.wyw.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/examservice/fillQuestion")
//@CrossOrigin
public class FillQuestionController {

    @Autowired
    private FillQuestionServiceImpl fillQuestionService;

    /**
     * 添加填空题信息
     * @param fillQuestion
     * @return
     */
    @PostMapping("/addfillQuestion")
    public ApiResult add(@RequestBody FillQuestion fillQuestion) {
        int res = fillQuestionService.add(fillQuestion);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(20000,"添加成功",res);
        }
        return ApiResultHandler.buildApiResult(20001,"添加失败",res);
    }

    @GetMapping("/fillQuestionId")
    public ApiResult findOnlyQuestionId() {
        FillQuestion res = fillQuestionService.findOnlyQuestionId();
        return ApiResultHandler.buildApiResult(20000,"查询成功",res);
}
}

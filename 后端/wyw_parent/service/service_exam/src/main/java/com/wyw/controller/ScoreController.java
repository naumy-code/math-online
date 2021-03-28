package com.wyw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyw.entity.ApiResult;
import com.wyw.entity.Score;
import com.wyw.service.impl.ScoreServiceImpl;
import com.wyw.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * // @CrossOrigin 跨域的注解
 * @author Administrator
 */
@RestController
@RequestMapping("/examservice/Score")
public class ScoreController {
    @Autowired
    private ScoreServiceImpl scoreService;

    /**
     * 查询所有的考试成绩
     * @return
     */
    @GetMapping("/scores")
    public ApiResult findAll() {
        List<Score> res = scoreService.findAll();
        return ApiResultHandler.buildApiResult(20000,"查询所有学生成绩",res);
    }

    /**
     * 学生分页查询考试信息
     * @param page
     * @param size
     * @param studentId
     * @return
     */
    @GetMapping("/score/{page}/{size}/{studentId}")
    public ApiResult findById(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @PathVariable("studentId") String studentId) {
        Page<Score> scorePage = new Page<>(page, size);
        IPage<Score> res = scoreService.findById(scorePage, studentId);
        return ApiResultHandler.buildApiResult(20000, "根据ID查询成绩", res);
    }

    /**
     * 不分页查询成绩
     * @param studentId
     * @return
     */
    @GetMapping("/score/{studentId}")
        public ApiResult findById(@PathVariable("studentId") String studentId) {
        List<Score> res = scoreService.findById(studentId);
        if (!res.isEmpty()) {
            return ApiResultHandler.buildApiResult(20000, "根据ID查询成绩", res);
        } else {
            return ApiResultHandler.buildApiResult(20001, "ID不存在", res);
        }
    }

    /**
     * 添加考试学生成绩
     * @param score
     * @return
     */
    @PostMapping("/addScore")
    public ApiResult add(@RequestBody Score score) {
        int res = scoreService.add(score);
        if (res == 0) {
            return ApiResultHandler.buildApiResult(20001,"成绩添加失败",res);
        }else {
            return ApiResultHandler.buildApiResult(20000,"成绩添加成功",res);
        }
    }

    /**
     * @param examCode
     * @return
     */
    @GetMapping("/scores/{examCode}")
    public ApiResult findByExamCode(@PathVariable("examCode") Integer examCode) {
        List<Score> scores = scoreService.findByExamCode(examCode);
        return ApiResultHandler.buildApiResult(20000,"查询成功",scores);
    }
}

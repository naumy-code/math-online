package com.wyw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wyw.entity.ApiResult;
import com.wyw.entity.ExamManage;
import com.wyw.service.impl.ExamManageServiceImpl;
import com.wyw.util.ApiResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Administrator
 */
@RestController
//@CrossOrigin
@RequestMapping("/examservice/exam")
public class ExamManageController {

    @Autowired
    private ExamManageServiceImpl examManageService;

    /**
     * 不分页查询考试信息
     * @return
     */
    @GetMapping("/exams")
    public ApiResult findAll(){
        System.out.println("不分页查询所有试卷");
        ApiResult apiResult;
        apiResult = ApiResultHandler.buildApiResult(20000, "请求成功！", examManageService.findAll());
        return apiResult;
    }

    /**
     * 分页查询考试信息
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/exams/{page}/{size}")
    public ApiResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        System.out.println("分页查询所有试卷");
        ApiResult apiResult;
        Page<ExamManage> examManage = new Page<>(page,size);
        IPage<ExamManage> all = examManageService.findAll(examManage);
        apiResult = ApiResultHandler.buildApiResult(20000, "请求成功！", all);
        return apiResult;
    }

    /**
     * 根据ID查询考试信息
     * @param examCode
     * @return
     */
    @GetMapping("/exam/{examCode}")
    public ApiResult findById(@PathVariable("examCode") Integer examCode){
        System.out.println("根据ID查找");
        ExamManage res = examManageService.findById(examCode);
        if(res == null) {
            return ApiResultHandler.buildApiResult(10000,"考试编号不存在",null);
        }
        return ApiResultHandler.buildApiResult(20000,"请求成功！",res);
    }

    /**
     * 删除考试信息
     * @param examCode
     * @return
     */
    @DeleteMapping("/exam/{examCode}")
    public ApiResult deleteById(@PathVariable("examCode") Integer examCode){
        int res = examManageService.delete(examCode);
        return ApiResultHandler.buildApiResult(20000,"删除成功",res);
    }

    /**
     * 修改考试信息
     * @param exammanage
     * @return
     */
    @PostMapping("/updateExam")
    public ApiResult update(@RequestBody ExamManage exammanage){
        int res = examManageService.update(exammanage);
        System.out.print("更新操作执行---");
        return ApiResultHandler.buildApiResult(20000,"更新成功",res);
    }

    /**
     * 添加考试信息
     * @param exammanage
     * @return
     */
    @PostMapping("/exam")
    public ApiResult add(@RequestBody ExamManage exammanage){
        int res = examManageService.add(exammanage);
        if (res ==1) {
            return ApiResultHandler.buildApiResult(20000, "添加成功", res);
        } else {
            return  ApiResultHandler.buildApiResult(400,"添加失败",res);
        }
    }

    /**
     * 获得试卷paperId
     * @return
     */
    @GetMapping("/examManagePaperId")
    public ApiResult findOnlyPaperId() {
        ExamManage res = examManageService.findOnlyPaperId();
        if (res != null) {
            return ApiResultHandler.buildApiResult(20000,"请求成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"请求失败",res);
    }
}

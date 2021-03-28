package com.wyw.eduservice.client;

import com.wyw.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    /**
     * 出错之后会执行
     */
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}

package com.wyw.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    /**
     * 上传头像到oss
     * @param file
     * @return
     */
    String uploadFileAvatar(MultipartFile file);
}

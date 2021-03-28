package com.wyw.service;

import com.wyw.entity.PaperManage;

import java.util.List;

public interface PaperService {

    List<PaperManage> findAll();

    List<PaperManage> findById(Integer paperId);

    int add(PaperManage paperManage);
}

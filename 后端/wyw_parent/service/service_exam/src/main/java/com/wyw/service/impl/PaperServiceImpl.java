package com.wyw.service.impl;

import com.wyw.entity.PaperManage;
import com.wyw.mapper.PaperMapper;
import com.wyw.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;
    @Override
    public List<PaperManage> findAll() {
        return paperMapper.findAll();
    }

    @Override
    public List<PaperManage> findById(Integer paperId) {
        return paperMapper.findById(paperId);
    }

    @Override
    public int add(PaperManage paperManage) {
        return paperMapper.add(paperManage);
    }

}

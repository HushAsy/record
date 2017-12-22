package org.hhs.record.service;

import org.hhs.record.dao.mapper.BaseMapper;
import org.hhs.record.dao.mapper.JiuMapper;
import org.hhs.record.dao.pojo.Jiu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JiuService extends BaseService<Jiu> {
    @Autowired
    private JiuMapper jiuMapper;
    public BaseMapper getMapper() {
        return jiuMapper;
    }
}

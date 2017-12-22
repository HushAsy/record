package org.hhs.record.service;

import org.hhs.record.dao.mapper.BaseMapper;
import org.hhs.record.dao.mapper.CodeMapper;
import org.hhs.record.dao.pojo.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeService extends BaseService<Code> {
    @Autowired
    private CodeMapper mapper;

    public BaseMapper getMapper() {
        return mapper;
    }
}

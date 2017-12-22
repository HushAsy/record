package org.hhs.record.service;

import org.hhs.record.dao.mapper.BaseMapper;
import org.hhs.record.dao.mapper.RecordMapper;
import org.hhs.record.dao.pojo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecordService extends BaseService<Record> {
    @Autowired
    private RecordMapper orderMapper;

    public BaseMapper getMapper() {
        return orderMapper;
    }

    public List<Map<String, Object>> getRecordByCodeOrName(String code){
        return orderMapper.selectByCode(code);
    }

}

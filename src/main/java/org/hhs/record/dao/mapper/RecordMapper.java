package org.hhs.record.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.hhs.record.dao.pojo.Record;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RecordMapper extends BaseMapper<Record> {
    public List<Map<String, Object>> selectByCode(@Param("code")String code);
}

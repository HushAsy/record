package org.hhs.record.dao.mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.hhs.record.dao.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

//    @Select("select * from user")
//    @ResultMap("org.hhs.record.dao.mapper.UserMapper.BaseResultMap")
//    public List<User> selectLists();
}

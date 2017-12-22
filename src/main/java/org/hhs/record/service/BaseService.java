package org.hhs.record.service;

import org.hhs.record.dao.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
public abstract class BaseService<T> {
    public int insert(T t){
        return getMapper().insert(t);
    }

    public List<T> getAll(){
        return getMapper().selectAll();
    }

    public T selectByName(String name){
        return (T) getMapper().selectByName(name);
    }
    public abstract BaseMapper getMapper();
}

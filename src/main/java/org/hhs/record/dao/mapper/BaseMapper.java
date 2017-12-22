package org.hhs.record.dao.mapper;

import java.util.List;
public interface  BaseMapper<T> {
    int insert(T t);
    List<T> selectAll();
    T selectByName(String name);
}

package org.hhs.record.dao.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private String id;
    private String username;
    private String tel;
    private String address;
}

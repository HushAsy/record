package org.hhs.record.dao.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Order {
    private String id;
    private String u_id;
    private String j_id;
    private String c_id;
    private String o_time;
}

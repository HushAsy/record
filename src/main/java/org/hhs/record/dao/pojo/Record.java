package org.hhs.record.dao.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Record {
    private String rId;
    private String rUId;
    private String rJId;
    private String rCId;
    private String rOTime;

}

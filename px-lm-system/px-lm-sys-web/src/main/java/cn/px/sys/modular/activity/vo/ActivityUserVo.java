package cn.px.sys.modular.activity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ActivityUserVo {
    private Long id;
    private Long activityId;
    private Long userId;
    private Integer status;
    private Integer userType;
    private String name;
    private Integer integral;
    private Date startTime;
    private Date endTime;
    private String contactPerson;
    private String phone;
    private String address;
    private String headFigure;

}

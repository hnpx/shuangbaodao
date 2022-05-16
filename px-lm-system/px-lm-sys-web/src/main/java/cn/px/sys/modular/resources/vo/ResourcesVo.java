package cn.px.sys.modular.resources.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResourcesVo {
    private Long id;
    private String name;
    private String address;
    private Date startTime;
    private Date endTime;
}

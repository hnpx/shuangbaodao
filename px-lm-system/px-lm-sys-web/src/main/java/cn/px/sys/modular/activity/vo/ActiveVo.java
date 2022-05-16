package cn.px.sys.modular.activity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ActiveVo {
    private Long id;
    private String name;
    private String headFigure;
    private Date  createTime;
}

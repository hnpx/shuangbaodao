package cn.px.sys.modular.activity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NoteVo {
    private String name;
    private String reason;
    private Date createTime;
    private Long id;
    private String type;
    private Long createBy;
    private String applyName;
}

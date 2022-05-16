package cn.px.sys.modular.resources.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ResourcesUserVo {
    private Long id;
    private Date createTime;
    private Long userId;
    private String name;
    private String phone;
    private String nickname;
    private String avatar;

}

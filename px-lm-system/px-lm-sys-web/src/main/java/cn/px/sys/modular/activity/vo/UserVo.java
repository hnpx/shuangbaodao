package cn.px.sys.modular.activity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    private Long userId;
    private String nickname;
    private String avatar;
    private String createTime;
    private Integer status;
    private String realName;
    private String phone;
    private Long id;
    private String unitName;
    private String picture;
}

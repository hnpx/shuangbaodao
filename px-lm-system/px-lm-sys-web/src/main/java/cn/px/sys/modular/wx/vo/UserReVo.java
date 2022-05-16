package cn.px.sys.modular.wx.vo;

import lombok.Data;

@Data
public class UserReVo {
    private Long userId;
    private String name;
    private Long activityId;
    private Integer type;
    private String avatar;
    private String nickname;

}

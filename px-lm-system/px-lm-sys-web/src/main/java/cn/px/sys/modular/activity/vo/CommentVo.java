package cn.px.sys.modular.activity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVo {
    private Long id;
    private Long userId;
    private String nickname;
    private String content;
    private String avatar;
    private String createTime;
    private String img;
    private String realName;
    private String phone;
}

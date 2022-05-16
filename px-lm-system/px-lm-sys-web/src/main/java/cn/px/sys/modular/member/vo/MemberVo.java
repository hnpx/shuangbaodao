package cn.px.sys.modular.member.vo;

//import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

@Data
public class MemberVo {
    private Long id;
    private String name;
    private String nickname;
    private String phone;
    private String avatar;
    private Integer integral;
    private Date createTime;
    private Long userId;
    private Integer remainingPoints;

}

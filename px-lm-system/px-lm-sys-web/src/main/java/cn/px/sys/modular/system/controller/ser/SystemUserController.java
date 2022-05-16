package cn.px.sys.modular.system.controller.ser;

import cn.px.base.core.AbstractController;
import cn.px.sys.modular.system.entity.User;
import cn.px.sys.modular.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ser/sys/user")
public class SystemUserController extends AbstractController {
    @Autowired
    private UserService userService;

    @PostMapping("/update/bind")
    public boolean bindMember(Long userId,Long memberId){
        User user=new User();
        user.setUserId(userId);
        user.setMember(memberId);
        try {
            this.userService.updateById(user);
        }catch(Exception e) {
            return false;
        }
        return true;
    }
}

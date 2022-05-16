package cn.px.sys.modular.activity.vo;

import cn.px.sys.modular.project.vo.ProVo;
import cn.px.sys.modular.system.entity.SysConfigHomeBannersEntity;
import lombok.Data;

import java.util.List;

@Data
public class ActiveAndProjectVo {
    private List<SysConfigHomeBannersEntity> sysConfigHomeBannersEntityList;
    private List<ActiveVo> activeVoList;
    private List<ProVo>  proVoList;
}

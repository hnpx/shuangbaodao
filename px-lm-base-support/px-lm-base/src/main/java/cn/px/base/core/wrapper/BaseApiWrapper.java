package cn.px.base.core.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据处理
 */
@Component
public abstract class BaseApiWrapper<T,VO>{

    /**
     *
     * @return
     */
    public List<VO> wraps(List<T> list,Long id){
       List<VO> vos=new ArrayList<>();
       for(T t:list){
           vos.add(this.wrap(t,id));
       }
       return vos;
    }

    public abstract VO wrap(T item,Long id);

    public Page<VO> getVoPage(Page<T> page,Long id){
        Page<VO> p=new Page<>();
        p.setTotal(page.getTotal());
        p.setCurrent(page.getCurrent());
        p.setSize(page.getSize());
        p.setPages(page.getPages());
        p.setRecords(this.wraps(page.getRecords(),id));
        return p;
    }

}

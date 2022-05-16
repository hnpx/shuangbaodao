package cn.px.sys.modular.activity.job;

import cn.px.base.config.job.DelayQueueManager;
import cn.px.sys.modular.activity.job.impl.*;
import cn.px.sys.modular.activity.service.ActivityUserSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 订单任务创建工厂
 */
@Component
public class OrderJobFactory {
    @Autowired
    private DelayQueueManager queueManager;
    @Resource
    private ActivityUserSignService activityUserSignService;

    /**
     * 创建延时任务
     * @param param
     * @param expire
     */
    public void createJob(OrderTaskParam param,long expire){
        OrderTaskActuator task=new OrderTaskActuator(param,expire,this.activityUserSignService);
        this.queueManager.put(task);
    }
}

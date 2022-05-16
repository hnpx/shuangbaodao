package cn.px.sys.modular.activity.job.impl;

import cn.px.base.config.job.DelayTask;
import cn.px.base.config.job.TaskBase;
import cn.px.sys.modular.activity.service.ActivityUserSignService;
import cn.px.sys.modular.project.entity.ProjectUserSignEntity;
import cn.px.sys.modular.project.service.ProjectUserSignService;

public class OrderTaskActuator extends DelayTask {

    /**
     * 订单操作服务
     */
   // private OrderHandlerService orderService;
    private ActivityUserSignService activityUserSignService;
    private ProjectUserSignService projectUserSignService;

    /**
     * 构造延时任务
     *
     * @param data         业务数据
     * @param expire       任务延时时间（ms）
     * @param
     */
    public OrderTaskActuator(OrderTaskParam data, long expire, ActivityUserSignService activityUserSignService) {
        super(data, expire);
        this.activityUserSignService = activityUserSignService;
    }

    @Override
    public void execute(TaskBase data) {
        OrderTaskParam taskParam = (OrderTaskParam) data;
        switch (taskParam.getTask()) {
            // 活动积分
            case OrderTaskParam.TASK_ACTIVITY:
                this.activityUserSignService.getIntegeral(taskParam.getAid());
                break;

            // 项目积分
            case OrderTaskParam.TASK_PROJECT:
                this.activityUserSignService.getIntegeral1(taskParam.getAid());
                break;
            default:
                break;
        }
    }
}

package cn.px.sys.modular.activity.job.impl;

import cn.px.base.config.job.TaskBase;

/**
 * 订单任务
 */
public class OrderTaskParam extends TaskBase {
    /**
     * 取消订单
     */
    public static final int TASK_ACTIVITY=2;
    public static final int TASK_PROJECT=3;


    private Long aid;
    private int task;

    /**
     * 初始化
     *
     * @param identifier
     * @param aid
     * @param task
     */
    public OrderTaskParam(String identifier, Long aid, int task) {
        super(identifier);
        this.aid = aid;
        this.task = task;
    }


    public Long getAid() {
        return aid;
    }

    public int getTask() {
        return task;
    }
}

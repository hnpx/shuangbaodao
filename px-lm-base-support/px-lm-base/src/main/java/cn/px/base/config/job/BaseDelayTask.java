package cn.px.base.config.job;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseDelayTask extends DelayTask{
    /**
     * 构造延时任务
     *
     * @param data   业务数据
     * @param expire 任务延时时间（ms）
     */
    public BaseDelayTask(TaskBase data, long expire) {
        super(data, expire);
    }

    @Override
    public void execute(TaskBase data) {
      log.debug("执行了任务{}",data.toString());
    }
}

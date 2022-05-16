package cn.px.base.config.job;

/**
 * 任务执行器
 * @author  吕郭飞
 */
public interface ITaskHandle {
    /**
     * 执行任务
     * @param  data
     */
    public void execute(TaskBase data);
}

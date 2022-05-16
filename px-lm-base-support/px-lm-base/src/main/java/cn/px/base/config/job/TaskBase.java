package cn.px.base.config.job;
import com.alibaba.fastjson.JSON;

public class TaskBase {
    private String identifier;

    public TaskBase(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
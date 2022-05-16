package cn.px.base.support.file.vo;

import cn.hutool.json.JSONUtil;

public class FileVo {
	private String name;
	private String path;
	private String simPath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSimPath() {
		return simPath;
	}

	public void setSimPath(String simPath) {
		this.simPath = simPath;
	}

	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}

}

/** 
 * 2017-4-1 
 * Demo.java * author:吕郭飞
 */
package cn.px.base.util;

import java.util.UUID;

/**
 * 返回UUID
 * 
 * @author 吕郭飞
 * @date 2017-4-1
 */
public class UUIDUtil {
	/**
	 * 返回UUID ，默认是18位
	 * 
	 * @author 吕郭飞
	 * @date 2017-4-1
	 * @return
	 */
	public static Long getUUId() {
		return getUUId(16);
	}

	/**
	 * 返回指定位数的UUID
	 * 
	 * @author 吕郭飞
	 * @date 2017-4-1
	 * @param len
	 * @return
	 */
	public static Long getUUId(int len) {
		len = len - 1;
		int machineId = 1;// 最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		String id = machineId + String.format("%0" + len + "d", hashCodeV);
		return Long.parseLong(id);
	}

	/**
	 * 生成16位唯一ID
	 */
	public static Long getOrderIdByUUId() {
		int machineId = 1;// 最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		String id = machineId + String.format("%015d", hashCodeV);
		return Long.parseLong(id);
	}

	public static void main(String[] args) {
		System.out.println(getUUId());
	}
}

package cn.px.base.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 分页辅助工具
 * 
 * @author PXHLT
 * @since 2019年5月24日 下午6:32:32
 */
public final class PageUtil {

	private PageUtil() {
	}

	/** 分页查询参数封装 */
	@SuppressWarnings({ "unchecked" })
	public static Page getPage(Map<String, Object> params) {
		Integer current = 1;
		Integer size = 10;
		String orderBy = "Id", sortAsc = null, openSort = "Y";
		String asc = (String) params.get("asc");
		String desc = (String) params.get("desc");
		if (DataUtil.isNotEmpty(params.get("pageNum"))) {
			current = Integer.valueOf(params.get("pageNum").toString());
		}
		if (DataUtil.isNotEmpty(params.get("pageNo"))) {
			current = Integer.valueOf(params.get("pageNo").toString());
		}
		if (DataUtil.isNotEmpty(params.get("pageIndex"))) {
			current = Integer.valueOf(params.get("pageIndex").toString());
		}
		if (DataUtil.isNotEmpty(params.get("pageSize"))) {
			size = Integer.valueOf(params.get("pageSize").toString());
		}
		if (DataUtil.isNotEmpty(params.get("limit"))) {
			size = Integer.valueOf(params.get("limit").toString());
		}
		if (DataUtil.isNotEmpty(params.get("offset"))) {
			current = Integer.valueOf(params.get("offset").toString()) / size + 1;
		}
		if (DataUtil.isNotEmpty(params.get("sort"))) {
			orderBy = (String) params.get("sort");
			params.remove("sort");
		}
		if (DataUtil.isNotEmpty(params.get("orderBy"))) {
			orderBy = (String) params.get("orderBy");
			params.remove("orderBy");
		}
		if (DataUtil.isNotEmpty(params.get("sortAsc"))) {
			sortAsc = (String) params.get("sortAsc");
			params.remove("sortAsc");
		}
		if (DataUtil.isNotEmpty(params.get("openSort"))) {
			openSort = (String) params.get("openSort");
			params.remove("openSort");
		}
		Object filter = params.get("filter");
		if (filter != null) {
			params.putAll(JSON.parseObject(filter.toString(), Map.class));
		}
		if (size == -1) {
			Page page = new Page();
//			if ("Y".equals(openSort)) {
//				if (DataUtil.isEmpty(asc) && DataUtil.isEmail(desc)) {
//					if ("Y".equals(sortAsc)) {
//						page.setAsc(orderBy.split(","));
//					} else {
//						page.setDesc(orderBy.split(","));
//					}
//				} else {
//					if (DataUtil.isNotEmpty(asc)) {
//						page.setAsc(asc.split(","));
//					}
//					if (DataUtil.isNotEmpty(desc)) {
//						page.setDesc(desc.split(","));
//					}
//				}
//			}
			//TODO 排序
			return page;
		}
		Page page = new Page(current, size);
//		if ("Y".equals(openSort)) {
//			if (DataUtil.isEmpty(asc) && DataUtil.isEmail(desc)) {
//				if ("Y".equals(sortAsc)) {
//					page.setAsc(orderBy.split(","));
//				} else {
//					page.setDesc(orderBy.split(","));
//				}
//			} else {
//				if (DataUtil.isNotEmpty(asc)) {
//					page.setAsc(asc.split(","));
//				}
//				if (DataUtil.isNotEmpty(desc)) {
//					page.setDesc(desc.split(","));
//				}
//			}
//		}
		//TODO 排序
		return page;
	}
}

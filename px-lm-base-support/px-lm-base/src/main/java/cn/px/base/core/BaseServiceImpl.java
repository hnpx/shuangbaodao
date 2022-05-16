package cn.px.base.core;

import cn.hutool.core.bean.BeanUtil;
import cn.px.base.Constants;
import cn.px.base.core.annotation.AutoID;
import cn.px.base.core.annotation.UUID;
import cn.px.base.exception.BusinessException;
import cn.px.base.support.Pagination;
import cn.px.base.support.cache.CacheKey;
import cn.px.base.support.context.ApplicationContextHolder;
import cn.px.base.support.dbcp.HandleDataSource;
import cn.px.base.support.generator.Sequence;
import cn.px.base.util.*;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;




/**
 * @author PXHLT
 * @since 2019年5月24日 下午6:18:41
 * @param <T>
 * @param <M>
 */
public class BaseServiceImpl<T extends BaseModel, M extends BaseMapperImpl<T>> implements BaseService<T>, InitializingBean {
	protected Logger logger = LogManager.getLogger();
	@Autowired
	protected M mapper;
	private static ExecutorService executorService = ThreadUtil.threadPool(1, 100, 30);
	@Value("${redis.expiration}")
	private Integer expiration;

	@Override
	public void afterPropertiesSet() throws Exception {
		Field[] fields = getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				Object v = field.get(this);
				Class<?> cls = field.getType();
				if (v == null && cls.getSimpleName().toLowerCase().contains("service")) {
					v = ApplicationContextHolder.getService(cls);
					field.set(this, v);
				}
				field.setAccessible(false);
			}
		} catch (Exception e) {
			logger.error("", e);
			ThreadUtil.sleep(1, 5);
			afterPropertiesSet();
		}
	}

	/** 逻辑批量删除 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(List<Long> ids, Long userId) {
		ids.forEach(id -> del(id, userId));
	}

	/** 逻辑删除 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void del(Long id, Long userId) {
		try {
			T record = this.getById(id);
			record.setEnable(Constants.ENABLE_FALSE);
			record.setUpdateTime(new Date());
			record.setUpdateBy(userId);
			mapper.updateById(record);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/** 物理删除 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) {
		try {
			mapper.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/** 物理删除 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer deleteByEntity(T t) {
		Wrapper<T> wrapper = new UpdateWrapper<T>(t);
		return mapper.delete(wrapper);
	}

	/** 物理删除 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer deleteByMap(Map<String, Object> columnMap) {
		return mapper.deleteByMap(columnMap);
	}

	/** 根据Id查询(默认类型Map) */
	public Pagination<Map<String, Object>> getPageMap(final Page<Long> ids) {
		if (ids != null) {
			Pagination<Map<String, Object>> page = new Pagination<Map<String, Object>>(ids.getCurrent(), ids.getSize());
			page.setTotal(ids.getTotal());
			final List<Map<String, Object>> records = InstanceUtil.newArrayList();
			final String datasource = HandleDataSource.getDataSource();
			IntStream.range(0, ids.getRecords().size()).forEach(i -> records.add(null));
			IntStream.range(0, ids.getRecords().size()).parallel().forEach(i -> {
				HandleDataSource.putDataSource(datasource);
				records.set(i, InstanceUtil.transBean2Map(getById(ids.getRecords().get(i))));
			});
			page.setRecords(records);
			return page;
		}
		return new Pagination<Map<String, Object>>();
	}

	/** 根据参数分页查询 */
	@Override
	public Pagination<T> query(Map<String, Object> params) {
		Page<Long> page = PageUtil.getPage(params);
		page.setRecords(mapper.selectIdPage(page, params));
		return getPage(page);
	}

	/** 根据实体参数分页查询 */
	@Override
	public Pagination<T> query(T entity, Pagination<T> rowBounds) {
		Page<Long> page = new Page<Long>();
		try {
			PropertyUtils.copyProperties(page, rowBounds);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTraceAsString(e));
		}
		List<Long> ids = mapper.selectIdPage(page, entity);
		page.setRecords(ids);
		return getPage(page);
	}

	@Override
	public Pagination<T> query(Map<String, Object> params, Page<Long> page) {
		List<Long> ids=this.mapper.selectIdPage(page,params);
		page.setRecords(ids);
		return getPage(page);
	}

	@Override
	public Pagination<T> query(T params, Page<Long> page) {
		Map<String, Object> paramsMap = params == null ? new HashMap<String, Object>() : BeanUtil.beanToMap(params);
		return this.query(paramsMap,page);
	}

	/** 根据实体参数分页查询 */
	@Override
	public Pagination<T> queryFromDB(T entity, Pagination<T> rowBounds) {
		Page<T> page = new Page<T>();
		try {
			PropertyUtils.copyProperties(page, rowBounds);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTraceAsString(e));
		}
		Wrapper<T> wrapper = new QueryWrapper<T>(entity);
		IPage<T> iPage = mapper.selectPage(page, wrapper);
		Pagination<T> pager = new Pagination<T>(page.getCurrent(), page.getSize());
		pager.setRecords(iPage.getRecords());
		pager.setTotal(page.getTotal());
		return pager;
	}

	@Override
	public Pagination<T> queryFromDB(Map<String, Object> params) {
		Page<Long> page = PageUtil.getPage(params);
		List<T> list = mapper.selectPage(page, params);
		Pagination<T> pager = new Pagination<T>(page.getCurrent(), page.getSize());
		pager.setRecords(list);
		pager.setTotal(page.getTotal());
		return pager;
	}

	@Override
	public List<T> queryListFromDB(Map<String, Object> params) {
		return mapper.selectByMap(params);
	}

	@Override
	public List<T> queryListFromDB(T entity) {
		Wrapper<T> wrapper = new QueryWrapper<T>(entity);
		return mapper.selectList(wrapper);
	}

	@Override
	public Integer count(T entity) {
		Wrapper<T> wrapper = new QueryWrapper<T>(entity);
		return mapper.selectCount(wrapper);
	}

	@Override
	public Integer count(Map<String, Object> params) {
		return mapper.selectCount(params);
	}

	@Override
	/** 根据id查询实体 */
	public T queryById(Long id) {
		return queryById(id, 1);
	}

	@Override /** 根据参数查询 */
	public List<T> queryList(Map<String, Object> params) {
		if (DataUtil.isEmpty(params.get("orderBy"))) {
			params.put("orderBy", "Id");
		}
		List<Long> ids = mapper.selectIdPage(params);
		List<T> list = queryList(ids);
		return list;
	}

	@Override /** 根据实体参数查询 */
	public List<T> queryList(T params) {
		List<Long> ids = mapper.selectIdPage(params);
		List<T> list = queryList(ids);
		return list;
	}

	/** 根据Id查询(默认类型T) */
	@Override
	public List<T> queryList(final List<Long> ids) {
		final List<T> list = InstanceUtil.newArrayList();
		if (ids != null) {
			final String datasource = HandleDataSource.getDataSource();
			IntStream.range(0, ids.size()).forEach(i -> list.add(null));
			IntStream.range(0, ids.size()).parallel().forEach(i -> {
				HandleDataSource.putDataSource(datasource);
				list.set(i, getById(ids.get(i)));
			});
		}
		return list;
	}

	/** 根据Id查询(cls返回类型Class) */
	@Override
	public <K> List<K> queryList(final List<Long> ids, final Class<K> cls) {
		final List<K> list = InstanceUtil.newArrayList();
		if (ids != null) {
			final String datasource = HandleDataSource.getDataSource();
			IntStream.range(0, ids.size()).forEach(i -> list.add(null));
			IntStream.range(0, ids.size()).parallel().forEach(i -> {
				HandleDataSource.putDataSource(datasource);
				T t = getById(ids.get(i));
				K k = InstanceUtil.to(t, cls);
				list.set(i, k);
			});
		}
		return list;
	}

	@Override /** 根据实体参数查询一条记录 */
	public T selectOne(T entity) {
		Wrapper<T> wrapper = new QueryWrapper<T>(entity);
		T t = mapper.selectOne(wrapper);
		return t;
	}

	/** 修改/新增 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public T update(T record) {
		try {
			record.setUpdateTime(new Date());
			if (record.getId() == null) {
				record.setCreateTime(new Date());
				// TODO 后期可以根据ID的注解不同，选用自增或者是UUID
				Field idField = this.getIdField();
				if (!this.isAutoID(idField)) {// 是否是自动编号，如果不是，则采用UUID
					record.setId(UUIDUtil.getOrderIdByUUId());// 如果是新增的话，写入ID
				}
				SymbolFilterUtil.defaultSymbolFilter(record);
				mapper.insert(record);
				// TODO 暂时不需要更新之后的查询方法
//				record = mapper.selectById(record.getId());
				saveCache(record);
			} else {
				String requestId = Sequence.next().toString();
				String lockKey = this.getLockKey(record.getId());
//				if (CacheUtil.getLock(lockKey, "更新", requestId)) {
					try {
						SymbolFilterUtil.defaultSymbolFilter(record);
						mapper.updateById(record);
						record = mapper.selectById(record.getId());
						saveCache(record);
					} finally {
//						CacheUtil.unLock(lockKey, requestId);
					}
//				} else {
//					throw new RuntimeException("数据不一致!请刷新页面重新编辑!");
//				}
			}
		} catch (DuplicateKeyException e) {
			logger.error(ExceptionUtil.getStackTraceAsString(e));
			throw new BusinessException("已经存在相同的记录.");
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTraceAsString(e));
			throw new RuntimeException(ExceptionUtil.getStackTraceAsString(e));
		}
		return record;
	}

	@SuppressWarnings("rawtypes")
	public Class getModelClass() {
		return BaseModel.class;
	}

	private Field getIdField() {
		Field id;
		try {
			Class mc = this.getModelClass();
			Field[] fs = mc.getDeclaredFields();
			for (Field f : fs) {
				if (f.getName().equals("id")) {
					return f;
				}
			}
			return null;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isUuid(Field idField) {
		UUID u = idField.getAnnotation(UUID.class);
		return u != null;
	}

	private boolean isAutoID(Field idField) {
		AutoID a = idField.getAnnotation(AutoID.class);
		return a != null;
	}

	/** 批量修改/新增 */
	@Override
	public boolean updateBatch(List<T> entityList) {
		return updateBatch(entityList, 30);
	}

	@SuppressWarnings("unchecked")
	protected Class<?> currentModelClass() {
		return ReflectionKit.getSuperClassGenericType(getClass(), 0);
	}

	/**
	 * 获取缓存键值
	 *
	 * @param id
	 * @return
	 */
	protected String getLockKey(Object id) {
		CacheKey cacheKey = CacheKey.getInstance(getClass(), this.expiration);
		StringBuilder sb = new StringBuilder();
		if (cacheKey == null) {
			sb.append(getClass().getName());
		} else {
			sb.append(cacheKey.getValue());
		}
		return sb.append(":LOCK:").append(id).toString();
	}

	/**
	 * @param params
	 * @param cls
	 * @return
	 */
	protected <P> Pagination<P> query(Map<String, Object> params, Class<P> cls) {
		Page<Long> page = PageUtil.getPage(params);
		page.setRecords(mapper.selectIdPage(page, params));
		return getPage(page, cls);
	}

	/**
	 * <p>
	 * 批量操作 SqlSession
	 * </p>
	 */
	protected SqlSession sqlSessionBatch() {
		return SqlHelper.sqlSessionBatch(currentModelClass());
	}

	/**
	 * 获取SqlStatement
	 *
	 * @param sqlMethod
	 * @return
	 */
	protected String sqlStatement(SqlMethod sqlMethod) {
		return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
	}

	/** 根据Id查询(默认类型T) */
	private T getById(Long id) {
		return queryById(id, 1);
	}

	/** 根据Id批量查询(默认类型T) */
	protected Pagination<T> getPage(final Page<Long> ids) {
		if (ids != null) {
			Pagination<T> page = new Pagination<T>(ids.getCurrent(), ids.getSize());
			page.setTotal(ids.getTotal());
			final List<T> records = InstanceUtil.newArrayList();
			final String datasource = HandleDataSource.getDataSource();
			IntStream.range(0, ids.getRecords().size()).forEach(i -> records.add(null));
			IntStream.range(0, ids.getRecords().size()).parallel().forEach(i -> {
				HandleDataSource.putDataSource(datasource);
				records.set(i, getById(ids.getRecords().get(i)));
			});
			page.setRecords(records);
			return page;
		}
		return new Pagination<T>();
	}

	/** 根据Id查询(cls返回类型Class) */
	private <K> Pagination<K> getPage(final Page<Long> ids, final Class<K> cls) {
		if (ids != null) {
			Pagination<K> page = new Pagination<K>(ids.getCurrent(), ids.getSize());
			page.setTotal(ids.getTotal());
			final List<K> records = InstanceUtil.newArrayList();
			final String datasource = HandleDataSource.getDataSource();
			IntStream.range(0, ids.getRecords().size()).forEach(i -> records.add(null));
			IntStream.range(0, ids.getRecords().size()).parallel().forEach(i -> {
				HandleDataSource.putDataSource(datasource);
				records.set(i, InstanceUtil.to(getById(ids.getRecords().get(i)), cls));
			});
			page.setRecords(records);
			return page;
		}
		return new Pagination<K>();
	}

	/** 保存缓存 */
	private void saveCache(String key, T record, int timeOut) {
		if (key != null && record != null) {
			try {
				CacheUtil.getCache().set(key, record, timeOut);
			} catch (Exception e) {
				logger.error(ExceptionUtil.getStackTraceAsString(e));
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						deleteCache(key, 1);
					}
				});
			}
		}
	}

	/** 保存缓存 */
	private void saveCache(T record) {
		if (record != null) {
			CacheKey key = CacheKey.getInstance(getClass(), this.expiration);
			if (key != null) {
				saveCache(key.getValue() + ":" + record.getId(), record, key.getTimeToLive());
			}
		}
	}

	private void deleteCache(String key, int times) {
		try {
			CacheUtil.getCache().del(key);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTraceAsString(e));
			ThreadUtil.sleep(10, Math.min(Integer.MAX_VALUE, times * 100));
			deleteCache(key, times + 1);
		}
	}

	@SuppressWarnings("unchecked")
	private T queryById(Long id, int times) {
		CacheKey key = CacheKey.getInstance(getClass(), this.expiration);
		T record = null;
		// 如果需要缓存
		if (key != null) {
			try {
				record = (T) CacheUtil.getCache().get(key.getValue() + ":" + id, key.getTimeToLive());
			} catch (Exception e) {
				logger.error(ExceptionUtil.getStackTraceAsString(e));
			}
			if (record == null) {
				try {
					record = (T) CacheUtil.getCache().get(key.getValue() + ":R:" + id, key.getTimeToLive());
				} catch (Exception e) {
					logger.error(ExceptionUtil.getStackTraceAsString(e));
				}
			}
			if (record == null) {
				String lockKey = getLockKey("R:" + id);
				String requestId = Sequence.next().toString();
				if (CacheUtil.getLock(lockKey, "根据ID查询数据", requestId)) {
					try {
						record = mapper.selectById(id);
						saveCache(key.getValue() + ":R:" + id, record, key.getTimeToLive());
					} finally {
						CacheUtil.unLock(lockKey, requestId);
					}
				} else {
					if (times > 5) {
						record = mapper.selectById(id);
					} else {
						logger.info(getClass().getSimpleName() + ":" + id + " retry getById.");
						ThreadUtil.sleep(1, 20);
						return queryById(id, times + 1);
					}
				}
			}
		} else {// 如果不需要缓存
			record = mapper.selectById(id);
		}

		return record;
	}

	/** 批量修改/新增 */
	@Override
	public boolean updateBatch(List<T> entityList, int batchSize) {
		if (CollectionUtils.isEmpty(entityList)) {
			throw new IllegalArgumentException("Error: entityList must not be empty");
		}
		try (SqlSession batchSqlSession = sqlSessionBatch()) {
			IntStream.range(0, entityList.size()).forEach(i -> {
				update(entityList.get(i));
				if (i >= 1 && i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
			});
			batchSqlSession.flushStatements();
		} catch (Throwable e) {
			throw new RuntimeException("Error: Cannot execute insertOrUpdateBatch Method. Cause", e);
		}
		return true;
	}
}

package cn.px.base.core;

import cn.hutool.core.exceptions.ValidateException;
import cn.px.base.exception.BusinessException;
import cn.px.base.support.Pagination;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @author PXHLT
 * @version 2019年5月20日 下午3:19:19
 */
public interface BaseService<T extends BaseModel> {
    /**
     * 修改
     * @param record
     * @return
     */
    T update(T record) throws BusinessException, ValidateException;

    /**
     * 逻辑删除批量
     * @param ids
     * @param userId
     */
    void del(List<Long> ids, Long userId) throws BusinessException, ValidateException;

    /**
     * 逻辑删除单条
     * @param id
     * @param userId
     */
    void del(Long id, Long userId) throws BusinessException, ValidateException;

    /**
     * 物理删除
     * @param id
     */
    void delete(Long id) throws BusinessException, ValidateException;

    /**
     * 物理删除
     * @param t
     * @return
     */
    Integer deleteByEntity(T t) throws BusinessException, ValidateException;

    /**
     * 物理删除
     * @param columnMap
     * @return
     */
    Integer deleteByMap(Map<String, Object> columnMap) throws BusinessException, ValidateException;

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    T queryById(Long id);

    /**
     * 分页查询
     * @param params
     * @return
     */
    Pagination<T> query(Map<String, Object> params);

    /**
     * @param entity
     * @param rowBounds
     * @return
     */
    Pagination<T> query(T entity, Pagination<T> rowBounds);

    /**
     * @param params
     * @param page
     * @return
     */
    Pagination<T> query(Map<String,Object> params, Page<Long> page);


    /**
     * @param params
     * @param page
     * @return
     */
    Pagination<T> query(T params, Page<Long> page);

    /**
     * @param params
     * @return
     */
    List<T> queryList(Map<String, Object> params);

    /**
     * @param ids
     * @return
     */
    List<T> queryList(List<Long> ids);

    /**
     * @param ids
     * @param cls
     * @return
     */
    <K> List<K> queryList(List<Long> ids, Class<K> cls);

    /**
     * @param entity
     * @return
     */
    List<T> queryList(T entity);

    /**
     * @param params
     * @return
     */
    Pagination<T> queryFromDB(Map<String, Object> params);

    /**
     * @param entity
     * @param rowBounds
     * @return
     */
    Pagination<T> queryFromDB(T entity, Pagination<T> rowBounds);

    /**
     * @param params
     * @return
     */
    List<T> queryListFromDB(Map<String, Object> params);

    /**
     * @param entity
     * @return
     */
    List<T> queryListFromDB(T entity);

    /**
     * @param entity
     * @return
     */
    T selectOne(T entity);

    /**
     * @param entity
     * @return
     * @throws BusinessException
     * @throws ValidateException
     */
    Integer count(T entity) throws BusinessException, ValidateException;

    /**
     * @param param
     * @return
     */
    Integer count(Map<String, Object> param);

    /**
     * @param entityList
     * @return
     * @throws BusinessException
     * @throws ValidateException
     */
    boolean updateBatch(List<T> entityList) throws BusinessException, ValidateException;

    /**
     * @param entityList
     * @param batchSize
     * @return
     * @throws BusinessException
     * @throws ValidateException
     */
    boolean updateBatch(List<T> entityList, int batchSize) throws BusinessException, ValidateException;


}

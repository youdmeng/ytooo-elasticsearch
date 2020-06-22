package ml.ytooo.el.service;

import ml.ytooo.el.entity.CombinedQueryEO;
import ml.ytooo.el.entity.CombinedQueryResult;
import ml.ytooo.el.entity.Criteria;
import ml.ytooo.el.entity.ElQueryParam;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 查询实现
 * Created by Youdmeng on 2019/6/27 0027.
 **/
@Repository
public interface IDocumentDao<T> {


    /**
     * 删除指定 索引所有数据
     * @param index
     * @return
     */
    boolean deleteIndex(String index);


    /**
     * 设置index的mapping（设置数据类型和分词方式）
     *
     * @param index
     * @param type
     * @param mappingString
     * @return
     */
    boolean createIndexMapping(String index, String type, String mappingString);
    /**
     * 插入
     */
    boolean insert(T doc, String index, String type);

    /**
     * 清理缓存
     */
    boolean clearCache();
    /**
     * 获取指定索引信息
     */
    String getMapping(String indexName, String typeName);

    /*
     * 采用条件:数据量大,每页的size应该很大
     */
    CombinedQueryResult combinedQueryByScroll(String index, String type, CombinedQueryEO combinedQueryDto);

    /**
     * 组合查询+深入分页
     * 采用条件:数据量小,不会进行大翻页时
     */
    CombinedQueryResult completionSuggestionQueryFromSize(ElQueryParam param);

    /**
     * 创建索引
     * @return
     */
    boolean createIndex(String index);
    /**
     * 批量插入
     */
    boolean batchInsertDoc(List<T> list, String index, String type);

    /**
     * 替换
     */
    boolean replace(T doc, String index, String type);

    /**
     * 更新
     */
    boolean update(T doc, String index, String type);

    /**
     * 删除
     */
    boolean delete(long id, String index, String type);

    /**
     * 根据ID查询
     */
    JsonObject searchById(long id, String index, String type);

    /**
     * 条件查询
     */
    List<String> search(List<Criteria> criterias, String index, String type);


    /**
     * 清楚滚动ID
     */
    boolean clearScrollIds();
    /**
     * 清空type
     */
    boolean cleanAll(String index,String type);
}

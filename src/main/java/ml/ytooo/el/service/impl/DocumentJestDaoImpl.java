package ml.ytooo.el.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ml.ytooo.el.client.EsJestClient;
import ml.ytooo.el.entity.CombinedQueryResult;
import ml.ytooo.el.entity.Criteria;
import ml.ytooo.el.entity.CombinedQueryEO;
import ml.ytooo.el.entity.ElQueryParam;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ml.ytooo.el.service.IDocumentDao;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.ClearScroll;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.SearchScroll;
import io.searchbox.core.Update;
import io.searchbox.indices.ClearCache;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import io.searchbox.params.Parameters;
import io.searchbox.params.SearchType;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 查询实现
 * <p>
 * Created by Youdmeng on 2019/6/27 0027.
 **/
@Service
public class DocumentJestDaoImpl<T> implements IDocumentDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentJestDaoImpl.class);

    /**
     * es 读取超时时间
     */
    private static final String ES_READ_TIMEOUT = "es.read.timeout";

    /**
     * es 连接超时时间
     */
    private static final String ES_CONNECT_TIMEOUT = "es.connect.timeout";

    /**
     * 滚动ID key
     */
    private static final String SCORLL_ID_KEY = "_scroll_id";

    /**
     * 命中集 key
     */
    private static final String QUERY_HITS_KEY = "hits";

    /**
     * 命中集 suggest
     */
    private static final String SUGGEST = "suggest";

    /**
     * 命中集 song-suggest
     */
    private static final String SONG_SUGGEST = "song-suggest";

    /**
     * 数据源 song-suggest
     */
    private static final String SUGGEST_KEY = "options";

    /**
     * 数据源 key
     */
    private static final String SOURCE_KEY = "_source";

    /**
     * 搜索上下文的时间,用来支持该批次
     */
    private static final String SCROLL_ALIVE_TIME = "5m";

    private JestClient client = EsJestClient.getClient();

//    public static boolean main(String[] args) {
//        DocumentJestDaoImpl documentDao = new DocumentJestDaoImpl();
//
//        Document document = new Document();
//        document.setId(2);
//        document.setTitle("lalala");
//        document.setAuthor("youdmeng");
//        document.setPublishTime(new Date());
//        document.setTags(new String[] { "1", "2", "3", "4" });
//
//        //保存
//        documentDao.insert(document, DocumentDB.INDICES, DocumentDB.TYPE);
//        logger.error(documentDao.searchById(1, DocumentDB.INDICES, DocumentDB.TYPE));
//    }

    /**
     * 创建索引
     *
     * @param index 索引名称
     */
    @Override
    public boolean createIndex(String index) {
        boolean result = false;
        try {
            JestResult jestResult = client.execute(new CreateIndex.Builder(index).build());
            if (jestResult != null && jestResult.isSucceeded()) {
                result = true;
            }
        } catch (IOException e) {
            logger.error("EsJestClient-createIndex error", e);
            throw new RuntimeException("索引创建失败");
        }
        return result;
    }

    @Override
    public boolean deleteIndex(String index) {
        try {
            JestResult jr = client.execute(new DeleteIndex.Builder(index).build());
            if (null == jr) {
                return false;
            }
            return jr.isSucceeded();
        } catch (Exception e) {
            throw new RuntimeException("delete exception", e);
        }
    }

    /*
     *
     */
    public boolean createIndexMapping(String index, String type, String mappingString) {
        //mappingString为拼接好的json格式的mapping串
        //json反转义
        mappingString = mappingString.replaceAll("&quot;", "\"");
        mappingString = mappingString.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        mappingString = mappingString.replaceAll("&#40;", "(").replaceAll("&#41;", ")");
        mappingString = mappingString.replaceAll("&#39;", "'");
        PutMapping.Builder builder = new PutMapping.Builder(index, type, "{" + mappingString + "}");
        try {
            JestResult jestResult = client.execute(builder.build());
            if (null == jestResult) {
                return false;
            }
            if (!jestResult.isSucceeded()) {
                logger.error("settingIndexMapping error:{}" + jestResult.getErrorMessage());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取指定索引信息
     *
     * @param indexName
     * @param typeName
     * @return
     */
    @Override
    public String getMapping(String indexName, String typeName) {
        GetMapping.Builder builder = new GetMapping.Builder();
        builder.addIndex(indexName).addType(typeName);
        String res = null;
        try {
            JestResult result = client.execute(builder.build());
            if (result != null && result.isSucceeded()) {
                res = result.getSourceAsObject(JsonObject.class).toString();
            }
        } catch (Exception e) {
            logger.error("es get mapping Exception ", e);
            throw new RuntimeException("获取索引信息失败");
        }
        return res;
    }



    //  清理缓存
    public boolean clearCache() {
        JestResult jestResult = null;
        try {
            ClearCache clearCache = new ClearCache.Builder().build();
            jestResult = client.execute(clearCache);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == jestResult) {
            return false;
        }
        return jestResult.isSucceeded();
    }

    /**
     * 插入
     *
     * @param doc 文档
     * @return 是佛插入成功
     */
    @Override
    public boolean insert(T doc, String index, String type) {
        logger.info(doc.toString());
        try {
            DocumentResult result = client.execute(new Index.Builder(doc)
                    .index(index)
                    .type(type)
                    .refresh(true)
                    .build());
            logger.info(result.toString());
            return result.isSucceeded();

        } catch (Exception e) {
            throw new RuntimeException("insert exception", e);
        }
    }

    /**
     * 批量插入
     *
     * @author Youdmeng
     * Date 2019-06-27
     **/
    @Override
    public boolean batchInsertDoc(List<T> list, String index, String type) {
        //批量数据操作结果
        BulkResult br = null;
        try {
            Bulk.Builder bulkBuilder = new Bulk.Builder();
            //循环构造批量数据
            for (T t : list) {
                Index indexDoc = new Index.Builder(t).index(index).type(type).build();
                bulkBuilder.addAction(indexDoc);
            }
            br = client.execute(bulkBuilder.build());
        } catch (Exception e) {
            logger.error("ESJestClient.batchInsertDoc-exception", e);
        }
        if (null == br) {
            return false;
        }
        return br.isSucceeded();
    }


    /**
     * 组合查询文档+滚动分页
     * 采用条件:数据量大,每页的size应该很大
     *
     * @param index
     * @param type
     * @param combinedQueryDto
     * @return
     * @throws Exception
     */
    @Override
    public CombinedQueryResult combinedQueryByScroll(String index, String type, CombinedQueryEO combinedQueryDto) {
        CombinedQueryResult res = null;
        String scrollId = combinedQueryDto.getScrollId();
//        Map<String, Object> req = combinedQueryDto.getParams();
        JestResult result = null;
        try {
            //首次查询或滚动时间超时,则重新查询
            if (StringUtils.isEmpty(scrollId)) {
                //清除滚动ID
                clearScrollIds();
                //循环构造查询条件
                SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                //过滤器中,must表示查询出来的文档必须包含传入的值
                boolQueryBuilder.must(QueryBuilders.matchQuery(combinedQueryDto.getKey().toString(), combinedQueryDto.getValue()));

                searchSourceBuilder.query(boolQueryBuilder).size(combinedQueryDto.getSize());
                //构造查询条件,设置索引及类型
                Search search = new Search.Builder(searchSourceBuilder.toString())
                        .addIndex(index).addType(type).setParameter(Parameters.SCROLL, SCROLL_ALIVE_TIME)
                        .build();
                //第一次检索,拍下快照
                result = client.execute(search);
            } else {
                //只能向后滚动,不能向前滚动
                for (int i = 0; i < combinedQueryDto.getScrollTime(); i++) {
                    //直接滚动
                    SearchScroll scroll = new SearchScroll.Builder(scrollId, SCROLL_ALIVE_TIME).build();
                    result = client.execute(scroll);
                    //第一次滚动时,判断scrollId是否过期,过期抛出异常
                    if (i == 1) {
                        JsonObject errMsg = result.getJsonObject().getAsJsonObject("error");
                        if (errMsg != null) {
                            throw new RuntimeException(errMsg.getAsJsonArray("root_cause")
                                    .getAsString());
                        }
                    }
                }
            }
            if (result != null && !result.isSucceeded()) {
                throw new RuntimeException("ESJestClient ScrollQuery Fail...");
            }
            //构造返回查询返回结果
            res = buildResponse(result);
        } catch (IOException e) {
            logger.error("ESJestClient ScrollQuery IOException...", e);
            throw new RuntimeException("从ES查询DOC异常...");
        } catch (Exception e) {
            logger.error("ESJestClient ScrollQuery Exception...", e);
            throw new RuntimeException("从ES查询DOC异常...");
        }
        return res;
    }

    /**
     * 清楚滚动ID
     */
    public boolean clearScrollIds() {
        JestResult jestResult = null;
        ClearScroll clearScroll = new ClearScroll.Builder().build();
        try {
            jestResult =  client.execute(clearScroll);
        } catch (IOException e) {
            logger.error("ESJestClient Clean ScrollIds Exception...", e);
        }
        if (null == jestResult) {
            return false;
        }
        return jestResult.isSucceeded();
    }

    /**
     * 替换
     *
     * @param doc 文档
     * @return 是否执行成功
     */
    @Override
    public boolean replace(T doc, String index, String type) {
        return update(doc, index, type);
    }

    /**
     * 更新
     *
     * @param doc 文档
     * @return 是否更新成功
     */
    @Override
    public boolean update(T doc, String index, String type) {
        try {
            DocumentResult result = client.execute(new Update.Builder(doc)
                    .index(index)
                    .type(type)
                    .refresh(true)
                    .build());
            return result.isSucceeded();
        } catch (Exception e) {
            throw new RuntimeException("update exception", e);
        }
    }

    /**
     * 删除
     *
     * @param id 文档id
     * @return 是否执行成功
     */
    @Override
    public boolean delete(long id, String index, String type) {
        try {
            DocumentResult result = client.execute(new Delete.Builder(String.valueOf(id))
                    .index(index)
                    .type(type)
                    .build());
            return result.isSucceeded();
        } catch (Exception e) {
            throw new RuntimeException("delete exception", e);
        }
    }

    /**
     * 根据ID查询
     *
     * @param id id
     * @return 文档
     */
    @Override
    public JsonObject searchById(long id, String index, String type) {
        try {
            DocumentResult result = client.execute(
                    new Get.Builder(index, String.valueOf(id))
                            .type(type)
                            .build());
            return result.getJsonObject();
        } catch (Exception e) {
            throw new RuntimeException("searchById exception", e);
        }
    }

    /**
     * 条件查询
     *
     * @param criterias 条件列表
     * @return 结果集
     */
    @Override
    public List<String> search(List<Criteria> criterias, String index, String type) {
        try {
            SearchResult result = client.execute(new Search.Builder(buildSearch(criterias).toString())
                    // multiple index or types can be added.
                    .addIndex(index)
                    .addType(type)
                    .build());
            return result.getSourceAsStringList();

        } catch (Exception e) {
            throw new RuntimeException("search exception", e);
        }
    }

    private SearchSourceBuilder buildSearch(List<Criteria> criterias) {

        //指定查询的库表
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        if (criterias != null && !criterias.isEmpty()) {
            //构建查询条件必须嵌入filter中！
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            for (Criteria c : criterias) {
                boolQueryBuilder.filter(QueryBuilders.termQuery(c.getFieldName(), c.getFieldValue()));
            }

            searchSourceBuilder.query(boolQueryBuilder);
        }
        return searchSourceBuilder;
    }

    /**
     * 组合查询+深入分页
     * 采用条件:数据量小,不会进行大翻页时
     *
     * @param param
     * @return
     */
    @Override
    public CombinedQueryResult completionSuggestionQueryFromSize(ElQueryParam param) {
        CombinedQueryResult res = new CombinedQueryResult();
        JestResult result;
        List<T> list;
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            CompletionSuggestionBuilder completionSuggestionBuilder = new
                    CompletionSuggestionBuilder(param.getKey());
            completionSuggestionBuilder.prefix(param.getValue());

            SuggestBuilder suggestBuilder = new SuggestBuilder();
            suggestBuilder.addSuggestion(SONG_SUGGEST, completionSuggestionBuilder);
            searchSourceBuilder.suggest(suggestBuilder).size(param.getSize()).from(param.getFrom());
            Search search = new Search.Builder(searchSourceBuilder.toString())
                    .addIndex(param.getIndex()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH).addType(param.getType())
                    .setParameter(Parameters.SCROLL, SCROLL_ALIVE_TIME).build();
            result = client.execute(search);
            if (result != null && !result.isSucceeded()) {
                throw new RuntimeException("ESJestClient FromSizeQuery Fail...");
            }
            list = null;
            JsonObject hitsObj = result.getJsonObject().getAsJsonObject(SUGGEST);
            JsonArray hitsArray = hitsObj.getAsJsonArray(SONG_SUGGEST);
            JsonArray jsonElements = new JsonArray();
            for (JsonElement element : hitsArray) {
                JsonObject object = element.getAsJsonObject();
                JsonArray temp = (JsonArray) object.get(SUGGEST_KEY);

                jsonElements.addAll(temp);
            }

            list = new Gson().fromJson(jsonElements, new TypeToken<List<T>>() {
            }.getType());
        } catch (Exception e) {
            logger.error("ESJestClient FromSizeQuery Exception...", e);
            throw new RuntimeException("从ES查询DOC异常...");
        }
        res.setResList(list);
        return res;
    }

    /**
     * 查询结果构造器
     *
     * @param result
     * @return
     */
    private CombinedQueryResult buildResponse(JestResult result) {
        CombinedQueryResult res = new CombinedQueryResult();
        JsonObject jsonObject = result.getJsonObject();
        JsonArray jsonElements = jsonObject.getAsJsonObject(QUERY_HITS_KEY).getAsJsonArray(QUERY_HITS_KEY);
        List<T> list = new ArrayList<T>();
        Gson gson = new Gson();
        for (JsonElement jsonElement : jsonElements) {
            list.add((T) gson.fromJson(jsonElement, Map.class).get(SOURCE_KEY));
        }
        String scrollId = jsonObject.getAsJsonPrimitive(SCORLL_ID_KEY).getAsString();
        //不为空,才算文档查询成功
        if (list.size() > 0) {
            res.setResList(list);
            res.setScrollId(scrollId);
        } else
            throw new RuntimeException("ESJestClient ScrollQuery Fail...");
        return res;
    }

    @Override
    public boolean cleanAll(String index,String type) {
        JestResult result = null;

        try {
            //构造查询条件,设置索引及类型
            DeleteByQuery deleteByQuery = new DeleteByQuery.Builder(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()).toString())
                    .addIndex(index)
                    .addType(type)
                    .build();
            result = client.execute(deleteByQuery);
        } catch (IOException e) {
            logger.error("清空失败", e);
        }
        if (null == result) {
            return false;
        }
        return result.isSucceeded();

    }
}

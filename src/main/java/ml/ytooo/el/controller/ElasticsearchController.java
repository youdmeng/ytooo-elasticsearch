package ml.ytooo.el.controller;

import ml.ytooo.el.entity.CombinedQueryResult;
import ml.ytooo.el.entity.Document;
import ml.ytooo.el.entity.ElQueryParam;
import ml.ytooo.el.entity.MappingSetter;
import ml.ytooo.el.enums.DocumentDB;
import ml.ytooo.el.service.IDocumentDao;
import ml.ytooo.el.service.impl.DocumentJestDaoImpl;
import ml.ytooo.utils.Response;
import ml.ytooo.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ES操作
 * Created by Youdmeng on 2019/6/27 0027.
 **/
@RestController
@Api(description = "ElasticsearchController| ES操作")
@RequestMapping("/elasticsearchController")
public class ElasticsearchController {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchController.class);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        IDocumentDao<Document> dao = new DocumentJestDaoImpl<Document>();
        Document d = new Document();
        d.setAuthor("ytooo");
        d.setTitle("联动测试");
        d.setId(88);
        dao.insert(d, DocumentDB.ADC, DocumentDB.DEFINE_PARTS);
        return "success";
    }

    /**
     * 搜索引擎快速查询
     *
     * @param elQueryParam
     * @return
     * @author Youdmeng
     * Date 2019-07-03
     **/
    @RequestMapping(value = "/shutByName", method = RequestMethod.POST)
    @ApiOperation(value = "搜索引擎快速查询")
    public Response shutByName(@RequestBody ElQueryParam elQueryParam) {
        try {
            IDocumentDao dao = new DocumentJestDaoImpl();
            CombinedQueryResult combinedQueryResult = dao.completionSuggestionQueryFromSize(elQueryParam);
            List<Map<String, Object>> list = combinedQueryResult.getResList();

            List<Map> result = new ArrayList<>();
            for (Map<String, Object> stringMap : list) {
                Map temp = (Map) stringMap.get("_source");
                result.add(temp);
            }
            return Result.success(result);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 为指定索引创建mapping
     * @param mapping
     * @return
     * @author Youdmeng
     * Date 2019-07-03
     **/
    @ApiOperation(value = "为指定索引创建mapping")
    @RequestMapping(value = "/setMapping", method = RequestMethod.POST)
    public Response setMapping(@Valid @RequestBody MappingSetter mapping) {
        try {
            IDocumentDao dao = new DocumentJestDaoImpl<>();
            if (!dao.createIndexMapping(mapping.getIndex(), mapping.getType(), mapping.getMappingString())) {
                return Result.error();
            }

            return Result.success();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }

    }

//    /**
//     *   candidateParts 数据重置
//     * @return
//     * @author Youdmeng
//     * Date 2019-07-03
//     **/
//    @ApiOperation(value = " candidateParts 数据重置")
//    @RequestMapping(value = "/candidatePartsInsert", method = RequestMethod.GET)
//    public Response batchInsert() {
//
//        try {
//            IDocumentDao<OeAndNameEO> dao = new DocumentJestDaoImpl<OeAndNameEO>();
//            if (!dao.cleanAll("adc_candidate_parts", "items")) {
//                return Result.error("清空文档失败");
//            }
//            dao.batchInsertDoc(oeNameDAO.queryOes(), "adc_candidate_parts", "items");
//            return Result.success();
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return Result.error(e.getMessage());
//        }
//    }
//    /**
//     *   defineParts 数据重置
//     * @return
//     * @author Youdmeng
//     * Date 2019-07-03
//     **/
//    @ApiOperation(value = " defineParts 数据重置")
//    @RequestMapping(value = "/definePartsInsert", method = RequestMethod.GET)
//    public Response definePartsInsert() {
//        try {
//            IDocumentDao<DefinePartSimplify> dao = new DocumentJestDaoImpl<DefinePartSimplify>();
//            if (!dao.cleanAll("adc_define_parts", "items")) {
//                return Result.error("清空文档失败");
//            }
//            dao.batchInsertDoc(oeNameDAO.queryDefineParts(), "adc_define_parts", "items");
//            return Result.success();
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return Result.error(e.getMessage());
//        }
//
//    }
//
//    /**
//     * 创建索引
//     * @return
//     * @author Youdmeng
//     * Date 2019-07-03
//     **/
//    @ApiOperation(value = " 创建索引")
//    @RequestMapping(value = "/createIndex", method = RequestMethod.GET)
//    public Response createIndex(@RequestParam String index) {
//        try {
//            IDocumentDao dao = new DocumentJestDaoImpl();
//            if (!dao.createIndex(index)) {
//                return Result.error("创建失败");
//            }
//            return Result.success();
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return Result.error(e.getMessage());
//        }
//    }
//
//    /**
//     *   vinInfo 数据重置
//     * @return
//     * @author Youdmeng
//     * Date 2019-07-03
//     **/
//    @ApiOperation(value = " vinInfo 数据重置")
//    @RequestMapping(value = "/vinInfoInsert", method = RequestMethod.GET)
//    public Response vinInfoInsert() {
//
//        try {
//            IDocumentDao<VinInfo> dao = new DocumentJestDaoImpl<VinInfo>();
//            if (!dao.cleanAll("adc_vin_info", "items")) {
//                return Result.error("清空文档失败");
//            }
//
//            dao.batchInsertDoc(oeNameDAO.queryVinInfo(), "adc_vin_info", "items");
//            return Result.success();
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return Result.error(e.getMessage());
//        }
//
//    }

}

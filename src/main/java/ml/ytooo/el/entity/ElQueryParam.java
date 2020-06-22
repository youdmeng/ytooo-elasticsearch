package ml.ytooo.el.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Youdmeng on 2019/7/2 0002.
 */
@Data
public class ElQueryParam {

    private String index;

    private String type;

    /**
     * 分页大小
     */
    private int size = 5;

    /**
     * 从第几条数据开始
     */
    private int from = 0;

    private String key;

    private String value;

}

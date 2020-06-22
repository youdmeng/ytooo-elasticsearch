package ml.ytooo.el.entity;

import lombok.Data;

import java.util.Map;

@Data
public class CombinedQueryEO {
    /**
     * 滚动ID
     */
    private String scrollId;

    /**
     * 滚动次数
     */
    private int scrollTime;

    /**
     * 分页大小
     */
    private int size;

    /**
     * 从第几条数据开始
     */
    private int from;

    private String key;

    private String value;
}

package ml.ytooo.el.entity;

import lombok.Data;

import java.util.List;

@Data
public class CombinedQueryResult<T> {
    /**
     * 当前批次结果
     */
    List<T> resList;

    /**
     * 滚动ID
     */
    String scrollId;
}

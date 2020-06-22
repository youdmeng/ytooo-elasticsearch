package ml.ytooo.el.entity;

import java.util.List;

public class CombinedQueryResult<T> {
    /**
     * 当前批次结果
     */
    List<T> resList;
    /**
     * 滚动ID
     */
    String scrollId;

    public List<T> getResList() {
        return resList;
    }

    public void setResList(List<T> resList) {
        this.resList = resList;
    }

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }
}

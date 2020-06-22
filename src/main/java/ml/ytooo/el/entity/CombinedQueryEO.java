package ml.ytooo.el.entity;

import java.util.Map;

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

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }

    public int getScrollTime() {
        return scrollTime;
    }

    public void setScrollTime(int scrollTime) {
        this.scrollTime = scrollTime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * 属性get
     */
    public String getKey() {
        return key;
    }

    /**
     * 属性set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 属性get
     */
    public String getValue() {
        return value;
    }

    /**
     * 属性set
     */
    public void setValue(String value) {
        this.value = value;
    }
}

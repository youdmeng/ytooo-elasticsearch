package com.ytooo.el.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * //TODO 添加类/接口功能描述 *
 * Created by Youdmeng on 2019/7/2 0002.
 */
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

    /**
     * 属性get
     */
    public String getIndex() {
        return index;
    }

    /**
     * 属性set
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * 属性get
     */
    public String getType() {
        return type;
    }

    /**
     * 属性set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 属性get
     */
    public int getSize() {
        return size;
    }

    /**
     * 属性set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * 属性get
     */
    public int getFrom() {
        return from;
    }

    /**
     * 属性set
     */
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

package ml.ytooo.el.entity;

import java.util.Arrays;
import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * 测试bean
 * Created by Youdmeng on 2019/6/27 0027.
 */
public class Document implements Cloneable {
    @JestId
    private long id;
    private String title;
    private String author;
    private String[] tags;
    private Date publishTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", publishTime=" + publishTime +
                '}';
    }
}

package org.aicha.servlet;

import java.util.List;

public class TaskFilterRequest {
    private List<String> tagsIds;
    private String year;
    private String month;

    public List<String> getTagsIds() {
        return tagsIds;
    }

    public void setTagsIds(List<String> tagsIds) {
        this.tagsIds = tagsIds;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}

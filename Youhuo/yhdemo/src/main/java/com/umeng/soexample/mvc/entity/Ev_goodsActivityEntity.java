package com.umeng.soexample.mvc.entity;

import java.util.List;

public class Ev_goodsActivityEntity {
    private List<Children> child;
    private String title;

    public Ev_goodsActivityEntity(List<Children> child, String title) {
        this.child = child;
        this.title = title;
    }

    public List<Children> getChild() {
        return child;
    }

    public void setChild(List<Children> child) {
        this.child = child;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Children {
        public Children(String content) {
            this.content = content;
        }

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

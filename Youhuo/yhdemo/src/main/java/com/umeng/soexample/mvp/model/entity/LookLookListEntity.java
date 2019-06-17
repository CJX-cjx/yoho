package com.umeng.soexample.mvp.model.entity;

import java.util.List;

public class LookLookListEntity extends BaseEntity {
    private List<Banner> banner;
    private List<Values> values;

    public class Banner {
        private String recommend_id;
        private String recommend_name;
        private String recommend_url;
        private String recommend_path;

        public String getRecommend_id() {
            return recommend_id;
        }

        public void setRecommend_id(String recommend_id) {
            this.recommend_id = recommend_id;
        }

        public String getRecommend_name() {
            return recommend_name;
        }

        public void setRecommend_name(String recommend_name) {
            this.recommend_name = recommend_name;
        }

        public String getRecommend_url() {
            return recommend_url;
        }

        public void setRecommend_url(String recommend_url) {
            this.recommend_url = recommend_url;
        }

        public String getRecommend_path() {
            return recommend_path;
        }

        public void setRecommend_path(String recommend_path) {
            this.recommend_path = recommend_path;
        }
    }

    public class Values {
        private String see_edt_icon;
        private String see_edt_name;
        private String see_edt_tag;
        private String see_edt_values;
        private String see_news_collection;
        private String see_news_img;
        private String see_news_num;
        private String see_news_time;
        private String see_news_title;
        private String see_news_values;

        public String getSee_edt_icon() {
            return see_edt_icon;
        }

        public void setSee_edt_icon(String see_edt_icon) {
            this.see_edt_icon = see_edt_icon;
        }

        public String getSee_edt_name() {
            return see_edt_name;
        }

        public void setSee_edt_name(String see_edt_name) {
            this.see_edt_name = see_edt_name;
        }

        public String getSee_edt_tag() {
            return see_edt_tag;
        }

        public void setSee_edt_tag(String see_edt_tag) {
            this.see_edt_tag = see_edt_tag;
        }

        public String getSee_edt_values() {
            return see_edt_values;
        }

        public void setSee_edt_values(String see_edt_values) {
            this.see_edt_values = see_edt_values;
        }

        public String getSee_news_collection() {
            return see_news_collection;
        }

        public void setSee_news_collection(String see_news_collection) {
            this.see_news_collection = see_news_collection;
        }

        public String getSee_news_img() {
            return see_news_img;
        }

        public void setSee_news_img(String see_news_img) {
            this.see_news_img = see_news_img;
        }

        public String getSee_news_num() {
            return see_news_num;
        }

        public void setSee_news_num(String see_news_num) {
            this.see_news_num = see_news_num;
        }

        public String getSee_news_time() {
            return see_news_time;
        }

        public void setSee_news_time(String see_news_time) {
            this.see_news_time = see_news_time;
        }

        public String getSee_news_title() {
            return see_news_title;
        }

        public void setSee_news_title(String see_news_title) {
            this.see_news_title = see_news_title;
        }

        public String getSee_news_values() {
            return see_news_values;
        }

        public void setSee_news_values(String see_news_values) {
            this.see_news_values = see_news_values;
        }
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }
}

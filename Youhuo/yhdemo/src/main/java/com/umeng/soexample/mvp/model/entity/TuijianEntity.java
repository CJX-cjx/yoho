package com.umeng.soexample.mvp.model.entity;

import java.util.List;

public class TuijianEntity extends BaseEntity {
    private List<Category_values> category;
    private List<Recommend_values> recommend;

    public class Category_values {
        private String category_id;
        private String category_img_path;
        private String category_name;
        private String menu_id;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_img_path() {
            return category_img_path;
        }

        public void setCategory_img_path(String category_img_path) {
            this.category_img_path = category_img_path;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getMenu_id() {
            return menu_id;
        }

        public void setMenu_id(String menu_id) {
            this.menu_id = menu_id;
        }
    }

    public class Recommend_values {
        private String recommend_id;
        private String recommend_name;
        private String recommend_path;
        private String recommend_url;

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

        public String getRecommend_path() {
            return recommend_path;
        }

        public void setRecommend_path(String recommend_path) {
            this.recommend_path = recommend_path;
        }

        public String getRecommend_url() {
            return recommend_url;
        }

        public void setRecommend_url(String recommend_url) {
            this.recommend_url = recommend_url;
        }
    }

    public List<Category_values> getCategory() {
        return category;
    }

    public void setCategory(List<Category_values> category) {
        this.category = category;
    }

    public List<Recommend_values> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<Recommend_values> recommend) {
        this.recommend = recommend;
    }
}

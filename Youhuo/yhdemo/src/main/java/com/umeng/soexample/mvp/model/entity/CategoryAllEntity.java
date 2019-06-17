package com.umeng.soexample.mvp.model.entity;

import java.util.List;

public class CategoryAllEntity extends BaseEntity {

    private List<Values> values;

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public class Values {
        private String category_id;
        private String menu_id;
        private String category_name;
        private String category_img_path;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getMenu_id() {
            return menu_id;
        }

        public void setMenu_id(String menu_id) {
            this.menu_id = menu_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCategory_img_path() {
            return category_img_path;
        }

        public void setCategory_img_path(String category_img_path) {
            this.category_img_path = category_img_path;
        }
    }

}

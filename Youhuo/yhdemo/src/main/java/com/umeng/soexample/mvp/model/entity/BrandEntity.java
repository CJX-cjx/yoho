package com.umeng.soexample.mvp.model.entity;

import java.util.List;

public class BrandEntity extends BaseEntity {

    private List<Values> values;

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public class Values {
        private String brand_bg;
        private String brand_icon;
        private String brand_id;
        private String brand_letter;
        private String brand_name;
        private String brand_time;
        private String hot_tag;
        private String menu_id;

        public String getBrand_bg() {
            return brand_bg;
        }

        public void setBrand_bg(String brand_bg) {
            this.brand_bg = brand_bg;
        }

        public String getBrand_icon() {
            return brand_icon;
        }

        public void setBrand_icon(String brand_icon) {
            this.brand_icon = brand_icon;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_letter() {
            return brand_letter;
        }

        public void setBrand_letter(String brand_letter) {
            this.brand_letter = brand_letter;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getBrand_time() {
            return brand_time;
        }

        public void setBrand_time(String brand_time) {
            this.brand_time = brand_time;
        }

        public String getHot_tag() {
            return hot_tag;
        }

        public void setHot_tag(String hot_tag) {
            this.hot_tag = hot_tag;
        }

        public String getMenu_id() {
            return menu_id;
        }

        public void setMenu_id(String menu_id) {
            this.menu_id = menu_id;
        }
    }


}

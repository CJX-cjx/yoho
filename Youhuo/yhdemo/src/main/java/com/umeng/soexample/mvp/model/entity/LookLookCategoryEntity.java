package com.umeng.soexample.mvp.model.entity;

import java.util.List;

public class LookLookCategoryEntity extends BaseEntity {
    private List<Values> values;

    public class Values {
        private String see_category_id;
        private String see_category_name;

        public String getSee_category_id() {
            return see_category_id;
        }

        public void setSee_category_id(String see_category_id) {
            this.see_category_id = see_category_id;
        }

        public String getSee_category_name() {
            return see_category_name;
        }

        public void setSee_category_name(String see_category_name) {
            this.see_category_name = see_category_name;
        }
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }
}

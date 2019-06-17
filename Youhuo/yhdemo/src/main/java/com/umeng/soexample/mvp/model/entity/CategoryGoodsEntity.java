package com.umeng.soexample.mvp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class CategoryGoodsEntity extends BaseEntity {
    private List<Values> values;

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public class Values implements MultiItemEntity {
        private String category_id;
        private String image_path;
        private String name;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int getItemType() {
            return 0;
        }
    }
}

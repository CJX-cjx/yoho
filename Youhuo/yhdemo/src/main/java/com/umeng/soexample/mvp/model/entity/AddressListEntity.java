package com.umeng.soexample.mvp.model.entity;

import java.util.List;

public class AddressListEntity extends BaseEntity {
    private List<Values> values;

    public static class Values {
        private String address_area;
        private String address_default;
        private String address_detail;
        private String address_id;
        private String address_tag;
        private String phone;
        private String user_id;
        private String user_name;

        public String getAddress_area() {
            return address_area;
        }

        public void setAddress_area(String address_area) {
            this.address_area = address_area;
        }

        public String getAddress_default() {
            return address_default;
        }

        public void setAddress_default(String address_default) {
            this.address_default = address_default;
        }

        public String getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(String address_detail) {
            this.address_detail = address_detail;
        }

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getAddress_tag() {
            return address_tag;
        }

        public void setAddress_tag(String address_tag) {
            this.address_tag = address_tag;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }
}

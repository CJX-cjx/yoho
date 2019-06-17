package com.umeng.soexample.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class BrandActivityEntity extends BaseEntity {
    private List<Values> values;
    private List<Banner> banner;

    public static class Banner {
        private String recommend_url;

        public String getRecommend_url() {
            return recommend_url;
        }

        public void setRecommend_url(String recommend_url) {
            this.recommend_url = recommend_url;
        }
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanners(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public static class Values {
        private String brand_bg;
        private String brand_icon;
        private String brand_id;
        private String brand_letter;
        private String brand_name;
        private String brand_time;
        private String hot_tag;
        private String menu_id;
        private List<Goods> goods;

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

        public List<Goods> getGoods() {
            return goods;
        }

        public void setGoods(List<Goods> goods) {
            this.goods = goods;
        }

        public static class Goods implements Serializable {
            private String brand_id;
            private String category_id;
            private String detail_flag;
            private String discount_detail;
            private String follow;
            private String goods_discount_price;
            private String goods_guarantee;
            private String goods_id;
            private String goods_img_id;
            private String goods_img_path;
            private String goods_name;
            private String goods_original_price;
            private String goods_popularity;
            private String shop_id;
            private String time;

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getDetail_flag() {
                return detail_flag;
            }

            public void setDetail_flag(String detail_flag) {
                this.detail_flag = detail_flag;
            }

            public String getDiscount_detail() {
                return discount_detail;
            }

            public void setDiscount_detail(String discount_detail) {
                this.discount_detail = discount_detail;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getGoods_discount_price() {
                return goods_discount_price;
            }

            public void setGoods_discount_price(String goods_discount_price) {
                this.goods_discount_price = goods_discount_price;
            }

            public String getGoods_guarantee() {
                return goods_guarantee;
            }

            public void setGoods_guarantee(String goods_guarantee) {
                this.goods_guarantee = goods_guarantee;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_img_id() {
                return goods_img_id;
            }

            public void setGoods_img_id(String goods_img_id) {
                this.goods_img_id = goods_img_id;
            }

            public String getGoods_img_path() {
                return goods_img_path;
            }

            public void setGoods_img_path(String goods_img_path) {
                this.goods_img_path = goods_img_path;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_original_price() {
                return goods_original_price;
            }

            public void setGoods_original_price(String goods_original_price) {
                this.goods_original_price = goods_original_price;
            }

            public String getGoods_popularity() {
                return goods_popularity;
            }

            public void setGoods_popularity(String goods_popularity) {
                this.goods_popularity = goods_popularity;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public String getBrand_bg() {
            return brand_bg;
        }

        public void setBrand_bg(String brand_bg) {
            this.brand_bg = brand_bg;
        }
    }
}

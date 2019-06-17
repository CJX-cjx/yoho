package com.umeng.soexample.mvp.model.entity;

import java.util.List;

public class CommunityEntity extends BaseEntity {
    private List<Values> values;
    private List<Banner> banner;

    public class Banner {
        private String recommend_url;

        public String getRecommend_url() {
            return recommend_url;
        }

        public void setRecommend_url(String recommend_url) {
            this.recommend_url = recommend_url;
        }
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public class Values {
        private String collection;
        private String community_id;
        private String community_time;
        private String community_values;
        private String fabulous;
        private String hot_flag;
        private String tag;
        private String title;
        private String user_id;
        private List<Imgs> imgs;

        public class Imgs {
            private String community_id;
            private String community_img_id;
            private String img_path;

            public String getCommunity_id() {
                return community_id;
            }

            public void setCommunity_id(String community_id) {
                this.community_id = community_id;
            }

            public String getCommunity_img_id() {
                return community_img_id;
            }

            public void setCommunity_img_id(String community_img_id) {
                this.community_img_id = community_img_id;
            }

            public String getImg_path() {
                return img_path;
            }

            public void setImg_path(String img_path) {
                this.img_path = img_path;
            }
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getCollection() {
            return collection;
        }

        public void setCollection(String collection) {
            this.collection = collection;
        }

        public String getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(String community_id) {
            this.community_id = community_id;
        }

        public String getCommunity_time() {
            return community_time;
        }

        public void setCommunity_time(String community_time) {
            this.community_time = community_time;
        }

        public String getCommunity_values() {
            return community_values;
        }

        public void setCommunity_values(String community_values) {
            this.community_values = community_values;
        }

        public String getFabulous() {
            return fabulous;
        }

        public void setFabulous(String fabulous) {
            this.fabulous = fabulous;
        }

        public String getHot_flag() {
            return hot_flag;
        }

        public void setHot_flag(String hot_flag) {
            this.hot_flag = hot_flag;
        }

        public List<Imgs> getImgs() {
            return imgs;
        }

        public void setImgs(List<Imgs> imgs) {
            this.imgs = imgs;
        }
    }
}

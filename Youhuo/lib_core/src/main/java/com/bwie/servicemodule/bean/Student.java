package com.bwie.servicemodule.bean;

import com.google.gson.annotations.Expose;
import com.orm.SugarRecord;
import java.io.Serializable;
//程序本地数据库中多Student表
//Serializable可序列化标签->Activity间传递实体类对象使用
//@Expose->gson与sugar公用一个实体类

public class Student extends SugarRecord implements Serializable {

    @Expose
    private String name;
    @Expose
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

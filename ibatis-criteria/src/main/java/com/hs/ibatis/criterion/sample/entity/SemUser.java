package com.hs.ibatis.criterion.sample.entity;

import com.hs.ibatis.criterion.beans.BaseIbtsCriteria;

public class SemUser extends BaseIbtsCriteria implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;


    private String userName;


    private Integer age;


    private String address;


    public Integer getId(){
        return id;
    }

    public String getUserName(){
        return userName;
    }

    public Integer getAge(){
        return age;
    }

    public String getAddress(){
        return address;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public void setAddress(String address){
        this.address = address;
    }

}


package com.nice.model;

import java.io.Serializable;

/**
 * Created by jiao on 2016/1/21.
 */
public class NiceQuestion implements Serializable{

    public String name;
    public String time;

    public NiceQuestion(String name, String time){
        this.name = name;
        this.time = time;
    }

}

package com.nice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2016/2/21.
 */
public class SignInModel implements Serializable{

    public String shId;
    public String silongitude;
    public String silatitude;
    public String siadd;
    public String sipicurl;
    public String siTime;
    public List<FileModel> files = new ArrayList<>();

    public SignInModel(String shId, String silongitude, String silatitude, String siadd, String sipicurl, String siTime,List<FileModel> files) {
        this.shId = shId;
        this.silongitude = silongitude;
        this.silatitude = silatitude;
        this.siadd = siadd;
        this.sipicurl = sipicurl;
        this.siTime = siTime;
        this.files = files;
    }

    public SignInModel(String shId, String silongitude, String silatitude, String siadd, String sipicurl, String siTime) {
        this.shId = shId;
        this.silongitude = silongitude;
        this.silatitude = silatitude;
        this.siadd = siadd;
        this.sipicurl = sipicurl;
        this.siTime = siTime;
    }

    public SignInModel() {

    }
}

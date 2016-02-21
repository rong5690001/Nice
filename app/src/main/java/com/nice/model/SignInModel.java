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

}

package com.nice.model;

import java.io.Serializable;

/**
 * 签到表
 * Created by jiao on 2016/1/26.
 */
public class NicetUserSign implements Serializable {
    public long siId;         //签到编号  900200000000001  自增加+1
    public long shId;         //问卷编号
    public String silongitude;//经度
    public String silatitude; //纬度
    public String siadd;      //详细地址
    public String sipicurl;   //图片
    public String siTime;     //记录生成时间  Default CURRENT DATATIME

}

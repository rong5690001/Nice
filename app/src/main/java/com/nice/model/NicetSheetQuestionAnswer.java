package com.nice.model;

import java.io.Serializable;

/**
 * 问题回答
 * Created by jiao on 2016/1/26.
 */
public class NicetSheetQuestionAnswer implements Serializable {
    public long qaId;           //回答编号  400500000000001
    public long sqId;           //问题编号
    public String qaValue;      //答案
    public int qoStatus;        //状态  1:有效0:无效-1:删除
    public String rbTime;   //记录生成时间  Default CURRENT DATATIME
    public String ruTime;   //记录变更时间  Default CURRENT DATATIME
}

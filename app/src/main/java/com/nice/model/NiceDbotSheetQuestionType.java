package com.nice.model;

import java.io.Serializable;

/**
 * 问卷问题类型表
 * Created by jiao on 2016/1/26.
 */
public class NiceDbotSheetQuestionType implements Serializable {
    public long stId;           //类型ID  400300000000001
    public String stName;       //类型名称
    public int stStatus;        //状态  1:有效0:无效-1:删除
    public String rbTime;         //记录生成时间  Default CURRENT DATATIME
    public String ruTime;         //记录变更时间  Default CURRENT DATATIME
}

package com.nice.model;

import java.io.Serializable;

/**
 * 问卷问题选项表
 * Created by jiao on 2016/1/26.
 */
public class NicetSheetQuestionOption implements Serializable {
    public long qoId;            //选项编号  400400000000001
    public long sqId;            //问题编号
    public String qoText;        //选项显示
    public String qoValue;       //选项值
    public int qoIsDefault;      //是否默认  1:默认0:不默认
    public int qoSequence;       //选项序号
    public int qoStatus;         //状态  1:有效0:无效-1:删除
    public String rbTime;   //记录生成时间  Default CURRENT DATATIME
    public String ruTime;   //记录变更时间  Default CURRENT DATATIME
}

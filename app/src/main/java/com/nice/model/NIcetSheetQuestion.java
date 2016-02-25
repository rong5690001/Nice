package com.nice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 问卷问题表
 * Created by jiao on 2016/1/26.
 */
public class NIcetSheetQuestion implements Serializable {
    public long sqId;             //问题编号  400300000000001
    public long shId;             //问卷编号
    public long qgId;             //问题分组编号
    public int sqSequence;        //问题序号
    public long sqType;           //问题类别  P.S.
    public String sqTitle;        //问题标题
    public String sqDescription;  //问题说明
    public int sqStatus;          //状态  1:有效0:无效-1:删除
    public boolean isEnable = true;      //能否编辑
    public String rbTime;         //记录生成时间  Default CURRENT DATATIME
    public String ruTime;         //记录变更时间  Default CURRENT DATATIME

    public List<NiceSheetQuestionOption> SheetQuestionOption; //选项
}

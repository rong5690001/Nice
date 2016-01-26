package com.nice.model;

import java.io.Serializable;

/**
 * 问题组
 * Created by jiao on 2016/1/26.
 */
public class NicetSheetQuestionGroup implements Serializable {
    public long qgId;       //问题组编号  400200000000001
    public long shId;       //问卷编号
    public int qgSequence;  //组顺序
    public String qgCode;   //组编码
    public String qgName;   //组名称
    public int qgStatus;    //状态  1:有效0:无效-1:删除
    public String rbTime;   //记录生成时间  Default CURRENT DATATIME
    public String ruTime;   //记录变更时间  Default CURRENT DATATIME
}

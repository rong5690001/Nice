package com.nice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 问卷主体信息
 * Created by jiao on 2016/1/26.
 */
public class NicetSheet implements Serializable {
    public long shId;           //问卷编号  400100000000001
    public long oiId;           //订单编号
    public String shCode;       //问卷编码
    public String shName;       //问卷名称
    public int shVersion;       //版本号
    public int shStatus;        //状态  1:有效0:无效-1:删除
    public int shFinishStatus;  //问卷完成状态
    public String shDescription;//问卷备注
    public long shEstablishId;  //问卷设立者
    public String rbTime;       //记录生成时间  Default CURRENT DATATIME
    public String ruTime;       //记录变更时间  Default CURRENT DATATIME
    public int shQaaStatus;     //审核状态  0未审核 1 已通过 2 未通过

    public List<NicetSheetQuestionGroup> SheetQuestionGroup; //问题组
}

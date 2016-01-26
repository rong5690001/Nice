package com.nice.model;

import java.io.Serializable;

/**
 * 订单信息
 * Created by jiao on 2016/1/27.
 */
public class NicetOrderInfo implements Serializable {
    public long oiId;                //订单编号  3001+RYRYRMRMRDRDRRR
    public long piId;                //项目编号
    public String oiInternalCode;    //订单编码(内部)
    public String oiExternalCode;    //订单编码(外部)
    public String oiName;            //订单名称
    public String oiConName;         //联系人
    public String oiConPhone;        //联系人电话
    public String oiConEmail;        //联系人Email
    public String oiStartDate;       //开始日期
    public String oiEndDate;         //结束日期
    public String oiLocation;        //订单位置
    public long oiExecuteId;         //订单执行者  订单(问卷)分配的执行人员
    public int oiStatus;             //订单状态  订单与问卷的当前执行状态 1未分配 2已分配 3已下载 4已上传 5 已退回 -1已删除
    public String oiDescription;     //订单描述
    public String oiMemo;            //订单备注  备用
    public String rbTime;            //记录生成时间  Default CURRENT DATATIME
    public String ruTime;            //记录变更时间  Default CURRENT DATATIME
    public long oidlId;              //代理协调员
    public String oiDesc1;           //备用
    public String oiDesc2;           //备用
}

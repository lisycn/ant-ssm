import React from "react";
import {Row, Col, Tooltip, message, Button, Modal, Icon, Transfer, Form } from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, parseDate, filterObject,formatDate} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";
import TaskMonitorSearchForm from "./TaskMonitorSearchForm";
import TaskMonitorHistoryForm from "./TaskMonitorHistoryForm";

import "./style/style.less";

/**
 * 任务监控
 */
class TaskMonitorManage extends React.Component{
  constructor(props) {
    super(props);
    this.state = {
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      tdTableReload: true,
      tdTableParam: {},
      modalTitle: "任务历史",
      modalVisible: false,
      confirmLoading: false,
      historyData: []
    }
  }
  //行选择事件
  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys
    });
  }
  //取得任务状态
  getOperatetype(operatetype){
    //01-申请中（暂存）   02-审批通过   03-审批退回  04-撤回  05-取消  06-退回  99-完毕
    if(operatetype == '01'){
      return '申请中';
    }else if(operatetype == '02'){
      return '审批通过';
    }else if(operatetype == '03'){
      return '审批退回';
    }else if(operatetype == '04'){
      return '撤回';
    }else if(operatetype == '05'){
      return '取消';
    }else if(operatetype == '06'){
      return '退回';
    }else if(operatetype == '99'){
      return '完毕';
    }
  }
  getFlowstatus(flowstatus){
    if(flowstatus == '01'){
      return '申请中';
    }else if(flowstatus == '02'){
      return '审核中';
    }else if(flowstatus == '03'){
      return '作废';
    }else if(flowstatus == '06'){
      return '退回';
    }else if(flowstatus == '99'){
      return '完毕';
    }
  }
  //列表【历史】按钮事件
  handleHisLinkClick(text, record, key){
    let obj = this;
    let opt = {
      url: ylagent.workflow.taskMonitor.findTrace,
      type: "POST",
      dataType: "json",
      data: {flowbusinesstoken: record.flowbusinesstoken}
    }
    callAjax(opt, function (result) {
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        const historyData = [];
        result.rspData.list.map(function(val,idx){
          historyData.push({
            "moduleName":val.nodename,
            "operatetype":val.operatetype,
            "approveTime":parseDate(val.operatedate),
            "approver":val.operatorname,
            "approveStatus":obj.getOperatetype(val.operatetype),
            "approvedescription":val.approvedescription,
            "logtype":val.logtype
          });
        });
        obj.setState({
          modalTitle:record.referbusinessname+"历史",
          modalVisible: true,
          historyData:historyData,
          tdTableReload:false
        });
      }else {
        openNotice("error", result.rspMsg, "查看任务历史失败");
      }
    }, function (req, info, opt) {
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
    });
  }
  //模态OK
  handleModalOk(){
    this.setState({ modalVisible: false,historyData:{},confirmLoading:false,tdTableReload:false })
  }
  //搜索提交按钮
  handleSearchFormSubmit(data){
    if(data.startcreatedate){
      data.startcreatedate = formatDate(new Date(data.startcreatedate),"yyyyMMdd");
    }
    if(data.endcreatedate){
      data.endcreatedate = formatDate(new Date(data.endcreatedate),"yyyyMMdd");
    }
    this.setState({tdTableReload: true, tdTableParam: data},()=>{this.setState({tdTableReload: false})});
  }
  //列表底部
  renderTableList(result) {
    return {
      total: result.rspData.total,
      list: result.rspData.list,
      count: {a: "haha"}
    };
  }

  render(){
    const obj = this;

    const tableColumns = [
      {title: "任务号", dataIndex: "flowbusinesstoken"},
      {title: "任务类型", dataIndex: "modelname"},
      {title: "任务名称", dataIndex: "referbusinessname",render(text){
        return(
          <Tooltip title={text}>
            <Row type="flex" justify="start">
              <Col span={22} className="ellipsis">
                <span>{text}</span>
              </Col>
            </Row>
          </ Tooltip>
        )
      }},
      {title: "任务状态", dataIndex: "flowstatus",render(text,record,key){
          return (obj.getFlowstatus(record.flowstatus));
      }},
      {title: "下一岗位", dataIndex: "nextpositionname"},
      {title: "申请时间", dataIndex: "createdate",render(text, record, key){
        return (parseDate(text));
      }},
      {title: "申请人", dataIndex: "referbusinessusername"},
      {title: "操作时间", dataIndex: "operatedate",render(text, record, key){
        return (parseDate(text));
      }},
      {title: "操作人", dataIndex: "operatorname"},
      {title:"操作",dataIndex:"operation",render(text, record, key){
        return(
          <span>
                <a href="javascript:void(0)"
                   onClick={() => {obj.handleHisLinkClick(text, record, key)}}>历史</a>
            </span>
        );
      }},
    ];

    return(
      <div >
        <p className="br"/>
        <TdCard hideHead="true" shadow="true">
          <TaskMonitorSearchForm onSubmit={this.handleSearchFormSubmit.bind(this)}/>
          <p className="br"/>
          <TdPageTable checkbox={false} rowKey={record => record.flowstatusno} rowSelectCallback={this.handlerRowSelect.bind(this)}
               url={ylagent.workflow.taskMonitor.findTaskByPage}
               loadParam={this.state.tdTableParam} reload={this.state.tdTableReload}
               renderResult={this.renderTableList} columns={tableColumns}/>
          </TdCard>
        <Modal title={this.state.modalTitle} visible={this.state.modalVisible}
               confirmLoading={this.state.confirmLoading}
               onCancel={() => { this.setState({ modalVisible: false }); } }
               onOk={this.handleModalOk.bind(this) } footer="" className="monitorManager">
            <TaskMonitorHistoryForm  historyData={this.state.historyData} />
        </Modal>
      </div>
    );
  }
}
//必须有create包装,才会带this.props.form属性
TaskMonitorManage = Form.create()(TaskMonitorManage);
export default TaskMonitorManage;

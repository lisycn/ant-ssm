import React from "react";
import {Row, Col, message, Button, Modal, Icon, Form} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice,buildTableTip} from "../../../common/antdUtil";
import {tdJobUrl} from "../../../config/server";
import {callAjax, formatDate, filterObject} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";

import JobInfoSearchForm from "../jobInfo/JobInfoSearchForm";
const confirm = Modal.confirm;

class JobLogManage extends React.Component{
  constructor(props){
    super(props);
    this.state={
      tdTableReload:false,
      tdTableParam:{},
      modalVisible:false,
      modalTitle:'',
      logInfo:''
    };
  }
  //搜索事件
  handleFormSubmit(dat){
    this.setState({tdTableReload: true, tdTableParam: dat}, ()=> {
      this.setState({tdTableReload: false})
    });
  }
  //行选择
  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys
    });
  }
  //处理列表数据
  renderTableList(result) {

    return {
      total: result.recordsTotal,
      list: result.data,
      count: {a: "haha"}
    };
  }
  //执行日志
  handleLogLinkClick(text, record, key){
    let baseFrontUrl = window.location.href.substring(0,window.location.href.indexOf("#"));
    window.open(baseFrontUrl+"#/jobLogDetail?id="+record.id);
  }
  //终止任务
  handleStopLinkClick(text, record, key){
    let obj = this;
    let opt = {
      url: tdJobUrl.jobLog.logKill,
      type: "POST",
      dataType: "json",
      data: {id:record.id}
    };

    confirm({
      title: '您是否确认要执行此项JOB',
      content: '',
      onOk() {
        //请求后台添加工作流接口
        callAjax(opt, function (result) {
          if (result.code == jobResp.RSP_SUCCESS) {
            openNotice("info", "终止JOB成功", "提示");

            obj.setState({
              confirmLoading: false,
              modalVisible: !obj.state.modalVisible,
              modalOprType: 0,
              tdTableReload: true,
              tableSelectedRowKeys: [],
              tableSelectedRows: []
            }, () => {
              obj.setState({
                tdTableReload: false
              });
            });
          } else {
            openNotice("error", result.msg, "终止JOB失败");
            obj.setState({
              confirmLoading: false
            });
          }
        }, function (req, info, opt) {
          openNotice("error", jobResp.RSP_NETWORK_ERROR, "提示");
          obj.setState({
            confirmLoading: false
          });
        });
      },
      onCancel(){
      }
    });
  }

  //查看调度日志
  displayLog(text, record, key){
    console.log(text);
    this.setState({
      logInfo:text,
      modalVisible:true,
      modalTitle:'JOB日志'
    });
  }
  //模态窗口ok事件
  handleModalOk(){}

  render(){
    const obj = this;

    const tableColumns = [
      {title: "执行器地址", dataIndex: "executorAddress",width: 150, render: (text) => buildTableTip(text, 150)},
      {title: "JobHandler", dataIndex: "executorHandler",width: 150, render: (text) => buildTableTip(text, 150)},
      {title: "任务名"    , dataIndex: "jobName"        ,width: 150, render: (text) => buildTableTip(text, 150)},
      {title: "任务参数"  , dataIndex: "executorParam"  ,width: 100, render: (text) => buildTableTip(text, 100)},
      {title: "调度时间"  , dataIndex: "triggerTime"    ,width: 150, render(text, record, key){
        if(text){
          const da = formatDate(new Date(text),"yyyy-MM-dd hh:mm:ss");
          return (<span>{da}</span>);
        }else{
          return (<span></span>);
        }
      }},
      {title: "调度结果" , dataIndex: "triggerStatus"  ,width: 150, render: (text) => buildTableTip(text, 150)},
      {title: "调度日志" , dataIndex: "triggerMsg"     ,width: 100, render(text, record, key){
        return (<span><a href="javascript:void(0)" onClick={()=>{obj.displayLog(text, record, key)}}>查看</a></span>);
      }},
      {title: "执行时间", dataIndex: "handleTime"      ,width: 150, render(text, record, key){
        if(text){
          const da = formatDate(new Date(text),"yyyy-MM-dd hh:mm:ss");
          return (<span>{da}</span>);
        }else{
          return (<span></span>);
        }
      }},
      {title: "执行结果", dataIndex: "handleStatus"   ,width: 100, render: (text) => buildTableTip(text, 100)},
      {title: "执行日志", dataIndex: "handleMsg"      ,width: 150, render(text, record, key){
        return (text?(<span><a href="javascript:void(0)" onClick={()=>{obj.displayLog(text, record, key)}}>查看</a></span>):<span>无</span>);
      }},
      {title: "操作", key: "operation", render(text, record, key) {
        console.log(record.handleStatus);
        if(record.triggerStatus == 'SUCCESS'){
          if(!record.handleStatus){
            return (
              <span>
                <a href="javascript:void(0)"
                   onClick={() => {obj.handleLogLinkClick(text, record, key)}}>执行日志</a>
                <span className="ant-divider"></span>
                <a href="javascript:void(0)"
                   onClick={() => {obj.handleStopLinkClick(text, record, key)}}>终止任务</a>
              </span>
            );
          }else{
            return (
              <span>
                  <a href="javascript:void(0)"
                     onClick={() => {obj.handleLogLinkClick(text, record, key)}}>执行日志</a>
              </span>
            );
          }
        }else{
          return (<span></span>);
        }
      }}
    ]

    return(
      <div>
        <h2>调度日志</h2><p className="br"/>
        <TdCard hideHead="true" shadow="true">
          <JobInfoSearchForm onSubmit={obj.handleFormSubmit.bind(this)} visableDate={true}/>
          <p className="br"/>
          <TdPageTable checkbox={false} rowSelectCallback={obj.handlerRowSelect.bind(this)}
                       url={tdJobUrl.jobLog.pageList}
                       loadParam={obj.state.tdTableParam} reload={obj.state.tdTableReload}
                       renderResult={obj.renderTableList} columns={tableColumns}  rowKey={record => record.id}/>
        </TdCard>

        <Modal width={600} title={obj.state.modalTitle} visible={obj.state.modalVisible}
               footer=""
               onCancel={() => { obj.setState({ modalVisible: false }); } }
               onOk={obj.handleModalOk.bind(this) }>
          <div dangerouslySetInnerHTML={{__html: this.state.logInfo}} />
        </Modal>

      </div>
    );
  }
}

//必须有create包装,才会带this.props.form属性
JobLogManage = Form.create()(JobLogManage);
export default JobLogManage;

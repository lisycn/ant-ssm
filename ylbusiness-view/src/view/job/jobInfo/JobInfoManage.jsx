import React from "react";
import {Row, Col, message, Button, Modal, Icon, Form} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice,buildTableTip} from "../../../common/antdUtil";
import {tdJobUrl} from "../../../config/server";
import {callAjax, parseDate, filterObject} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";
import {jobResp} from "../../../common/authConstant";

import JobInfoSearchForm from "./JobInfoSearchForm";
import JobInfoManageForm from "./JobInfoManageForm";

const confirm = Modal.confirm;

class JobInfoManage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      tdTableParam: {},
      tdTableReload: false,

      modalTitle: "新增任务调度信息",
      modalVisible: false,
      modelIsValid: false,
      confirmLoading: false,
      modalOprType: 0,
      formReset: false,
      formData: {},
    }
  }

  //新增任务
  handleAddBtnClick() {
    this.setState({
      formData: {},
      modalVisible: !this.state.modalVisible,
      modalOprType: 1,
      modalTitle: '新增任务调度信息'
    }, () => {
      //重置子组件表单数据
      this.setState({formReset: true}, () => {
        //将子组件表单重置标识置为false
        this.setState({
          formReset: false
        });
      })
    });
  }

  //执行
  handleExcuteLinkClick(text, record, key) {
    var obj  = this;
    confirm({
      title: '您是否确认要执行此项JOB',
      content: '',
      onOk() {
        obj.execInterface(tdJobUrl.jobInfo.trigger, record, "JOB执行成功", "JOB执行失败");
      },
      onCancel(){
      }
    });
  }

  //暂停
  handlePauseLinkClick(text, record, key) {
    var obj  = this;
    confirm({
      title: '您是否确认要暂停此项JOB',
      content: '',
      onOk() {
        obj.execInterface(tdJobUrl.jobInfo.pause, record, "JOB暂停成功", "JOB暂停失败");
      },
      onCancel(){
      }
    });
  }

  //编辑
  handlerEditLinkClick(text, record, key) {
    this.setState({
      oprType: 2,
      modalVisible: !this.state.modalVisible,
      modalOprType: 2,
      modalTitle: '编辑JOB',
      formData: record
    }, () => {
      //重置子组件表单数据
      this.setState({formReset: true}, () => {
        //将子组件表单重置标识置为false
        this.setState({
          formReset: false
        });
      })
    });
  }

  //删除
  handlerDeleteLinkClick(text, record, key) {
    var obj  = this;
    confirm({
      title: '您是否确认要删除此项JOB',
      content: '',
      onOk() {
        obj.execInterface(tdJobUrl.jobInfo.remove, record, "删除JOB成功", "删除JOB失败");
      },
      onCancel(){
      }
    });
  }

  //恢复执行
  handleRecoveryLinkClick(text, record, key) {
    var obj  = this;
    confirm({
      title: '您是否确认要恢复此项JOB',
      content: '',
      onOk() {
        obj.execInterface(tdJobUrl.jobInfo.resume, record, "JOB恢复成功", "JOB恢复失败");
      },
      onCancel(){
      }
    });
  }

  //执行接口
  execInterface(url, record, successMess, errorMess) {
    let opt = {
      url: url,
      type: "POST",
      dataType: "json",
      data: {"jobGroup": record.jobGroup, "jobName": record.jobName}
    };
    let obj = this;

    //请求后台添加工作流接口
    callAjax(opt, function (result) {
      if (result.code == jobResp.RSP_SUCCESS) {
        openNotice("info", successMess, "提示");
        obj.props.form.resetFields();
        obj.setState({
          confirmLoading: false,
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
        openNotice("error", result.msg, errorMess);
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
  }

  //行选择
  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys
    });
  }

  //表格处理数据
  renderTableList(result) {
    return {
      total: result.recordsTotal,
      list: result.data,
      count: {a: "haha"}
    };
  }

  //搜索按钮
  handleFormSubmit(dat) {
    this.setState({tdTableReload: true, tdTableParam: dat}, ()=> {
      this.setState({tdTableReload: false})
    });
  }

  //模态确定按钮事件
  handleModalOk() {
    this.setState({
      modelIsValid: true
    });
  }

  //表单验证
  callbackValid(oprType, errors, data) {
    this.setState({
      modelIsValid: false
    });
    const obj = this;
    if (!!errors) {
      console.log("表单校验失败!");
      return;
    } else {
      console.log("子页面表单校验成功。");
      this.setState({
        formData: Object.assign({}, this.state.formData, data),
        formReset: false,
        confirmLoading: true
      }, () => {
        switch (oprType) {
          case 1:
            obj.handleAddModalOk();
            break;
          case 2:
            obj.handlEditModalOk();
            break;
          default:
            openNotice("error", "操作失败");
            break;
        }
      });
    }
  }

  //新增
  handleAddModalOk() {
    this.saveData(tdJobUrl.jobInfo.jobAdd, false);
  }

  //修改
  handlEditModalOk() {
    this.saveData(tdJobUrl.jobInfo.reschedule, true);
  }

  //保存数据
  saveData(url, isUpdate) {
    //获取addModelForm表单数据
    let formData = filterObject(this.state.formData);
    formData.glueSwitch = 0;
    let opt = {
      url: url,
      type: "POST",
      dataType: "json",
      data: formData
    };
    let obj = this;


    //请求后台添加工作流接口
    callAjax(opt, function (result) {
      if (result.code == jobResp.RSP_SUCCESS) {
        if (isUpdate) {
          openNotice("info", "JOB修改成功", "提示");
        } else {
          openNotice("info", "JOB添加成功", "提示");
        }
        obj.props.form.resetFields();
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
        if (isUpdate) {
          openNotice("error", result.msg, "JOB修改失败");
        } else {
          openNotice("error", result.msg, "JOB添加失败");
        }
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
  }

  displayOperation(text, record, key,obj) {

    if(record.jobStatus == "NORMAL"){
      return (<span>
        <a href="javascript:void(0)" onClick={() => {obj.handleExcuteLinkClick(text, record, key)}}>执行</a>
        <span className="ant-divider"></span>
        <a href="javascript:void(0)" onClick={() => {obj.handlePauseLinkClick(text, record, key)}}>暂停</a>
        <span className="ant-divider"></span>
        <a href="javascript:void(0)" onClick={() => {obj.handlerEditLinkClick(text, record, key)}}>编辑</a>
        <span className="ant-divider"></span>
        <a href="javascript:void(0)" onClick={() => {obj.handlerDeleteLinkClick(text, record, key)}}>删除</a>
      </span>);
    }else{
      return (<span>
        <a href="javascript:void(0)" onClick={() => {obj.handleExcuteLinkClick(text, record, key)}}>执行</a>
        <span className="ant-divider"></span>
        <a href="javascript:void(0)" onClick={() => {obj.handleRecoveryLinkClick(text, record, key)}}>恢复</a>
        <span className="ant-divider"></span>
        <a href="javascript:void(0)" onClick={() => {obj.handlerEditLinkClick(text, record, key)}}>编辑</a>
        <span className="ant-divider"></span>
        <a href="javascript:void(0)" onClick={() => {obj.handlerDeleteLinkClick(text, record, key)}}>删除</a>
      </span>);
    }
  }
  render(){
    const obj = this;
    const modalIsDetail = {
      footer: ""
    };
    //表格列
    const tableColumns = [
      {title: "任务名", dataIndex: "jobName"  ,width: 250, render: (text) => buildTableTip(text, 250)},
      {title: "描述"  , dataIndex: "jobDesc"  ,width: 250, render: (text) => buildTableTip(text, 250)},
      {title: "Cron"  , dataIndex: "jobCron"  ,width: 100, render: (text) => buildTableTip(text, 100)},
      {title: "负责人", dataIndex: "author"   ,width: 130, render: (text) => buildTableTip(text, 130)},
      {title: "状态"  , dataIndex: "jobStatus",width: 100, render: (text) => buildTableTip(text, 100)},
      {title: "操作"  , key: "operation", render(text, record, key) {
        return obj.displayOperation(text, record, key,obj);
      }}
    ];
    //表格按钮
    const toolbar = [
      {
        icon: "plus", text: "新增", click: () => {
        obj.handleAddBtnClick()}
      }
    ];

    return(<div>
      <h2>调度管理</h2><p className="br"/>
      <TdCard hideHead="true" shadow="true">
        <JobInfoSearchForm onSubmit={obj.handleFormSubmit.bind(this)} visableDate={false}/>
        <p className="br"/>
        <TdPageTable checkbox={false} rowSelectCallback={obj.handlerRowSelect.bind(this)}
                     url={tdJobUrl.jobInfo.pageList} toolbar={toolbar}
                     loadParam={obj.state.tdTableParam} reload={obj.state.tdTableReload}
                     renderResult={obj.renderTableList} columns={tableColumns} rowKey={record => record.id}/>

        <Modal width={700} title={obj.state.modalTitle} visible={obj.state.modalVisible}
               confirmLoading={obj.state.confirmLoading}
               onCancel={() => { obj.setState({ modalVisible: false }); } }
               onOk={obj.handleModalOk.bind(this) }
          {...(this.state.modalOprType === 3 ? modalIsDetail : null) }>

          <JobInfoManageForm formReset={this.state.formReset}
                                 valid={this.state.modelIsValid}
                                 formData={this.state.formData}
                                 oprType={this.state.modalOprType}
                                 validCallback={(oprType, errors, data) => {
                                obj.callbackValid(oprType, errors, data);
                            } }/>
        </Modal>

      </TdCard>

    </div>);
  }
}

//必须有create包装,才会带this.props.form属性
JobInfoManage = Form.create()(JobInfoManage);
export default JobInfoManage;

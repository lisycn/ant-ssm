import React from "react";
import {Row, Col, Tooltip, message, Button, Modal, Icon, Transfer, Form} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, parseDate, filterObject} from "../../../common/util";
import WorkflowModelMainManageSearchForm from "./WorkflowMainManageSearchForm";
import WorkflowModelMainForm from "./WorkflowMainForm";
import TdPageTable from "../../../component/TdPageTable";
import WorkflowModelMainProcess from "./WorkflowMainProcess";
import TdTransfer from "../../../component/Td-transfer";
const confirm = Modal.confirm;
/**
 * WorkflowModelMainManage 工作流管理功能
 *
 * Auth: jiang.mc  Time: 2016-05-21
 */
class WorkflowModelMainManage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
      tableLoading: false,
      tableData: [],
      tdTableReload: false,
      tdTableParam: {},
      modalVisible: false,
      modelIsValid: false,
      processIsValid: false,
      modalOprType: 0,
      formReset: false,
      formData: {},
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      modalTitle: "工作流管理",
      timelineTitle: "查看流程",
      processName: "",
      confirmLoading: false,
      processCfgList: [],

      assignTitle: "流程配置",
      assignVisible: false,
      assignData: [],
      assignTarget: [],
      assignConfirmLoading: false,
      modelNo: ""
    }
  }

  //点击“高级搜索”事件
  handleAdvLinkClick() {
    this.setState({
      advSearchShow: !this.state.advSearchShow
    });
  }

  //点击添加按钮弹出添加对话框
  handleAddBtnClick() {
    this.setState({
      formData: {},
      modalVisible: !this.state.modalVisible,
      modalOprType: 1,
      modalTitle: '添加工作流'
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

  //添加对话框的确定按钮
  handleAddModalOk() {
    //获取addModelForm表单数据
    let formData = filterObject(this.state.formData);
    console.log("modeltype:" + formData.modeltype);
    console.log("modelname:" + formData.modelname);
    console.log("status:" + formData.status);
    let opt = {
      url: ylagent.workflow.modelMain.addModelMain,
      type: "POST",
      dataType: "json",
      data: formData
    };
    let obj = this;
    //请求后台添加工作流接口
    callAjax(opt, function (result) {
      console.log(result);
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        openNotice("info", "添加工作流成功", "提示");
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
        openNotice("error", result.rspMsg, "添加工作流失败");
        obj.setState({
          confirmLoading: false
        });
      }
    }, function (req, info, opt) {
      console.log(info);
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
      obj.setState({
        confirmLoading: false
      });
    });
  }

  /*** 编辑菜单按钮事件 */
  handlerEditBtnClick(text, record, key) {
    this.setState({
      oprType: 2
    });
    this.setState({
      modalVisible: !this.state.modalVisible,
      modalOprType: 2,
      modalTitle: '编辑菜单',
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

  /*** 编辑菜单弹出框确定事件*/
  handlEditModalOk() {
    let editFormData = filterObject(this.state.formData);
    console.log("modeltype:" + editFormData.modeltype);
    console.log("modelname:" + editFormData.modelname);
    console.log("status:" + editFormData.status);

    let opt = {
      url: ylagent.workflow.modelMain.updateModelMain,
      type: "POST",
      dataType: "json",
      data: editFormData
    };
    let obj = this;
    callAjax(opt, function (result) {
      console.log(result);
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        openNotice("info", "修改工作流成功", "提示");
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
        openNotice("error", result.rspMsg, "修改工作流失败");
        obj.setState({
          confirmLoading: false
        });
      }
    }, function (req, info, opt) {
      console.log(info);
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
      obj.setState({
        confirmLoading: false
      });
    });
  }

  /*** 删除按钮点击事件*/
  handlerDeleteBtnClick(text, record, key) {
    let obj = this;
    confirm({
      title: '您是否确认要删除选中项',
      content: '',
      onOk() {
        let opt = {
          url: ylagent.workflow.modelMain.deleteModelMainByNos,
          type: "POST",
          dataType: "json",
          data: {modelno: record.modelno}
        };
        callAjax(opt, function (result) {
          if (result.rspCod === rspInfo.RSP_SUCCESS) {
            openNotice("info", "删除工作流成功", "提示");
            obj.setState({
              tdTableReload: true,
              tableSelectedRows: [],
              tableSelectedRowKeys: []
            }, () => {
              obj.setState({
                tdTableReload: false
              });
            });
          } else {
            openNotice("error", result.rspMsg, "删除工作流失败");
          }
        }, function (req, info, opt) {
          openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
        });
      },
      onCancel() {
      }
    });


  }

  handlerEnableBtnClick() {
    if (this.state.tableSelectedRows.length > 0) {
      this.handlerRecordSts("enable");
    } else {
      openNotice("warning", "请选择需启用的记录");
    }
  }

  /** 点击禁用确定按钮事件*/
  handlerDisableBtnOkClick(record) {
    let obj = this;
    let opt = {
      url: ylagent.workflow.modelMain.disableModelMain,
      type: "POST",
      dataType: "json",
      data: {modelno: record.modelno}
    };
    callAjax(opt, function (result) {
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        openNotice("info", "禁用工作流成功", "提示");
        obj.setState({
          tdTableReload: true,
          tableSelectedRows: [],
          tableSelectedRowKeys: []
        }, ()=> {
          obj.setState({tdTableReload: false});
        });
      } else {
        openNotice("error", result.rspMsg, "禁用工作流失败");
      }
    }, function (req, info, opt) {
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
    });
  }

  /*** 禁用按钮点击事件*/
  handlerDisableBtnClick(text, record, key) {
    let obj = this;
    let opt = {
      url: ylagent.workflow.modelMain.findUnfinishedFlowStatusEntity,
      type: "POST",
      dataType: "json",
      data: {modelno: record.modelno}
    };

    callAjax(opt, function (result) {
      if (result.rspData.unfinishedTaskTotal === 0) {
        confirm({
          title: "您是否确认要禁用选中项",
          content: "",
          onOk() {
            obj.handlerDisableBtnOkClick(record);
          },
          onCancel() {
          }
        });
      } else {
        confirm({
          title: '该工作流还有 ' + result.rspData.unfinishedTaskTotal + ' 个任务未完成，若继续，未完成的任务状态将更改为作废!',
          content: "",
          onOk() {
            obj.handlerDisableBtnOkClick(record);
          },
          onCancel() {
          }
        });
      }
    }, function (req, info, opt) {
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
    });
  }

  handlerRecordSts(param) {
    let obj = this;
    let data = obj.state.tableSelectedRows;
    let itmIds = "";
    data.forEach(v => (itmIds = itmIds + "," + v.modelno + ""));
    param === "enable" ? console.log("要启用的工作流ID：" + itmIds) : console.log("要禁用的工作流ID：" + itmIds);
    confirm({
      title: "您是否确认要" + (param === "enable" ? "启用" : "禁用") + "选中项",
      content: "",
      onOk() {
        let opt = {
          url: ylagent.workflow.modelMain.batchEnableModelMain,
          type: "POST",
          dataType: "json",
          data: {status: (param === "enable" ? "1" : "0"), modelnos: itmIds.substring(1, itmIds.length)}
        };
        callAjax(opt, function (result) {
          if (result.rspCod === rspInfo.RSP_SUCCESS) {
            openNotice("info", (param === "enable" ? "启用" : "禁用") + "修改成功", "提示");
            obj.setState({
              tdTableReload: true,
              tableSelectedRows: [],
              tableSelectedRowKeys: []
            }, ()=> {
              obj.setState({tdTableReload: false});
            });
          } else {
            openNotice("error", result.rspMsg, (param === "enable" ? "启用" : "禁用") + "修改失败");
          }
        }, function (req, info, opt) {
          openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
        });
      },
      onCancel() {
      }
    });
  }

  handleFormSubmit(dat) {
    this.setState({tdTableReload: true, tdTableParam: dat}, ()=> {
      this.setState({tdTableReload: false})
    });
  }

  handleFormReset() {
    this.setState({formData: {}});
  }

  //模态框确认点击事件，修改子页面props valid状态,触发子页面执行回调
  handleModalOk() {
    this.setState({
      modelIsValid: true
    });
  }

  //模态框子页面回调
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
      console.log("modal form data=>" + data);
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

  renderTableList(result) {
    return {
      total: result.rspData.total,
      list: result.rspData.list,
      count: {a: "haha"}
    };
  }

  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys
    });
  }

  //点击“详情”链接事件(text:该单元格数据 record:该行记录对象 key:该行KEY)
  handleProcessClick(text, record, key) {
    let obj = this;
    let opt = {
      url: ylagent.workflow.modelMain.findProcessCfg,
      type: "POST",
      dataType: "json",
      data: {
        modelno: record.modelno
      }
    };
    callAjax(opt, function (result) {
      if ((result.rspCod === rspInfo.RSP_SUCCESS)) {
        obj.setState({
          processCfgList: result.rspData.processCfgList,
          processName: record.modeltype + " - " + record.modelname
        });
      } else {
        openNotice("error", result.rspMsg, "查看流程失败");
        obj.setState({
          confirmLoading: false
        });
      }
    }, function (req, info, opt) {
      console.log(info);
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
      obj.setState({
        confirmLoading: false
      });
    });
    console.log(record);
    this.setState({
      formData: record,
      processIsValid: true,
      modalTitle: "步骤详情"
    }, () => {

    });
  }

  //工作流步骤分配页面功能
  handleAssignLinkClick(text, record, key) {
    let obj = this;
    let opt = {
      url: ylagent.workflow.modelMain.queryModelNode,
      type: "POST",
      dataType: "json",
      data: {modelno: record.modelno}
    };
    //清空
    callAjax(opt, function (result) {
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        obj.setState({
          assignData: result.rspData, assignVisible: true
        });
      } else {
        openNotice("error", result.rspMsg, "");
      }
    }, function (req, info, opt) {
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
    });
  }

  //模态
  handleAssignModelOk() {
    let obj = this;
    if (obj.state.assignTarget.length < 1) {
      openNotice("warn", "请选择步骤进行组装", "提示");
      return;
    } else {
      this.handleSaveTargetKey();
    }
  }

  handleAssignModelCancel() {
    this.setState({
      assignData: [],
      assignTarget: [],
      assignVisible: false,
      assignConfirmLoading: false
    });
  }

  handleAssignChange(targetKeys) {
    this.setState({assignTarget: targetKeys});
  }

  handleSaveTargetKey() {
    let obj = this;
    let nodes = "";
    obj.state.assignTarget.map(function (val, idx) {
      nodes = nodes + "," + val;
    });
    nodes = nodes.substring(1);
    let opt = {
      url: ylagent.workflow.modelMain.saveModelPath,
      type: "POST",
      dataType: "json",
      data: {"nodenos": nodes}
    };
    callAjax(opt, function (result) {
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        openNotice("info", "流程组装成功", "提示");
        obj.setState({
          assignVisible: false,
          assignConfirmLoading: false,
          assignTarget: [],
          assignData: []
        });
      } else {
        openNotice("error", result.rspMsg, "流程组装失败  ");
      }
    }, function (req, info, opt) {
      console.log(info);
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
    });
  }

  render() {
    //定义变量和参数
    const obj = this;
    const modalIsDetail = {
      footer: ""
    };

    const tableColumns = [
      {title: "工作流类型", dataIndex: "modeltype"},
      {title: "工作流名称", dataIndex: "modelname"},
      {
        title: "工作流状态", dataIndex: "status", render(text, record, key){
        return (
          text === "0" ? "禁用" : "启用"
        );
      }
      },
      {title: "创建人", dataIndex: "createobj"},
      {
        title: "创建时间", dataIndex: "createdate", render(text, record, key){
        return (
          parseDate(text)
        );
      }
      },
      {title: "更新人", dataIndex: "updateobj"},
      {
        title: "更新时间", dataIndex: "updatedate", render(text, record, key){
        return (
          parseDate(text)
        );
      }
      },
      //以下click事件中不能使用this
      {
        title: "操作", key: "operation", width: 240, render(text, record, key) {
        return (
          <span>
                        <a href="javascript:void(0)"
                           onClick={() => {obj.handleProcessClick(text, record, key)}}>查看流程</a>
                        <span className="ant-divider"></span>
                        <a href="javascript:void(0)"
                           onClick={() => {obj.handleAssignLinkClick(text, record, key)}}>分配流程</a>
                        <span className="ant-divider"></span>
                        <a href="javascript:void(0)"
                           onClick={() => {obj.handlerEditBtnClick(text, record, key)}}>修改</a>
                        <span className="ant-divider"></span>
                        <a href="javascript:void(0)"
                           onClick={() => {obj.handlerDeleteBtnClick(text, record, key)}}>删除</a>
                        <span className="ant-divider"></span>
                        <a href="javascript:void(0)"
                           onClick={() => {obj.handlerDisableBtnClick(text, record, key)}}>禁用</a>
                    </span>
        );
      }
      }
    ];

    const toolbar = [
      {
        icon: "plus", text: "新增", click: () => {
        obj.handleAddBtnClick()
      }
      },
      {
        icon: "check", text: "启用", click: () => {
        obj.handlerEnableBtnClick()
      }
      }
    ];

    //渲染虚拟DOM
    return (
      <div>
        <TdCard hideHead="true" shadow="true">
          <WorkflowModelMainManageSearchForm onSubmit={this.handleFormSubmit.bind(this)}
                                             onReset={this.handleFormReset.bind(this)}/>
          <p className="br"/>
          <TdPageTable rowSelectCallback={this.handlerRowSelect.bind(this)} rowKey={record => record.modelno}
                       url={ylagent.workflow.modelMain.queryModelMainList} toolbar={toolbar}
                       loadParam={this.state.tdTableParam} reload={this.state.tdTableReload}
                       renderResult={this.renderTableList} columns={tableColumns}/>
          <Modal title={this.state.modalTitle} visible={this.state.modalVisible}
                 confirmLoading={this.state.confirmLoading}
                 onCancel={() => { this.setState({ modalVisible: false }); } }
                 onOk={this.handleModalOk.bind(this) }
            {...(this.state.modalOprType === 3 ? modalIsDetail : null) }>

            <WorkflowModelMainForm formReset={this.state.formReset}
                                   valid={this.state.modelIsValid}
                                   formData={this.state.formData}
                                   oprType={this.state.modalOprType}
                                   validCallback={(oprType, errors, data) => {
                                this.callbackValid(oprType, errors, data);
                            } }/>
          </Modal>
          <Modal title={this.state.timelineTitle} visible={this.state.processIsValid} footer=""
                 confirmLoading={this.state.confirmLoading}
                 onCancel={() => { this.setState({ processIsValid: false }); } }>
            <WorkflowModelMainProcess processCfgList={this.state.processCfgList} processName={this.state.processName}

            />
          </Modal>
          <Modal title={this.state.assignTitle} visible={this.state.assignVisible}
                 confirmLoading={this.state.assignConfirmLoading}
                 onCancel={this.handleAssignModelCancel.bind(this) }
                 onOk={this.handleAssignModelOk.bind(this) }
                 width="600"
          >
            <TdTransfer
              dataSource={this.state.assignData}
              showSearch
              listStyle={{ width: 200, height: 300,marginLeft:20,marginRight:15}}
              titles={['未选步骤','已选步骤']} operations={['添加步骤', '移除步骤']}
              targetKeys={this.state.assignTarget} onChange={this.handleAssignChange.bind(this)}
              render={item => `${item.nodename}`}
              upOrDown={true}
            />
          </Modal>
        </TdCard>
      </div>
    );
  }
}
//必须有create包装,才会带this.props.form属性
WorkflowModelMainManage = Form.create()(WorkflowModelMainManage);
export default WorkflowModelMainManage;

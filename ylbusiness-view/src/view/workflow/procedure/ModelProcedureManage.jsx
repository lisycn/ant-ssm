import React from "react";
import {Row, Col, Tooltip, message, Button, Modal, Icon, Transfer, Form} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, parseDate, filterObject} from "../../../common/util";
import ModelNodeManageSearchForm from "./ModelProcedureManageSearchForm";
import ModelNodeForm from "./ModelProcedureForm";
import TdPageTable from "../../../component/TdPageTable";
const confirm = Modal.confirm;
/**
 * ModelNodeManage 步骤管理功能
 *
 * Auth: jiang.mc  Time: 2016-05-25
 */
class ModelNodeManage extends React.Component {
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
      modalOprType: 0,
      formReset: false,
      formData: {},
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      modalTitle: "步骤管理",
      confirmLoading: false,
      assignModalVisible: false,
      usrRoleData: []
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
      modalTitle: '添加步骤'
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
    let opt = {
      url: ylagent.workflow.modelNode.addModelNode,
      type: "POST",
      dataType: "json",
      data: formData
    };
    let obj = this;
    //请求后台添加岗位接口
    callAjax(opt, function (result) {
      console.log(result);
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        openNotice("info", "添加步骤成功", "提示");
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
        openNotice("error", result.rspMsg, "添加步骤失败");
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

  /*** 编辑步骤按钮事件 */
  handlerEditBtnClick(text, record, key) {
    this.setState({
      modalVisible: !this.state.modalVisible,
      modalOprType: 2,
      modalTitle: '编辑步骤',
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

  /*** 编辑步骤弹出框确定事件*/
  handlEditModalOk() {
    let editFormData = filterObject(this.state.formData);
    let opt = {
      url: ylagent.workflow.modelNode.updateModelNode,
      type: "POST",
      dataType: "json",
      data: editFormData
    };
    let obj = this;

    callAjax(opt, function (result) {
      console.log(result);
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        openNotice("info", "修改步骤成功", "提示");
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
        openNotice("error", result.rspMsg, "修改步骤失败");
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
        console.log("要删除的步骤ID：" + record.nodeno);
        let opt = {
          url: ylagent.workflow.modelNode.deleteModelNodeByNo,
          type: "POST",
          dataType: "json",
          data: {nodeno: record.nodeno}
        };
        callAjax(opt, function (result) {
          if (result.rspCod === rspInfo.RSP_SUCCESS) {
            openNotice("info", "删除步骤成功", "提示");
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
            openNotice("error", result.rspMsg, "删除步骤失败");
          }
        }, function (req, info, opt) {
          openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
        });
      },
      onCancel() {
      }
    });
  }

  /*** 启用按钮点击事件*/
  handlerEnableBtnClick(param) {
    if (this.state.tableSelectedRows.length > 0) {
      this.handlerRecordSts("enable");
    } else {
      openNotice("warning", "请选择需启用的记录");
    }
  }

  handlerRecordSts(param) {
    let obj = this;
    let data = obj.state.tableSelectedRows;
    let itmIds = "";
    data.forEach(v => (itmIds = itmIds + "," + v.nodeno + ""));
    param === "enable" ? console.log("要启用的工作流ID：" + itmIds) : console.log("要禁用的工作流ID：" + itmIds);
    confirm({
      title: "您是否确认要" + (param === "enable" ? "启用" : "禁用") + "选中项",
      content: "",
      onOk() {
        let opt = {
          url: ylagent.workflow.modelNode.batchEnableModelNode,
          type: "POST",
          dataType: "json",
          data: {status: (param === "enable" ? "1" : "0"), nodenos: itmIds.substring(1, itmIds.length)}
        };
        callAjax(opt, function (result) {
          if (result.rspCod === rspInfo.RSP_SUCCESS) {
            openNotice("info", (param === "enable" ? "启用" : "禁用") + "步骤成功", "提示");
            obj.setState({
              tdTableReload: true,
              tableSelectedRows: [],
              tableSelectedRowKeys: []
            }, ()=> {
              obj.setState({tdTableReload: false});
            });
          } else {
            openNotice("error", result.rspMsg, (param === "enable" ? "启用" : "禁用") + "步骤失败");
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

  /*** 禁用按钮点击事件*/
  handlerDisableBtnClick(text, record, key) {
    let opt = {
      url: ylagent.workflow.modelNode.disableModelNode,
      type: "POST",
      dataType: "json",
      data: {nodeno: record.nodeno}
    };
    let obj = this;
    confirm({
      title: "您是否确认要禁用选中项",
      content: "",
      onOk() {
        callAjax(opt, function (result) {
          if (result.rspCod === rspInfo.RSP_SUCCESS) {
            openNotice("info", "禁用步骤成功", "提示");
            obj.setState({
              tdTableReload: true,
              tableSelectedRows: [],
              tableSelectedRowKeys: []
            }, ()=> {
              obj.setState({tdTableReload: false});
            });
          } else {
            openNotice("error", result.rspMsg, "禁用步骤失败");
          }
        }, function (req, info, opt) {
          openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
        });
      },
      onCancel() {
      }
    });
  }

  render() {
    //定义变量和参数
    const obj = this;
    const modalIsDetail = {
      footer: ""
    };

    const tableColumns = [
      {title: "所属工作流", dataIndex: "modelname"},
      {title: "步骤名称", dataIndex: "nodename"},
      {
        title: "步骤状态", dataIndex: "status", render(text, record, key){
          return (
            text === "0" ? "禁用" : "启用"
          );
        }
      },
      {title: "岗位代码", dataIndex: "positioncode"},
      {title: "岗位名称", dataIndex: "positionname"},
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
        title: "操作", key: "operation", width: 120, render(text, record, key) {
        return (
          <span>
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
          <ModelNodeManageSearchForm onSubmit={this.handleFormSubmit.bind(this)}
                                     onReset={this.handleFormReset.bind(this)}/>
          <p className="br"/>
          <TdPageTable rowKey={record => record.positionno} rowSelectCallback={this.handlerRowSelect.bind(this)}
                       url={ylagent.workflow.modelNode.queryNodeList} rowKey={record => record.nodeno} toolbar={toolbar}
                       loadParam={this.state.tdTableParam} reload={this.state.tdTableReload}
                       renderResult={this.renderTableList} columns={tableColumns}/>
          <Modal title={this.state.modalTitle} visible={this.state.modalVisible}
                 confirmLoading={this.state.confirmLoading}
                 onCancel={() => { this.setState({ modalVisible: false }); } }
                 onOk={this.handleModalOk.bind(this) }
            {...(this.state.modalOprType === 3 ? modalIsDetail : null) }>

            <ModelNodeForm formReset={this.state.formReset}
                           valid={this.state.modelIsValid}
                           formData={this.state.formData}
                           oprType={this.state.modalOprType}
                           validCallback={(oprType, errors, data) => {
                                this.callbackValid(oprType, errors, data);
                            } }/>
          </Modal>
        </TdCard>
      </div>
    );
  }
}
//必须有create包装,才会带this.props.form属性
ModelNodeManage = Form.create()(ModelNodeManage);
export default ModelNodeManage;

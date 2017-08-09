import React from "react";
import {Modal, Form} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, filterObject} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";
import LoadbalancesSearch from "./LoadbalancesSearch";
import LoadbalancesManageForm from "./LoadbalancesManageForm";

const confirm = Modal.confirm;
class LoadbalancesManage extends React.Component {
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

      modalTitle: '',
      confirmLoading: false,
    }
  }

  handleAddBtnClick() {
    this.setState({modalTitle: '新增负载均衡', modalVisible: true, formData: {}, modalOprType: 1}, () => {
      //重置子组件表单数据
      this.setState({formReset: true}, () => {
        //将子组件表单重置标识置为false
        this.setState({
          formReset: false
        });
      })
    });
  }

  handleDeleteBtnClick() {
    const obj = this;
    if (obj.state.tableSelectedRows.length < 1) {
      openNotice("请选择需要删除的负载均衡");
      return;
    }
    confirm({
      title: '您是否确认要删除选中项',
      content: '',
      onOk() {
        let data = obj.state.tableSelectedRows;
        let itmIds = "";
        data.forEach(v => (itmIds = itmIds + "," + v.id + ""));
        let opt = {
          url: ylagent.dubbo.loadbalances.deleteLoadbalance,
          data: {ids: itmIds.substring(1, itmIds.length)}
        }
        callAjax(opt, function (result) {
          if (result.rspCod === rspInfo.RSP_SUCCESS) {
            openNotice("info", "删除菜单成功", "提示");
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
            openNotice("error", result.rspMsg, "删除负载均衡失败");
          }
        }, function (req, info, opt) {
          openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
        });
      },
      onCancel() {
      }
    });
  }

  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys
    });
  }

  handlerEditLinkClick(text, record, key) {
    this.setState({
      modalTitle: '修改负载均衡',
      modalVisible: true,
      formData: record, modalOprType: 2
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

  handlerDeleteLinkClick(text, record, key) {
    let obj = this;
    confirm({
      title: '您是否确认要删除选中项',
      content: '',
      onOk() {
        let opt = {
          url: ylagent.dubbo.loadbalances.deleteLoadbalance,
          data: {"ids": record.id}
        };
        //请求后台添加菜单接口
        callAjax(opt, function (result) {
          if (result.rspCod === "000000") {
            openNotice("info", "删除负载均衡成功", "提示");
            obj.props.form.resetFields();
            obj.setState({
              confirmLoading: false,
              tdTableReload: true,
            }, () => {
              obj.setState({
                tdTableReload: false
              });
            });
          } else {
            openNotice("error", result.rspMsg, "删除负载均衡失败");
            obj.setState({
              confirmLoading: false
            });
          }
        }, function (req, info, opt) {
          openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
          obj.setState({
            confirmLoading: false
          });
        });
      },
      onCancel() {
      }
    });
  }

  handlerSearch(prop) {
    this.setState({tdTableParam: prop, tdTableReload: true}, () => {
      this.setState({tdTableReload: false});
    });
  }

  handlerSearchReset() {
    console.log("清空查询条件");
  }

  //表格处理数据
  renderTableList(result) {
    return {
      total: result.rspData.total,
      list: result.rspData.list,
      count: {a: "haha"}
    };
  }

  handleModalOk() {
    this.setState({
      modelIsValid: true
    });
  }

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
      console.log("modal form data=>" + JSON.stringify(data));
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

  handleAddModalOk() {
    //获取addUserForm表单数据
    let addFormData = filterObject(this.state.formData);
    let obj = this;
    let opt = {
      url: ylagent.dubbo.loadbalances.createLoadbalance,
      data: addFormData
    };

    //请求后台添加菜单接口
    callAjax(opt, function (result) {
      if (result.rspCod === "000000") {
        openNotice("info", "添加负载均衡成功", "提示");
        obj.props.form.resetFields();
        obj.setState({
          confirmLoading: false,
          modalVisible: !obj.state.modalVisible,
          modalOprType: 0,
          tdTableReload: true,
          tableSelectedRows: [],
          tableSelectedRowKeys: []
        }, () => {
          obj.setState({
            tdTableReload: false
          });
        });
      } else {
        openNotice("error", result.rspMsg, "添加负载均衡失败");
        obj.setState({
          confirmLoading: false
        });
      }
    }, function (req, info, opt) {
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
      obj.setState({
        confirmLoading: false
      });
    });
  }

  handlEditModalOk() {
    //获取addUserForm表单数据
    let addFormData = filterObject(this.state.formData);
    let obj = this;

    let opt = {
      url: ylagent.dubbo.loadbalances.editLoadbalance,
      data: addFormData
    };

    //请求后台添加菜单接口
    callAjax(opt, function (result) {
      if (result.rspCod === "000000") {
        openNotice("info", "修改负载均衡成功", "提示");
        obj.props.form.resetFields();
        obj.setState({
          confirmLoading: false,
          modalVisible: !obj.state.modalVisible,
          modalOprType: 0,
          tdTableReload: true,
          tableSelectedRows: [],
          tableSelectedRowKeys: []
        }, () => {
          obj.setState({
            tdTableReload: false
          });
        });
      } else {
        openNotice("error", result.rspMsg, "修改负载均衡失败");
        obj.setState({
          confirmLoading: false
        });
      }
    }, function (req, info, opt) {
      openNotice("error", rspInfo.RSP_NETWORK_ERROR, "提示");
      obj.setState({
        confirmLoading: false
      });
    });
  }


  render() {
    const obj = this;
    const modalIsDetail = {
      footer: ""
    };
    //表格列
    const tableColumns = [
      {title: "服务方法", dataIndex: "method"},
      {title: "服务名", dataIndex: "service"},
      {
        title: "负载均衡策略", dataIndex: "strategy", render(text, record, key){
        if (text == "random") {
          return (<span>随机</span>);
        } else if (text == "roundrobin") {
          return (<span>轮询</span>);
        } else if (text == "leastactive") {
          return (<span>最少并发</span>);
        }
      }
      },
      {
        title: "操作", dataIndex: "option", render(text, record, key){
        return (<span>
        <a href="javascript:void(0)" onClick={() => {obj.handlerEditLinkClick(text, record, key)}}>编辑</a>
        <span className="ant-divider"></span>
        <a href="javascript:void(0)" onClick={() => {obj.handlerDeleteLinkClick(text, record, key)}}>删除</a>
      </span>);
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
        icon: "cross", text: "批量删除", click: () => {
        obj.handleDeleteBtnClick()
      }
      }
    ];
    return (
      <div>
        <h2>负载均衡</h2><p className="br"/>
        <TdCard hideHead="true" shadow="true">
          <LoadbalancesSearch onSubmit={this.handlerSearch.bind(this)} onReset={this.handlerSearchReset.bind(this)}/>
          <p className="br"/>
          <TdPageTable
            url={ylagent.dubbo.loadbalances.getAllLoadBalances}
            rowSelectCallback={obj.handlerRowSelect.bind(this)}
            toolbar={toolbar}
            loadParam={obj.state.tdTableParam} reload={obj.state.tdTableReload}
            renderResult={obj.renderTableList} columns={tableColumns} rowKey={record => record.id}/>
        </TdCard>

        <Modal width={650} title={obj.state.modalTitle} visible={obj.state.modalVisible}
               confirmLoading={obj.state.confirmLoading}
               onCancel={() => { obj.setState({ modalVisible: false }); } }
               onOk={obj.handleModalOk.bind(this) }
          {...(this.state.modalOprType === 3 ? modalIsDetail : null) } >
          <LoadbalancesManageForm formReset={this.state.formReset}
                                  valid={this.state.modelIsValid}
                                  formData={this.state.formData}
                                  oprType={this.state.modalOprType}
                                  validCallback={(oprType, errors, data) => {
                                obj.callbackValid(oprType, errors, data);
                            } }/>

        </Modal>


      </div>
    );
  }

}

//必须有create包装,才会带this.props.form属性
LoadbalancesManage = Form.create()(LoadbalancesManage);
export default LoadbalancesManage;

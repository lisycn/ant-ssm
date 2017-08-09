import React from "react";
import {message, Button, Modal, Form} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, parseDate, filterObject} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";
import ApplicationsSearchManage from "./ApplicationsSearchManage";

const confirm = Modal.confirm;

class ApplicationsManage extends React.Component {
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
      formData: {}
    }
  }

  handlerSearch(prop) {
    console.log("查询条件" + JSON.stringify(prop));
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

  handleProvidersLinkClick(text, record, key) {
    let baseFrontUrl = window.location.href.substring(0, window.location.href.indexOf("#"));
    window.location.href = baseFrontUrl + "#/main/dubbo/providers?keyword=" + record.name;

  }

  handleConsumersLinkClick(text, record, key) {
    let baseFrontUrl = window.location.href.substring(0, window.location.href.indexOf("#"));
    window.location.href = baseFrontUrl + "#/main/dubbo/consumers?keyword=" + record.name;
  }

  render() {
    const obj = this;
    const modalIsDetail = {
      footer: ""
    };
    //表格列
    const tableColumns = [
      {
        title: "应用名", dataIndex: "name"
      },
      {
        title: "角色", dataIndex: "status", render(text, record, key){
        if (text == 1) {
          return (<span><span className="greenFont"><a href="javascript:void(0)"
                                                       onClick={() => {obj.handleProvidersLinkClick(text, record, key)}}>提供者</a></span>&nbsp;&nbsp;<span
            className="blueFont"><a href="javascript:void(0)"
                                    onClick={() => {obj.handleConsumersLinkClick(text, record, key)}}>消费者</a></span></span>);
        } else if (text == 2) {
          return (<span className="blueFont"><a href="javascript:void(0)"
                                                onClick={() => {obj.handleConsumersLinkClick(text, record, key)}}>消费者</a></span>);
        } else if (text == 3) {
          return (<span className="greenFont"><a href="javascript:void(0)"
                                                 onClick={() => {obj.handleProvidersLinkClick(text, record, key)}}>提供者</a></span>);
        }
      }
      }
    ];

    return (
      <div>
        <h2>应用</h2><p className="br"/>
        <TdCard hideHead="true" shadow="true">
          <ApplicationsSearchManage onSubmit={this.handlerSearch.bind(this)}
                                    onReset={this.handlerSearchReset.bind(this)}/>
          <p className="br"/>
          <TdPageTable
            checkbox={false}
            url={ylagent.dubbo.applications.getAllApplications}
            loadParam={obj.state.tdTableParam}
            reload={obj.state.tdTableReload}
            renderResult={obj.renderTableList} columns={tableColumns} rowKey={record => record.uuid}/>
        </TdCard>
      </div>
    );
  }
}
//必须有create包装,才会带this.props.form属性
ApplicationsManage = Form.create()(ApplicationsManage);
export default ApplicationsManage;

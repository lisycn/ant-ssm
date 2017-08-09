import React from "react";
import { Modal,Form} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice,buildTableTip} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, parseDate, filterObject} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";

import ConsumerSearchManage from "./ConsumerSearchManage";
import ConsumersManageForm from "./ConsumersManageForm";
import "../style/style.less";

const confirm = Modal.confirm;

class ConsumersManage extends React.Component {
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
      searchPara:{}
    }
  }

  getUrlParamterValue(){
    //url例子：XXX.aspx?ID=" + ID + "&Name=" + Name；
    var url = location.hash; //获取url中"?"符以及其后的字串
    var theRequest = new Object();
    if(url.indexOf("?") != -1)//url中存在问号，也就说有参数。
    {
      url = url.substr(url.indexOf("?")+1);
      const strs = url.split("&");
      for(var i = 0; i < strs.length; i ++)
      {
        theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
      }
    }
    return theRequest;
  }

  componentWillMount(){
    const obj = this;
    const para = obj.getUrlParamterValue();

    obj.setState({
      searchPara:{"keyword":para["keyword"]},
      tdTableParam:{"keyword":para["keyword"]},
      tdTableReload:true
    },()=>{
      tdTableReload:false
    });
  }

  handlerSearch(prop){
    this.setState({tdTableParam:prop,tdTableReload:true}, () => {
      this.setState({ tdTableReload: false });
    });
  }
  handlerSearchReset(){
    console.log("清空查询条件");
  }
  handlerRowSelect(selectedRowKeys, selectedRows){
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys
    });
  }
  handlerViewLinkClick(text, record, key){
    this.setState({modalTitle:'查看消费者者',modalVisible:true,formData:record,modalOprType:3});
  }
  renderTableList(result) {
    return {
      total: result.rspData.total,
      list: result.rspData.list,
      count: {a: "haha"}
    };
  }
  handleModalOk(){
    this.setState({
      modelIsValid: true
    });
  }
  callbackValid(oprType, errors, data){

  }
  render(){
    const obj = this;
    const modalIsDetail = {
      footer: ""
    };
    //表格列
    /*<a href="javascript:void(0)" onClick={() => {obj.handlerEditLinkClick(text, record, key)}}>编辑</a>
    <span className="ant-divider"></span>
      <a href="javascript:void(0)" onClick={() => {obj.handlerDisableLinkClick(text, record, key)}}>禁止</a>
    <span className="ant-divider"></span>
      <a href="javascript:void(0)" onClick={() => {obj.handlerShieldLinkClick(text, record, key)}}>屏蔽</a>
    <span className="ant-divider"></span>
      <a href="javascript:void(0)" onClick={() => {obj.handlerFaultLinkClick(text, record, key)}}>容错</a>*/
    const tableColumns = [
      {title: "机器IP", dataIndex: "address"    ,width: 120, render: (text) => buildTableTip(text, 120)},
      {title: "应用名", dataIndex: "application",width: 130, render: (text) => buildTableTip(text, 130)},
      {title: "服务名", dataIndex: "service"    ,width: 320, render: (text) => buildTableTip(text, 320)},
      {title: "访问"  , dataIndex: "allowed"    ,width: 65 , render(text, record, key){
        return (text == 0 ? <span>已禁止</span>:<span>已允许</span>)
      }},
      {title: "降级"  , dataIndex: "level"      ,width: 65 , render(text, record, key){
        if(text == 2){
          return(<span>已屏蔽</span>);
        }else if(text==3){
          return(<span>已容错</span>);
        }else if(text==1){
          return(<span>未降级</span>);
        }
      }},
      {title: "路由"  , dataIndex: "route"     ,width: 65 ,render(text, record, key){
        return(record.routes && record.routes.length > 0?<span>有路由({record.routes.length})</span>:<span>无路由</span>);
      }},
      {title: "通知"  , dataIndex: "providers" ,width: 90 ,render(text, record, key){
        if(record.providers != null && record.providers.length > 0){
          return(<span>已通知({record.providers.length})</span>);
        }else{
          return(<span className="redFont">未通知</span>);
        }
      }},
      {title: "操作"  , dataIndex: "option",render(text, record, key){
        return(<span>
        <a href="javascript:void(0)" onClick={() => {obj.handlerViewLinkClick(text, record, key)}}>查看</a>
      </span>);
      }}
    ];

    const toolbar = [
      {
        icon: "plus", text: "批量禁止", click: () => {
        obj.handleDisableBtnClick()}
      },
      {
        icon: "check", text: "批量允许", click: () => {
        obj.handleEnableBtnClick()}
      },
      {
        icon: "cross-circle-o", text: "只禁止", click: () => {
        obj.handleJustDisableBtnClick()}
      },
      {
        icon: "check", text: "只允许", click: () => {
        obj.handleJustEnableBtnClick()}
      },
      {
        icon: "cross-circle-o", text: "批量屏蔽", click: () => {
        obj.handleShieldBtnClick()}
      },
      {
        icon: "plus", text: "批量容错", click: () => {
        obj.handleFaultBtnClick()}
      },
      {
        icon: "check", text: "批量回复", click: () => {
        obj.handleReplyClick()}
      }
    ];
    return(
    <div>
      <h2>消费者</h2><p className="br"/>
      <TdCard hideHead="true" shadow="true">
        <ConsumerSearchManage onSubmit={obj.handlerSearch.bind(this)} onReset={obj.handlerSearchReset.bind(this)} searchPara={obj.state.searchPara}/>
        <p className="br"/>
        <TdPageTable
          checkbox={false}
          url={ylagent.dubbo.consumers.getAllComsumers}
          loadParam={obj.state.tdTableParam} reload={obj.state.tdTableReload}
          renderResult={obj.renderTableList} columns={tableColumns} rowKey={record => record.id}/>
      </TdCard>

      <Modal width={700} title={obj.state.modalTitle} visible={obj.state.modalVisible}
             confirmLoading={obj.state.confirmLoading}
             onCancel={() => { obj.setState({ modalVisible: false }); } }
             onOk={obj.handleModalOk.bind(this) }
        {...(this.state.modalOprType === 3 ? modalIsDetail : null) } className="monitorManager">
        <ConsumersManageForm formReset={this.state.formReset}
                             valid={this.state.modelIsValid}
                             formData={this.state.formData}
                             oprType={this.state.modalOprType}
                             validCallback={(oprType, errors, data) => {
                                obj.callbackValid(oprType, errors, data);
                            } }/>

      </Modal>

    </div>);
  }
}
//必须有create包装,才会带this.props.form属性
ConsumersManage = Form.create()(ConsumersManage);
export default ConsumersManage;

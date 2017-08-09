import React from "react";
import {Button, Modal,Form,Tooltip} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice,buildTableTip} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, parseDate, filterObject} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";
import  ProvidersSearchManage from "./ProvidersSearchManage";
import ProvidersManageForm from "./ProvidersManageForm";
import "../style/style.less";

const confirm = Modal.confirm;

class ProvidersManage extends React.Component {
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
      modalTitle:'',
      confirmLoading: false,
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

  handlerViewLinkClick(text, record, key){
    this.setState({modalTitle:'查看提供者',modalVisible:true,formData:record,modalOprType:3});
  }

  handlerSearch(prop){
    this.setState({tdTableParam:prop,tdTableReload:true}, () => {
      this.setState({ tdTableReload: false });
    });
  }
  handlerSearchReset(){
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
  //回调方法
  callbackValid(oprType, errors, data){
    console.log(JSON.stringify(data));
  }
  //模态框确认事件
  handleModalOk(){
    this.setState({
      modelIsValid: true
    });
  }
  render(){
    const obj = this;
    const modalIsDetail = {
      footer: ""
    };


    /*<span className="ant-divider"></span>
    <a href="javascript:void(0)" onClick={() => {obj.handlerEditLinkClick(text, record, key)}}>编辑</a>
    <span className="ant-divider"></span>
      <a href="javascript:void(0)" onClick={() => {obj.handlerCopyLinkClick(text, record, key)}}>复制</a>
    <span className="ant-divider"></span>
      <a href="javascript:void(0)" onClick={() => {obj.handlerDoubledLinkClick(text, record, key)}}>倍权</a>
    <span className="ant-divider"></span>
      <a href="javascript:void(0)" onClick={() => {obj.handlerHalfLinkClick(text, record, key)}}>半权</a>
    <span className="ant-divider"></span>
      <a href="javascript:void(0)" onClick={() => {obj.handlerDisableLinkClick(text, record, key)}}>禁用</a>*/
    //表格列
    const tableColumns = [
      {title: "机器IP", dataIndex: "address"    ,width: 150, render: (text) => buildTableTip(text, 150)},
      {title: "应用"  , dataIndex: "application",width: 150, render: (text) => buildTableTip(text, 150)},
      {title: "服务名", dataIndex: "service"    ,width: 320, render: (text) => buildTableTip(text, 320)},
      {title: "权重"  , dataIndex: "weight"     ,width: 50 , render: (text) => buildTableTip(text, 50) },
      {title: "类型"  , dataIndex: "dynamic"    ,width: 50 , render(text, record, key){
        if(text == true){
          return(<span>动态</span>);
        }else{
          return(<span>静态</span>);
        }
      }},
      {title: "状态"  , dataIndex: "enabled"    ,width: 60, render(text, record, key){
        if(text == true){
          return(<span>已启用</span>);
        }else{
          return(<span>已禁用</span>);
        }
      }},
      {title: "检查"  , dataIndex: "checked"    ,width: 60, render(text,record,key){
        if(text == 1){
          return(<span>正常</span>);
        }else if(text ==2){
          let warnMes = "多个不同应用注册了相同服务，请检查"+record.application+"和"+record.firstApplication+"中是否有误暴露。";
          return(<Tooltip title={warnMes}><span className="yellowFont">警告</span></Tooltip>);
        }else if(text ==3){
          let warnMes = record.address+"不是有效的远程服务地址，请检查提供方/etc/hosts映射是否正确。"
          return(<Tooltip title={warnMes}><span className="yellowFont">警告</span></Tooltip>);
        }
      }},
      {title: "操作"  , dataIndex: "option",render(text, record, key){
        return(<span>
        <a href="javascript:void(0)" onClick={() => {obj.handlerViewLinkClick(text, record, key)}}>查看</a>
      </span>);
      }}
    ];

    //const toolbar = [
    //  {
    //    icon: "plus", text: "新增", click: () => {
    //    obj.handleAddBtnClick()}
    //  },
    //  {
    //    icon: "up", text: "批量倍权", click: () => {
    //    obj.handleDoubleBtnClick()}
    //  },
    //  {
    //    icon: "down", text: "批量半权", click: () => {
    //    obj.handleHalfBtnClick()}
    //  },
    //  {
    //    icon: "cross-circle-o", text: "批量禁用", click: () => {
    //    obj.handleDisableBtnClick()}
    //  },
    //  {
    //    icon: "check", text: "批量启用", click: () => {
    //    obj.handleEnableBtnClick()}
    //  },
    //  {
    //    icon: "cross", text: "批量删除", click: () => {
    //    obj.handleDelBtnClick()}
    //  }
    //];


    return(
      <div>
        <h2>提供者</h2><p className="br"/>
        <TdCard hideHead="true" shadow="true">
          <ProvidersSearchManage onSubmit={this.handlerSearch.bind(this)} onReset={this.handlerSearchReset.bind(this)} searchPara={obj.state.searchPara}/>
          <p className="br"/>
          <TdPageTable
            checkbox={false}
            url={ylagent.dubbo.providers.getAllProviders}
            loadParam={obj.state.tdTableParam} reload={obj.state.tdTableReload}
            renderResult={obj.renderTableList} columns={tableColumns} rowKey={record => record.id}/>
        </TdCard>

        <Modal width={700} title={obj.state.modalTitle} visible={obj.state.modalVisible}
               confirmLoading={obj.state.confirmLoading}
               onCancel={() => { obj.setState({ modalVisible: false }); } }
               onOk={obj.handleModalOk.bind(this) }
          {...(this.state.modalOprType === 3 ? modalIsDetail : null) } className="monitorManager">
          <ProvidersManageForm formReset={this.state.formReset}
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
ProvidersManage = Form.create()(ProvidersManage);
export default ProvidersManage;

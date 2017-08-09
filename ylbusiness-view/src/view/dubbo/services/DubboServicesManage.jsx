import React from "react";
import {message, Button, Modal, Icon,Form } from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice,buildTableTip} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, parseDate, filterObject} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";

import DubboServicesSearchManage from "./DubboServicesSearchManage";

const confirm = Modal.confirm;

class DubboServicesManage extends React.Component {
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

  //点击查看
  handleProAndConsLinkClick(text, record, key,flag){
    let baseFrontUrl = window.location.href.substring(0,window.location.href.indexOf("#"));
    if(flag){
      window.location.href = baseFrontUrl+"#/main/dubbo/providers?keyword="+record.name;
    }else{
      window.location.href = baseFrontUrl+"#/main/dubbo/consumers?keyword="+record.name;
    }
  }

  render(){
    const obj = this;
    const modalIsDetail = {
      footer: ""
    };
    //表格列
    const tableColumns = [
      {title:"服务名" , dataIndex: "name"    ,width: 600, render: (text) => buildTableTip(text, 600)},
      {title:"提供者" ,dataIndex:"providers" ,width: 100, render(text, record, key){
        return( <a href="javascript:void(0)"
                   onClick={() => {obj.handleProAndConsLinkClick(text, record, key,true)}}>查看</a>);
      }},
      {title:"消费者" ,dataIndex:"consumners",width: 100,render(text, record, key){
        return( <a href="javascript:void(0)"
                   onClick={() => {obj.handleProAndConsLinkClick(text, record, key,false)}}>查看</a>);
      }},
      {title:"状态"   , dataIndex: "status"  ,render(text, record, key){
        if(text == 1){
          return(<span>正常</span>);
        }else if(text == 2){
          return(<span className="redFont">没有提供者</span>);
        }else if(text == 3){
          return(<span className="blueFont">没有消费者</span>);
        }
      }}
    ];

    return(
      <div>
        <h2>服务</h2><p className="br"/>
        <TdCard hideHead="true" shadow="true">
            <DubboServicesSearchManage  onSubmit={this.handlerSearch.bind(this)} onReset={this.handlerSearchReset.bind(this)} searchPara={this.state.searchPara}/>
            <p className="br"/>
            <TdPageTable
                checkbox={false}
                 url={ylagent.dubbo.services.getAllServices}
                 loadParam={obj.state.tdTableParam} reload={obj.state.tdTableReload}
                 renderResult={obj.renderTableList} columns={tableColumns} rowKey={record => record.uuid}/>

        </TdCard>
      </div>);
  }
}
//必须有create包装,才会带this.props.form属性
DubboServicesManage = Form.create()(DubboServicesManage);
export default DubboServicesManage;

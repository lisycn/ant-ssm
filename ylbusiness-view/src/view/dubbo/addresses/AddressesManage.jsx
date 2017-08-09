import React from "react";
import {message, Modal,Form  } from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, parseDate, filterObject} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";
import AddressesSearchManage from './AddressesSearchManage';

const confirm = Modal.confirm;

class AdderssesManage extends React.Component {
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
  handlerSearch(prop){
    console.log("查询条件"+JSON.stringify(prop));
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
  handleLinkClick(text, record, key){
    let baseFrontUrl = window.location.href.substring(0,window.location.href.indexOf("#"));
    let condition = "consumers";
    if(record.status == 3){
      condition = "providers"
    }
    window.location.href = baseFrontUrl+"#/main/dubbo/"+condition+"?keyword="+text;
  }
  render(){

    const obj = this;
    const modalIsDetail = {
      footer: ""
    };
    //表格列
    const tableColumns = [
      {title: "机器IP", dataIndex: "name",render(text, record, key){
        return( <a href="javascript:void(0)"
             onClick={() => {obj.handleLinkClick(text, record, key)}}>{text}</a>);
      }},
      {title: "角色", dataIndex: "status",render(text, record, key){
        if(text == 1){
          return(<span><span className="greenFont">提供者</span>&nbsp;&nbsp;<span className="blueFont">消费者</span></span>);
        }else if(text == 2){
          return(<span className="blueFont">消费者</span>);
        }else if(text == 3){
          return(<span className="greenFont">提供者</span>);
        }
        }}
    ];

    return(
      <div>
        <h2>机器</h2><p className="br"/>
        <TdCard hideHead="true" shadow="true">
          <AddressesSearchManage onSubmit={this.handlerSearch.bind(this)} onReset={this.handlerSearchReset.bind(this)}/>
          <p className="br"/>
          <TdPageTable
            checkbox={false}
            url={ylagent.dubbo.addresses.getAllAddresses}
            loadParam={obj.state.tdTableParam} reload={obj.state.tdTableReload}
            renderResult={obj.renderTableList} columns={tableColumns} rowKey={record => record.uuid}/>
        </TdCard>
      </div>
      );
  }
}
//必须有create包装,才会带this.props.form属性
AdderssesManage = Form.create()(AdderssesManage);
export default AdderssesManage;

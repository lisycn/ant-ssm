import React from "react";
import {Row, Col, message, Button, Modal, Icon, Form} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice} from "../../../common/antdUtil";
import {tdJobUrl} from "../../../config/server";
import {callAjax, filterObject} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";

import JobInfoSearchForm from "../jobInfo/JobInfoSearchForm";

class JobLogDetail extends React.Component{
  constructor(props){
    super(props);
    this.state={
      logDetail:''
    };
  }

  /**
   * 取得URL参数值
   * @param name
   */
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

  componentDidMount(){
    const obj = this;
    const para = obj.getUrlParamterValue();
    let opt = {
      url: tdJobUrl.jobLog.logDetail,
      type: "POST",
      dataType: "json",
      data: {id:para["id"]}
    };
    //请求后台添加工作流接口
    callAjax(opt, function (result) {
      if(result.code == "200"){
        if(!result.content){
          obj.setState({logDetail:"暂无内容"});
        }else{
          obj.setState({logDetail:result.content.replace(/\n/g, "<br>")});
        }

      }else{
        obj.setState({logDetail:result.msg});
      }

    }, function (req, info, opt) {
      openNotice("error", "网络错误", "提示");
      obj.setState({
        confirmLoading: false
      });
    });
  }

  render(){
    return(
      <div style={{"color":"white","backgroundColor":"black","minHeight":"100%","width":"100%","overflowX":"auto"}} dangerouslySetInnerHTML={{__html: this.state.logDetail}} />
    );
  }
}

//必须有create包装,才会带this.props.form属性
JobLogDetail = Form.create()(JobLogDetail);
export default JobLogDetail;

import React from "react";
import {Row, Col,Form ,message, Button,  Icon,Timeline } from "antd";
import "./style/style.less";

class TaskMonitorHistoryForm extends React.Component{
  constructor(props) {
    super(props);
    this.state = {
    }
  }

  render(){
    let obj = this;
    const {historyData} =obj.props;

    var data=[];
    historyData.map(function(val,idx){

      //申请人
      if(val.operatetype == '01'){
        data.push(<Timeline.Item key={idx} color="green">
          <p>{val.moduleName}</p>
          <p>申请人：{val.approver}</p>
          <p>申请时间：{val.approveTime}</p>
        </Timeline.Item>);
      }else{
        if(val.logtype == 0 || val.logtype == null || val.logtype == "null"){
          data.push(<Timeline.Item key={idx} color="gray">
            <p>{val.moduleName}</p>
          </Timeline.Item>);
        }
        //else if(val.logtype == 1 && (val.operatetype == '03' || val.operatetype == '04' ||  val.operatetype == '05')){
        //  data.push(<Timeline.Item key={idx} color="gray">
        //    <p>{val.moduleName}</p>
        //  </Timeline.Item>);
        //}
        else{
          data.push(<Timeline.Item key={idx} color="green">
            <p>{val.moduleName}</p>
            <p>审批人：{val.approver}</p>
            <p>审批时间：{val.approveTime}</p>
            <p>审批结果：{val.approveStatus}</p>
            <p>审批意见：{val.approvedescription}</p>
          </Timeline.Item>);
        }
      }
    });


    return(
      <Timeline>
        {data}
      </Timeline>
    );
  }
}

//必须有create包装,才会带this.props.form属性
TaskMonitorHistoryForm = Form.create()(TaskMonitorHistoryForm);
export default TaskMonitorHistoryForm;

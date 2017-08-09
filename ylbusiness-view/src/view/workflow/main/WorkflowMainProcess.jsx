import React, { PropTypes } from "react";
import { Timeline, Spin } from "antd";  //时间轴

class WorkflowModelMainProcess extends React.Component {
    constructor() {
        super();
    }

    render() {
        const { processCfgList,processName} = this.props;
        const loading = false;
      const items = processCfgList.map((item, i) => {
        return (
          <Timeline.Item key={i} color="blue">
            <p>步骤名称：{item.nodename}</p>
            <p>岗位名称：{item.positionname}</p>
            <p>角色名称：{item.roleNames}</p>
          </Timeline.Item>
        )
      });
      if(processCfgList.length === 0){
        return(
          <Spin spinning={loading}><h4>{processName}</h4><br/><h3>暂无数据</h3><br/></Spin>
        );
      }else{
        return (
          <Spin spinning={loading}>
            <h4>{processName}</h4>
            <br/>
            <Timeline>
              {
                items
              }
            </Timeline>
          </Spin>
        );
      }
    }
}

//定义组件标签的可配置属性
WorkflowModelMainProcess.defaultProps = {
    processName: "流程名称",
    data: [],
    loading: false
};

export default WorkflowModelMainProcess;

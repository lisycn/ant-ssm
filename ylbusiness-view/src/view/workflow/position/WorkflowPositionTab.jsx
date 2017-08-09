import React, {PropTypes} from "react";
import {Tabs, Spin} from "antd";
import TdPageTable from "../../../component/TdPageTable";
import {rspInfo} from "../../../common/authConstant";
import {openNotice} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";

class WorkflowModelPositionTab extends React.Component {
  constructor() {
    super();
    this.state = {
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      tdTableParam: {},
      tdTableReload: true,
      defaultActiveKey: "1"
    }
  }

  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys
    });
  }

  nodeList(result) {
    if (result.rspCod === rspInfo.RSP_SUCCESS) {
      return {
        total: 10000,
        list: result.rspData.nodeList
      };
    } else {
      openNotice("error", result.rspMsg, "查询失败");
    }
  }

  positionMappingList(result) {
    if (result.rspCod === rspInfo.RSP_SUCCESS) {
      return {
        total: 10000,
        list: result.rspData.positionMappingList
      };
    } else {
      openNotice("error", result.rspMsg, "查询失败");
    }
  }

  render() {
    const TabPane = Tabs.TabPane;

    const auditColumns = [
      {title: "所属工作流", dataIndex: "modelname", width: 120},
      {title: "审核步骤", dataIndex: "nodename", width: 120}
    ];
    const ruleColumns = [
      {title: "角色信息", dataIndex: "systemrolename", width: 120}
    ];

    return (
      <Tabs>
        <TabPane tab="审核步骤" key="1">
          <TdPageTable scroll={{x:true}}
                       loadParam={this.props.tdTableParam}
                       rowKey={record => record.nodeno}
                       checkbox={false}
                       rowSelectCallback={this.handlerRowSelect.bind(this) }
                       url={ylagent.workflow.modelNode.findNodeListByPositionno}
                       reload={this.state.tdTableReload}
                       renderResult={this.nodeList}
                       pagination={false}
                       columns={auditColumns}/>
        </TabPane>

        <TabPane tab="角色信息" key="2">
          <TdPageTable scroll={{x:true}}
                       loadParam={this.props.tdTableParam}
                       rowKey={record => record.positionmappingno}
                       checkbox={false}
                       rowSelectCallback={this.handlerRowSelect.bind(this) }
                       url={ylagent.workflow.modelPosition.findPositionMappingListByPositionno}
                       reload={this.state.tdTableReload}
                       renderResult={this.positionMappingList}
                       pagination={false}
                       columns={ruleColumns}/>
        </TabPane>
      </Tabs>);

  }
}

//定义组件标签的可配置属性
WorkflowModelPositionTab.defaultProps = {};

export default WorkflowModelPositionTab;

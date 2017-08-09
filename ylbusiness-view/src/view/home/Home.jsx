import './Home.less';
import React from 'react';
import { Row, Col, Table } from 'antd';
import TdCodeBox from '../../component/TdCodeBox';
import TdCard from '../../component/TdCard';
import TdPageTable from '../../component/TdPageTable';
import TdMulUploader from '../../component/TdMulUploader';
import { buildFixedLength } from '../../common/antdUtil';
import { ylagent } from '../../config/server';
import { parseDate } from '../../common/util';
class Home extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tableLoading: false,
      tableData: [],
      tdTableReload: false,
      tdTableParam: {
        sysId: '010',
      },
    };
  }
      // TdPageTable 标签依赖 设置行数选择
  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys,
    });
  }
  renderTableList(result) {
    return {
      total: result.rspData.total,
      list: result.rspData.list,
    };
  }
  render() {
    // const tableColumns = [
    // //   { title: '公告编号', dataIndex: 'noticeId', width: 150, render: (text) => buildFixedLength(text, 150) },
    //   { title: '公告标题', dataIndex: 'noticeTitle', width: 180, render: (text) => buildFixedLength(text, 180) },
    //   { title: '公告内容', dataIndex: 'noticeBody', width: 700, render: (text) => buildFixedLength(text, 700) },
    //  // { title: '公告发布人', dataIndex: 'noticeIssue', width: 100, render: (text) => buildFixedLength(text, 100) },
    //   { title: '公告时间', dataIndex: 'createDate', width: 100, render: (text) => buildFixedLength(parseDate(text), 150) },
    // ];
    return (
      <div><h2>首页</h2><br /></div>
    // <div>
    //    {/* <TdCard hideHead="true" shadow="true">
    //  <p className="br" /> */}
    //       <TdPageTable
    //         url={ylagent.agent.noticeList}
    //         rowSelectCallback={this.handlerRowSelect.bind(this)}
    //         loadParam={this.state.tdTableParam}
    //         reload={this.state.tdTableReload}
    //         renderResult={this.renderTableList}
    //         columns={tableColumns}
    //         checkbox="false"
    //         pagination="false"
    //       />
    //      {/* </TdCard> */}
    // </div>
        );
  }
}


export default Home;

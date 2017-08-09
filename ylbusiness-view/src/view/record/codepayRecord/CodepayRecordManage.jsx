import React from 'react';
import { Row, Col, Tooltip, Form, Input, DatePicker, Select, Table, Button, Modal, Radio, Checkbox, Icon } from 'antd';
import QueueAnim from 'rc-queue-anim';
import TdCard from '../../../component/TdCard';
import { openNotice, buildTableTip } from '../../../common/antdUtil';
import { ylagent } from '../../../config/server';
import { defaultPage } from '../../../common/authConstant';
import { rspInfo } from '../../../common/authConstant';
import { callAjax, parseDate, handleTranstatus, getLocalToken } from '../../../common/util';
import { filterObject } from '../../../common/util';
import CodepayRecordManageForm from './CodepayRecordManageForm';
import TdPageTable from '../../../component/TdPageTable';
import CodepayRecordForm from './CodepayRecordForm';

const confirm = Modal.confirm;
/**
 * CodepayRecordManage 一码付记录
 * Auth:  jiangdi  Tim: 2016-05-10
 */
class CodepayRecordManage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
      tableLoading: false,
      tableData: [],
      tdTableReload: false,
      tdTableParam: {
        sysId: '009',
        token: getLocalToken(),
      },
      modalVisible: false,
      modelIsValid: false,
      modalOprType: 0,
      formReset: false,
      formData: {},
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      modalTitle: '菜单管理',
      confirmLoading: false,
    };
  }

  handleFormSubmit(dat) {
    this.setState({ tdTableReload: true, tdTableParam: dat, tableSelectedRows: [] }, () => {
      this.setState({ tdTableReload: false });
    });
  }
  handleFormReset() {
    this.setState({ formData: {} });
  }
  // 模态框确认点击事件，修改子页面props valid状态,触发子页面执行回调
  handleModalOk() {
    this.setState({
      modelIsValid: true,
    });
  }

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
    // 定义变量和参数
    const modalIsDetail = {
      footer: '',
    };
    const tableColumns = [
      { title: '支付订单号', dataIndex: 'payid', width: 250, render: (text) => buildTableTip(text, 250) },
      { title: '账号', dataIndex: 'account', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '交易金额', dataIndex: 'amount', width: 150, render: (text) => buildTableTip(text, 150) },
      { title: '渠道代码', dataIndex: 'channelCode', width: 150, render: (text) => buildTableTip(text, 150) },
      { title: '二维码地址', dataIndex: 'qrcodeurl', width: 300, render: (text) => buildTableTip(text, 300) },
      { title: '订单编号', dataIndex: 'orderId', width: 150, render: (text) => buildTableTip(text, 150) },
      { title: '扫码订单编号', dataIndex: 'proid', width: 150, render: (text) => buildTableTip(text, 150) },
      { title: '通道返回码', dataIndex: 'respcode', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '通道返回信息', dataIndex: 'respinfo', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '交易结果返回码', dataIndex: 'trancode', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '交易结果返回码描述', dataIndex: 'traninfo', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '通道标识', dataIndex: 'channel', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '接入方', dataIndex: 'accesspart', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '交易状态', dataIndex: 'transtatus', width: 100, render: (text) => buildTableTip(handleTranstatus(text), 100) },
      { title: '下游ID', dataIndex: 'accessid', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '创建时间', dataIndex: 'createtime', width: 150, render: (text) => buildTableTip(parseDate(text), 150) },

    ];

    // 渲染虚拟DOM
    return (
      <div>
        <TdCard hideHead='true' shadow='true'>
          <CodepayRecordManageForm onSubmit={this.handleFormSubmit.bind(this) } onReset={this.handleFormReset.bind(this) } />
          <p className='br' />
          <TdPageTable rowKey={record => record.payid}
            rowSelectCallback={this.handlerRowSelect.bind(this) } scroll={{ x: 1200 }}
            url={ylagent.record.queryCodepayRecord}
            loadParam={this.state.tdTableParam} reload={this.state.tdTableReload}
            renderResult={this.renderTableList} columns={tableColumns}
          />
          <Modal title={this.state.modalTitle} visible={this.state.modalVisible}
            confirmLoading={this.state.confirmLoading}
            onCancel={() => { this.setState({ modalVisible: false }); } }
            onOk={this.handleModalOk.bind(this) }
            {...(this.state.modalOprType === 3 ? modalIsDetail : null) }
          >
            <CodepayRecordForm formReset={this.state.formReset}
              valid={this.state.modelIsValid}
              formData={this.state.formData}
              oprType={this.state.modalOprType}
              validCallback={(oprType, errors, data) => {
                this.callbackValid(oprType, errors, data);
              } }
            />
          </Modal>
        </TdCard>
      </div>
    );
  }
}
// 必须有create包装,才会带this.props.form属性
CodepayRecordManage = Form.create()(CodepayRecordManage);
export default CodepayRecordManage;

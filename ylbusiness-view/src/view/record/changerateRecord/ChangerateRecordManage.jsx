import React from 'react';
import { Row, Col, Tooltip, Form, Input, DatePicker, Select, Table, Button, Modal, Radio, Checkbox, Icon } from 'antd';
import QueueAnim from 'rc-queue-anim';
import TdCard from '../../../component/TdCard';
import { openNotice, buildTableTip } from '../../../common/antdUtil';
import { ylagent } from '../../../config/server';
import { defaultPage } from '../../../common/authConstant';
import { rspInfo } from '../../../common/authConstant';
import { callAjax, parseDate, getLocalToken } from '../../../common/util';
import { filterObject } from '../../../common/util';
import ChangerateRecordManageForm from './ChangerateRecordManageForm';
import TdPageTable from '../../../component/TdPageTable';
import ChangerateRecordForm from './ChangerateRecordForm';

const confirm = Modal.confirm;
/**
 * ChangerateRecordManage 修改费率记录
 * Auth:  jiangdi  Tim: 2016-05-10
 */
class ChangerateRecordManage extends React.Component {
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
      { title: '账号', dataIndex: 'account', width: 150, render: (text) => buildTableTip(text, 150) },
      { title: '微信费率', dataIndex: 'wxRate', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '支付宝费率', dataIndex: 'aliRate', width: 150, render: (text) => buildTableTip(text, 150) },
      { title: '通道码', dataIndex: 'channelCode', width: 150 },
      { title: '返回码', dataIndex: 'respcode', width: 100, render: (text) => buildTableTip(text, 100) },
      { title: '返回信息', dataIndex: 'respinfo', width: 150, render: (text) => buildTableTip(text, 100) },
      { title: '通道标识', dataIndex: 'channel', width: 150, render: (text) => buildTableTip(text, 100) },
      { title: '接入方', dataIndex: 'accesspart', width: 150, render: (text) => buildTableTip(text, 100) },
      { title: '创建时间', dataIndex: 'createtime', width: 150, render: (text) => buildTableTip(parseDate(text), 150) },
    ];

    // 渲染虚拟DOM
    return (
      <div>
        <TdCard hideHead='true' shadow='true'>
          <ChangerateRecordManageForm onSubmit={this.handleFormSubmit.bind(this) } onReset={this.handleFormReset.bind(this) } />
          <p className='br' />
          <TdPageTable rowKey={record => record.changerateid}
            rowSelectCallback={this.handlerRowSelect.bind(this) } scroll={{ x: 1200 }}
            url={ylagent.record.queryChangerateRecord}
            loadParam={this.state.tdTableParam} reload={this.state.tdTableReload}
            renderResult={this.renderTableList} columns={tableColumns}
          />
          <Modal title={this.state.modalTitle} visible={this.state.modalVisible}
            confirmLoading={this.state.confirmLoading}
            onCancel={() => { this.setState({ modalVisible: false }); } }
            onOk={this.handleModalOk.bind(this) }
            {...(this.state.modalOprType === 3 ? modalIsDetail : null) }
          >
            <ChangerateRecordForm formReset={this.state.formReset}
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
ChangerateRecordManage = Form.create()(ChangerateRecordManage);
export default ChangerateRecordManage;

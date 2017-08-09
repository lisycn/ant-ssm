import React from 'react';
import { Form, Modal, Spin, Tabs } from 'antd';
import TdCard from '../../../component/TdCard';
import TdPageTable from '../../../component/TdPageTable';
import { buildFixedLength } from '../../../common/antdUtil';
import { getLoginInfo, requestSelectData } from '../../../common/util';
import { ylagent, tdpub } from '../../../config/server';
import OverviewSearchForm from './OverviewSearchForm';

const confirm = Modal.confirm;
/**
 * AgentManage 代理商管理功能
 *
 * Auth: zhuyongwei  Time: 2016-07-07
 */
const TabPane = Tabs.TabPane;
class OverviewManage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tdTableParam: { token: getLoginInfo().token }, // 表格查询条件
      tdTableReload: false, // 表格是否重新加载
      formReset: false, // 相关联form是否重置
      formData: {}, // 表单初始化数据
      modelIsValid: false, // 模态框是否校验
      tableSelectedRows: [],  // 列表数据选中行数组
      tableSelectedRowKeys: [],
      transferVisible: false, // 代理商移交页面是否展示
      detailVisible: false, // 代理商详情模态框
      modifyBasicVisible: false, // 代理商基本信息修改模态框
      modifyScopeVisible: false, // 代理信息修改模态框
      modifySettleVisible: false, // 结算账户信息修改模态框
      modifyProfitVisible: false, // 代理商分润信息修改模态框
      modifySupportVisible: false, // 实时结算信息修改模态框
      modifyFileVisible: false, // 附件信息修改
      spinLoading: false, // 详情和修改弹框等待图标
      modalOprType: 0, // 模态框操作方式
      defaultActiveKey: 1, // 默认模态框显示页面key
      confirmLoading: false, // 代理商开通实时结算按钮加载标识
      // 下拉框数据加载
      allSelectValues: {},
      picReset: false,
    };
  }
  // 组件构建完成时触发,暂时装载下拉框的所有的数据
  componentDidMount() {
    // 必须要使用obj，否则无法加载内容*****
    const obj = this;
    requestSelectData(tdpub.dict.qryDictMutiList, { type: ['AGENTSTATUS', 'AGENTFROZSTATE', 'AGENTTYPE'], isSpace: true }, true, (oRes) => {
      obj.setState({
        allSelectValues: oRes,
      });
    });
  }


  // 点击“详情”链接事件(text:该单元格数据 record:该行记录对象 key:该行KEY)
  handleDetailModelVisible(text, record) {
    this.setState({
      formData: {},
      spinLoading: true,
      detailVisible: true,
    }, () => {
      this.handleModifyModelData(record);
    });
  }
  /**
  * 搜索查询事件
 */
  handleSearchFormSubmit(dat) {
    this.setState({ tdTableReload: true, tdTableParam: dat, tableSelectedRows: [] }, () => {
      this.setState({ tdTableReload: false });
    });
  }

  /**
  * 表单重置
  */
  handleFormReset() {
    this.setState({ formData: {} });
  }

  /**
   * 列选中回调
   */
  handleRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys,
    });
  }

  // 模态框确认点击事件，修改子页面props valid状态,触发子页面执行回调
  handleModalOk() {
    this.setState({
      modelIsValid: true,
    });
  }

  // 模态框子页面回调
  callbackValid(oprType, errors, data) {
    this.setState({ formReset: true, modelIsValid: false, formData: {}, picReset: true }, () => {
      this.setState({
        formReset: false,
        picReset: false,
      });
    });
  }
  // 渲染数据
  renderTableList(result) {
    console.log(result);
    return {
      total: result.rspData.total,
      list: result.rspData,
      count: { a: 'haha' },
    };
  }
  render() {
    // 定义变量和参数
    const obj = this;
    const getTableData = (dictName, dictValue) => {
      // 获取页面初始化时的数据字典
      const entity = obj.state.allSelectValues;
      // 根据字典名获取数据集
      const arrayList = entity[dictName];
      if (!(typeof (arrayList) === 'undefined')) {
        let result;
        for (let i = 0; i < arrayList.length; i++) {
          if (arrayList[i].value === dictValue) {
            result = arrayList[i].text;
          }
        }
        return result;
      }
      // 如果下拉数据没有初始化成功的话，返回空
      return '';
    };

    const tableColumns = [
      { title: '代理商编号', dataIndex: 'agentId', width: 100, render: (text) => buildFixedLength(text, 100) },
      { title: '代理商登录账号', dataIndex: 'logonName', width: 110, render: (text) => buildFixedLength(text, 110) },
      { title: '代理商名称', dataIndex: 'agentName', width: 100, render: (text) => buildFixedLength(text, 100) },
      { title: '有效商户数', dataIndex: 'merTrueCount', width: 80, render: (text) => buildFixedLength(text, 80) },
      { title: '分享数', dataIndex: 'merCount', width: 70, render: (text) => buildFixedLength(text, 70) },
      { title: '认证数', dataIndex: 'merIdentify', width: 100, render: (text) => buildFixedLength(text, 100) },
      { title: '交易额', dataIndex: 'money', width: 120, render: (text) => buildFixedLength(text, 120) },
      { title: '交易笔数', width: 60, dataIndex: 'payCount', render: (text) => buildFixedLength(text, 60) },
    ];
    return (
      <div>
        <TdCard hideHead='true' shadow='true'>
          <OverviewSearchForm onSubmit={this.handleSearchFormSubmit.bind(this) }
            onReset={this.handleFormReset.bind(this) }
            selectDatas={this.state.allSelectValues}
            valid={this.state.exportValid}
          />
          <p className='br' />
          <TdPageTable rowKey={record => record.agentId}
            rowSelectCallback={this.handleRowSelect.bind(this) }
            scroll={{ x: 1200 }}
            url={ylagent.agent.queryChildAgent}
          //  toolbar={toolbar}
            loadParam={this.state.tdTableParam}
            reload={this.state.tdTableReload}
            renderResult={this.renderTableList.bind(this) }
            columns={tableColumns}
          />
        </TdCard>
      </div>
    );
  }
}
// 必须有create包装,才会带this.props.form属性
OverviewManage = Form.create()(OverviewManage);
export default OverviewManage;

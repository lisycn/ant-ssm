import { ylagent, tdpub } from '../../../config/server';
import NoticeSearchForm from './NoticeSearchForm';
import TdPageTable from '../../../component/TdPageTable';
import React from 'react';
import { Form, Modal, Spin } from 'antd';
import TdCard from '../../../component/TdCard';
import { openNotice, buildFixedLength } from '../../../common/antdUtil';
import { rspInfo } from '../../../common/tdposConstant';
import { callAjax, parseDate, requestSelectData } from '../../../common/util';

/**
 * 公告
 */

class NoticeManage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
      tableLoading: false,
      tableData: [],
      tdTableReload: false,
      tdTableParam: {
        sysId: '010',
      },
      modelIsValid: false,
      modalOprType: 0,
      formReset: false,
      formData: {},
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      // 下拉框数据加载
      allSelectValues: {},
      modalTitle: '',
      confirmLoading: false,

      modalMerCmerEditVisible: false,

      transferData: {},
      merNos: {},
      handleType: 1,
      detailInfo: {},
      spinLoading: false,
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
  componentDidMount() {
    // 必须要使用obj，否则无法加载内容*****
    const obj = this;
    requestSelectData(tdpub.dict.qryDictMutiList, { type: ['NOTICE_PLATFORM'], isSpace: true }, true, (oRes) => {
      obj.setState({
        allSelectValues: oRes,
      });
    });
  }
  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys,
    });
  }
// 事件控制相关方法
  // 点击“详情”链接事件(text:该单元格数据 record:该行记录对象 key:该行KEY)
  handleDetailLinkClick(text, record) {
    console.log(record);
    this.state.formData = record;
    this.setState({
      modalVisible: !this.state.modalVisible,
      modalOprType: 3,
    }, () => {
      // 重置子组件表单数据
      this.setState({ formReset: true }, () => {
        // 将子组件表单重置标识置为false
        this.setState({
          formReset: false,
        });
      });
    });
  }


  handleFormSubmit(dat) {
    this.setState({
      tdTableReload: true,
      tdTableParam: dat,
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
    }, () => {
      this.setState({
        tdTableReload: false,
      });
    });
  }

  handleFormReset() {
    this.setState({ formData: {} });
  }

  // 模态框确认点击事件，修改子页面props valid状态,触发子页面执行回调
  handleModalOk() {
    console.log('click ok');
    this.setState({
      modelIsValid: true,
    }, () => {
      this.setState({
        modelIsValid: false,
      });
    });
    this.disabled();
  }
  // TdPageTable 标签依赖 设置行数选择
  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys,
    });
  }

  // 禁用点击事件
  disabled() {
    document.onclick = new Function('event.returnValue=true;');
  }

  // TdPageTable 标签依赖  设置数据和总条数
  renderTableList(result) {
    return {
      total: result.rspData.total,
      list: result.rspData.list,
    };
  }

  render() {
    // 定义变量和参数
    const obj = this;
    const getTableData = (dictName, dictValue) => {
      // 获取页面初始化时的数据字典
      const entity = this.state.allSelectValues;
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
      { title: '公告编号', dataIndex: 'noticeId', width: 150, render: (text) => buildFixedLength(text, 150) },
      { title: '公告平台', dataIndex: 'noticePlatform', width: 150, render: (text) => buildFixedLength(getTableData('NOTICE_PLATFORM', text), 150) },
      { title: '公告标题', dataIndex: 'noticeTitle', width: 150, render: (text) => buildFixedLength(text, 150) },
      { title: '公告内容', dataIndex: 'noticeBody', width: 300, render: (text) => buildFixedLength(text, 300) },
      { title: '公告发布人', dataIndex: 'noticeIssue', width: 100, render: (text) => buildFixedLength(text, 100) },
      { title: '公告时间', dataIndex: 'createDate', width: 150, render: (text) => buildFixedLength(parseDate(text), 150) },
    ];

   // const toolbar = [
    //  { icon: 'plus', text: '新增', click: () => { obj.handleFormNoticeAdd(); } },
     // { icon: 'swap', text: '修改', click: () => { obj.handlerNoticeEditClick(); } },
    //  { icon: 'delete', text: '删除', click: () => { obj.handlerNoticeDeleteClick(); } },
     // { icon: 'minus', text: '详细', click: () => { obj.handlerEnableBtnClick(); } },

  //  ];
    // 渲染虚拟DOM
    return (
      <div>
        <TdCard hideHead="true" shadow="true">
          <NoticeSearchForm onSubmit={this.handleFormSubmit.bind(this) }
            onReset={this.handleFormReset.bind(this) }
            selectDatas={this.state.allSelectValues}
          />
          <p className="br" />
          <TdPageTable
            rowkey={record => record.noticeId}
            url={ylagent.system.noticeList}
            rowSelectCallback={this.handlerRowSelect.bind(this)}
            loadParam={this.state.tdTableParam}
            toolbar={toolbar}
            reload={this.state.tdTableReload}
            renderResult={this.renderTableList}
            columns={tableColumns}
            scroll={{ x: true }}
          />


        </TdCard>
      </div>
    );
  }
}
// 必须有create包装,才会带this.props.form属性
NoticeManage = Form.create()(NoticeManage);
export default NoticeManage;


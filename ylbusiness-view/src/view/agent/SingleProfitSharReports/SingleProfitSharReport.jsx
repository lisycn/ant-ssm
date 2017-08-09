import React from 'react';
import TdCard from '../../../component/TdCard';
import TdPageTable from '../../../component/TdPageTable';
import SingleProfitSharReportSearch from './SingleProfitSharReportSearch';
import { ylagent, tdpub } from '../../../config/server';
import { openNotice, buildFixedLength } from '../../../common/antdUtil';
import { rspInfo, pubConstant } from '../../../common/tdposConstant';
// 一些通用的方法，调用ajax，格式化日期，格式化下拉字典
import { callAjax, requestSelectData, cent2Yuan } from '../../../common/util';

class SingleProfitSharReport extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      modalOprType: 0,
      formReset: false,
      formData: {},
      modalVisible: false,
      modalSubVisible: false,
      modalOrderVisible: false,
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      tdTableReload: false, // 表格是否重新加载
      tdTableParam: {}, // 表格查询条件
      downloadParam: {}, // 列表下载条件
      total: '', // 数据记录数
      defaultActiveKey: false, // 默认值
      spinLoading: false,
      dtlData: {}, // 详情数据
      dtlDetailModalVisible: false, // 清分列表显示标识
      dtlTdTableParam: {}, // 清分列表查询条件
      buttonLoading: false, // 清算按钮加载标识
      exportValid: false,
      countSum: '',
      txamtSum: '',
      feeSum: '',
      actualTxamtSum: '',
    };
  }

  // 组件构建完成时触发,暂时装载下拉框的所有的数据
  componentDidMount() {
    // 必须要使用obj，否则无法加载内容*****
    const obj = this;
    requestSelectData(tdpub.dict.qryDictMutiList, { type: ['CHANNELCODE'], isSpace: true }, true, (oRes) => {
      obj.setState({
        allSelectValues: oRes,
      });
    });
  }
  // 搜索
  handlerFormSubmit(dat) {
    console.log('dat', Object.assign({}, this.state.tdTableParam, dat));
    const param = Object.assign({}, this.state.tdTableParam, dat);
    this.setState({
      tdTableParam: dat,
      tdTableReload: true,
      downloadParam: param,
    }, () => {
      this.setState({
        tdTableReload: false,
        // tdTableParam: {},
        formReset: true,
      }, () => {
        this.setState({
          formReset: false,
        });
      });
    });
  }

  // 搜索表单重置
  handlerFormReset() {
    this.setState({ formReset: true }, () => {
      this.setState({ formReset: false });
    });
  }
  
  // 点击导出按钮触发
  exportCallback(dat) {
    console.log('导出按钮触发，', dat);
    this.setState({
      tdTableParam: dat,
    });

    const downloadParams = {
      EXPORT_TYPE: pubConstant.PUB_EXPORT_TYPE_AGT_APPLY_COUNT,     // 代理商开户申请统计报表 需要修改
      IS_DEL: pubConstant.PUB_DELETE_FILE_CAN,
      SAVE_DAYS: pubConstant.PUB_SAVE_DAYS,
      TEMPLATENAME: 'singleProfitSharReport.ftl',   // 模版文件
      SERVICE: 'download.SingleProfitSharManageServiceImpl',  // sysconfig.propertities 配置
      FLAG: 'MER',
    };

    const param = Object.assign(dat, downloadParams);

    const opts = {
      url: tdpub.download.downloadFile,  // 统一下载地址   tdpub.download.downloadFile
      type: 'POST',
      dataType: 'json',
      data: param,
    };
    // const obj = this;
    callAjax(opts, (result) => {
      console.log(result);
      if (result.rspCode === rspInfo.RSPCOD_SUCCESS) {
        openNotice('success', result.rspMsg, '提示');
      } else {
        openNotice('error', result.rspMsg, '提示');
      }
    }, (req, info) => {
      console.log(info);
    });
  }

  /**
   * 导出excel按钮点击事件
   */
  handlerButtonExportClick(param) {
    console.log('params', param);
    this.setState({
      exportValid: true,
    }, () => {
      this.setState({
        exportValid: false,
      });
    });
  }


  // 在静态从json文件返回数据时，此时这个函数是不生效的
  renderTableList(result) {
    this.setState({
      feeSum: cent2Yuan(result.rspData.payfeecount),
      countSum: result.rspData.cntNum,
      txamtSum: cent2Yuan(result.rspData.payamtcount),
      actualTxamtSum: cent2Yuan(result.rspData.actualsharamtcount),
    });
    return {
      total: result.rspData.map.total,
      list: result.rspData.map.list,
      count: { a: 'haha' },
    };
  }
  render() {
    // 定义变量的参数
    const obj = this;
    const tableColumns = [
      { title: '代理商编号', width: 120, dataIndex: 'agentId', render: (text) => buildFixedLength(text, 120) },
      { title: '代理商名称', width: 120, dataIndex: 'agentName', render: (text) => buildFixedLength(text, 120) },
      { title: '商户名', width: 120, dataIndex: 'custName', render: (text) => buildFixedLength(text, 120) },
      { title: '交易额（元）', width: 100, dataIndex: 'payamt', render: (text) => buildFixedLength(text, 100) },
      { title: '手续费（元）', width: 100, dataIndex: 'payfee', render: (text) => buildFixedLength(text, 100) },
      { title: '分润额（元）', width: 100, dataIndex: 'sharamt', render: (text) => buildFixedLength(text, 100) },
      { title: '实际分润额（元）', width: 120, dataIndex: 'actualsharamt', render: (text) => buildFixedLength(text, 120) },
      { title: '代理商等级', width: 100, dataIndex: 'agentDgr', render: (text) => buildFixedLength(text, 100) },
      { title: '分润计算日期', width: 100, dataIndex: 'sharDate', render: (text) => buildFixedLength(text, 100) },
      { title: '支付方式', width: 150, dataIndex: 'CHANNELCODE_ZH', render: (text) => buildFixedLength(text, 150) },
    ];
    const toolbar = [
      { icon: 'export', text: '导出EXCEL', click: () => { obj.handlerButtonExportClick(); } },
      { icon: 'heart', text: '交易笔数:[' + obj.state.countSum + ']  ' },
      { icon: 'heart', text: '交易总额:[' + obj.state.txamtSum + ']  ' },
      { icon: 'heart', text: '总手续费:[' + obj.state.feeSum + ']  ' },
      { icon: 'heart', text: '总分润:[' + obj.state.actualTxamtSum + ']  ' },
    ];
    return (
      <TdCard hideHead='true' shadow='true'>
        <SingleProfitSharReportSearch
          onSubmit={this.handlerFormSubmit.bind(this)}
          onReset={this.handlerFormReset.bind(this)}
          valid={this.state.exportValid}
          callback={this.exportCallback.bind(this)}
        />
        <p className='br' />
        <TdPageTable
          rowKey={record => record.payOrdNo}
          url={ylagent.agent.querySingleProfitSharList}
          loadParam={this.state.tdTableParam}
          reload={this.state.tdTableReload}
          renderResult={this.renderTableList.bind(this)}
          columns={tableColumns}
          toolbar={toolbar}
        />
      </TdCard>
    );
  }
}

export default SingleProfitSharReport;

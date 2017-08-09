import React from 'react';
import TdCard from '../../../component/TdCard';
import TdPageTable from '../../../component/TdPageTable';
import QRCodeTransSearchForm from './QRCodeTransSearchForm';
import { ylagent, tdpub } from '../../../config/server';
import { openNotice, buildFixedLength, buildTableTip } from '../../../common/antdUtil';
import { rspInfo, pubConstant } from '../../../common/tdposConstant';
import { callAjax, requestSelectData, cent2Yuan } from '../../../common/util';

class QRCodeTransManage extends React.Component {
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
      exportValid: false,
      feeSum: '',
      countSum: '',
      txamtSum: '',
    };
  }
  // 组件构建完成时触发,暂时装载下拉框的所有的数据
  componentDidMount() {
    // 必须要使用obj，否则无法加载内容*****
    const obj = this;
    requestSelectData(tdpub.dict.qryDictMutiList, { type: ['PAYSTATUS', 'CHANNELCODE', 'SETTLE_STATUS'], isSpace: true }, true, (oRes) => {
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
      EXPORT_TYPE: pubConstant.PUB_EXPORT_TYPE_MER_APPLY_COUNT,     // 代理商开户申请统计报表 需要修改
      IS_DEL: pubConstant.PUB_DELETE_FILE_CAN,
      SAVE_DAYS: pubConstant.PUB_SAVE_DAYS,
      TEMPLATENAME: 'transactionReport.ftl',   // 模版文件
      SERVICE: 'download.TransactionReportServiceImpl',  // sysconfig.propertities 配置
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
      feeSum: cent2Yuan(result.rspData.transactionCount.fee),
      countSum: result.rspData.transactionCount.count,
      txamtSum: cent2Yuan(result.rspData.transactionCount.txamt),
    });
    return {
      total: result.rspData.total,
      list: result.rspData.list,
      count: { a: 'haha' },
    };
  }
  render() {
    // 定义变量的参数
    const obj = this;
    const tableColumns = [
      { title: '商户编号', width: 120, dataIndex: 'custId', render: (text) => buildFixedLength(text, 120) },
      { title: '商户名称', width: 90, dataIndex: 'custName', render: (text) => buildFixedLength(text, 90) },
      { title: '订单金额(元)', width: 100, dataIndex: 'txamt', render: (text) => buildFixedLength(text, 100) },
      { title: '支付时间', width: 120, dataIndex: 'payDate', render: (text) => buildFixedLength(text, 120) },
      { title: '渠道订单号', width: 170, dataIndex: 'channelOrderId', render: (text) => buildTableTip(text, 170) },
      { title: '支付订单号', width: 150, dataIndex: 'payOrdNo', render: (text) => buildFixedLength(text, 150) },
      { title: '代理商编号', width: 120, dataIndex: 'agentId', render: (text) => buildFixedLength(text, 120) },
      { title: '代理商名称', width: 100, dataIndex: 'agentName', render: (text) => buildFixedLength(text, 100) },
      { title: '订单状态', width: 90, dataIndex: 'PAYSTATUS_ZH', render: (text) => buildFixedLength(text, 90) },
      { title: '手续费(元)', width: 90, dataIndex: 'fee', render: (text) => buildFixedLength(text, 90) },
      { title: '实际金额(元)', width: 120, dataIndex: 'netrecamt', render: (text) => buildFixedLength(text, 120) },
      { title: '费率(%)', width: 90, dataIndex: 'rate', render: (text) => buildFixedLength(text, 90) },
      { title: '支付方式', width: 90, dataIndex: 'CHANNELCODE_ZH', render: (text) => buildFixedLength(text, 90) },
      { title: '代付状态', width: 90, dataIndex: 'SETTLE_STATUS_ZH', render: (text) => buildFixedLength(text, 90) },
      { title: '支付渠道', width: 100, dataIndex: 'payChannel', render: (text) => buildFixedLength(text, 100) },
    ];
    // const b = obj.state.total;
    const toolbar = [
      { icon: 'export', text: '导出EXCEL', click: () => { obj.handlerButtonExportClick(); } },
      { icon: 'heart', text: '汇总笔数和金额:[' + obj.state.countSum + ']笔 | [' + obj.state.txamtSum + ']元  ' },
      { icon: 'heart', text: '手续费总额:[' + obj.state.feeSum + ']元  ' },
    ];
    return (
      <TdCard hideHead='true' shadow='true'>
        <QRCodeTransSearchForm
          onSubmit={this.handlerFormSubmit.bind(this)}
          onReset={this.handlerFormReset.bind(this)}
          valid={this.state.exportValid}
          selectDatas={this.state.allSelectValues}
          callback={this.exportCallback.bind(this)}
        />
        <p className='br' />
        <TdPageTable
          rowKey={record => record.payOrdNo}
          url={ylagent.report.queryTransactionList}
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

export default QRCodeTransManage;

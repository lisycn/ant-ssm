import React from 'react';
import { Form, Modal, Spin } from 'antd';
import TdCard from '../../../component/TdCard';
// import QueueAnim from 'rc-queue-anim';
import { openNotice, buildTableTip, buildFixedLength } from '../../../common/antdUtil';
import { ylpay, tdpub } from '../../../config/server';
import { rspInfo } from '../../../common/tdposConstant';
import { posConstant, pubConstant } from '../../../common/tdposConstant';
// 暂时没有用到callAjax，但是调用后台交易一定用的到
import { callAjax, parseDate, requestSelectData } from '../../../common/util';
import TdPageTable from '../../../component/TdPageTable';

// 引入商户基本信息管理-查询条件组件
import MermManageSerachForm from './MermManageSerachForm';
// 商户修改费率
import MermRateEditForm from './MermRateEditForm';
// 商户绑定代理商
import MermBindAgentForm from './MermBindAgentForm';
// 商户实名认证
import MermCustAduitForm from './MermManageDetailInfForm';

// 测试信息，获取需要批量移交的商户信息
const confirm = Modal.confirm;

/**
 *
 */
class MermManage extends React.Component {
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
      // 商户费率修改
      modalMerRateEditVisible: false,
      // 商户绑定代理商
      modalMerBindAgentVisible: false,
      modalMerCustAuditVisible: false,
      modelIsValid: false,
      modalOprType: 0,
      formReset: false,
      formData: {},
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      // 商户信息修改时的ID
      curUpdateFlag: '',
      curUpdateId: '',
      // 下拉框数据加载
      allSelectValues: {},
      modalTitle: '商户信息管理',
      confirmLoading: false,
      test: 'json:demo00TableData.json',
      transferData: {},
      merNos: {},
      handleType: 1,
      detailInfo: {},
      spinLoading: false,
    };
  }

  // 组件构建完成时触发,暂时装载下拉框的所有的数据
  componentDidMount() {
    // 必须要使用obj，否则无法加载内容*****
    const obj = this;
    requestSelectData(tdpub.dict.qryDictMutiList, { type: ['AUTHSTATUS', 'LOGINSTATUS', 'EXAMISTATUS'], isSpace: true }, true, (oRes) => {
      obj.setState({
        allSelectValues: oRes,
      });
    });
  }
  handleFormCancelMerBindAgent() {
    this.setState({
      modalMerBindAgentVisible: !this.state.modalMerBindAgentVisible,
    });
  }
  handleFormCancelMerRateEdit() {
    this.setState({
      modalMerRateEditVisible: !this.state.modalMerRateEditVisible,
    });
  }
  handleFormCancelMerCustAudit() {
    this.setState({
      modalMerCustAuditVisible: !this.state.modalMerCustAuditVisible,
    });
  }
  handleFormSubmit(d) {
    if (d.BIZ_SALE !== null && d.BIZ_SALE !== '' && d.BIZ_SALE !== undefined) {
      const param = { USR_ID: d.BIZ_SALE };
      const opts = {
        url: ylpay.mer.selectUsrNameByUserId,
        type: 'POST',
        dataType: 'json',
        data: param,
      };
      callAjax(opts, (result) => {
        if (result !== null) {
          let dataParams = {};
          const bizparams = {
            BIZ_SALE: result.resultList[0].USR_NAME,
          };
          dataParams = Object.assign(d, bizparams);
          this.setState({ tdTableReload: true, tdTableParam: dataParams }, () => {
            this.setState({ tdTableReload: false });
          });
        } else {
          openNotice('error', '', '该业务员不存在');
        }
      }, () => {
        openNotice('error', posConstant.RSP_NETWORK_ERROR, '提示');
      });
    }
    const dat = d;
    // 地区级联格式转换
    if (dat.MER_AREA) {
      // 省份
      dat.MER_PROV = dat.MER_AREA[0];
      // 城市
      dat.MER_CITY = dat.MER_AREA[1];
      // 区县
      dat.MER_AREA = dat.MER_AREA[2];
    }
    this.setState({ tdTableReload: true, tdTableParam: dat }, () => {
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

  /**
   * 导出excel按钮点击事件
   */
  handlerButtonExportClick() {
    const downloadParams = {
      EXPORT_TYPE: pubConstant.PUB_EXPORT_TYPE_MER_BASIC,
      IS_DEL: pubConstant.PUB_DELETE_FILE_CAN,
      SAVE_DAYS: pubConstant.PUB_SAVE_DAYS,
      TEMPLATENAME: 'merManageInfo.ftl',
      SERVICE: 'download.merManageServiceImpl',
    };
    const param = Object.assign(this.state.tdTableParam, downloadParams);
    const opts = {
      url: tdpub.download.downloadFile,
      type: 'POST',
      dataType: 'json',
      data: param,
    };
    callAjax(opts, (result) => {
      if (result.rspCod === posConstant.POS_SUCCEESS) {
        openNotice('success', result.rspMsg, '提示');
      } else {
        openNotice('error', result.rspMsg, '提示');
      }
    }, () => {
      // console.log(info);
    });
  }

  exportCallback(dat) {
    this.setState({
      tdTableParam: dat,
    });
    openNotice('success', '下载成功，请到下载中心查看！', '提示');
    // const downloadParams = {
    //   'EXPORT_TYPE': pubConstant.PUB_EXPORT_TYPE_MER_BASIC,
    //   'IS_DEL': pubConstant.PUB_DELETE_FILE_CAN,
    //   'SAVE_DAYS': pubConstant.SAVE_DAYS,
    //   'TEMPLATENAME': 'merInfoManage.ftl',
    //   'SERVICE': 'download.merServiceImpl',
    // };

    // const param = Object.assign(dat, downloadParams);
    // console.log('param', param);

    // let opts = {
    //   url: tdpay.download.downloadFile,
    //   type: 'POST',
    //   dataType: 'json',
    //   data: param,
    // };
    // let obj = this;
    // callAjax(opts, function (result) {
    //   if (result.rspCod === rspInfo.COMM_SUCCESS) {
    //     openNotice('success', result.rspMsg, '提示');
    //   } else {
    //     openNotice('error', result.rspMsg, '提示');
    //   }
    // }, function (req, info, opts) {
    //   console.log(info);
    // });
  }
  // 登录锁定解锁
  handlerEnableBtnClick() {
    if (this.state.tableSelectedRows.length === 1) {
      const obj = this;
      const row = obj.state.tableSelectedRows[0];
      if (row.isLoginFlag === '00') {
        this.handlerRecordSts('disable');
      }
      if (row.isLoginFlag === '01') {
        this.handlerRecordSts('enable');
      }
    } else if (this.state.tableSelectedRows.length > 1) {
      openNotice('warning', '不允许同时操作多个商户');
    } else {
      openNotice('warning', '请选择需要操作的商户');
    }
  }

  handlerRecordSts(param) {
    const obj = this;
    // const data = obj.state.tableSelectedRows;
    const row = obj.state.tableSelectedRows[0];
    if (param === 'disable' && row.isLoginFlag === '01') {
      openNotice('warn', '商户登录状态已开通，无需操作', '提示');
      return;
    }
    if (param === 'enable' && row.isLoginFlag === '00') {
      openNotice('warn', '商户登录状态已关闭，无需操作', '提示');
      return;
    }
    // let MER_NOS = '';
    // data.forEach(v => (MER_NOS = MER_NOS + ',' + v.MER_NO + ''));
    confirm({
      title: `您是否确认要 ${(param === 'enable' ? '【开通】' : '【关闭】')} 选中项`,
      content: '',
      onOk() {
        const opt = {
          url: ylpay.mer.updateMerStatus,
          data: { status: (param === 'enable' ? '00' : '01'), custId: row.custId },
        };
        callAjax(opt, (result) => {
          if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
            openNotice('success', `此商户已${(param === 'enable' ? '开通' : '关闭')}`, '提示');
            obj.setState({
              tdTableReload: true,
              tableSelectedRows: [],
              tableSelectedRowKeys: [],
            }, () => {
              obj.setState({ tdTableReload: false });
            });
          } else {
            openNotice('error', result.rspMsg, `${(param === 'enable' ? '开通' : '关闭')}业务状态失败`);
          }
        }, () => {
          openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
        });
        // openNotice('success', (param === 'enable' ? '开通' : '关闭') + '商户业务状态成功', '提示');
        obj.setState({
          tdTableReload: true,
          tableSelectedRows: [],
          tableSelectedRowKeys: [],
        }, () => {
          obj.setState({ tdTableReload: false });
        });
      },
      onCancel() { },
    });
  }
  // 商户费率修改
  handleIndEditRateClick() {
    this.setState({
      modalMerRateEditVisible: !this.state.modalMerRateEditVisible,
      modalOprType: 2,
      handleType: 2,
      modalTitle: '商户费率修改',
      formData: this.state.tableSelectedRows[0],
      spinLoading: false,
    }, () => {
          // 重置子组件表单数据
      this.setState({ formReset: true }, () => {
            // 将子组件表单重置标识置为false
        this.setState({
          formReset: false,
          spinLoading: false,
        });
      });
    });
  }

  // 绑定代理商
  handleIndBIndAgentClick() {
    this.setState({
      modalMerBindAgentVisible: !this.state.modalMerBindAgentVisible,
      modalOprType: 2,
      handleType: 2,
      modalTitle: '绑定代理商',
      formData: this.state.tableSelectedRows[0],
      spinLoading: false,
    }, () => {
          // 重置子组件表单数据
      this.setState({ formReset: true }, () => {
            // 将子组件表单重置标识置为false
        this.setState({
          formReset: false,
          spinLoading: false,
        });
      });
    });
  }
  // 商户费率修改回调
  updMerRatecallbackValid(handleType, errors, data) {
    this.setState({
      modelIsValid: false,
    });
    if (!!errors) {
      // return;
    } else {
      this.setState({
        formData: data,
        formReset: false,
      }, () => {
        const obj = this;
        const opt = {
          url: ylpay.mer.updateMerRateInfo,
          data,
        };
        callAjax(opt, (result) => {
          if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
            openNotice('success', '商户费率信息修改成功', '提示');
            obj.props.form.resetFields();
            obj.setState({
              modalMerRateEditVisible: !obj.state.modalMerRateEditVisible,
              modalOprType: 0,
              tdTableReload: true,
            }, () => {
              obj.setState({
                tdTableReload: false,
                tableSelectedRows: [],
                tableSelectedRowKeys: [],
              });
            });
          } else {
            openNotice('error', result.rspMsg, '商户费率修改失败');
          }
        }, () => {
          openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
        });
      });
    }
  }
  // 商户绑定代理商回调
  merBindAgentcallbackValid(handleType, errors, data) {
    this.setState({
      modelIsValid: false,
    });
    if (!!errors) {
      // return;
    } else {
      this.setState({
        formData: data,
        formReset: false,
      }, () => {
        const obj = this;
        const opt = {
          url: ylpay.mer.bindAgent,
          data,
        };
        callAjax(opt, (result) => {
          if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
            openNotice('success', '商户绑定代理商成功', '提示');
            obj.props.form.resetFields();
            obj.setState({
              modalMerBindAgentVisible: !obj.state.modalMerBindAgentVisible,
              modalOprType: 0,
              tdTableReload: true,
            }, () => {
              obj.setState({
                tdTableReload: false,
                tableSelectedRows: [],
                tableSelectedRowKeys: [],
              });
            });
          } else {
            openNotice('error', result.rspMsg, '商户绑定代理商失败');
          }
        }, () => {
          openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
        });
      });
    }
  }
  // 在静态从json文件返回数据时，此时这个函数是不生效的
  renderTableList(result) {
    return {
      total: result.rspData.total,
      list: result.rspData.list,
      count: { a: 'haha' },
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
      { title: '所属代理商', width: 100, dataIndex: 'agentName', render: (text) => buildFixedLength(text, 100) },
      { title: '所属代理商编号', width: 130, dataIndex: 'agentId', render: (text) => buildFixedLength(text, 130) },
      { title: '分享人名称', width: 90, dataIndex: 'referName', render: (text) => buildFixedLength(text, 90) },
      { title: '分享人手机号', width: 90, dataIndex: 'referMobile', render: (text) => buildFixedLength(text, 90) },
      { title: '商户名称', width: 90, dataIndex: 'custName', render: (text) => buildFixedLength(text, 90) },
      { title: '商户费率', width: 70, dataIndex: 'rateQrCode', render: (text) => buildFixedLength(text, 70) },
      { title: '商户编号', width: 130, dataIndex: 'custId', render: (text) => buildFixedLength(text, 130) },
      { title: '手机号  ', width: 100, dataIndex: 'usrMobile', render: (text) => buildFixedLength(text, 100) },
      { title: '固定码', width: 100, dataIndex: 'qrCodeId', render: (text) => buildFixedLength(text, 100) },
      { title: '注册时间', width: 140, dataIndex: 'custRegDateTime', render: (text) => buildFixedLength(parseDate(text), 140) },
      { title: '登录状态', width: 90, dataIndex: 'isLoginFlag', render: (text) => buildFixedLength(getTableData('LOGINSTATUS', text), 90) },
      { title: '认证状态', width: 80, dataIndex: 'authStatus', render: (text) => buildFixedLength(getTableData('AUTHSTATUS', text), 80) },
      { title: '银行卡审核状态', width: 110, dataIndex: 'examiStatus', render: (text) => buildFixedLength(getTableData('EXAMISTATUS', text), 110) },
      { title: '扫码付审核状态', width: 110, dataIndex: 'auditStatus', render: (text) => buildFixedLength(getTableData('AUDITSTATUS', text), 110) },
      { title: '证件号码', width: 150, dataIndex: 'certificateNo', render: (text) => buildFixedLength(text, 150) },
      { title: '银行卡', width: 170, dataIndex: 'cardNo', render: (text) => buildFixedLength(text, 170) },
    ];

    const toolbar = [
      // { icon: 'export', text: '导出EXCEL', click: () => { obj.handlerButtonExportClick(); } },
      { icon: 'swap', text: '锁定/解锁', click: () => { obj.handlerEnableBtnClick(); } },
      { icon: 'swap', text: '修改费率', click: () => { obj.handleIndEditRateClick(); } },
      { icon: 'swap', text: '绑定代理商', click: () => { obj.handleIndBIndAgentClick(); } },
    ];
    // 渲染虚拟DOM
    return (
      <TdCard hideHead='true' shadow='true'>
        <MermManageSerachForm onSubmit={this.handleFormSubmit.bind(this)} onReset={this.handleFormReset.bind(this)}
          selectDatas={this.state.allSelectValues}
          valid={this.state.exportValid}
          callback={this.exportCallback.bind(this)}
        />
        <p className='br' />
        <TdPageTable
          rowKey={record => record.custId}
          rowSelectCallback={this.handlerRowSelect.bind(this)}
          toolbar={toolbar}
          url={ylpay.mer.qryMerInfList}
          loadParam={this.state.tdTableParam} reload={this.state.tdTableReload}
          renderResult={this.renderTableList}
          columns={tableColumns}
        />
        <Modal width={400} title={this.state.modalTitle} visible={this.state.modalMerRateEditVisible}
          onCancel={() => { this.setState({ modalMerRateEditVisible: false }); } }
          footer={null}
        >
          <Spin spinning={this.state.spinLoading}>
            <MermRateEditForm formReset={this.state.formReset}
              valid={this.state.modelIsValid}
              oprType={this.state.modalOprType}
              handleType={this.state.handleType}
              formData={this.state.formData}
              handleFormCancel={this.handleFormCancelMerRateEdit.bind(this)}
              validCallback={(handleType, errors, data) => {
                this.updMerRatecallbackValid(handleType, errors, data);
              } }
            />
          </Spin>
        </Modal>
        <Modal width={400} title={this.state.modalTitle} visible={this.state.modalMerBindAgentVisible}
          onCancel={() => { this.setState({ modalMerBindAgentVisible: false }); } }
          footer={null}
        >
          <Spin spinning={this.state.spinLoading}>
            <MermBindAgentForm formReset={this.state.formReset}
              valid={this.state.modelIsValid}
              oprType={this.state.modalOprType}
              handleType={this.state.handleType}
              formData={this.state.formData}
              handleFormCancel={this.handleFormCancelMerBindAgent.bind(this)}
              validCallback={(handleType, errors, data) => {
                this.merBindAgentcallbackValid(handleType, errors, data);
              } }
            />
          </Spin>
        </Modal>
        <Modal width={900} title={this.state.modalTitle} visible={this.state.modalMerCustAuditVisible}
          onCancel={() => { this.setState({ modalMerCustAuditVisible: false }); } }
          footer={null}
        >
          <Spin spinning={this.state.spinLoading}>
            <MermCustAduitForm formReset={this.state.formReset}
              valid={this.state.modelIsValid}
              oprType={this.state.modalOprType}
              handleType={this.state.handleType}
              formData={this.state.formData}
              handleFormCancel={this.handleFormCancelMerCustAudit.bind(this)}
              validCallback={(handleType, errors, data) => {
                this.merCustAuditcallbackValid(handleType, errors, data);
              } }
            />
          </Spin>
        </Modal>
      </TdCard>
    );
  }
}
// 必须有create包装,才会带this.props.form属性
MermManage = Form.create()(MermManage);
export default MermManage;

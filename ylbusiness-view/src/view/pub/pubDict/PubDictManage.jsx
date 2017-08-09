import React from 'react';
import { Form, Button, Modal } from 'antd';
import TdCard from '../../../component/TdCard';
import { openNotice, buildTableTip } from '../../../common/antdUtil';
import { tdpos } from '../../../config/server';
import { rspInfo } from '../../../common/authConstant';
import { callAjax, filterObject } from '../../../common/util';
import TdPageTable from '../../../component/TdPageTable';
import PubDictManageForm from './PubDictManageForm';
import PubDictForm from './PubDictForm';
import { ylagent } from '../../../config/server';
const confirm = Modal.confirm;


class PubDictManage extends React.Component {
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
      modalVisible: false,
      modelIsValid: false,
      modalOprType: 0,
      formReset: false,
      formData: {},
      tableSelectedRows: [],
      tableSelectedRowKeys: [],
      modalTitle: '码表管理',
      confirmLoading: false,
      loading: false,
    };
  }
  handleFormSubmit(dat) {
    console.log(this.props.form);
    console.log('dictCode=', this.props.form.getFieldValue('dictCode'));
    console.log('dictName=', this.props.form.getFieldValue('dictName'));
    console.log('查询条件1：', dat);
    const param = {
      sysId: '010',
      dictCode: (dat.dictCode === null || dat.dictCode === undefined || dat.dictCode === 'undefined') ? '' : dat.dictCode.trim(),
      dictName: (dat.dictName === null || dat.dictName === undefined || dat.dictName === 'undefined') ? '' : dat.dictName.trim(),
    };
    console.log(param);
    this.setState({ tdTableReload: true, tdTableParam: dat }, () => {
      this.setState({ tdTableReload: false });
    });
  }
  handleFormReset() {
    this.setState({ formData: {} });
  }
  renderTableList(result) {
    return {
      total: result.rspData.total,
      list: result.rspData.total === 0 ? null : result.rspData.DATA,
    };
  }
  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys,
    });
  }
  handleAddBtnClick() {
    const tableSelectedRows = this.state.tableSelectedRows;
    if (tableSelectedRows !== undefined && tableSelectedRows.length === 1) {
      this.setState({
        formData: {
          PARENT_ID: tableSelectedRows[0].DICT_ID,
          DICT_CODE: tableSelectedRows[0].DICT_CODE,
          DICT_LEVEL: parseInt(tableSelectedRows[0].DICT_LEVEL) + 1,
        },
        modalVisible: !this.state.modalVisible,
        modalOprType: 1,
        modalTitle: '添加',
      }, () => {
        this.setState({ formReset: true }, () => {
          this.setState({
            formReset: false,
          });
        });
      });
    } else if (tableSelectedRows !== undefined && tableSelectedRows.length > 1) {
      openNotice('warning', '不支持对批量参数编码进行添加参数操作，只能选择一条');
    } else {
      this.setState({
        formData: {
          DICT_LEVEL: 1,
        },
        modalVisible: !this.state.modalVisible,
        modalOprType: 1,
        modalTitle: '添加',
      }, () => {
        this.setState({ formReset: true }, () => {
          this.setState({
            formReset: false,
          });
        });
      });
    }
  }
  callbackValid(oprType, errors, data) {
    this.setState({
      loading: true,
      modelIsValid: false,
    });
    const obj = this;
    if (!!errors) {
      console.log('表单校验失败!', errors);
      this.setState({ loading: false });
      return;
    } else {
      console.log('子页面表单校验成功。');
      console.log('modal form data=>' + data);
      this.setState({
        formData: Object.assign({}, this.state.formData, data),
        formReset: false,
        confirmLoading: true,
      }, () => {
        switch (oprType) {
          case 1:
            obj.handleAddDictInfo();
            break;
          case 2:
            obj.handleEditDictInfo();
            break;
          default:
            openNotice('error', '操作失败');
            break;
        }
      });
    }
  }
  handleAddDictInfo() {
    const obj = this;
    const formData = filterObject(this.state.formData);
    if (formData.keys.length > 0) {
      formData.keysStr = formData.keys.join(',');
    }
    const opt = {
      url: tdpos.pubDict.addPubDictInf,
      type: 'POST',
      dataType: 'json',
      data: formData,
    };
    console.log('data 1====>',formData);
    if (formData.DICT_LEVEL !== 1){
      sessionStorage.removeItem('DICT_SELECT_' + formData.DICT_CODE);
    }
    callAjax(opt, function (result) {
      if (result.rspCod === '01000000') {
        openNotice('success', '添加参数成功', '提示');
        obj.props.form.resetFields();
        obj.setState({
          modalVisible: !obj.state.modalVisible,
          modalOprType: 0,
          tdTableReload: true,
          confirmLoading: false,
        }, () => {
          obj.setState({
            tdTableReload: false,
            loading: false,
          });
        });
      } else {
        openNotice('error', result.rspMsg, '提示');
        obj.setState({
          confirmLoading: false,
          loading: false,
        });
      }
    }, function (req, info, opt) {
      console.log(info);
      openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
      obj.setState({
        confirmLoading: false,
        loading: false,
      });
    });
  }

  handleEditDictInfo() {
    const formData = filterObject(this.state.formData);
    const opt = {
      url: tdpos.pubDict.updPubDictInf,
      type: 'POST',
      dataType: 'json',
      data: formData,
    };
    console.log('data 2====>',formData);
    const obj = this;
    sessionStorage.removeItem('DICT_SELECT_' + formData.DICT_CODE);
    callAjax(opt, function (result) {
      if (result.rspCod === '01000000') {
        openNotice('success', '修改参数成功', '提示');
        obj.props.form.resetFields();
        obj.setState({
          modalVisible: !obj.state.modalVisible,
          modalOprType: 0,
          tdTableReload: true,
          confirmLoading: false,
        }, () => {
          obj.setState({
            tdTableReload: false,
            loading: false,
          });
        });
      } else {
        openNotice('error', result.rspMsg, '提示');
        obj.setState({
          confirmLoading: false,
          loading: false,
        });
      }
    }, (info) => {
      console.log(info);
      openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
      obj.setState({
        confirmLoading: false,
        loading: false,
      });
    });
  }
  handleEditLinkClick(text, record, key) {
    this.setState({
      formData: record,
      modalVisible: !this.state.modalVisible,
      modalOprType: 2,
      modalTitle: '修改',
    }, () => {
      this.setState({ formReset: true }, () => {
        this.setState({
          formReset: false,
        });
      });
    });
  }
  handleModalOk() {
    this.setState({ modelIsValid: true });
  }
  handlEditModalOk() {
    const editFormData = filterObject(this.props.form.getFieldsValue());
    this.props.form.validateFields((errors, values) => {
      if (!!errors) {
        console.log('Errors in form!!!');
        return;
      }
      this.setState({
        buttonLoading: true,
      }, () => {
        if (editFormData.DICT_ID) {
          const opt = {
            url: tdpos.pubDict.updPubDictInf,
            type: 'POST',
            dataType: 'json',
            data: editFormData,
          };
          sessionStorage.removeItem('DICT_SELECT_' + editFormData.DICT_CODE);
          console.log('data 3====>',editFormData);
          const obj = this;
          callAjax(opt, (result) => {
            console.log(result);
            if (result.rspCod === rspInfo.COMM_SUCCESS) {
              openNotice('success', '修改参数成功', '提示');
              obj.props.form.resetFields();
              obj.setState({
                modalVisible: !obj.state.modalVisible,
                tdTableReload: true,
              }, () => {
                obj.setState({
                  tdTableReload: false,
                  loading: false,
                });
              });
            } else {
              openNotice('error', result.rspMsg, '修改参数失败');
              obj.setState({ loading: false });
            }
          }, (info) => {
            console.log(info);
            obj.setState({ loading: false });
            openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
          });
        } else {
          openNotice('error', '请输入参数值', '提示');
          obj.setState({ loading: false });
        }
      });
    });
  }
  dictCodeValidate(rule, value, callback) {
    if (!value) {
      callback();
    } else {
      if (value.length > 20) {
        callback('参数编码长度需小于等于20！');
      }
      const numReg = /[a-zA-Z0-9]+/;
      if (!numReg.test(value)) {
        callback('参数编码只能有字母、数字和下划线');
        return false;
      } else {
        callback();
      }
    }
    callback();
  }
  getFormData() {
    return this.props.form.getFieldsValue();
  }
  handlerDeleteBtnClick() {
    if (this.state.tableSelectedRows.length >= 1) {
      const obj = this;
      let data = obj.state.tableSelectedRows;
      let Dict_Cdes = '';
      console.log('data 4====>',data);
      data.forEach(v => (Dict_Cdes = Dict_Cdes + ",'" + v.DICT_ID + "'"));
      confirm({
        title: '您是否确认要删除选中项及以下的子项',
        content: '',
        onOk() {
          sessionStorage.removeItem('DICT_SELECT_' + data[0].DICT_CODE);
          const opt = {
            url: tdpos.pubDict.delPubDictInf,
            type: 'POST',
            dataType: 'json',
            data: { DICT_IDS: Dict_Cdes.substring(1, Dict_Cdes.length) },
          };      
          callAjax(opt, (result) => {
            if (result.rspCod === '01000000') {
              openNotice('success', '删除参数成功', '提示');
              obj.setState({
                tdTableReload: true,
                tableSelectedRowKeys: [],
                tableSelectedRows: [],
              }, () => {
                obj.setState({
                  tdTableReload: false,
                });
              });
            } else {
              openNotice('error', result.rspMsg, '提示');
            }
          },  (info) =>{
            console.log(info);
            openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
          });
        },
        onCancel() { },
      });
    }
    else {
      openNotice('warn', '请选择需删除的记录');
    }
  }
  handlerEnableBtnClick(param) {
    if (this.state.tableSelectedRows.length >= 1) {
      this.handlerRecordSts('enable');
    } else {
      openNotice('warning', '请选择需启用的记录');
    }
  }
  handlerDisableBtnClick() {
    if (this.state.tableSelectedRows.length >= 1) {
      this.handlerRecordSts('disable');
    }
    else {
      openNotice('warning', '请选择需禁用的记录');
    }
  }
  handlerRecordSts(param) {
    const obj = this;
    let data = obj.state.tableSelectedRows;
    let row = obj.state.tableSelectedRows[0];
    console.log('param 55====>',row.DICT_CODE);
    console.log('data 55====>',data);
    if (param === 'enable' && row.STATUS === '1') {
      openNotice('warn', '状态已启用，无需操作', '提示');
      return;
    }
    if (param === 'disable' && row.STATUS === '0') {
      openNotice('warn', '状态已禁用，无需操作', '提示');
      return;
    }
    let DICT_IDS = '';
    data.forEach(v => (DICT_IDS = DICT_IDS + ',' + v.DICT_ID + ''));
    confirm({
      title: '您是否确认要' + (param === 'enable' ? '启用' : '禁用') + '选中项',
      content: '',
      onOk() {
        sessionStorage.removeItem('DICT_SELECT_' + row.DICT_CODE);
        console.log('row.DICT_CODE',sessionStorage.getItem('DICT_SELECT_' + row.DICT_CODE));
        const opt = {
          url: tdpos.pubDict.updPubDictStatus,
          data: { STATUS: (param === 'enable' ? '1' : '0'), DICT_IDS: DICT_IDS.substring(1, DICT_IDS.length) },
        };
        callAjax(opt, (result) => {
          if (result.rspCod === '01000000') {
            openNotice('success', (param === 'enable' ? '启用' : '禁用') + '参数成功', '提示');
            obj.setState({
              tdTableReload: true,
              tableSelectedRows: [],
              tableSelectedRowKeys: [],
            }, () => {
              obj.setState({ tdTableReload: false });
            });
          } else {
            openNotice('error', result.rspMsg, (param === 'enable' ? '启用' : '禁用') + '参数失败');
          }
        }, () => {
          openNotice('error', '网络错误', '提示');
        });
      },
      onCancel() { },
    });
  }
  handlerRediuBtnClick(param) {
    const obj = this;
    confirm({
      title: '您是否确认要刷新缓存么',
      content: '',
      onOk() {
        const opt = {
          url: ylagent.pubDict.updDictRediu,
          data: {},
        };
        callAjax(opt, (result) => {
          if (result.rspCod === '01000000') {
            openNotice('success', '缓存刷新成功', '提示');
            obj.setState({
              tdTableReload: true,
              tableSelectedRows: [],
              tableSelectedRowKeys: [],
            }, () => {
              obj.setState({ tdTableReload: false });
            });
          } else {
            openNotice('error', result.rspMsg, '缓存刷新失败');
          }
        }, (req, info, opt) => {
          openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
        });
      },
      onCancel() { },
    });
  }
  render() {
    const obj = this;
    const tableColumns = [

            { title: '参数编码', dataIndex: 'DICT_CODE', width: 200 },
            { title: '参数名称', dataIndex: 'DICT_NAME', width: 200, render: (text) => buildTableTip(text, 150) },
            { title: '参数值', dataIndex: 'DICT_VALUE', width: 150 },
            { title: '级别', dataIndex: 'DICT_LEVEL', width: 150 },
            { title: '状态', dataIndex: 'STATUS', width: 150, render: (text) => (text === '1' ? '启用' : '禁用') },

      {
        title: '操作', key: 'operation', width: 150, render(text, record, key) {
          return (
                        <span>
                            <a href="javascript:void(0)" onClick={() => { obj.handleEditLinkClick(text, record, key); } } >修改</a>
                        </span>
                    );
        },
      },
    ];
    const toolbar = [
            { icon: 'plus', text: '新增', click: this.handleAddBtnClick.bind(this) },
            { icon: 'delete', text: '删除', click: this.handlerDeleteBtnClick.bind(this) },
            { icon: 'check', text: '启用', click: this.handlerEnableBtnClick.bind(this) },
            { icon: 'minus', text: '禁用', click: this.handlerDisableBtnClick.bind(this) },
            { icon: 'pushpin', text: '刷新缓存', click: this.handlerRediuBtnClick.bind(this) },
    ];
    return (
            <div>
                <TdCard hideHead="true" shadow="true">
                    <PubDictManageForm onSubmit={this.handleFormSubmit.bind(this) } onReset={this.handleFormReset.bind(this) } />
                    <p className="br" />
                    <TdPageTable
                      rowKey={record => record.DICT_ID}
                      rowSelectCallback={this.handlerRowSelect.bind(this) }
                      url={ylagent.pubDict.qryPubDictList} toolbar={toolbar}
                      loadParam={this.state.tdTableParam} reload={this.state.tdTableReload}
                      renderResult={this.renderTableList} columns={tableColumns} />
                    <Modal width={1000} title={this.state.modalTitle} visible={this.state.modalVisible}
                      onCancel={() => { this.setState({ modalVisible: false }); } }
                      footer={this.state.modalOprType === 3 ? '' : [
                        <Button key="back" type="ghost" size="large" onClick={() => { this.setState({ modalVisible: false, loading: false }); } }>取消</Button>,
                        <Button key="submit" type="primary" size="large" loading={this.state.loading} onClick={this.handleModalOk.bind(this) }>
                                确认
                            </Button>,
                      ]}>
                        <PubDictForm formReset={this.state.formReset}
                          valid={this.state.modelIsValid}
                          formData={this.state.formData}
                          oprType={this.state.modalOprType}
                          validCallback={(oprType, errors, data) => {
                            this.callbackValid(oprType, errors, data);
                          } } />
                    </Modal>
                </TdCard>
            </div>
        );
  }
}
PubDictManage = Form.create()(PubDictManage);
export default PubDictManage;
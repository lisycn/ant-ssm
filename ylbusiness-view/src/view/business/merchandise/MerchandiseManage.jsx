import React from 'react';
import { Form, Modal, Spin, Tooltip } from 'antd';
import TdCard from '../../../component/TdCard';
import { openNotice, buildTableTip, buildFixedLength } from '../../../common/antdUtil';
import { ylagent, tdpub } from '../../../config/server';
import { rspInfo } from '../../../common/tdposConstant';
// 一些通用的方法，调用ajax，格式化日期，格式化下拉字典
import { callAjax, parseDate, requestSelectData } from '../../../common/util';
import TdPageTable from '../../../component/TdPageTable';

// 引入商户基本信息管理-查询条件组件
import MerchandiseManageSerachForm from './MerchandiseSerachForm';
// 新增
import MerchandiseAddForm from './MerchandiseAddForm';
import MerchandiseEditForm from './MerchandiseForm';


// 测试信息，获取需要批量移交的商户信息
const confirm = Modal.confirm;

/**
 *
 */
class MerchandiseManage extends React.Component {
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

      modalMerchandiseAddVisible: false,
      modalMerchandiseEditVisible: false,
      transferData: {},
      merNos: {},
      handleType: 1,
      detailInfo: {},
      spinLoading: false,
      // merchandise: [],
    };
  }
  componentDidMount() {
    const obj = this;
    const opt = {
      url: ylagent.business.merchandiseTypeList,
    };
    callAjax(opt, result => {
      if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
        const options = [];
        result.rspData.list.forEach((v) => {
          options.push({ value: v.merchandiseId, text: v.merchandiseType });
        });
        this.setState({
          merchandiseId: options,
        });
       
      } else {
        openNotice('error', result.rspMsg, '查询失败');
      }
    }, (req, info) => {
      openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
    });
  }
  handleFormCancelMerchandiseAdd() {
    this.setState({
      modalMerchandiseAddVisible: !this.state.modalMerchandiseAddVisible,
    });
  }

  handleFormCancelMerchandiseEdit() {
    this.setState({
      modalMerchandiseEditVisible: !this.state.modalMerchandiseEditVisible,
    });
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
  // 商品新增
  handlerMerchandiseAddClick() {
    const obj = this;
    obj.setState({
      modalMerchandiseAddVisible: !this.state.modalMerchandiseAddVisible,
      modalOprType: 2,
      handleType: 2,
      modalTitle: '商品信息',
      formData: '',
      spinLoading: false,
    }, () => {
          // 重置子组件表单数据
      obj.setState({ formReset: true }, () => {
            // 将子组件表单重置标识置为false
        obj.setState({
          formReset: false,
          spinLoading: false,
        });
      });
    });
  }

  // 修改
  handlerMerchandiseEditClick() {
    if (this.state.tableSelectedRows.length === 1) {
      const obj = this;
      const row = obj.state.tableSelectedRows[0];
      this.setState({
        modalMerchandiseEditVisible: !this.state.modalMerchandiseEditVisible,
        modalOprType: 2,
        handleType: 2,
        modalTitle: '商品信息修改',
        spinLoading: true,
      }, () => {
        const opt = {
          url: ylpay.integral.queryGoodsById,
          type: 'POST',
          dataType: 'json',
          data: { goodId: row.goodId },
        };
        callAjax(opt, (result) => {
          if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
            obj.setState({
              formData: result.rspData,
              spinLoading: false,
            }, () => {
              // 重置子组件表单数据
              obj.setState({ formReset: true }, () => {
                // 将子组件表单重置标识置为false
                obj.setState({
                  formReset: false,
                  spinLoading: false,
                });
              });
            });
          } else {
            openNotice('error', `${result.rspMsg}!`, '提示');
          }
        }, () => {
          openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
        });
      });
    } else if (this.state.tableSelectedRows.length > 1) {
      openNotice('warning', '不允许同时操作多个商品');
    } else {
      openNotice('warning', '请选择需要操作的商品');
    }
  }
  // 删除商品
  handlerDeleteClick() {
    const obj = this;
    if (this.state.tableSelectedRows.length > 0) {
      confirm({
        title: '您确认要删除该商品吗？',
        content: '',
        onOk() {
          // const data = obj.state.tableSelectedRows[0];
          const data = obj.state.tableSelectedRows;
          let merchandiseIds = '';
          data.forEach(v => (merchandiseIds = merchandiseIds + ',' + v.merchandiseId));
          const opt = {
            url: ylagent.business.deleteEnerty,
           // data: { goodId: data.goodId },
            type: 'POST',
            dataType: 'json',
            data: { merchandiseId: merchandiseIds.substring(1, merchandiseIds.length) },
          };
          callAjax(opt, (result) => {
            console.log(result);
            if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
              openNotice('success', '删除商品成功', '提示');
              // 重新加载table数据
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
              openNotice('error', result.rspMsg, '删除商品失败');
            }
          }, (req, info, opt) => {
            openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
          });
        },
        onCancel() { },
      });
    } else {
      openNotice('warning', '请选择需要删除的记录');
    }
  }
  // 商品新增回调
  addMerchandisecallbackValid(handleType, errors, data) {
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
          url: ylagent.business.insertentity,
          data,
        };
        callAjax(opt, (result) => {
          if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
            openNotice('success', '商品新增成功', '提示');
            obj.props.form.resetFields();
            obj.setState({
              modalMerchandiseAddVisible: !obj.state.modalMerchandiseAddVisible,
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
            openNotice('error', result.rspMsg, '商品新增失败');
          }
        }, () => {
          openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
        });
      });
    }
  }
  // 商品修改
  updGoodcallbackValid(handleType, errors, data) {
    console.info(data);
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
          url: ylpay.integral.updateGoodById,
          data,
        };
        callAjax(opt, (result) => {
          if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
            openNotice('success', '商品信息修改成功', '提示');
            obj.props.form.resetFields();
            obj.setState({
              modalMerchandiseEditVisible: !obj.state.modalMerchandiseEditVisible,
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
            openNotice('error', result.rspMsg, '商品信息修改失败');
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
    const tableColumns = [
      { title: '商品编号', width: 160, dataIndex: 'merchandiseId', render: (text) => buildFixedLength(text, 160) },
      { title: '商品名称', width: 80, dataIndex: 'merchandiseName', render: (text) => buildFixedLength(text, 80) },
      { title: '商品类型', width: 120, dataIndex: 'merchandiseType', render: (text) => buildFixedLength(text, 120) },
      { title: '单价（元）', width: 120, dataIndex: 'price', render: (text) => buildFixedLength(text, 120) },
      { title: '库存数量', width: 90, dataIndex: 'quantity', render: (text) => buildFixedLength(text, 90) },
      { title: '商品描述', width: 200, dataIndex: 'des', render: (text) => buildTableTip(text, 200) },
      { title: '创建时间', width: 160, dataIndex: 'createTime', render: (text) => buildFixedLength(parseDate(text), 160) },
    ];

    const toolbar = [
      { icon: 'plus', text: '新增', click: () => { obj.handlerMerchandiseAddClick(); } },
      { icon: 'swap', text: '修改', click: () => { obj.handlerMerchandiseEditClick(); } },
      { icon: 'delete', text: '删除', click: () => { obj.handlerDeleteClick(); } },
    ];

    // 渲染虚拟DOM
    return (
      <TdCard hideHead='true' shadow='true'>
        <MerchandiseManageSerachForm onSubmit={this.handleFormSubmit.bind(this)} onReset={this.handleFormReset.bind(this)}
          selectDatas={this.state.allSelectValues}
          valid={this.state.exportValid}
        />
        <p className='br' />
        <TdPageTable
          rowKey={record => record.merchandiseId}
          rowSelectCallback={this.handlerRowSelect.bind(this)}
          toolbar={toolbar}
          url={ylagent.business.queryMerchandiseList}
          loadParam={this.state.tdTableParam} reload={this.state.tdTableReload}
          renderResult={this.renderTableList}
          columns={tableColumns}
        />
        <Modal width={600} title={this.state.modalTitle} visible={this.state.modalMerchandiseAddVisible}
          onCancel={() => { this.setState({ modalMerchandiseAddVisible: false }); } }
          footer={null}
          selectDatas={this.state.merchandiseId}
        >
          <Spin spinning={this.state.spinLoading}>
            <MerchandiseAddForm formReset={this.state.formReset}
              valid={this.state.modelIsValid}
              oprType={this.state.modalOprType}
              handleType={this.state.handleType}
              formData={this.state.formData}
              selectDatas={this.state.merchandiseId}
              handleFormCancel={this.handleFormCancelMerchandiseAdd.bind(this)}
              validCallback={(handleType, errors, data) => {
                this.addMerchandisecallbackValid(handleType, errors, data);
              } }
            />
          </Spin>
        </Modal>
        <Modal width={600} title={this.state.modalTitle} visible={this.state.modalMerchandiseEditVisible}
          onCancel={() => { this.setState({ modalMerchandiseEditVisible: false }); } }
          footer={null}
        >
          <Spin spinning={this.state.spinLoading}>
            <MerchandiseEditForm formReset={this.state.formReset}
              valid={this.state.modelIsValid}
              oprType={this.state.modalOprType}
              handleType={this.state.handleType}
              formData={this.state.formData}
              handleFormCancel={this.handleFormCancelMerchandiseEdit.bind(this)}
              validCallback={(handleType, errors, data) => {
                this.updGoodcallbackValid(handleType, errors, data);
              } }
            />
          </Spin>
        </Modal>

      </TdCard>
    );
  }
}
// 必须有create包装,才会带this.props.form属性
MerchandiseManage = Form.create()(MerchandiseManage);
export default MerchandiseManage;


import { ylagent, tdpub } from '../../../config/server';
import TdPageTable from '../../../component/TdPageTable';
import React from 'react';
import { Form, Modal, Spin } from 'antd';
import TdCard from '../../../component/TdCard';
import { openNotice, buildFixedLength, buildTableTip } from '../../../common/antdUtil';
import { rspInfo } from '../../../common/tdposConstant';
import { callAjax, parseDate, requestSelectData } from '../../../common/util';
import MerchandiseTypeAddForm from './MerchandiseTypeAddForm';
import MerchandiseTypeForm from './MerchandiseTypeForm';
import MerchandiseTypeSearchForm from './MerchandiseTypeSearchForm';
/**
 * 公告
 *
 */
const confirm = Modal.confirm;
class MerchandiseTypeManage extends React.Component {
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

      merchandiseTypeAddVisible: false, // 新增页面是否显示
      merchandiseTypeEditFormVisible: false, // 修改界面显示
      transferData: {},
      merNos: {},
      handleType: 1,
      detailInfo: {},
      spinLoading: false,
    };
  }
// 添加
  handleFormCancelMerchandiseTypeAdd() {
    this.setState({
      merchandiseTypeAddVisible: !this.state.merchandiseTypeAddVisible,
    });
  }
// 修改
  handleFormCancelMerchandiseTypeEdit() {
    this.setState({
      merchandiseTypeEditFormVisible: !this.state.merchandiseTypeEditFormVisible,
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
  // componentDidMount() {
  //   // 必须要使用obj，否则无法加载内容*****
  //   const obj = this;
  //   requestSelectData(tdpub.dict.qryDictMutiList, { type: ['NOTICE_PLATFORM'], isSpace: true }, true, (oRes) => {
  //     obj.setState({
  //       allSelectValues: oRes,
  //     });
  //   });
  // }
  handlerRowSelect(selectedRowKeys, selectedRows) {
    this.setState({
      tableSelectedRows: selectedRows,
      tableSelectedRowKeys: selectedRowKeys,
    });
  }
  // 新增
  handleFormMerchandiseTypeAdd() {
    const obj = this;
    obj.setState({
      merchandiseTypeAddVisible: !this.state.merchandiseTypeAddVisible,
      modalOprType: 2,
      handleType: 2,
      modalTitle: '商品类型信息',
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
  // 公告新增回调
  addMerchandiseTypecallbackValid(handleType, errors, data) {
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
          url: ylagent.business.addMerchandiseType,
          data,
        };
        console.info('111222221');
        callAjax(opt, (result) => {
          if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
            openNotice('success', '新增成功', '提示');
            obj.props.form.resetFields();
            obj.setState({
              merchandiseTypeAddVisible: !obj.state.merchandiseTypeAddVisible,
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
            openNotice('error', result.rspMsg, '新增失败');
          }
        }, () => {
          openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
        });
      });
    }
  }
  // 删除公告
  handlerMerchandiseTypeDeleteClick() {
    const obj = this;
    if (obj.state.tableSelectedRows.length > 0) {
      confirm({
        title: '您确认要删除该类型吗？',
        content: '',
        onOk() {
          const data = obj.state.tableSelectedRows;
          let merchandiseIds = '';
          data.forEach(v => (merchandiseIds = merchandiseIds + "," + v.merchandiseId));
          //  const data = obj.state.tableSelectedRows[0];
          const opt = {
            url: ylagent.business.deleteMerchandiseType,
            // data: { noticeId: data.noticeId },
            type: 'POST',
            dataType: 'json',
            data: { merchandiseId: merchandiseIds.substring(1, merchandiseIds.length) },
          };
          callAjax(opt, (result) => {
            if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
              openNotice('success', '删除成功', '提示');
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
              openNotice('error', result.rspMsg, '删除失败');
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
  // 修改
  handlerMerchandiseTypeEditClick() {
    if (this.state.tableSelectedRows.length === 1) {
      const obj = this;
      const row = obj.state.tableSelectedRows[0];
      this.setState({
        merchandiseTypeEditFormVisible: !this.state.merchandiseTypeEditFormVisible,
        modalOprType: 2,
        handleType: 2,
        modalTitle: '商品类型修改',
        spinLoading: true,
      }, () => {
        const opt = {
          url: ylagent.business.queryMerchandiseTypeById,
          type: 'POST',
          dataType: 'json',
          data: { merchandiseId: row.merchandiseId },
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
      openNotice('warning', '不允许同时操作多个类型');
    } else {
      openNotice('warning', '请选择需要操作的类型');
    }
  }
  // 修改回调
  upMerchandiseTypecallbackValid(handleType, errors, data) {
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
          url: ylagent.business.updateMerchandiseById,
          data,
        };
        callAjax(opt, (result) => {
          if (result.rspCod === rspInfo.RSPCOD_SUCCESS) {
            openNotice('success', '修改成功', '提示');
            obj.props.form.resetFields();
            obj.setState({
              merchandiseTypeEditFormVisible: !obj.state.merchandiseTypeEditFormVisible,
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
            openNotice('error', result.rspMsg, '修改失败');
          }
        }, () => {
          openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
        });
      });
    }
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
    // const getTableData = (dictName, dictValue) => {
    //   // 获取页面初始化时的数据字典
    //   const entity = this.state.allSelectValues;
    //   // 根据字典名获取数据集
    //   const arrayList = entity[dictName];
    //   if (!(typeof (arrayList) === 'undefined')) {
    //     let result;
    //     for (let i = 0; i < arrayList.length; i++) {
    //       if (arrayList[i].value === dictValue) {
    //         result = arrayList[i].text;
    //       }
    //     }
    //     return result;
    //   }
    //   // 如果下拉数据没有初始化成功的话，返回空
    //   return '';
    // };
    const tableColumns = [
      { title: '商品类型编号', dataIndex: 'merchandiseId', width: 150, render: (text) => buildFixedLength(text, 150) },
      { title: '商品类型', dataIndex: 'merchandiseType', width: 100, render: (text) => buildFixedLength(text, 100) },

    ];

    const toolbar = [
      { icon: 'plus', text: '新增', click: () => { obj.handleFormMerchandiseTypeAdd(); } },
      { icon: 'swap', text: '修改', click: () => { obj.handlerMerchandiseTypeEditClick(); } },
      { icon: 'delete', text: '删除', click: () => { obj.handlerMerchandiseTypeDeleteClick(); } },

    ];
    // 渲染虚拟DOM
    return (
      <div>
        <TdCard hideHead="true" shadow="true">
          <MerchandiseTypeSearchForm onSubmit={this.handleFormSubmit.bind(this) }
            onReset={this.handleFormReset.bind(this) }
            selectDatas={this.state.allSelectValues}
          />
          <p className="br" />
          <TdPageTable
            rowkey={record => record.merchandiseId}
            url={ylagent.business.merchandiseTypeList}
            rowSelectCallback={this.handlerRowSelect.bind(this)}
            loadParam={this.state.tdTableParam}
            toolbar={toolbar}
            reload={this.state.tdTableReload}
            renderResult={this.renderTableList}
            columns={tableColumns}
            scroll={{ x: true }}
          />
        <Modal width={600} title={this.state.modalTitle} visible={this.state.merchandiseTypeAddVisible}
          onCancel={() => { this.setState({ merchandiseTypeAddVisible: false }); } }
          footer={null}
        >
          <Spin spinning={this.state.spinLoading}>
            <MerchandiseTypeAddForm formReset={this.state.formReset}
              valid={this.state.modelIsValid}
              selectDatas={this.state.allSelectValues}
              oprType={this.state.modalOprType}
              handleType={this.state.handleType}
              formData={this.state.formData}
              handleFormCancel={this.handleFormCancelMerchandiseTypeAdd.bind(this)}
              validCallback={(handleType, errors, data) => {
                this.addMerchandiseTypecallbackValid(handleType, errors, data);
              } }
            />
          </Spin>
        </Modal>
         <Modal width={600} title={this.state.modalTitle} visible={this.state.merchandiseTypeEditFormVisible}
           onCancel={() => { this.setState({ merchandiseTypeEditFormVisible: false }); } }
           footer={null}
    >
          <Spin spinning={this.state.spinLoading}>
            <MerchandiseTypeForm formReset={this.state.formReset}
              selectDatas={this.state.allSelectValues}
              valid={this.state.modelIsValid}
              oprType={this.state.modalOprType}
              handleType={this.state.handleType}
              formData={this.state.formData}
              handleFormCancel={this.handleFormCancelMerchandiseTypeEdit.bind(this)}
              validCallback={(handleType, errors, data) => {
                this.upMerchandiseTypecallbackValid(handleType, errors, data);
              } }
            />
          </Spin>
        </Modal>


        </TdCard>
      </div>
    );
  }
}
// 必须有create包装,才会带this.props.form属性
MerchandiseTypeManage = Form.create()(MerchandiseTypeManage);
export default MerchandiseTypeManage;

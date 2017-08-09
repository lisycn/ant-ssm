
import { ylagent, tdpub } from '../../../config/server';
import FileDownloadSearchForm from './FileDownloadSearchForm';
import TdPageTable from '../../../component/TdPageTable';
import React from 'react';
import { Form, Modal } from 'antd';
import TdCard from '../../../component/TdCard';
import { openNotice, buildFixedLength, buildTableTip } from '../../../common/antdUtil';
import { parseDate, requestSelectData } from '../../../common/util';


/**
 * 文件
 *
 */
const confirm = Modal.confirm;
class FileDownloadManage extends React.Component {
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

  componentDidMount() {
    // 必须要使用obj，否则无法加载内容*****
    const obj = this;
    requestSelectData(tdpub.dict.qryDictMutiList, { type: ['FILETYPE', 'RPSTATUS'], isSpace: true }, true, (oRes) => {
      obj.setState({
        allSelectValues: oRes,
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
  fileLoad(text, record, key) {
    /** 判断文件是否存在 */
    if (record.status == '02') {
      openNotice('error', '文件生成失败，请删除后，重新下载', '提示');
    } else if (record.status == '00') {
      openNotice('info', '文件正在生成，请稍后，刷新重试', '提示');
    } else {
      window.location.href = ylagent.agent.fileDown + '?dId=' + record.dId;
    }
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
    const dSizeText = (status) => {
      return (status / 1024) + 'k';
    };
    const dTimeText = (status) => {
      return (status / 1000) + 's';
    };
    const tableColumns = [
      { title: '编号', dataIndex: 'dId', width: 200, render: (text) => buildFixedLength(text, 200) },
      { title: '文件名', dataIndex: 'dName', width: 150, render: (text) => buildFixedLength(text, 150) },
      { title: '文件类型', dataIndex: 'dType', width: 60, render: (text) => buildFixedLength(text, 60) },
      { title: '开始时间', dataIndex: 'dSTime', width: 150, render: (text) => buildFixedLength(parseDate(text), 150) },
      { title: '文件地址', dataIndex: 'dPath', width: 250, render: (text) => buildTableTip(text, 250) },
      { title: '状态', dataIndex: 'status', width: 50, render: (text) => buildFixedLength(text=== '01' ? '完成' : '未完成' , 50) },
      {
        title: '操作', key: 'operation', width: 50, render(text, record, key) {
          return (
            <span>
              <a href='javascript:void(0)' onClick={() => { obj.fileLoad(text, record, key);} }>下载</a>
            </span>
          );
        },
      },

    ];


    // 渲染虚拟DOM
    return (
      <div>
        <TdCard hideHead="true" shadow="true">
          <FileDownloadSearchForm onSubmit={this.handleFormSubmit.bind(this) }
            onReset={this.handleFormReset.bind(this) }
            selectDatas={this.state.allSelectValues}
          />
          <p className="br" />
          <TdPageTable
            rowkey={record => record.dId}
            url={ylagent.agent.fileDownloadList}
            rowSelectCallback={this.handlerRowSelect.bind(this)}
            loadParam={this.state.tdTableParam}
            // toolbar={toolbar}
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
FileDownloadManage = Form.create()(FileDownloadManage);
export default FileDownloadManage;

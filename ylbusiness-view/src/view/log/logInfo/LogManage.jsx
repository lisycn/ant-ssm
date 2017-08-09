import React from "react";
import {message, Modal, Form} from "antd";
import TdCard from "../../../component/TdCard";
import {openNotice} from "../../../common/antdUtil";
import {ylagent} from "../../../config/server";
import {rspInfo} from "../../../common/authConstant";
import {callAjax, parseDate, filterObject, formatDate} from "../../../common/util";
import TdPageTable from "../../../component/TdPageTable";
import LogManageSearchForm from "./LogManageSearchForm";
import LogManageForm from "./LogManageForm";
const confirm = Modal.confirm;

class LogManage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
      tableLoading: false,
      tableData: [],
      tdTableReload: false,
      tdTableParam: {},
      modalVisible: false,
      modelIsValid: false,
      processIsValid: false,
      modalOprType: 0,
      formReset: false,
      formData: {}
    }
  }

  handleFormSubmit(dat) {
    this.setState({tdTableReload: true, tdTableParam: dat}, ()=> {
      this.setState({tdTableReload: false})
    });
  }

  handleFormReset() {
    this.setState({formData: {}});
  }

  //表格处理数据
  renderTableList(result) {
    return {
      total: result.rspData.total,
      list: result.rspData.list,
      count: {a: "haha"}
    };
  }

  //点击“详情”链接事件(text:该单元格数据 record:该行记录对象 key:该行KEY)
  handleDetailLinkClick(text, record, key) {
    this.state.formData = record;
    this.setState({
      modalVisible: !this.state.modalVisible,
      modalOprType: 3,
      modalTitle: '日志详情'
    }, () => {
      //重置子组件表单数据
      this.setState({ formReset: true }, () => {
        //将子组件表单重置标识置为false
        this.setState({
          formReset: false
        });
      });
    });
  }

  //模态框确认事件
  handleModalOk(){
    this.setState({
      modelIsValid: true
    });
  }
  
  render() {

    const obj = this;
    const modalIsDetail = {
      footer: ""
    };
    //表格列
    const tableColumns = [
      {title: "路径", dataIndex: "path", width:  320},
      {
        title: "时间", dataIndex: "date", width:  150
        ,render(text, record, key){
        if (text) {
          const da = formatDate(new Date(text), "yyyy-MM-dd hh:mm:ss");
          return (<span>{da}</span>);
        } else {
          return (<span></span>);
        }
      }
      },
      {title: "类名", dataIndex: "className", width: 300},
      {title: "级别", dataIndex: "logLevel", width: 80},
      {title: "消息", dataIndex: "detail", width:  1000  },
      {
        title: "操作", key: "operation", width: 110, render(text, record, key) {
        return (
          <span style={{"width": 110,"display":"block"}}>
                            <a href="javascript:void(0)"
                               onClick={() => { obj.handleDetailLinkClick(text, record, key) } }>详情</a>
                        </span>
        );
      }
      }
    ];
    return (
      <div>
        <h2>日志查看</h2><p className="br"/>
        <TdCard hideHead="true" shadow="true">
          <LogManageSearchForm onSubmit={this.handleFormSubmit.bind(this)}
                               onReset={this.handleFormReset.bind(this)}/>
          <p className="br"/>

          <TdPageTable
            scroll={{ x: "true" }}
            checkbox={true}
            pagination={true}
            url={ylagent.log.logInfo.getAll}
            loadParam={obj.state.tdTableParam} reload={obj.state.tdTableReload}
            renderResult={obj.renderTableList} columns={tableColumns} rowKey={record => record.id}/>
        </TdCard>
        <Modal width={700} title={obj.state.modalTitle} visible={obj.state.modalVisible}
               confirmLoading={obj.state.confirmLoading}
               onCancel={() => { obj.setState({ modalVisible: false }); } }
               onOk={obj.handleModalOk.bind(this) }
          {...(this.state.modalOprType === 3 ? modalIsDetail : null) } className="monitorManager">
          <LogManageForm formReset={this.state.formReset}
                               valid={this.state.modelIsValid}
                               formData={this.state.formData}
                               oprType={this.state.modalOprType}/>

        </Modal>
      </div>
    );
  }
}
//必须有create包装,才会带this.props.form属性
LogManage = Form.create()(LogManage);
export default LogManage;

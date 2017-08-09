import React from "react";
import {Row, Col, Form, Input, DatePicker, Button,Select} from "antd";
import QueueAnim from "rc-queue-anim";
import { filterObject,requestSelectData} from "../../../common/util";
import TdSelect from "../../../component/TdSelect";
import {ylagent} from "../../../config/server";
const RangePicker = DatePicker.RangePicker;
const Option = Select.Option;

/**
 * TaskMonitorSearchForm 查询条件表单
 *
 * Auth：Bing Time：2016-06-07
 */
class TaskMonitorSearchForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
      modeltypeData:[]
    }
  }
  componentDidMount(){
    this.getTaskType();
  }
  onChange(value, dateString) {
    this.props.form.setFieldsValue({ "startcreatedate": dateString[0], "endcreatedate": dateString[1] });
  }
  handlerDatePickerReset(){
    this.props.form.setFieldsValue({ "startcreatedate": "", "endcreatedate": "" });
  }
  handleAdvLinkClick(){
    this.setState({
      advSearchShow: !this.state.advSearchShow
    });
  }
  getTaskType(){
    requestSelectData(ylagent.workflow.taskMonitor.findTaskByPagePre, {
      type: ["modelMainList"],
      isSpace: true
    }, true, (oRes) => {
      this.setState({
        modeltypeData: oRes
      });
    });
  }
  render(){
    //定义变量和参数
    const FormItem = Form.Item;
    const {onSubmit, onReset} = this.props;
    const {getFieldProps,getFieldValue} = this.props.form;


    const advSearchVDom = [
      <Row key="r1">
        <Col sm={12} md={6}>
          <FormItem label="已审岗位名称：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
            <Input placeholder="请输入待审岗位名称" {...getFieldProps("donepositionname")} />
          </FormItem>
        </Col>
        <Col sm={12} md={6}>
          <FormItem label="已审核人：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
            <Input placeholder="请输入已审核人" {...getFieldProps("doneoperatorname")} />
          </FormItem>
        </Col>
      </Row>
      ];

    const modeltype = [];
    this.props.modeltype.map(function(val,idx){
      modeltype.push(<Option key={val.key} value={val.key}>{val.value}</Option>);
    });

    const flowstatusSelect = getFieldProps("flowstatus", {
      initialValue: ""
    });
    const modeltypeSelect = getFieldProps("modelno", {
      initialValue: ""
    });
    //<Select {...modeltypeSelect}>
    //  <Option value="">---请选择---</Option>
    //  {modeltype}
    //</Select>

    return(
      <Form horizontal className="advanced-search-form">
        <div hidden={true}>
          <FormItem>
            <Input {...getFieldProps("startcreatedate") } />
          </FormItem>
          <FormItem>
            <Input {...getFieldProps("endcreatedate") } />
          </FormItem>
        </div>
        <Row>
          <Col sm={12} md={6}>
            <FormItem label="任务描述：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入任务描述" {...getFieldProps("referbusinessname")} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label="任务类型：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <TdSelect {...modeltypeSelect } blankText="---请选择---"
                       dict={{ dict_value: "value", dict_text: "text" }}
                       data={this.state.modeltypeData.modelMainList}
              />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label="任务状态：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Select {...flowstatusSelect}>
                  <Option value="">---请选择---</Option>
                  <Option value="01">申请中（暂存）</Option>
                  <Option value="02">审核中</Option>
                  <Option value="03">作废</Option>
                  <Option value="06">退回</Option>
                  <Option value="99">完毕</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={12} md={6}>
            <FormItem label="申请时间：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <RangePicker format="yyyy-MM-dd HH:mm:ss"
                           onChange={this.onChange.bind(this)}
                           value={[getFieldValue("startcreatedate"), getFieldValue("endcreatedate")]} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <div style={{ paddingTop: "7px" }}>
              <a href="javascript:void(0)" onClick={this.handleAdvLinkClick.bind(this) }>&nbsp; 高级搜索</a>
            </div>
          </Col>
        </Row>
        <Row >
          <Col span="24">
            <QueueAnim key="adv-search" type={["right", "left"]} ease={["easeOutQuart", "easeInOutQuart"]}>
              {this.state.advSearchShow === true ? advSearchVDom : null}
            </QueueAnim>
          </Col>
        </Row>
        <Row visabled={this.state.advSearchShow}>
          <Col sm={{span: 12, offset: 12}} md={{span: 6, offset: 18}} style={{ textAlign: "right" }}>
            <Button type="primary" htmlType="submit" onClick={(e) => {
                            e.preventDefault();
                            onSubmit(filterObject(this.props.form.getFieldsValue()));
                        }}>搜索</Button>
            <Button onClick={(e) => {
                            e.preventDefault();
                            this.props.form.resetFields();
                            this.handlerDatePickerReset();
                            onReset();
                        }}>重置</Button>
          </Col>
        </Row>
      </Form>
    );
  }
}

const noop = () => {
};
//定义组件标签的可配置属性
TaskMonitorSearchForm.defaultProps = {
  modeltype:[],
  onSubmit: noop,
  onReset: noop
};

TaskMonitorSearchForm = Form.create()(TaskMonitorSearchForm);
export default TaskMonitorSearchForm;


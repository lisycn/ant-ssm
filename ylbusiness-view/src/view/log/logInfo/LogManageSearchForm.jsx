import React from "react";
import {Row, Col, Form, Input, DatePicker, Button} from "antd";
import {filterObject} from "../../../common/util";
import QueueAnim from "rc-queue-anim";
const RangePicker = DatePicker.RangePicker;
/**
 * WorkflowModelMainManageSearchForm 查询条件表单
 *
 * Auth：jiang.mc Time：2016-05-25
 */
class LogManageSearchForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false
    }
  }

  //事件控制相关方法
  //点击“高级搜索”事件
  handleAdvLinkClick() {
    this.setState({
      advSearchShow: !this.state.advSearchShow
    });
  }

  onChange(value, dateString) {
    this.props.form.setFieldsValue({"startDate": dateString[0], "endDate": dateString[1]});
  }

  render() {
    //定义变量和参数
    const FormItem = Form.Item;
    const {onSubmit, onReset} = this.props;
    const {getFieldProps, getFieldValue} = this.props.form;

    //定义虚拟DOM代码片段
    const advSearchVDom = [];
    const positionname = getFieldProps("positionname", {
      initialValue: ""
    });
    const modelname = getFieldProps("modelname", {
      initialValue: ""
    });

    const searchDate = [];
    searchDate.push(<FormItem label="时间：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }} key="search1">
      <RangePicker showTime format="yyyy-MM-dd HH:mm:ss"
                   onChange={this.onChange.bind(this)}
                   value={[getFieldValue("startDate"), getFieldValue("endDate")]}/>
    </FormItem>);
    //渲染虚拟DOM
    return (
      <Form horizontal className="advanced-search-form">
        <div hidden={true}>
          <FormItem>
            <Input {...getFieldProps("startDate") } />
          </FormItem>
          <FormItem>
            <Input {...getFieldProps("endDate") } />
          </FormItem>
        </div>
        <Row>
          <Col sm={12} md={6}>
            <FormItem label="路径名：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入路径名称" {...getFieldProps("path")} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label="类名全称：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入类名" {...getFieldProps("className")} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label="消息内容：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入消息内容" {...getFieldProps("detail")} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label="日志级别：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入日志级别" {...getFieldProps("logLevel")} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label="时间：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }} key="search1">
              <RangePicker showTime format="yyyy-MM-dd HH:mm:ss"
                           onChange={this.onChange.bind(this)}
                           value={[getFieldValue("startDate"), getFieldValue("endDate")]}/>
            </FormItem>
          </Col>
        </Row>
          <Col span="24">
            <QueueAnim key="adv-search" type={["right", "left"]} ease={["easeOutQuart", "easeInOutQuart"]}>
              {this.state.advSearchShow === true ? advSearchVDom : null}
            </QueueAnim>
          </Col>
        <Row>
        </Row>
        <Row>
          <Col sm={{span: 12, offset: 12}} md={{span: 6, offset: 18}} style={{ textAlign: "right" }}>
            <Button type="primary" htmlType="submit" onClick={(e) => {
                            e.preventDefault();
                            onSubmit(filterObject(this.props.form.getFieldsValue()));
                        }}>搜索</Button>
            <Button onClick={(e) => {
                            e.preventDefault();
                            this.props.form.resetFields();
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
LogManageSearchForm.defaultProps = {
  onSubmit: noop,
  onReset: noop
};

LogManageSearchForm = Form.create()(LogManageSearchForm);
export default LogManageSearchForm;

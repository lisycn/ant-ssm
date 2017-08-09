import React from "react"
import { Row, Col, Form, Input, Select, Button } from "antd";
import {filterObject,formatDate} from "../../../common/util";

class LogManageForm extends React.Component{
  constructor(props) {
    super(props);
    this.state = {

    }
  }

  render() {
    //定义变量和参数
    const FormItem = Form.Item;
    const { idObj, oprType, formData } = this.props;
    const { getFieldProps, isFieldValidating, getFieldError } = this.props.form;

    //定义虚拟DOM代码片段
    const advSearchVDom = [];

    const itemDisable = {
      disabled: true
    }
    const itemHidden = {
      hidden: true
    }

    //渲染虚拟DOM
    return (
      <Form horizontal className="advanced-search-form">
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="路径：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="text" rows={5} {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps("path", { initialValue: this.props.formData.path }) }/>
            </FormItem>
            <FormItem label="时间：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="text" rows={5} {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps("date", { initialValue: formatDate(new Date(this.props.formData.date),"yyyy-MM-dd hh:mm:ss") }) }/>
            </FormItem>
            <FormItem label="线程名：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="text" rows={5} {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps("thread", { initialValue: this.props.formData.thread }) }/>
            </FormItem>
            <FormItem label="类名：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="text" rows={5} {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps("className", { initialValue: this.props.formData.className }) }/>
            </FormItem>
            <FormItem label="级别：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="text" rows={5} {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps("logLevel", { initialValue: this.props.formData.logLevel }) }/>
            </FormItem>
            <FormItem label="消息：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="textarea" rows={5} {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps("detail", { initialValue: this.props.formData.detail }) }/>
            </FormItem>
          </Col>
        </Row>
      </Form>);
  }

}

const noop = () => {};
//定义组件标签的可配置属性
LogManageForm.defaultProps = {
  valid: false,        //校验状态，通过父页面修改该值触发componentWillReceiveProps方法
  idObj: {},          //ID属性对象
  oprType: 0,         //操作类型 0：默认值 1：新增 2：修改 3详情：
  formData: {},        //父页面表单数据
  validCallback: noop, //回调函数
  formReset: false     //表单重置标识位
};

LogManageForm = Form.create()(LogManageForm);
export default LogManageForm;

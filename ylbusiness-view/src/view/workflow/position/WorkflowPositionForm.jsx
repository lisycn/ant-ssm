import React from "react";
import {Button, Form, Input, Col, Radio, Select, Row} from "antd";
const RadioGroup = Radio.Group;
/**
 * WorkflowModelPositionForm 岗位表单管理表单组件
 *
 * Auth: 蒋梦晨  Time: 2016-05-20
 */
class WorkflowModelPositionForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      valid: false
    }
  }

  //父页面通过修改props 中属性的值触发该方法
  componentWillReceiveProps(nextProps) {
    //更新表单数据
    if (nextProps.valid === true && this.state.valid === false) {
      this.setState({valid: true}, () => {
        this.validForm();
        this.setState({valid: false});
      });
    }

    //重置表单
    if (nextProps.formReset === true) {
      console.log("child component form reset.");
      this.props.form.resetFields();
    }
  }

  //子页面表单校验
  validForm() {
    const {oprType, validCallback} = this.props;
    const obj = this;
    this.props.form.validateFields((errors, data) => {
      //执行父页面回调函数
      validCallback(oprType, errors, data);
    });
  }

  render() {
    //渲染虚拟DOM
    const FormItem = Form.Item;
    const {idObj, formData} = this.props;
    const {getFieldProps} = this.props.form;


    //岗位代码校验
    const positioncodeProps = getFieldProps(idObj.positioncode ? idObj.positioncode : "positioncode", {
      initialValue: (formData.positioncode),
      validate: [{
        rules: [
          {required: true,min:2,max:10, message: '必填'}
        ],
        trigger: 'onBlur'
      }]
    });

    const positionno = getFieldProps(idObj.positionno ? idObj.positionno : "positionno", {
      initialValue: formData.positionno
    });

    const positionname = getFieldProps(idObj.positionname ? idObj.positionname : "positionname", {
      initialValue: formData.positionname,
      validate: [{
        rules: [
          {required: true,min:2,max:10, message: '必填'}
        ],
        trigger: 'onBlur'
      }]
    });

    const systemno = getFieldProps(idObj.systemno ? idObj.systemno : "systemno", {
      initialValue: formData.systemno
    });

    const status = getFieldProps(idObj.status ? idObj.status : "status", {
      initialValue: formData.status
    });

    const itemHidden = {
      hidden: true
    };

    const itemDisable = {
      disabled:true
    };

    return (
      <Form horizontal form={this.props.form}>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="岗位代码："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入岗位代码" {...positioncodeProps} {...(this.props.oprType === 2 ? itemDisable : null) }/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="岗位名称："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入岗位名" {...positionname}/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <div  {...(this.props.oprType === 3 ? null : itemHidden)}>
              <FormItem label="岗位状态："
                        labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                <RadioGroup {...status}>
                  <Radio value="1">启用</Radio>
                  <Radio value="0">禁用</Radio>
                </RadioGroup>
              </FormItem>
            </div>
          </Col>
        </Row>

      </Form>
    );
  }
}

const noop = () => {
};
WorkflowModelPositionForm.defaultProps = {
  valid: false,        //校验状态，通过父页面修改该值触发componentWillReceiveProps方法
  idObj: {},          //ID属性对象
  oprType: 0,         //操作类型 0：默认值 1：新增 2：修改 3详情：
  formData: {},        //父页面表单数据
  validCallback: noop, //回调函数
  formReset: false     //表单重置标识位
};

WorkflowModelPositionForm = Form.create()(WorkflowModelPositionForm);
export default WorkflowModelPositionForm;

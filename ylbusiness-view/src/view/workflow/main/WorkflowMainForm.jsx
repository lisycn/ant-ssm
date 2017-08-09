import React from "react";
import {Button, Form, Input, Col, Radio, Select, Row} from "antd";
const RadioGroup = Radio.Group;
/**
 * WorkflowModel 工作流表单管理表单组件
 *
 * Auth: 蒋梦晨  Time: 2016-05-20
 */
class WorkflowModelForm extends React.Component {
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
    const {idObj, oprType, formData} = this.props;
    const {getFieldProps, getFieldError, isFieldValidating} = this.props.form;


    //工作流业务类型校验
    const modeltypeProps = getFieldProps(idObj.modeltype ? idObj.modeltype : "modeltype", {
      initialValue: (formData.modeltype),
      validate: [{
        rules: [
          {required: true,min: 2, max:10, message: '必填'}
        ],
        trigger: 'onBlur'
      }]
    });

    const modelId = getFieldProps(idObj.modelId ? idObj.modelId : "modelId", {
      initialValue: formData.modelId
    });

    const modelname = getFieldProps(idObj.modelname ? idObj.modelname : "modelname", {
      initialValue: formData.modelname,
      validate: [{
        rules: [
          {required: true,min: 2, max:10, message: '必填'}
        ],
        trigger: 'onBlur'
      }]
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
            <FormItem label="工作流类型："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入工作流类型" {...modeltypeProps} {...(this.props.oprType === 2 ? itemDisable : null) }/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="工作流名称："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入工作流名" {...modelname}/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <div  {...(this.props.oprType === 3 ? null : itemHidden)}>
              <FormItem label="工作流状态："
                        labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                <RadioGroup defaultValue="0"  {...status}>
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
WorkflowModelForm.defaultProps = {
  valid: false,        //校验状态，通过父页面修改该值触发componentWillReceiveProps方法
  idObj: {},          //ID属性对象
  oprType: 0,         //操作类型 0：默认值 1：新增 2：修改 3详情：
  formData: {},        //父页面表单数据
  validCallback: noop, //回调函数
  formReset: false     //表单重置标识位
};

WorkflowModelForm = Form.create()(WorkflowModelForm);
export default WorkflowModelForm;

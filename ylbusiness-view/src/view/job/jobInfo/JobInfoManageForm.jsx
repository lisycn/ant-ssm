import React from "react";
import {Row, Col, Button,Form,DatePicker,Select,Input,Checkbox,InputNumber} from "antd";
import {filterObject} from "../../../common/util";


const RangePicker = DatePicker.RangePicker;
const Option = Select.Option;

class JobInfoManageForm extends React.Component{
  constructor(props){
    super(props);
    this.state={
      valid: false
    };
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

  checkNumber(rule, value, callback){
    if (value) {
      let type="^[0-9]*[1-9][0-9]*$";
      let re = new RegExp(type);
      if(value.toString().match(re)==null) {
        callback(new Error("报警阀值不能为空并且为正整数"));
      }else{
        callback();
      }
    }else{
      callback(new Error("报警阀值不能为空并且为正整数"));
    }
  }

  render(){
    //渲染虚拟DOM
    const FormItem = Form.Item;
    const {idObj, oprType, formData} = this.props;
    const {getFieldProps, getFieldError, isFieldValidating} = this.props.form;
    const isDisabel = (oprType == 2 || oprType==3) ? true:false;

    //工作流业务类型校验
    const jobGroup = getFieldProps(idObj.jobGroup ? idObj.jobGroup : "jobGroup", {
      initialValue: "DEFAULT",
      validate: [{
        rules: [
          {required: true, message: '任务名不能为空'}
        ],
        trigger: 'onBlur'
      }]
    });
    //工作流业务类型校验
    const jobName = getFieldProps(idObj.jobName ? idObj.jobName : "jobName", {
      initialValue: (formData.jobName),
      validate: [{
        rules: [
          {required: true, message: '任务名不能为空'}
        ],
        trigger: 'onBlur'
      }]
    });
    //Cron表达式
    const cron = getFieldProps(idObj.jobCron ? idObj.jobCron : "jobCron", {
      initialValue: (formData.jobCron),
      validate: [{
        rules: [
          {required: true, max: 100, message: 'Cron表达式不能为空'}
        ],
        trigger: 'onBlur'
      }]
    });
    //描述
    const jobDesc = getFieldProps(idObj.jobDesc ? idObj.jobDesc : "jobDesc", {
      initialValue: (formData.jobDesc),
      validate: [{
        rules: [
          {required: true, max: 200, message: '任务描述不能为空'}
        ],
        trigger: 'onBlur'
      }]
    });
    //执行地址
    const jobAddress = getFieldProps(idObj.executorAddress ? idObj.executorAddress : "executorAddress", {
      initialValue: (formData.executorAddress),
      validate: [{
        rules: [
          {required: true, max: 200,message: '执行地址不能为空'}
        ],
        trigger: 'onBlur'
      }]
    });
    //jobHandler
    const jobHandler = getFieldProps(idObj.executorHandler ? idObj.executorHandler : "executorHandler", {
      initialValue: (formData.executorHandler),
      validate: [{
        rules: [
          {required: true, max: 200, message: 'jobHandler不能为空'}
        ],
        trigger: 'onBlur'
      }]
    });
    //执行参数
    const jobPara = getFieldProps(idObj.executorParam ? idObj.executorParam : "executorParam", {
      initialValue: (formData.executorParam),
      validate: [{
        rules: [
          {required: false, max: 100, message: '执行参数不能为空'}
        ],
        trigger: 'onBlur'
      }]
    });
    //负责人
    const author =  getFieldProps(idObj.author ? idObj.author : "author", {
      initialValue: (formData.author),
      validate: [{
        rules: [
          {required: true,max: 50, message: '负责人不能为空'}
        ],
        trigger: 'onBlur'
      }]
    });
    //报警邮件：请输入报警邮件，多个邮件地址逗号分隔
    const email =  getFieldProps(idObj.alarmEmail ? idObj.alarmEmail : "alarmEmail", {
      initialValue: (formData.alarmEmail),
      validate: [{
        rules: [
          {required: true,max: 200, message: '报警邮件不能为空'}
        ],
        trigger: 'onBlur'
      }]
    });

    //报警阀值
    const threshold  =  getFieldProps(idObj.alarmThreshold ? idObj.alarmThreshold : "alarmThreshold", {
      initialValue: (formData.alarmThreshold),
      validate: [{
        rules: [
          {validator:this.checkNumber,required: true},
        ],
        trigger: 'onBlur'
      }]
    });

    return(
      <Form horizontal form={this.props.form}>
        <Row>
          <Col sm={24} md={12}>
            <FormItem label="任务组："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入任务组" {...jobGroup} disabled={true}/>
            </FormItem>
          </Col>
          <Col sm={24} md={12}>
            <FormItem label="任务名："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入任务名" {...jobName} disabled={isDisabel}/>
            </FormItem>
          </Col>
        </Row>

        <Row>
          <Col sm={24} md={12}>
            <FormItem label="Cron："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入Cron" {...cron}/>
            </FormItem>
          </Col>
          <Col sm={24} md={12}>
            <FormItem label="描述："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入描述" {...jobDesc}/>
            </FormItem>
          </Col>
        </Row>

        <Row>
          <Col sm={24} md={12}>
            <FormItem label="执行器地址："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入执行器地址，多个地址逗号分隔" {...jobAddress}/>
            </FormItem>
          </Col>
          <Col sm={24} md={12}>
            <FormItem label="jobHandler："
                      labelCol={{ span: 8 }} wrapperCol={{ span: 12 }}>
              <Input placeholder="请输入jobHandler" {...jobHandler}/>
            </FormItem>
          </Col>
        </Row>

        <Row>
          <Col sm={24} md={12}>
            <FormItem label="执行参数："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入执行参数" {...jobPara}/>
            </FormItem>
          </Col>
          <Col sm={24} md={12}>
            <FormItem label="负责人："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入负责人" {...author} />
            </FormItem>
          </Col>
        </Row>

        <Row>
          <Col sm={24} md={12}>
            <FormItem label="报警邮件："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="email" placeholder="请输入报警邮件，多个邮件地址逗号分隔" {...email}/>
            </FormItem>
          </Col>
          <Col sm={24} md={12}>
            <FormItem label="报警阀值："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input min={1} placeholder="请输入报警阀值" {...threshold}/>
            </FormItem>
          </Col>
        </Row>
      </Form>
    );
  }
}



const noop = () => {
};
JobInfoManageForm.defaultProps = {
  valid: false,        //校验状态，通过父页面修改该值触发componentWillReceiveProps方法
  idObj: {},          //ID属性对象
  oprType: 0,         //操作类型 0：默认值 1：新增 2：修改 3详情：
  formData: {},        //父页面表单数据
  validCallback: noop, //回调函数
  formReset: false     //表单重置标识位
};

JobInfoManageForm = Form.create()(JobInfoManageForm);
export default JobInfoManageForm;

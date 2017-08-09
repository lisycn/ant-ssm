import React from "react"
import { Row, Col, Form, Input, Select, Button } from "antd";
import {filterObject,formatDate} from "../../../common/util";

class ConsumersManageForm extends React.Component{
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

    const paraArray = formData.parameters.split("&");
    let paraJson = {};
    paraArray.map(function (val, idx) {
      let paraTemp = val.split("=");
      paraJson[paraTemp[0]] = paraTemp[1];
    });

    var newDate = new Date();
    newDate.setTime(paraJson.timestamp);
    var localTime = formatDate(newDate,"yyyy-MM-dd hh:mm:ss.S") + "("+paraJson.timestamp+")";

    const address = "consumer://"+formData.address+"/"+formData.service+"?"+formData.parameters;

    let routesMess = "";
    if(formData.routes && formData.routes.length > 0 ){
      routesMess="已路由("+formData.routes.length+")";
    }else{
      routesMess = "未路由";
    }

    let providerMess = "";
    if(formData.providers && formData.providers.length > 0 ){
      providerMess="已通知("+formData.providers.length+")";
    }else{
      providerMess = "没有提供者";
    }

    let levelMessage ="";
    if(formData.level == 0){
      levelMessage = '已屏蔽';
    }else if(formData.level==1){
      levelMessage = '已容错'
    }else if(formData.level==2){
      levelMessage = '未降级'
    }

    let allowedMessage ="";
    if(formData.allowed == 0){
      allowedMessage = "已禁止";
    }else if(formData.allowed == 1){
      allowedMessage = "已允许";
    }

    //渲染虚拟DOM
    return (
      <Form horizontal className="advanced-search-form">
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="服务名：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.service ? idObj.service : "service", { initialValue: this.props.formData.service ? this.props.formData.service : "" }) }/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="消费者地址：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="textarea" rows={5} {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps("consumnersAddress", { initialValue: this.props.formData.address ? address : "" }) }/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="动态配置：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.override ? idObj.override : "override", { initialValue: this.props.formData.override ? this.props.formData.override.params : "" }) }/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="主机名：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.address ? idObj.address : "address", { initialValue: this.props.formData.address ? this.props.formData.address : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="方法列表：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="textarea" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.methods ? paraJson.methods : "methods", { initialValue: paraJson.methods ? paraJson.methods : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="应用名：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.application ? paraJson.application : "application", { initialValue: paraJson.application ? paraJson.application : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="检查：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.check ? paraJson.check : "check", { initialValue: paraJson.check ? paraJson.check : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="进程号：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.pid ? paraJson.pid : "pid", { initialValue: paraJson.pid ? paraJson.pid : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="接口名：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.interface ? paraJson.interface : "interface", { initialValue: paraJson.interface ? paraJson.interface : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="时间戳：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input  {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.timestamp ? paraJson.timestamp : "timestamp", { initialValue: paraJson.timestamp ? localTime : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="Dubbo版本：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.dubbo ? paraJson.dubbo : "dubbo", { initialValue: paraJson.dubbo ? paraJson.dubbo : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="revision：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.revision ? paraJson.revision : "revision", { initialValue: paraJson.revision ? paraJson.revision : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="数据类型：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.category ? paraJson.category : "category", { initialValue: paraJson.category ? paraJson.category : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="所属端：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.side ? paraJson.side : "side", { initialValue: paraJson.side ? paraJson.side : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="集群：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.cluster ? paraJson.cluster : "cluster", { initialValue: paraJson.cluster ? paraJson.cluster : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="default.reference.filter：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps("default.reference.filter", { initialValue: paraJson['default.reference.filter'] ? paraJson['default.reference.filter'] : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="超时：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.timeout ? paraJson.timeout : "timeout", { initialValue: paraJson.timeout ? paraJson.timeout : "" }) }/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="降级：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.level ? idObj.level : "level", { initialValue:levelMessage  }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="状态：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input  {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.allowed ? idObj.allowed : "allowed", { initialValue:allowedMessage  }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="路由：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input  {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.routes ? idObj.routes : "routes", { initialValue:  routesMess  }) }/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="通知：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input  {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.providers ? idObj.providers : "providers", { initialValue: providerMess }) } />
            </FormItem>
          </Col>
        </Row>
      </Form>);
  }

}

const noop = () => {};
//定义组件标签的可配置属性
ConsumersManageForm.defaultProps = {
  valid: false,        //校验状态，通过父页面修改该值触发componentWillReceiveProps方法
  idObj: {},          //ID属性对象
  oprType: 0,         //操作类型 0：默认值 1：新增 2：修改 3详情：
  formData: {},        //父页面表单数据
  validCallback: noop, //回调函数
  formReset: false     //表单重置标识位
};

ConsumersManageForm = Form.create()(ConsumersManageForm);
export default ConsumersManageForm;

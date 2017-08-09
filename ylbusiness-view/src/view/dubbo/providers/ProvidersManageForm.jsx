import React from "react";
import {Row, Col,  Button,  Form,Select,Input} from "antd";
import {filterObject,formatDate} from "../../../common/util";
const Option = Select.Option;

class ProvidersManageForm extends React.Component{

  constructor(props){
    super(props);
    this.state={}
  }

  render() {

    //定义变量和参数
    const FormItem = Form.Item;
    const { idObj, oprType, formData } = this.props;
    const { getFieldProps, isFieldValidating, getFieldError } = this.props.form;

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

    console.log(JSON.stringify(paraJson));

    let errorLevel = "ok";
    let errorMessage = "正常";
    if (formData.address.startsWith("127.") || formData.address.startsWith("localhost:") || formData.address.startsWith("0.0.0.0:")){
      if (errorLevel != "error") set(errorLevel = "warn")
      errorMessage = errorMessage+"<br>警告:"+formData.address+"不是有效的远程服务地址，请检查提供方/etc/hosts映射是否正确。";
    }

    //动态配置
    let dynamicDisplay =false;
    let dynamic = "";
    if(formData.dynamic){
      dynamicDisplay = true;
      if(formData.override != null && formData.override.params != null){
        dynamic = formData.override.params
      }else{
        dynamic = "";
      }
    }

    //时间转换
    var newDate = new Date();
     newDate.setTime(paraJson.timestamp);
    var localTime = formatDate(newDate,"yyyy-MM-dd hh:mm:ss") + "("+paraJson.timestamp+")";

    /*<Row>
      <Col sm={24} md={24}>
        <FormItem  label="default.service.filter："
                   labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
          <Input {...(this.props.oprType === 3 ? null : itemDisable) } placeholder="请输入default.service.filter" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.parentItmName ? idObj.parentItmName : "parentItmName", { initialValue: this.props.formData.parentItmName ? this.props.formData.parentItmName : "" }) } />
        </FormItem>
      </Col>
    </Row>*/
    return(
      <Form horizontal form={this.props.form}>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="服务名："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input  placeholder="请输入服务名" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.service ? idObj.service : "service", { initialValue: this.props.formData.service ? this.props.formData.service : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="服务地址："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="textarea"  placeholder="请输入服务地址" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.url ? idObj.parentItmName : "url", { initialValue: this.props.formData.url ? this.props.formData.url : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <div {...(dynamicDisplay ? null : itemHidden)}>
          <Row>
            <Col sm={24} md={24}>
              <FormItem  label="动态配置："
                         labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                <Input  {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.dynamic ? idObj.dynamic : "dynamic", { initialValue: dynamic }) } />
              </FormItem>
            </Col>
          </Row>
        </div>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="主机名："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input  placeholder="请输入主机名" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.address ? idObj.address : "address", { initialValue: this.props.formData.address ? this.props.formData.address : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="时间戳："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input  placeholder="请输入时间戳" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.timestamp ? paraJson.timestamp : "timestamp", { initialValue: paraJson.timestamp ? localTime : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="方法列表："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请选择服务名" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.methods ? paraJson.methods : "methods", { initialValue: paraJson.methods ? paraJson.methods : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="Dubbo版本："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入Dubbo版本" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.dubbo ? paraJson.dubbo : "dubbo", { initialValue: paraJson.dubbo ?paraJson.dubbo : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="revision："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入revision" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.revision ? paraJson.revision : "revision", { initialValue: paraJson.revision ? paraJson.revision : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="应用名："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入应用名" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.application ? paraJson.application : "application", { initialValue: paraJson.application ? paraJson.application : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="所属端："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入所属端" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.side ? paraJson.side : "side", { initialValue: paraJson.side ? paraJson.side : "" }) } />
            </FormItem>
          </Col>
        </Row>

        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="default.service.filter："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps("default.service.filter", { initialValue: paraJson['default.service.filter'] ? paraJson['default.service.filter'] : "" }) } />
            </FormItem>
          </Col>
        </Row>

        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="进程号："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入进程号" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.pid ? paraJson.pid : "pid", { initialValue: paraJson.pid ? paraJson.pid : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="接口名："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入接口名" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.interface ? paraJson.interface : "interface", { initialValue: paraJson.interface ? paraJson.interface : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="绑定所有IP："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入绑定所有IP" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(paraJson.anyhost ? paraJson.anyhost : "anyhost", { initialValue: paraJson.anyhost ? paraJson.anyhost : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="类型："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入类型" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.dynamic ? idObj.dynamic : "dynamic", { initialValue: this.props.formData.dynamic ? this.props.formData.dynamic?'动态':'静态' : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="状态："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入状态" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.enabled ? idObj.enabled : "enabled", { initialValue: this.props.formData.enabled ? this.props.formData.enabled?'已启用':'已禁用' : "" }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem  label="检查："
                       labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input type="textarea" placeholder="请输入检查" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps( "check", { initialValue: errorMessage  }) } />
            </FormItem>
          </Col>
        </Row>
      </Form>
    )
  }
}

const noop = () => { };
ProvidersManageForm.defaultProps = {
  valid: false,        //校验状态，通过父页面修改该值触发componentWillReceiveProps方法
  idObj: {},          //ID属性对象
  oprType: 0,         //操作类型 0：默认值 1：新增 2：修改 3详情：
  formData: {},        //父页面表单数据
  validCallback: noop, //回调函数
  formReset: false     //表单重置标识位
}
ProvidersManageForm = Form.create()(ProvidersManageForm);
export default ProvidersManageForm;

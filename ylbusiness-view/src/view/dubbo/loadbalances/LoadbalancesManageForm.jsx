import React from "react"
import { Row, Col, Form, Input,Select, Button } from "antd";
import { filterObject } from "../../../common/util";
import TdSelect from "../../../component/TdSelect";
import {requestSelectData} from "../../../common/util";
import {ylagent} from "../../../config/server";
const Option = Select.Option;

class LoadbalancesManageForm extends React.Component{
  constructor(props) {
    super(props);
    this.state = {
      valid: false,
      modeltypeData:[]
    }
  }
  getServiceList(){
    requestSelectData(ylagent.dubbo.loadbalances.getServiceList, {
      type: ["serviceList"],
      isSpace: true
    }, true, (oRes) => {
      this.setState({
        modeltypeData: oRes
      });
    });
  }
  componentDidMount(){
    this.getServiceList();
  }
  //父页面通过修改props 中属性的值触发该方法
  componentWillReceiveProps(nextProps) {
    //更新表单数据
    if (nextProps.valid === true && this.state.valid === false) {
      this.setState({ valid: true }, () => {
        this.validForm();
        this.setState({ valid: false });
      });
    }
    //重置表单
    if (nextProps.formReset === true) {
      this.props.form.resetFields();
    }
  }
  //子页面表单校验
  validForm() {
    const { oprType, validCallback } = this.props;
    const obj = this;
    this.props.form.validateFields((errors, data) => {
      //执行父页面回调函数
      validCallback(oprType, errors, data);
    });
  }
  render() {
    //定义变量和参数
    const FormItem = Form.Item;
    const { idObj, oprType, formData } = this.props;
    const { onSubmit, onReset } = this.props;
    const { getFieldProps } = this.props.form;

    //定义虚拟DOM代码片段
    const advSearchVDom = [];
    const itemDisable = {
      disabled: true
    }
    const itemHidden = {
      hidden: true
    }
    const serverice = getFieldProps(idObj.service ? idObj.service : "service",
      {
        initialValue: this.props.formData.service ?this.props.formData.service:'' ,
        validate: [{
          rules: [
            { required: true,  message: '服务名不能为空' },
          ],
          trigger: 'onBlur'
        }],
      }
    );
    const method = getFieldProps(idObj.method ? idObj.method : "method",
      {
        initialValue: this.props.formData.method,
        validate: [{
          rules: [
            { required: true,  message: '方法名不能为空'},
          ],
          trigger: 'onBlur'
        }],
      }
    );
    const loadBalances = getFieldProps(idObj.strategy ? idObj.strategy :"strategy",
      {
        initialValue:  this.props.formData.strategy ? this.props.formData.strategy : "",
        validate: [{
          rules: [
            { required: true,  message: '服务策略不能为空' },
          ],
          trigger: 'onBlur'
        }],
      }
    );
    //<Input placeholder="请输入服务名" {...(this.props.oprType == 1 ? null : itemDisable) } {...serverice } />
    return (
      <Form horizontal form={this.props.form} className="advanced-search-form">
        <div hidden={true}>
          <Row>
            <Col sm={24} md={24}>
              <FormItem label="负载均衡ID：">
                <Input {...getFieldProps(idObj.id ? idObj.id : "id", { initialValue: this.props.formData.id ? this.props.formData.id : null }) }/>
              </FormItem>
            </Col>
          </Row>
        </div>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="服务名：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }} required>
              <TdSelect {...(this.props.oprType == 1 ? null : itemDisable) } {...serverice }
                  blankText="---请选择---"
                  dict={{ dict_value: "value", dict_text: "text" }}
                  data={this.state.modeltypeData.serviceList}
              />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="服务方法：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }} >
              <Input placeholder="请输入服务方法" {...(this.props.oprType == 1 ? null : itemDisable) } {...method}/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="服务策略：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }} >
              <Select {...loadBalances}>
                <Option value="">---请选择---</Option>
                <Option value="random">随机</Option>
                <Option value="roundrobin">轮询</Option>
                <Option value="leastactive">最少并发</Option>
              </Select>
            </FormItem>
          </Col>
        </Row>
      </Form>)
  }
}

const noop = () => {};
//定义组件标签的可配置属性
LoadbalancesManageForm.defaultProps = {
  valid: false,        //校验状态，通过父页面修改该值触发componentWillReceiveProps方法
  idObj: {},          //ID属性对象
  oprType: 0,         //操作类型 0：默认值 1：新增 2：修改 3详情：
  formData: {},        //父页面表单数据
  validCallback: noop, //回调函数
  formReset: false     //表单重置标识位
};

LoadbalancesManageForm = Form.create()(LoadbalancesManageForm);
export default LoadbalancesManageForm;

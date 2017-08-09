import React from "react";
import {Button, Form, Input, Col, Radio, Select, Row} from "antd";
import TdSelect from "../../../component/TdSelect";
import {requestSelectData} from "../../../common/util";
import {ylagent} from "../../../config/server";
/**
 * ModelNodeForm 步骤表单管理表单组件
 *
 * Auth: 蒋梦晨  Time: 2016-05-25
 */
class ModelNodeForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      valid: false,
      selectParams: {"positionList": "", "modelMainList": ""}
    }
  }

  //组件加载完成时触发该事件
  componentDidMount() {
    //加载下拉框数据
    requestSelectData(ylagent.workflow.modelNode.queryNodeDictMenu, {
      type: ["positionList", "modelMainList"],
      isSpace: true
    }, true, (oRes) => {
      this.setState({
        selectParams: oRes
      });
    });
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

    const positioncode = getFieldProps(idObj.positioncode ? idObj.positioncode : "positioncode", {
      initialValue: formData.positioncode == null ? "" : formData.positioncode,
      validate: [{
        rules: [
          {required: true, message: '必填'}
        ],
        trigger: 'onBlur'
      }]
    });

    const nodename = getFieldProps(idObj.nodename ? idObj.nodename : "nodename", {
      initialValue: formData.nodename,
      validate: [{
        rules: [
          {required: true, min: 2, max: 20, message: '必填'}
        ],
        trigger: 'onBlur'
      }]
    });

    const modelno = getFieldProps(idObj.modelno ? idObj.modelno : "modelno", {
      initialValue: formData.modelno == null ? "" : formData.modelno,
      validate: [{
        rules: [
          {required: true, message: '必填'}
        ],
        trigger: 'onBlur'
      }]
    });

    const itemDisable = {
      disabled: true
    };
    let selModelno=[];
    if(this.props.oprType != 2){
      selModelno.push(<FormItem key="proceSelect" label="工作流名称："
                                labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
        <TdSelect {...modelno} {...(this.props.oprType === 2 ? itemDisable : null) }
          dict={{ dict_value: "value", dict_text: "text" }}
          data={this.state.selectParams.modelMainList}
          blankText="---请选择---"
          size="large"/>
      </FormItem>);
    }

    return (
      <Form horizontal form={this.props.form}>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="步骤名称："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入步骤名" {...nodename}/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            <FormItem label="岗位名称："
                      labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
              <TdSelect {...positioncode } notFoundContent="暂无数据"
                                           blankText="---请选择---"
                                           dict={{ dict_value: "value", dict_text: "text" }}
                                           data={this.state.selectParams.positionList}
                                           size="large"/>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={24}>
            {selModelno}
          </Col>
        </Row>
      </Form>
    );
  }
}

const noop = () => {
};
ModelNodeForm.defaultProps = {
  valid: false,        //校验状态，通过父页面修改该值触发componentWillReceiveProps方法
  idObj: {},          //ID属性对象
  oprType: 0,         //操作类型 0：默认值 1：新增 2：修改 3详情：
  formData: {},        //父页面表单数据
  validCallback: noop, //回调函数
  formReset: false     //表单重置标识位
};

ModelNodeForm = Form.create()(ModelNodeForm);
export default ModelNodeForm;

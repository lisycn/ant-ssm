import React from "react";
import {Row, Col, Form, Input, DatePicker, Button} from "antd";
import {filterObject} from "../../../common/util";
import TdSelect from "../../../component/TdSelect";
import QueueAnim from "rc-queue-anim";
/**
 * WorkflowModelMainManageSearchForm 查询条件表单
 *
 * Auth：jiang.mc Time：2016-05-23
 */
class WorkflowModelMainManageSearchForm extends React.Component {
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

  render() {
    //定义变量和参数
    const FormItem = Form.Item;
    const {onSubmit, onReset} = this.props;
    const {getFieldProps} = this.props.form;
    const option = [{value: "", text: "请选择"}, {value: "1", text: "启用"}, {value: "0", text: "禁用"}];

    //定义虚拟DOM代码片段
    const advSearchVDom = [];
    const usrStatus = getFieldProps("usrStatus", {
      initialValue: ""
    });
    //渲染虚拟DOM
    return (
      <Form horizontal className="advanced-search-form">
        <Row>
          <Col sm={12} md={6}>
            <FormItem label="岗位名称：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入岗位名称" {...getFieldProps("positionname")} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label="岗位代码：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入岗位代码" {...getFieldProps("positioncode")} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label="岗位状态：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <TdSelect {...getFieldProps("status", {initialValue: ''}) } blankText="---请选择---"
                data={option}/>
            </FormItem>
          </Col>

        </Row>
        <Row>
          <Col span="24">
            <QueueAnim key="adv-search" type={["right", "left"]} ease={["easeOutQuart", "easeInOutQuart"]}>
              {this.state.advSearchShow === true ? advSearchVDom : null}
            </QueueAnim>
          </Col>
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
WorkflowModelMainManageSearchForm.defaultProps = {
  onSubmit: noop,
  onReset: noop
};

WorkflowModelMainManageSearchForm = Form.create()(WorkflowModelMainManageSearchForm);
export default WorkflowModelMainManageSearchForm;

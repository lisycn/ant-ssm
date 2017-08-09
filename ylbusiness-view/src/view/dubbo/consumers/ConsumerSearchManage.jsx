import React from "react";
import {Row, Col, message, Button, Modal, Icon, Form, Select, Input} from "antd";
import {filterObject} from "../../../common/util";
const Option = Select.Option;

class ConsumerSearchManage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {}
  }

  render() {
    //定义变量和参数
    const FormItem = Form.Item;
    const {onSubmit, onReset, searchPara} = this.props;
    const {getFieldProps, getFieldValue} = this.props.form;

    const degrade = getFieldProps("degrade", {
      initialValue: "0"
    });
    const route = getFieldProps("route", {
      initialValue: "0"
    });
    const notice = getFieldProps("notice", {
      initialValue: "0"
    });

    const keyword = getFieldProps("keyword", {
      initialValue: searchPara.keyword
    });
    // const address = getFieldProps("address", {
    //   initialValue: searchPara.address
    // });


    return (
      <Form horizontal className="advanced-search-form">
        <Row>
          <Col sm={12} md={8}>
            <FormItem label="机器IP/应用名/服务名：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入服务名" {...keyword} />
            </FormItem>
          </Col>
        <Col sm={12} md={8}>
          <FormItem label="降级：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
            <Select {...degrade}>
              <Option value="0">所有</Option>
              <Option value="1">未降级</Option>
              <Option value="2">已屏蔽</Option>
              <Option value="3">已容错</Option>
            </Select>
          </FormItem>
        </Col>
        </Row>
        <Row>
          <Col sm={12} md={8}>
            <FormItem label="路由：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Select {...route}>
                <Option value="0">所有</Option>
                <Option value="1">已路由</Option>
                <Option value="2">无路由</Option>
              </Select>
            </FormItem>
          </Col>
          <Col sm={12} md={8}>
            <FormItem label="通知：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Select {...notice}>
                <Option value="0">所有</Option>
                <Option value="1">已通知</Option>
                <Option value="2">未通知</Option>
              </Select>
            </FormItem>
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
                            this.props.form.setFieldsValue({["keyword"]:""});
                            onReset();
                        }}>重置</Button>
          </Col>
        </Row>
      </Form>);
  }
}
const noop = () => {
};

//定义组件标签的可配置属性
ConsumerSearchManage.defaultProps = {
  modeltype: [],
  onSubmit: noop,
  onReset: noop,
  searchPara: {}
};

//必须有create包装,才会带this.props.form属性
ConsumerSearchManage = Form.create()(ConsumerSearchManage);
export default ConsumerSearchManage;

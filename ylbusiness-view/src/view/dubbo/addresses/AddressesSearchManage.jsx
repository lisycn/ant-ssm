import React from "react";
import {Row, Col, Button, Form,Select,Input} from "antd";
import {filterObject} from "../../../common/util";
const Option = Select.Option;

class AdderssesSearchManage extends React.Component{
  constructor(props){
    super(props);
    this.state={}
  }
  render(){
    //定义变量和参数
    const FormItem = Form.Item;
    const {onSubmit, onReset} = this.props;
    const {getFieldProps,getFieldValue} = this.props.form;

    const status = getFieldProps("status", {
      initialValue: "0"
    });

    //<Col sm={12} md={8}>
    //  <FormItem label="服务名：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
    //    <Input placeholder="请输入服务名" {...getFieldProps("serviceName")} />
    //  </FormItem>
    //</Col>

    return(
      <Form horizontal className="advanced-search-form">
        <Row>
          <Col sm={12} md={8}>
            <FormItem label="机器IP：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入机器IP" {...getFieldProps("keyword")} />
            </FormItem>
          </Col>
          <Col sm={12} md={8}>
            <FormItem label="角色：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Select {...status}>
                <Option value="0">所有</Option>
                <Option value="3">提供者</Option>
                <Option value="2">消费者</Option>
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
AdderssesSearchManage.defaultProps = {
  modeltype:[],
  onSubmit: noop,
  onReset: noop
};

//必须有create包装,才会带this.props.form属性
AdderssesSearchManage = Form.create()(AdderssesSearchManage);
export default AdderssesSearchManage;

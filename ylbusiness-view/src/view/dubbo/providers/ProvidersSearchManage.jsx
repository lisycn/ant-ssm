import React from "react";
import {Row, Col,  Button, Form,Select,Input} from "antd";
import {filterObject} from "../../../common/util";
const Option = Select.Option;

class ProvidersSearchManage extends React.Component{
  constructor(props){
    super(props);
    this.state={}
  }
  render(){
    //定义变量和参数
    const FormItem = Form.Item;
    const {onSubmit, onReset,searchPara} = this.props;
    const {getFieldProps,getFieldValue} = this.props.form;

    const dynamic = getFieldProps("dynamic", {
      initialValue: ""
    });
    const enabled = getFieldProps("enabled", {
      initialValue: ""
    });

    const keyword = getFieldProps("keyword", {
      initialValue: searchPara.keyword
    });

    //<Col sm={12} md={8}>
    //  <FormItem label="检查：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
    //    <Select {...checkResult}>
    //      <Option value="0">所有</Option>
    //      <Option value="1">正常</Option>
    //      <Option value="2">警告</Option>
    //      <Option value="3">出错</Option>
    //    </Select>
    //  </FormItem>
    //</Col>

    return(
      <Form horizontal className="advanced-search-form">
        <Row>
          <Col sm={12} md={8}>
            <FormItem label="机器IP/应用名/服务名：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入服务名" {...keyword} />
            </FormItem>
          </Col>
          <Col sm={12} md={8}>
            <FormItem label="权重：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入权重" {...getFieldProps("weight")} />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={12} md={8}>
            <FormItem label="类型：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Select {...dynamic}>
                <Option value="">所有</Option>
                <Option value="true">动态</Option>
                <Option value="false">静态</Option>
              </Select>
            </FormItem>
          </Col>
          <Col sm={12} md={8}>
            <FormItem label="状态：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Select {...enabled}>
                <Option value="">所有</Option>
                <Option value="true">已启用</Option>
                <Option value="false">已禁用</Option>
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
                            this.props.form.setFieldsValue({["keyword"]:""});
                        }}>重置</Button>
          </Col>
        </Row>
      </Form>);
  }
}

const noop = () => {
};

//定义组件标签的可配置属性
ProvidersSearchManage.defaultProps = {
  modeltype:[],
  onSubmit: noop,
  onReset: noop,
  searchPara:{}
};

//必须有create包装,才会带this.props.form属性
ProvidersSearchManage = Form.create()(ProvidersSearchManage);
export default ProvidersSearchManage;

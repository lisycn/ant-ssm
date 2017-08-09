import React from "react";
import {Row, Col, message, Button, Icon, Form,Select,Input} from "antd";
import {filterObject} from "../../../common/util";
const Option = Select.Option;

class LoadbalancesSearch extends React.Component{
  constructor(props){
    super(props);
    this.state={}
  }
  render(){
    //定义变量和参数
    const FormItem = Form.Item;
    const {onSubmit, onReset} = this.props;
    const {getFieldProps,getFieldValue} = this.props.form;

    const loadBalances = getFieldProps("strategy", {
      initialValue: ""
    });
    /*<Col sm={12} md={8}>
      <FormItem label="服务方法：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
        <Input placeholder="请输入服务方法" {...getFieldProps("method")} />
      </FormItem>
    </Col>*/

    return(
      <Form horizontal className="advanced-search-form">
        <Row>

          <Col sm={12} md={8}>
            <FormItem label="服务名：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入服务名" {...getFieldProps("service")} />
            </FormItem>
          </Col>
          <Col sm={12} md={8}>
            <FormItem label="负载均衡策略：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Select {...loadBalances}>
                <Option value="">所有</Option>
                <Option value="random">随机</Option>
                <Option value="roundrobin">轮询</Option>
                <Option value="leastactive">最少并发</Option>
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
      </Form>)

  }
}

const noop = () => {
};

//定义组件标签的可配置属性
LoadbalancesSearch.defaultProps = {
  modeltype:[],
  onSubmit: noop,
  onReset: noop
};

//必须有create包装,才会带this.props.form属性
LoadbalancesSearch = Form.create()(LoadbalancesSearch);
export default LoadbalancesSearch;

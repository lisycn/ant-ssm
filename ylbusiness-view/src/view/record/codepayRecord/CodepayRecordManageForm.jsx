import React from 'react';
import { Row, Col, Form, Input, Button, Select } from 'antd';
import { filterObject } from '../../../common/util';
import TdSelect from '../../../component/TdSelect';
import { requestSelectData } from '../../../common/util';
import { tdpub } from '../../../config/server';

class CodepayRecordManageForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
      selectParams: {},
    };
  }

  render() {
    // 定义变量和参数
    const FormItem = Form.Item;
    const { onSubmit, onReset } = this.props;
    const { getFieldProps } = this.props.form;
    const transtatus = getFieldProps("transtatus", {
        initialValue: ""
    });
    // 渲染虚拟DOM
    return (
      <Form horizontal className='advanced-search-form'>
        <Row>
          <Col sm={12} md={6}>
            <FormItem label='账号' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder='请输入账号' {...getFieldProps('account')} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label='支付订单号' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder='请输入支付订单号：' {...getFieldProps('payid')} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
              <FormItem label="交易状态：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                  <Select {...transtatus}>
                      <Select.Option value="">请选择</Select.Option>
                      <Select.Option value="01">未上传</Select.Option>
                      <Select.Option value="02">未交易</Select.Option>
                      <Select.Option value="03">交易成功</Select.Option>
                      <Select.Option value="04">交易失败</Select.Option>
                      <Select.Option value="05">可疑</Select.Option>
                  </Select>
              </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={{ span: 12, offset: 12 }} md={{ span: 6, offset: 18 }} style={{ textAlign: 'right' }}>
            <Button type='primary' htmlType='submit' onClick={(e) => {
              e.preventDefault();
              onSubmit(filterObject(this.props.form.getFieldsValue()));
            }}
            >搜索</Button>
            <Button onClick={(e) => {
              e.preventDefault();
              this.props.form.resetFields();
              onReset();
            }}
            >重置</Button>
          </Col>
        </Row>
      </Form>
    );
  }
}

const noop = () => {};
// 定义组件标签的可配置属性
CodepayRecordManageForm.defaultProps = {
  onSubmit: noop,
  onReset: noop,
};

CodepayRecordManageForm = Form.create()(CodepayRecordManageForm);
export default CodepayRecordManageForm;

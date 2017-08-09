import React from 'react';
import { Row, Col, Form, Input, Button } from 'antd';
import { filterObject } from '../../../common/util';
import TdSelect from '../../../component/TdSelect';
import { requestSelectData } from '../../../common/util';
import { tdpub } from '../../../config/server';

class CallbackRecordManageForm extends React.Component {
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
    const sysId = getFieldProps('sysId', { initialValue: '009' });
    // 渲染虚拟DOM
    return (
      <Form horizontal className='advanced-search-form'>
        <Row>
          <Col sm={12} md={6}>
            <FormItem label='订单编号：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder='请输入订单编号' {...getFieldProps('orderId')} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label='微信订单号：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder='请输入微信订单号：' {...getFieldProps('wXOrderNo')} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label='漏单原因：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder='请输入漏单原因：' {...getFieldProps('status')} />
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
CallbackRecordManageForm.defaultProps = {
  onSubmit: noop,
  onReset: noop,
};

CallbackRecordManageForm = Form.create()(CallbackRecordManageForm);
export default CallbackRecordManageForm;

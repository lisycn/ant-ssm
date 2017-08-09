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
            <FormItem label='渠道方' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder='请输入渠道方：' {...getFieldProps('channel')} />
            </FormItem>
          </Col>
          <Col sm={12} md={6}>
            <FormItem label='接入方' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder='请输入接入方：' {...getFieldProps('accesspart')} />
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

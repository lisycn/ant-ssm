import React from 'react';
import { Form, Input, Button, Row, Col, DatePicker } from 'antd';
import TdSelect from '../../../component/TdSelect/index';
import { filterObject, requestSelectData, callAjax } from '../../../common/util';

const FormItem = Form.Item;
class AgentSearchFrom extends React.Component {
  render() {
    const { onSubmit, onReset, selectDatas } = this.props;
    const { getFieldProps, getFieldValue } = this.props.form;
    const formItemLayout = {
      labelCol: { span: 10 },
      wrapperCol: { span: 14 },
    };
    return (
      <Form horizontal className='advanced-search-form'>
        <Row>
          <Col sm={12} md={7}>
            <FormItem label='代理商编号' {...formItemLayout}>
              <Input placeholder='代理商编号' {...getFieldProps('agentId') } maxLength={20} />
            </FormItem>
            <FormItem label='总代理商编号' {...formItemLayout}>
              <Input placeholder='总代理商编号' {...getFieldProps('agentIdT') } maxLength={20} />
            </FormItem>
            <FormItem label='登录状态' {...formItemLayout}>
              <TdSelect blankText="请选择" {...getFieldProps('agentStatus', { initialValue: '' }) }
                dict={{ dict_value: 'value', dict_text: 'text' }}
                data={selectDatas.AGENTSTATUS}
                size="large"
              />
            </FormItem>
          </Col>
          <Col sm={12} md={7}>
            <FormItem label='代理商登录账号' {...formItemLayout}>
              <Input placeholder='代理商登录账号' {...getFieldProps('logonName') } maxLength={20} />
            </FormItem>
            <FormItem label='总代理商登录账号' {...formItemLayout}>
              <Input placeholder='总代理商登录账号' {...getFieldProps('logonNameT') } maxLength={20} />
            </FormItem>
            <FormItem label='交易状态' {...formItemLayout}>
              <TdSelect blankText="请选择" {...getFieldProps('frozState', { initialValue: '' }) }
                dict={{ dict_value: 'value', dict_text: 'text' }}
                data={selectDatas.AGENTFROZSTATE}
                size="large"
              />
            </FormItem>
          </Col>
          <Col sm={12} md={7}>
            <FormItem label='代理商名称' {...formItemLayout}>
              <Input placeholder='代理商名称' {...getFieldProps('agentName') } maxLength={20} />
            </FormItem>
            <FormItem label='总代理商名称' {...formItemLayout}>
              <Input placeholder='总代理商名称' {...getFieldProps('agentNameT') } maxLength={20} />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span={12} offset={12} style={{ textAlign: 'right' }}>
            <Button type='primary' icon='search' htmlType='submit'
              onClick={(e) => {
                e.preventDefault();
                onSubmit(filterObject(this.props.form.getFieldsValue()));
              } }
            >搜索</Button>
            <Button onClick={(e) => {
              e.preventDefault();
              this.props.form.resetFields();
              onReset();
            } }
            >重置</Button>
          </Col>
        </Row>
      </Form>
    );
  }
}
AgentSearchFrom = Form.create()(AgentSearchFrom);
export default AgentSearchFrom;

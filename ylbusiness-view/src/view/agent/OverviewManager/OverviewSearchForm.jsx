import React from 'react';
import { Form, Input, Button, Row, Col, DatePicker } from 'antd';
import TdSelect from '../../../component/TdSelect/index';
import { filterObject, requestSelectData, callAjax } from '../../../common/util';
const RangePicker = DatePicker.RangePicker;
const FormItem = Form.Item;
class OverviewSearchForm extends React.Component {
  componentWillReceiveProps(nextProps) {
    // 更新表单数据
    if (nextProps.valid === true && this.state.valid === false) {
      this.setState({ valid: true }, () => {
        this.validForm();
        this.setState({ valid: false });
      });
    }
  }

  // 获取父页面值
  validForm() {
    const { callback } = this.props;
    const formparams = filterObject(this.props.form.getFieldsValue());
    callback(formparams);
  }

  /**
   * 处理日期选择框
   */
  handlerDatePickerChange(value, dateString) {
    this.props.form.setFieldsValue({ startTime: dateString[0], endTime: dateString[1] });
  }
  /**
   * 日期选择框清空值
   */
  handlerDatePickerReset() {
    this.props.form.setFieldsValue({ startTime: '', endTime: '' });
  }

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
            <FormItem label='分润计算日期' {...formItemLayout}>
              <RangePicker onChange={this.handlerDatePickerChange.bind(this)} value={[getFieldValue('sTime'), getFieldValue('eTime')]} />
            </FormItem>
          </Col>
          <Col sm={12} md={7}>
            <FormItem label='代理商登录账号' {...formItemLayout}>
              <Input placeholder='代理商登录账号' {...getFieldProps('logonName') } maxLength={20} />
            </FormItem>
            <FormItem label='总代理商登录账号' {...formItemLayout}>
              <Input placeholder='总代理商登录账号' {...getFieldProps('logonNameT') } maxLength={20} />
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
OverviewSearchForm = Form.create()(OverviewSearchForm);
export default OverviewSearchForm;

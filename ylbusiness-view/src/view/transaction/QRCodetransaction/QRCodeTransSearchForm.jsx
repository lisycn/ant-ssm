import React from 'react';
import { Row, Col, Form, Input, Button, Icon, DatePicker } from 'antd';
import TdSelect from '../../../component/TdSelect';
const RangePicker = DatePicker.RangePicker;
import { filterObject } from '../../../common/util';

class QRCodeTransSearchForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      valid: false,
      selectParams: {},
      merAuthUsr: {},
    };
  }

  // 父页面通过修改props 中属性的值触发该方法
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
    const FormItem = Form.Item;
    const { onSubmit, onReset, selectDatas } = this.props;
    const { getFieldProps, getFieldValue } = this.props.form;
    const formItemLayout = {
      labelCol: { span: 7 },
      wrapperCol: { span: 14 },
    };
    return (
      <Form horizontal className="advanced-search-form">
        <Row>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="支付订单号" >
              <Input placeholder="支付订单号" {...getFieldProps('payOrdNo')} />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="商户编号" >
              <Input placeholder="商户编号" {...getFieldProps('custId') } />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="商户名称" >
              <Input placeholder="商户名称" {...getFieldProps('custName') } />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="代理商编号" >
              <Input placeholder="代理商编号" {...getFieldProps('agentId') } />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="总代理商编号" >
              <Input placeholder="总代理商编号" {...getFieldProps('agentIdTotal') } />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="代理商名称" >
              <Input placeholder="代理商名称" {...getFieldProps('agentName') } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="总代理商名称" >
              <Input placeholder="总代理商名称" {...getFieldProps('agentNameTotal') } />
            </FormItem>
          </Col>
          <div hidden='true' style={{ display: 'none' }}>
            <FormItem>
              <Input {...getFieldProps('startTime') } />
            </FormItem>
            <FormItem>
              <Input {...getFieldProps('endTime') } />
            </FormItem>
          </div>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="订单时间" >
              <RangePicker onChange={this.handlerDatePickerChange.bind(this)} value={[getFieldValue('startTime'), getFieldValue('endTime')]} />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout}
              label="订单状态"
            >
              <TdSelect blankText="请选择"
                {...getFieldProps('payStatus', { initialValue: '' }) }
                dict={{ dict_value: 'value', dict_text: 'text' }}
                data={ selectDatas.PAYSTATUS }
              />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="支付方式" >
              <TdSelect blankText="请选择"
                {...getFieldProps('channelCode', { initialValue: '' }) }
                dict={{ dict_value: 'value', dict_text: 'text' }}
                data={ selectDatas.CHANNELCODE }
              />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="代付状态" >
              <TdSelect blankText="请选择"
                {...getFieldProps('settleStatus', { initialValue: '' }) }
                dict={{ dict_value: 'value', dict_text: 'text' }}
                data={ selectDatas.SETTLE_STATUS }
              />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={{ span: 12, offset: 12 }} md={{ span: 6, offset: 18 }} style={{ textAlign: 'right' }}>
            <Button type="primary" htmlType="submit" onClick={(e) => {
              e.preventDefault();
              onSubmit(filterObject(this.props.form.getFieldsValue()));
            } }
            >
              <Icon type="search" />搜索</Button>
            <Button onClick={(e) => {
              e.preventDefault();
              this.props.form.resetFields();
              this.handlerDatePickerReset.bind(this);
              onReset();
            } }
            >重置</Button>
          </Col>
        </Row>
      </Form>
    );
  }
}

const noop = () => { };
// 定义组件标签的可配置属性
QRCodeTransSearchForm.defaultProps = {
  onSubmit: noop,
  onReset: noop,
};

QRCodeTransSearchForm = Form.create()(QRCodeTransSearchForm);
export default QRCodeTransSearchForm;

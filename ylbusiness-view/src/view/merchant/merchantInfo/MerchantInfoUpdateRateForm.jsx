import React from 'react';
import { Col, Form, Input, Row, Button } from 'antd';

class MerchantInfoUpdateRateForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      valid: false,
      validStatus: false,
    };
  }
  componentWillReceiveProps(nextProps) {
    // 由于this.props.form.validateFields会触发componentWillReceiveProps
    // 需要配合状态中的state来防止循环校验
    if (nextProps.valid === true && this.state.valid === false) {
      this.setState({ valid: true }, () => {
        this.validForm();
        this.setState({ valid: false });
      });
    }
    // 重置表单
    if (nextProps.formReset === true) {
      this.props.form.resetFields();
    }
  }

  // 提交表单操作
  handleSubmit() {
    const { handleType, validCallback } = this.props;
    this.props.form.validateFields((errors, data) => {
      validCallback(handleType, errors, data);
    });
  }
  validForm() {
    const { validCallback } = this.props;
    this.props.form.validateFields((errors, data) => {
      validCallback(errors, data);
    });
  }
  handleFormCancel() {
    const { handleFormCancel } = this.props;
    handleFormCancel();
  }
  render() {
    const FormItem = Form.Item;
    const { getFieldProps } = this.props.form;
    return (
      <Form horizontal className="compact-form" style={{ margin: 'auto', maxWidth: 960 }}>
        <Row>
          <Input maxLength="15" type="hidden" {...getFieldProps('custId', { initialValue: this.props.formData.custId ? this.props.formData.custId : '' }) } />
          <Col sm={24} md={11}>
            <FormItem label="二维码费率："labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input maxLength="80" {...getFieldProps('rateQrCode', { initialValue: this.props.formData.rateQrCode ? this.props.formData.rateQrCode : '' }) } />
            </FormItem>
          </Col>
        </Row>
        <div>
            <hr style={{ marginTop: 20, marginLeft: -16, marginRight: -16, borderTop: 'solid 1px #e9e9e9' }} />
            <div style={{ marginTop: 10, overflow: 'hidden' }}>
              <Button type="primary" onClick={this.handleSubmit.bind(this) } style={{ marginLeft: 16, float: 'right' }}>修改</Button>
              <Button type="ghost" onClick={this.handleFormCancel.bind(this) } style={{ float: 'right' }}>取消</Button>
            </div>
          </div>
      </Form>
    );
  }
}

const noop = () => { };
MerchantInfoUpdateRateForm.defaultProps = {
  valid: false,  // 用于标识表单当前是否需要被校验
  validCallback: noop, // 用于校验后的回调
  handleFormCancel: noop,
  handleType: 1,
  formData: {},
  basicFlag: 0,
};

MerchantInfoUpdateRateForm = Form.create()(MerchantInfoUpdateRateForm);
export default MerchantInfoUpdateRateForm;

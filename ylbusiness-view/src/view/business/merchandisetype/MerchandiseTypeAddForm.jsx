import React from 'react';
import { Col, Form, Input, Row, Button } from 'antd';
import TdSelect from '../../../component/TdSelect';

class MerchandiseTypeAddForm extends React.Component {

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
  handleCancel = () => this.setState({ previewVisible: false })

  handlePreview = (file) => {
    this.setState({
      previewImage: file.url || file.thumbUrl,
      previewVisible: true,
    });
  }
  render() {
    const FormItem = Form.Item;
    const { getFieldProps } = this.props.form;
    const { selectDatas } = this.props;
    
    // const noticePlatform = [
    //   { value: '1', text: '代理商' },
    //   { value: '2', text: '商户（APP）' },
    // ];
    return (
      <Form horizontal className="compact-form" style={{ margin: 'auto', maxWidth: 960 }}>
      
        <Row>
          <Col sm={24} md={21}>
            <FormItem label="商品类型："labelCol={{ span: 5 }} wrapperCol={{ span: 14 }}>
              <Input maxLength="50" placeholder='商品类型' {...getFieldProps('merchandiseType') } />
            </FormItem>
          </Col>
        </Row>
        <div>
            <hr style={{ marginTop: 20, marginLeft: -16, marginRight: -16, borderTop: 'solid 1px #e9e9e9' }} />
            <div style={{ marginTop: 10, overflow: 'hidden' }}>
              <Button type="primary" onClick={this.handleSubmit.bind(this) } style={{ marginLeft: 16, float: 'right' }}>确定</Button>
              <Button type="ghost" onClick={this.handleFormCancel.bind(this) } style={{ float: 'right' }}>取消</Button>
            </div>
          </div>
      </Form>
    );
  }
}

const noop = () => { };
MerchandiseTypeAddForm.defaultProps = {
  valid: false,  // 用于标识表单当前是否需要被校验
  validCallback: noop, // 用于校验后的回调
  handleFormCancel: noop,
  handleType: 1,
  formData: {},
  basicFlag: 0,
};

MerchandiseTypeAddForm = Form.create()(MerchandiseTypeAddForm);
export default MerchandiseTypeAddForm;

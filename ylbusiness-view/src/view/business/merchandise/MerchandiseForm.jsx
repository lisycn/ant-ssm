import React from 'react';
import { Col, Form, Input, Row, Button } from 'antd';
import { tdpub } from '../../../config/server';
import TdMulUploader from '../../../component/TdMulUploader';

class MerchandiseForm extends React.Component {
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
    const testUrl = tdpub.attachment.upload;

    const testData = { TABLENAME: 'mpomng_goodexchange_inf' };

    const image = 'data:image/png;base64,';
    /**
     * handletype: 1 //操作类型：1详情，2修改，3添加
     */
    const testFiles = [
      {
        label: '商品照片',
        name: 'a.png',
        editable: this.props.handletype === 2 ? false : true,
        url: image + this.props.formData.FJSRC_PIC_01,
        data: { LX: 'PIC', ORDERNUM: '01' },
      },
      {
        label: '商品详情照片',
        name: 'b.png',
        editable: this.props.handletype === 2 ? false : true,
        url: image + this.props.formData.FJSRC_PIC_02,
        data: { LX: 'PIC', ORDERNUM: '02' },
      },
    ];
    const testDoneCallback = (idx, file) => {
      const rsp = file.file.response,
        f = `FJSRC_${testFiles[idx].data.LX}_${testFiles[idx].data.ORDERNUM}`,
        n = `FJNAME_${testFiles[idx].data.LX}_${testFiles[idx].data.ORDERNUM}`,
        name = rsp[n],
        pos = name.lastIndexOf('.');

      // 赋值
      if (rsp.FJID_PIC_01) {
        this.props.form.setFieldsValue({ goodImg1: rsp.FJID_PIC_01 });
      }
      if (rsp.FJID_PIC_02) {
        this.props.form.setFieldsValue({ goodImg2: rsp.FJID_PIC_02 });
      }
      let img = null;
      if (pos > 0) {
        const suffix = name.substring(pos + 1, name.length);
        if ('jpg,jpeg,bmp,png,gif'.indexOf(suffix) !== -1) {
          img = 'data:image/png;base64,' + file.file.response[f];
        }
      }
      console.info('55', img);
      return img;
    };

    const testImgClick = (idx, isNew, ev) => {
      // 对于isNew为true的图片，可以考虑同步请求真实大小的图片地址并返回
      // 不返回或返回null, 则默认展现原img中的图片
      console.log(idx + isNew + ev);
    };
    return (
      <Form horizontal className="compact-form" style={{ margin: 'auto', maxWidth: 960 }}>
        <Row>
          <Input maxLength="15" type="hidden" {...getFieldProps('goodId', { initialValue: this.props.formData.goodId ? this.props.formData.goodId : '' }) } />
          <Col sm={24} md={21}>
            <FormItem label="商品名称："labelCol={{ span: 5 }} wrapperCol={{ span: 14 }}>
              <Input maxLength="50" {...getFieldProps('goodName', { initialValue: this.props.formData.goodName ? this.props.formData.goodName : '' }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={21}>
            <FormItem label="兑换所需积分："labelCol={{ span: 5 }} wrapperCol={{ span: 14 }}>
              <Input maxLength="50" {...getFieldProps('integral', { initialValue: this.props.formData.integral ? this.props.formData.integral : '' }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={21}>
            <FormItem label="商品价格："labelCol={{ span: 5 }} wrapperCol={{ span: 14 }}>
              <Input maxLength="50" {...getFieldProps('price', { initialValue: this.props.formData.price ? this.props.formData.price : '' }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
         <Col sm={24} md={21}>
            <FormItem label="库存数量："labelCol={{ span: 5 }} wrapperCol={{ span: 14 }}>
              <Input maxLength="50" {...getFieldProps('quantity', { initialValue: this.props.formData.quantity ? this.props.formData.quantity : '' }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col sm={24} md={21}>
            <FormItem label="商品描述："labelCol={{ span: 5 }} wrapperCol={{ span: 14 }}>
              <Input maxLength="50" type="textarea" rows={4} placeholder='商品描述' {...getFieldProps('des', { initialValue: this.props.formData.des ? this.props.formData.des : '' }) } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <p className="br" />
          <Input maxLength="15" type="hidden" {...getFieldProps('goodImg1', { initialValue: this.props.formData.goodImg1 ? this.props.formData.goodImg1 : '' }) } />
          <Input maxLength="15" type="hidden" {...getFieldProps('goodImg2', { initialValue: this.props.formData.goodImg2 ? this.props.formData.goodImg2 : '' }) } />
          <Col sm={22} md={22} offset={2}>
            <TdMulUploader
              url={testUrl}
              data={testData}
              files={testFiles}
              onImgClick={testImgClick.bind(this) }
              uploadDoneCallback={testDoneCallback.bind(this) }
            />
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
MerchandiseForm.defaultProps = {
  valid: false,  // 用于标识表单当前是否需要被校验
  validCallback: noop, // 用于校验后的回调
  handleFormCancel: noop,
  handleType: 2,
  formData: {},
  basicFlag: 0,
};

MerchandiseForm = Form.create()(MerchandiseForm);
export default MerchandiseForm;

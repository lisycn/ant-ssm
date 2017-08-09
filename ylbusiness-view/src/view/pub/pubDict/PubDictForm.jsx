import React from 'react';
import { Row, Col, Form, Input, Button } from 'antd';
import { callAjax } from '../../../common/util';
import { tdpos } from '../../../config/server';
import { openNotice } from '../../../common/antdUtil';
const FormItem = Form.Item;

class PubDictForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      valid: false,
      selectParams: {},
    };
  }
  componentWillReceiveProps(nextProps) {
    if (nextProps.valid === true && this.state.valid === false) {
      this.setState({ valid: true }, () => {
        this.validForm();
        this.setState({ valid: false });
      });
    }
    if (nextProps.formReset === true) {
      console.log('child component form reset.');
      this.props.form.resetFields();
    }
  }
  handleAdvLinkClick() {
    this.setState({
      advSearchShow: !this.state.advSearchShow,
    });
  }
  validForm() {
    const { oprType, validCallback } = this.props;
    this.props.form.validateFields((errors, data) => {
      validCallback(oprType, errors, data);
    });
  }
  dictCodeValidate(rule, value, callback) {
    if (!value) {
      callback();
    } else {
      if (value.length > 20) {
        callback('参数编码长度需小于等于20！');
      }
      const numReg = /[a-zA-Z0-9]+/;
      if (!numReg.test(value)) {
        callback('参数编码只能有字母、数字和下划线');
        return false;
      } else {
        callback();
      }
    }
    callback();
  }
  dictValValidate(rule, value, callback) {
    if (!value) {
      callback();
    } else {
      if (value.length > 20) {
        callback('参数值长度需小于等于20！');
      }
      const numReg = /[a-zA-Z0-9]+/;
      if (!numReg.test(value)) {
        callback('参数值只能有字母、数字和下划线');
        return false;
      } else {
        callback();
      }
    }
    callback();
  }
  add() {
    const { form } = this.props;
    let keys = form.getFieldValue('keys');
    let flag = keys[keys.length - 1];
    let name = form.getFieldProps('DICT_NAME_' + flag).value;
    let value = form.getFieldProps('DICT_VALUE_' + flag).value;
    if (name === null || name === '' || name === undefined) {
      openNotice('warning', '请填写第' + (flag + 1) + '个参数名称');

      return false;
    }
    if (value === null || value === '' || value === undefined) {
      openNotice('warning', '请填写第' + (flag + 1) + '个参数值');

      return false;
    }
    let i = keys[keys.length - 1] + 1;
    keys = keys.concat(i);
    console.log(keys);
    form.setFieldsValue({
      keys,
    });
  }
  remove(k) {
    if (k > 0) {
      const { form } = this.props;
      let keys = form.getFieldValue('keys');
      console.log('删除的 K =>', k);
      let i = 0;
      for (i in keys) {
        if (keys[i] == k) {
          console.log('删除的数组值的下标 i=>', i);
          keys = keys.filter((key) => {
            return key !== k;
          });
          console.log('KEYS =>', keys);
          form.setFieldsValue({
            keys,
          });
          console.log('KEYS length =>', typeof (keys.length));
        }
      }
    } else { }
  }


  checkDict(rule, value, callback) {
    const { form } = this.props;

    if (value) {
      const data = {
        DICT_NAME: value,
        DICT_CODE: form.getFieldValue('DICT_CODE'),
      };
      const opt = {
        url: tdpos.pubDict.checkDict,
        data,
      };
      callAjax(opt, (result) => {
        if (result.rspCod !== '01000000') {
          callback(result.rspMsg);
        }
      }, () => {
        openNotice('error', '网络错误', '提示');
      });
    } else {
      openNotice('error', '请填写参数名称', '提示');
    }
  }
  render() {
    const { idObj, formData } = this.props;
    const { getFieldProps, getFieldValue } = this.props.form;
    const formItemLayout = {
      labelCol: { span: 6 },
      wrapperCol: { span: 14 },
    };
    getFieldProps('keys', {
      initialValue: formData.key ? formData.key : [0],
    });
    const dictCodePorp = getFieldProps(idObj.DICT_CODE ? idObj.DICT_CODE : 'DICT_CODE', {
      initialValue: (formData.DICT_CODE),
      validate: [{
        rules: [
                    { required: true, message: '参数编码不能为空', whitespace: true },
                    { min: 1, max: 20, message: '参数编码为 1~20 个字符' },
                    { validator: this.dictCodeValidate.bind(this) },
        ],
        trigger: 'onBlur',
      }],
      validateFirst: true,
    });
    const dictNamePorp = getFieldProps(idObj.DICT_NAME ? idObj.DICT_NAME : 'DICT_NAME', {
      initialValue: (formData.DICT_NAME),
      validate: [{
        rules: [
                    { required: this.props.oprType === 2 || this.props.formData.DICT_LEVEL === 1 ? true : null, message: '参数名称不能为空', whitespace: true },
                    { min: 1, max: 50, message: '参数名称为 1~50 个字符' },
        ],
        trigger: 'onBlur',
      }],
      validateFirst: true,
    });
    const dictValuePorp = getFieldProps(idObj.DICT_VALUE ? idObj.DICT_VALUE : 'DICT_VALUE', {
      initialValue: (formData.DICT_VALUE),
      validate: [{
        rules: [
                    { min: 1, max: 10, message: '参数值为 1~10 个字符' },
                    { validator: this.dictValValidate.bind(this) },
        ],
        trigger: 'onBlur',
      }],
      validateFirst: true,
    });
    const dictAbrPorp = getFieldProps(idObj.DICT_ABR ? idObj.DICT_ABR : 'DICT_ABR', {
      initialValue: (formData.ABR),
      validate: [{
        rules: [
                    { min: 1, max: 10, message: '参数描述为 1~10 个字符' },
        ],
        trigger: 'onBlur',
      }],
      validateFirst: true,
    });
    const itemDisable = { disabled: true };
    const formItems = getFieldValue('keys').map((k) => {
      return (
                <Row key={k}>
                    <Input type='hidden' {...getFieldProps(idObj.ID ? idObj.ID : 'ID' + '_' + k, {
                      initialValue: formData.ids ? formData.ids[k] : '',
                      validate: [{
                        rules: [
                                { required: false },
                        ],
                        trigger: 'onBlur',
                      }],
                    }) } style={{ width: 200 }}
      />
                    <Col span={8}>
                        <FormItem label={'参数名称：'} labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
                            <Input placeholder="请输入参数名称" maxLength="50" {...getFieldProps(idObj.DICT_NAME ? idObj.DICT_NAME : 'DICT_NAME' + '_' + k, {
                              validate: [{
                                rules: [
                                        { required: (this.props.oprType === 1 && this.props.formData.DICT_LEVEL != 1) ? true : null, message: '参数名称不能为空', whitespace: true },
                                        { min: 1, max: 50, message: '参数名称为 1~50 个字符' },
                                        { validator: this.checkDict.bind(this) },
                                ],
                                trigger: 'onBlur',
                              }],
                            }) } style={{ width: 160 }}
      />
                        </FormItem>
                    </Col>
                    <Col span={8}>
                        <FormItem label={'参数值：'} labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
                            <Input placeholder="请输入参数值" maxLength="10" {...getFieldProps(idObj.DICT_VALUE ? idObj.DICT_VALUE : 'DICT_VALUE' + '_' + k, {
                              validate: [{
                                rules: [
                                        { required: (this.props.oprType === 1 && this.props.formData.DICT_LEVEL != 1) ? true : null, message: '参数名称不能为空', whitespace: true },
                                        { min: 1, max: 10, message: '参数值为 1~10 个字符' },
                                ],
                                trigger: 'onBlur',
                              }],
                            }) } style={{ width: 160 }}
      />
                        </FormItem>
                    </Col>

                    <Col span={8}>
                        <FormItem label={'描述：'} labelCol={{ span: 8 }} wrapperCol={{ span: 16 }}>
                            <Input placeholder="请输入参数描述"
                              {...getFieldProps(idObj.DICT_ABR ? idObj.DICT_ABR : 'DICT_ABR' + '_' + k) }
                              style={{ width: 160 }}
      />
                        </FormItem>
                    </Col>
                </Row>

            );
    });
    const removeButtonItems = getFieldValue('keys').map((k) => {
      return (
                <FormItem {...formItemLayout} labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                    <Button onClick={() => this.remove(k)} {...k === 0 ? itemDisable : null} >删除</Button>
                </FormItem>
            );
    });
    const addButtonItems = (
            <FormItem wrapperCol={{ span: 0, offset: 0 }}>
                <Button onClick={this.add.bind(this)} >新增参数</Button>
            </FormItem>
        );
    const editDiv = (
            <div >
                <Row>
                    <Col sm={24} md={24}>
                        <FormItem label="参数编码：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                            <Input {...dictCodePorp} placeholder="请输入参数编码" {...(this.props.oprType === 1 ? null : itemDisable) } />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col sm={24} md={24}>
                        <FormItem label="参数名称：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                            <Input {...dictNamePorp} placeholder="请输入参数名称" />
                        </FormItem>
                    </Col>
                </Row>

                <div hidden={this.props.formData.DICT_LEVEL === 1 || this.props.formData.DICT_LEVEL === '1' ? true : false}>
                    <Row>
                        <Col sm={24} md={24}>
                            <FormItem label="参数值：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                                <Input {...dictValuePorp} placeholder="请输入参数值" />
                            </FormItem>
                        </Col>
                    </Row>
                </div>
                <Row>
                    <Col sm={24} md={24}>
                        <FormItem label="描述：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                            <Input {...dictAbrPorp} placeholder="请输入参数描述" />
                        </FormItem>
                    </Col>
                </Row>
            </div>
        );


    const addDiv1 = (
            <div >
                <Row>
                    <Col sm={24} md={24}>
                        <FormItem label="参数编码：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                            <Input {...dictCodePorp} placeholder="请输入参数编码" {...(this.props.oprType === 1 ? null : itemDisable) } />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col sm={24} md={24}>
                        <FormItem label="参数名称：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                            <Input {...dictNamePorp} placeholder="请输入参数名称" />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col sm={24} md={24}>
                        <FormItem label="描述：" labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}>
                            <Input {...dictAbrPorp} placeholder="请输入参数描述" />
                        </FormItem>
                    </Col>
                </Row>
            </div>
        );

    const addDiv2 = (
            <div>
                <Row>
                    <Col span={9}>
                        <FormItem label="参数编码：" labelCol={{ span: 6 }} wrapperCol={{ span: 11 }}>
                            <Input {...dictCodePorp} placeholder="请输入参数编码" {...(this.props.oprType === 1 ? itemDisable : itemDisable) } />
                        </FormItem>
                    </Col>
                    <Col span={15}>
                        {addButtonItems}
                    </Col>
                </Row>

                <Row>
                    <Col span={20}>
                        {formItems}
                    </Col>
                    <Col span={4}>
                        {removeButtonItems}
                    </Col>
                </Row>
            </div>
        );


        // 如果是修改，则只可以修改参数名称或者参数值  1级只修改参数名称和备注，2级和3级可修改参数名称+参数值
        // 如果是添加，若是添加一级，则需要添加一次参数编码和参数名称
        // 如果是添加，若是添加二级或者三级，则需要增行添加多个
    return (
            <Form horizontal form={this.props.form} className="compact-form">
                <div hidden>
                    <Row>
                        <Col sm={24} md={24}>
                            <FormItem label="参数ID：">
                                <Input {...getFieldProps(idObj.DICT_ID ? idObj.DICT_ID : 'dictId', { initialValue: this.props.formData.DICT_ID ? this.props.formData.DICT_ID : null }) } />
                            </FormItem>
                        </Col>
                    </Row>
                </div>
                <div hidden>
                    <Row>
                        <Col sm={24} md={24}>
                            <FormItem label="上级参数编号："
                              labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}
    >
                                <Input {...(this.props.oprType === 3 ? null : itemDisable) } placeholder="请选择上级菜单ID" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.PARENT_ID ? idObj.PARENT_ID : 'PARENT_ID', { initialValue: this.props.formData.PARENT_ID ? this.props.formData.PARENT_ID : '' }) } />
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col sm={24} md={24}>
                            <FormItem label="级别："
                              labelCol={{ span: 6 }} wrapperCol={{ span: 14 }}
    >
                                <Input {...(this.props.oprType === 3 ? null : itemDisable) } placeholder="请选择上级菜单ID" {...(this.props.oprType === 1 ? null : itemDisable) } {...getFieldProps(idObj.DICT_LEVEL ? idObj.DICT_LEVEL : 'DICT_LEVEL', { initialValue: this.props.formData.DICT_LEVEL ? this.props.formData.DICT_LEVEL : '' }) } />
                            </FormItem>
                        </Col>
                    </Row>
                </div>
                {this.props.oprType === 2 ? editDiv : null}
                {this.props.oprType === 1 && this.props.formData.DICT_LEVEL === 1 ? addDiv1 : null}
                {this.props.oprType === 1 && this.props.formData.DICT_LEVEL != 1 ? addDiv2 : false}
            </Form>
        );
  }
}
const noop = () => { };
PubDictForm.defaultProps = {
  valid: false,        // 校验状态，通过父页面修改该值触发componentWillReceiveProps方法
  idObj: {},          // ID属性对象
  oprType: 0,         // 操作类型 0：默认值 1：新增 2：修改 3详情：
  formData: {},        // 父页面表单数据
  validCallback: noop, // 回调函数
  formReset: false,     // 表单重置标识位
  levelFlag: 0,           //增加一级或者二级菜单
};
PubDictForm = Form.create()(PubDictForm);
export default PubDictForm;

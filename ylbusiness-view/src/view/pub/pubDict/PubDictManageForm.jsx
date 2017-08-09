import React from 'react';
import { Row, Col, Form, Input, Button } from 'antd';
import { filterObject } from '../../../common/util';


class PubDictManageForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
      selectParams: {},
    };
  }

  checkValid() {
    let code = this.props.form.getFieldProps('dictCode').value.replace(/\s/g, '');
    if (!/^(?!_)/g.test(code)) {
      code = code.substring(1);
    }
    this.props.form.setFieldsValue({ 'dictCode': code });
  }

  checkValid2() {
    let name = this.props.form.getFieldProps('dictName').value.replace(/\s/g, '');
    if (!/^(?!_)/g.test(name)) {
      name = name.substring(1);
    }
    this.props.form.setFieldsValue({ 'dictName': name });
  }
  render() {
    const FormItem = Form.Item;
    const { onSubmit, onReset } = this.props;
    const { getFieldProps } = this.props.form;

    const formItemLayout = {
      labelCol: { span: 10 },
      wrapperCol: { span: 14 },
    };
    return (
            <Form horizontal className="advanced-search-form">
                <Row>
                    <Col sm={12} md={6}>
                        <FormItem label="参数编码：" {...formItemLayout}>
                            <Input maxLength="20" placeholder="请输入参数编码" {...getFieldProps('dictCode') } onKeyUp={this.checkValid.bind(this)} />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label="参数名称：" {...formItemLayout}>
                            <Input maxLength="50" placeholder="请输入参数名称" {...getFieldProps('dictName') } onKeyUp={this.checkValid2.bind(this)} />
                        </FormItem>
                    </Col>

                </Row>
                <Row>
                    <Col sm={{ span: 12, offset: 12 }} md={{ span: 6, offset: 18 }} style={{ textAlign: 'right' }}>
                        <Button type="primary" htmlType="submit" onClick={(e) => {
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
const noop = () => { };
PubDictManageForm.defaultProps = {
  onSubmit: noop,
  onReset: noop,
};
PubDictManageForm = Form.create()(PubDictManageForm);
export default PubDictManageForm;

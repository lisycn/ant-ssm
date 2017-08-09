import React from 'react';
import { Row, Col, Form, Input, Button } from 'antd';
import { filterObject } from '../../../common/util';
// 从后台交易中获取select列表时需要用到
// import { requestSelectData } from '../../../common/util';
// 调用后台交易时需要引入此后台交易码配置
// import { tdpub } from '../../../config/server';


/**
 * 商品信息查询【查询条件form组件】
 * add by liuyanwei 20160706
 */
class MerchandiseSerachForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
      selectParams: {},
      merAuthUsr: {},
    };
  }

  render() {
    // 定义变量和参数
    const FormItem = Form.Item;
    const { onSubmit, onReset } = this.props;
    const { getFieldProps } = this.props.form;

    // const sysId = getFieldProps('sysId', {
    //   initialValue: '010',
    // });
    // 渲染虚拟DOM
    return (
            <Form horizontal className='advanced-search-form'>
                <Row gutter={24}>
                  <Col sm={12} md={6}>
                        <FormItem label='商品编号：' labelCol={{ span: 10 }} wrapperCol={{ span: 10 }}>
                            <Input maxLength='20' placeholder='请输入商品编号' {...getFieldProps('merchandiseId')} style={{ width: 140 }} />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label='商品名称：' labelCol={{ span: 10 }} wrapperCol={{ span: 10 }}>
                            <Input maxLength='20' placeholder='请输入商品名称' {...getFieldProps('merchandiseName')} style={{ width: 140 }} />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label='商品类型：' labelCol={{ span: 10 }} wrapperCol={{ span: 10 }}>
                            <Input maxLength='20' placeholder='请输入商品类型' {...getFieldProps('merchandiseType')} style={{ width: 140 }} />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col sm={{ span: 10, offset: 12 }} md={{ span: 6, offset: 18 }} style={{ textAlign: 'right' }}>
                        <Button type='primary' htmlType='submit' icon='search' onClick={(e) => {
                          e.preventDefault();
                          onSubmit(filterObject(this.props.form.getFieldsValue()));
                        }}
                        >搜索</Button>
                        <Button onClick={(e) => {
                          e.preventDefault();
                          this.props.form.resetFields();
                          this.handlerDatePickerReset();
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
MerchandiseSerachForm.defaultProps = {
  onSubmit: noop,
  onReset: noop,
};

MerchandiseSerachForm = Form.create()(MerchandiseSerachForm);
export default MerchandiseSerachForm;

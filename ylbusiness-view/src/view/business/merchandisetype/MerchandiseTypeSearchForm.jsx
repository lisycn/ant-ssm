import React from 'react';
import { Row, Col, Form, Input, Button } from 'antd';
import { filterObject } from '../../../common/util';
import TdSelect from '../../../component/TdSelect';
/**
 *公告查找
 *
 */
class MerchandiseTypeSearchForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
    };
  }

  // 点击“高级搜索”事件
  handleAdvLinkClick() {
    this.setState({
      advSearchShow: !this.state.advSearchShow,
    });
  }


  render() {
    // 定义变量和参数
    const FormItem = Form.Item;
    const { onSubmit, onReset, selectDatas } = this.props;
    const { getFieldProps } = this.props.form;



    // 渲染虚拟DOM
    return (
      <Form horizontal className="advanced-search-form">
        <Row>
          <Col span="6">
           <FormItem label="商品类型编号："labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input maxLength="50" placeholder='商品类型编号' {...getFieldProps('merchandiseId') } />
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="商品类型："labelCol={{ span: 8 }} wrapperCol={{ span: 14 }}>
              <Input maxLength="50" placeholder='商品类型' {...getFieldProps('merchandiseType') } />
            </FormItem>
          </Col>
          <Col span="6" offset="18" style={{ textAlign: 'right' }}>
            <Button type="primary" htmlType="submit" onClick={(e) => {
              e.preventDefault();
              onSubmit(filterObject(this.props.form.getFieldsValue()));
            } }
            > 搜索</Button>
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
const noop = () => {
};
// 定义组件标签的可配置属性
MerchandiseTypeSearchForm.defaultProps = {
  onSubmit: noop,
  onReset: noop,
};

MerchandiseTypeSearchForm = Form.create()(MerchandiseTypeSearchForm);
export default MerchandiseTypeSearchForm;

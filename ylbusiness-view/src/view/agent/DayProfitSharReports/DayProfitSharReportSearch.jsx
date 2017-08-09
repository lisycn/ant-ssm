import React from 'react';
import { Row, Col, Form, Input, Button, Icon, DatePicker } from 'antd';
import TdSelect from '../../../component/TdSelect';
const RangePicker = DatePicker.RangePicker;
import { tdpos } from '../../../config/server';
import { filterObject, callAjax } from '../../../common/util';

class DayProfitSharReportSearch extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      valid: false,
      selectParams: {},
      merAuthUsr: {},
    };
  }
  // //组件加载完成时触发该事件, 加载业务员下拉列表
  // componentDidMount() {
  //   const obj = this;
  //   const param = {};
  //   const opts = {
  //     url: tdpos.mer.posGetAllUsrName,
  //     type: 'POST',
  //     dataType: 'json',
  //     data: param,
  //   };
  //   callAjax(opts, (result) => {
  //     if (result !== null) {
  //       obj.setState({
  //         merAuthUsr: result.resultList,
  //       }, () => {
  //       });
  //     } else {
  //       console.log();
  //     }
  //   }, () => {
  //     // console.log(info);
  //   });
  // }

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
    const { onSubmit, onReset } = this.props;
    const { getFieldProps, getFieldValue } = this.props.form;
    const formItemLayout = {
      labelCol: { span: 7 },
      wrapperCol: { span: 14 },
    };
    return (
      <Form horizontal className="advanced-search-form">
        <Row>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="代理商编号" >
              <Input placeholder="代理商编号" {...getFieldProps('agentId')} />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="代理商名称" >
              <Input placeholder="代理商名称" {...getFieldProps('agentName') } />
            </FormItem>
          </Col>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="总代理商名称" >
              <Input placeholder="总代理商名称" {...getFieldProps('agentNameCon') } />
            </FormItem>
          </Col>
        </Row>
        <Row>
          <div hidden='true' style={{ display: 'none' }}>
            <FormItem>
              <Input {...getFieldProps('startTime') } />
            </FormItem>
            <FormItem>
              <Input {...getFieldProps('endTime') } />
            </FormItem>
          </div>
          <Col sm={24} md={8}>
            <FormItem {...formItemLayout} label="分润计算日期" >
              <RangePicker onChange={this.handlerDatePickerChange.bind(this)} value={[getFieldValue('startTime'), getFieldValue('endTime')]} />
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
DayProfitSharReportSearch.defaultProps = {
  onSubmit: noop,
  onReset: noop,
};

DayProfitSharReportSearch = Form.create()(DayProfitSharReportSearch);
export default DayProfitSharReportSearch;

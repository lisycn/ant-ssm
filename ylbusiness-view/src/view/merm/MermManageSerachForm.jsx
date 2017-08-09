import React from 'react';
import { Row, Col, Form, Input, DatePicker, Button, Icon } from 'antd';
import QueueAnim from 'rc-queue-anim';
import { filterObject, callAjax } from '../../../common/util';
import TdSelect from '../../../component/TdSelect';
import { ylpay } from '../../../config/server';
// 从后台交易中获取select列表时需要用到
// import { requestSelectData } from '../../../common/util';
// 调用后台交易时需要引入此后台交易码配置
// import { tdpub } from '../../../config/server';

// 获取日历控件
const RangePicker = DatePicker.RangePicker;

/**
 * 商户基本信息查询【查询条件form组件】
 * add by liuyanwei 20160706
 */
class MermManageSerachForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      advSearchShow: false,
      selectParams: {},
      merAuthUsr: {},
    };
  }
    // //组件加载完成时触发该事件
  componentDidMount() {
  }
    // 事件控制相关方法
    // 点击“高级搜索”事件
  handleAdvLinkClick() {
    this.setState({
      advSearchShow: !this.state.advSearchShow,
    });
  }

  // 选择日历控件，需要设置对应form的字段，暂时还未修改参数名字？申请日期
  handlerDatePickerChange(value, dateString) {
    console.log(dateString[0], dateString[1]);
    this.props.form.setFieldsValue({ startTime: dateString[0], endTime: dateString[1] });
  }

    // 选择日历控件，需要设置对应form的字段，暂时还未修改参数名字？审批完成日期
  handlerDatePickerChange1(value, dateString) {
    console.log(dateString[0], dateString[1]);
    this.props.form.setFieldsValue({ identifyStartTime: dateString[0], identifyEndTime: dateString[1] });
  }
    // 重置日历控件，将查询条件两个参数置空，暂时还未修改参数名字？审批日期+审批完成日期
  handlerDatePickerReset() {
    this.props.form.setFieldsValue({ startTime: '', endTime: '' });
    this.props.form.setFieldsValue({ identifyStartTime: '', identifyEndTime: '' });
  }

  render() {
    // 定义变量和参数
    const FormItem = Form.Item;
    const { onSubmit, onReset, selectDatas } = this.props;
    const { getFieldProps, getFieldValue } = this.props.form;

    // 定义虚拟DOM代码片段，展示更多的查询条件
    const advSearchVDom = [
      <Row key='adv-2' gutter={24}>
        <Col sm={12} md={6}>
                        <FormItem label='所属代理商：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                            <Input maxLength='20' placeholder='请输入代理商名称' {...getFieldProps('agentName')} style={{ width: 140 }} />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label='总代理商：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                            <Input maxLength='20' placeholder='请输入总代理商名称' {...getFieldProps('agentCode')} style={{ width: 140 }} />
                        </FormItem>
                    </Col>
            </Row>,
      <Row key='adv-1' gutter={24}>
                 <Col sm={12} md={6}>
                        <FormItem label='注册日期：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                            <RangePicker onChange={this.handlerDatePickerChange.bind(this) }
                              value={[getFieldValue('startTime'), getFieldValue('endTime')]}
                              style={{ width: 184 }}
                            />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label='认证日期：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                            <RangePicker onChange={this.handlerDatePickerChange1.bind(this) }
                              value={[getFieldValue('identifyStartTime'), getFieldValue('identifyEndTime')]}
                              style={{ width: 184 }}
                            />
                        </FormItem>
                    </Col>
            </Row>,

    ];

    // const sysId = getFieldProps('sysId', {
    //   initialValue: '010',
    // });
    // 渲染虚拟DOM
    return (
            <Form horizontal className='advanced-search-form'>
                <div hidden>
                    <FormItem>
                        <Input {...getFieldProps('agentName') } />
                    </FormItem>
                    <FormItem>
                        <Input {...getFieldProps('agentCode') } />
                    </FormItem>
                    <FormItem>
                        <Input {...getFieldProps('startTime') } />
                    </FormItem>
                    <FormItem>
                        <Input {...getFieldProps('endTime') } />
                    </FormItem>
                    <FormItem>
                        <Input {...getFieldProps('identifyStartTime') } />
                    </FormItem>
                    <FormItem>
                        <Input {...getFieldProps('identifyEndTime') } />
                    </FormItem>
                </div>
                <Row gutter={24}>
                    <Col sm={12} md={6}>
                        <FormItem label='手机号：' labelCol={{ span: 10 }} wrapperCol={{ span: 10 }}>
                            <Input maxLength='20' placeholder='请输入手机号' {...getFieldProps('custLogin')} style={{ width: 140 }} />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label='分享人：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                            <Input maxLength='20' placeholder='请输入分享人手机号' {...getFieldProps('referrer')} style={{ width: 140 }} />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label='商户编号：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                            <Input maxLength='20' placeholder='请输入商户编号' {...getFieldProps('custId')} style={{ width: 140 }} />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label='商户名称：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                            <Input maxLength='50' placeholder='请输入商户名称' {...getFieldProps('custName')} style={{ width: 140 }} />
                        </FormItem>
                    </Col>

                </Row>
                <Row gutter={24}>
                    <Col sm={12} md={6}>
                        <FormItem label='登录状态：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                          <TdSelect {...getFieldProps('isLoginFlag', { initialValue: '' })}
                            blankText="请选择" placeholder="请选择" dict={{ dict_value: 'value', dict_text: 'text' }} data={selectDatas.LOGINSTATUS}
                          />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label='认证状态：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                            <TdSelect {...getFieldProps('authStatus', { initialValue: '' })} blankText="请选择" placeholder="请选择" dict={{ dict_value: 'value', dict_text: 'text' }} data={selectDatas.AUTHSTATUS} />
                        </FormItem>
                    </Col>
                    <Col sm={12} md={6}>
                        <FormItem label='审核状态：' labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
                            <TdSelect {...getFieldProps('examiStatus', { initialValue: '' })} blankText="请选择" placeholder="请选择" dict={{ dict_value: 'value', dict_text: 'text' }} data={selectDatas.EXAMISTATUS} />
                        </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span='24'>
                        <QueueAnim key='adv-search'
                          type={['right', 'left']}
                          ease={['easeOutQuart', 'easeInOutQuart']}
                        >
                          {this.state.advSearchShow === true ? advSearchVDom : null}
                        </QueueAnim>
                    </Col>
                </Row>
                <Row>
                    <Col sm={{ span: 10, offset: 12 }} md={{ span: 6, offset: 18 }} style={{ textAlign: 'right' }}>
                        <a href=' javascript:void(0)' style={{ paddingTop: 10, paddingRight: 10, fontSize: 12 }} onClick={this.handleAdvLinkClick.bind(this) }>{this.state.advSearchShow === true ? '基本搜索' : '高级搜索'}&nbsp;<Icon type={this.state.advSearchShow === true ? 'caret-up' : 'caret-down'} style={{ fontSize: 10 }} /></a>
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
MermManageSerachForm.defaultProps = {
  onSubmit: noop,
  onReset: noop,
};

MermManageSerachForm = Form.create()(MermManageSerachForm);
export default MermManageSerachForm;

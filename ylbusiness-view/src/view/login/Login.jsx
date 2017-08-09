import './Login.less';
import React, { PropTypes } from 'react';
import MD5 from 'crypto-js/md5';
import { Form, Button, Row, Col } from 'antd';
import TdIconInput from '../../component/TdIconInput';
import { ylagent } from '../../config/server';
import { setLoginInfo, setLocalToken, callAjax } from '../../common/util';
import { openNotice } from '../../common/antdUtil';
import { rspInfo } from '../../common/authConstant';

const FormItem = Form.Item;

class Login extends React.Component {
  constructor(prop) {
    super(prop);
    this.state = {
      loading: false,
    };
  }

  handleFormSubmit(ev) {
    ev.preventDefault();
    const dat = this.props.form.getFieldsValue();
    if (!dat.usrName || dat.usrName.trim() === '') {
      openNotice('warning', '请输入用户名', '提示');
    } else if (!dat.usrPsw || dat.usrPsw.trim() === '') {
      openNotice('warning', '请输入密码', '提示');
    } else {
      this.setState({ loading: true }, () => {
        callAjax({
          url: ylagent.authUsr.userLogin,
          type: 'POST',
          dataType: 'json',
          data: {
            usrName: dat.usrName,
            usrPsw: MD5(dat.usrPsw).toString(),
            skip: true,
          },
        }, (result) => {
          if (result.rspCod === rspInfo.RSP_SUCCESS) {
            // 登录成功,保存登录信息(应包含用户名,用户权限,用户Token等信息)
            const info = { usrName: dat.usrName, token: result.rspData.token,isFirstLogin:result.rspData.isFirstLogin };
            setLoginInfo(info);
            setLocalToken(info.token);
            this.context.router.replace('/main/home');
          } else {
            openNotice('error', result.rspMsg, '登录失败');
            this.setState({ loading: false });
          }
        });
      });
    }
  }

  render() {
    const { getFieldProps } = this.props.form;
    return (
      <div className='td-login'>
        <header />
        <div className='td-login-header'>
          <div className='td-login-header-logo' />
          
        </div>
            <div className='td-login-form-warp' />
            <Form horizontal onSubmit={this.handleFormSubmit.bind(this) } className='td-login-form compact-form'>
              <h1>渠道管理平台</h1>
              <FormItem labelCol={{ span: 0 }} wrapperCol={{ span: 24 }} >
                <TdIconInput position="left" icon="user" maxLength={20} placeholder="请输入用户名" {...getFieldProps('usrName') } />
              </FormItem>
              <FormItem labelCol={{ span: 0 }} wrapperCol={{ span: 24 }} >
                <TdIconInput position="left" icon="lock" maxLength={20} type="password" placeholder="请输入密码" {...getFieldProps('usrPsw') } />
              </FormItem>
              <Row>
                <Col span='24'>
                  <Button size='large' type='primary' htmlType='submit' loading={this.state.loading}>确定</Button>
                </Col>
              </Row>
            </Form>
      </div>
    );
  }
}

// 应用contextTypes(不做手工页面跳转则不需要)
Login.contextTypes = {
  router: PropTypes.object.isRequired,
};
// 必须有create包装,才会带this.props.form属性
Login = Form.create()(Login);
export default Login;

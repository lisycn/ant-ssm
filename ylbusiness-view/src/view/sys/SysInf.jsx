import './SysInf.less';
import React, { PropTypes } from 'react';
import MD5 from 'crypto-js/md5';
import { Modal, Icon, Button } from 'antd';
import TweenOne from 'rc-tween-one';
import BASE64 from 'crypto-js/enc-base64';
import ENC_UTF8 from 'crypto-js/enc-utf8';
import { openNotice } from '../../common/antdUtil';
import { ylagent } from '../../config/server';
import { defaultPage, rspInfo } from '../../common/authConstant';
import { getLoginInfo, removeLoginInfo, getLocalToken, refreshLoginInfo, callAjax, setLoginInfo } from '../../common/util';
import UpdatePwdForm from './UpdatePwdForm';
import { logoutApiUrl } from '../../config/url';


class SysInf extends React.Component {
  constructor(props) {
    super(props);
    const info = getLoginInfo();
    this.state = {
      checkInterval: null,
      info,
      ltk: getLocalToken(),
      hrefCard: 'javascript:void(0);',
      hrefPay: 'javascript:void(0);',
      hrefAuth: 'javascript:void(0);',
      hrefPos: 'javascript:void(0);',
      hrefMore: 'javascript:void(0);',
      scaleCard: true,
      scalePay: true,
      scaleAuth: true,
      scalePos: true,
      scaleMore: true,
      modalVisible: info.isFirstLogin === 1,
      confirmLoading: false,
      modalTitle: '请修改初始密码',
      modelIsValid: false,
      modalOprType: 0,
      formReset: false,
      formData: {},
    };
  }

  componentDidMount() {
    this.loadSystemList();
    if (this.state.checkInterval === null) {
      this.state.checkInterval = setInterval(() => {
        if (this.state.ltk !== this.state.info.token) {
          refreshLoginInfo(this.state.ltk, true);
        }
      }, 30 * 1000);
    }
  }

  componentWillUnmount() {
    if (this.state.checkInterval !== null) {
      clearInterval(this.state.checkInterval);
      this.state.checkInterval = null;
    }
  }

  loadSystemList() {
    callAjax({
      url: ylagent.authUsr.getSysInfByUsr,
      data: defaultPage,
    }, (result) => {
      if (result.rspCod === rspInfo.RSP_SUCCESS) {
        const arr = result.rspData;
        for (let i = 0; i < arr.length; i++) {
          const sys = arr[i];
          const href = `${sys.sysUrl}#/getTkn?tk=${BASE64.stringify(ENC_UTF8.parse(this.state.ltk))}&usr=${this.state.info.usrName}&sys=${sys.sysId}`;
          switch (sys.sysId) {
            case '009':
              this.setState({ hrefAuth: href });
              break;
            case '010':
              this.setState({ hrefPay: href });
              break;
            case '011':
              this.setState({ hrefPos: href });
              break;
            default:
              break;
          }
        }
      } else {
        openNotice('error', result.rspMsg);
      }
    });
  }

  doLogoutClick() {
    const obj = this;
    Modal.confirm({
      title: '退出',
      content: '确认要退出登录吗？',
      onOk() {
        const ltk = getLocalToken();
        if (ltk !== null) {
          callAjax({
            url: ylagent.authUsr.userLogout,
            data: { token: ltk },
          }, () => {
            removeLoginInfo();
            obj.context.router.replace('/login');
          });
        } else {
          removeLoginInfo();
          obj.context.router.replace('/login');
        }
      },
    });
  }

  doLogout() {
    const ltk = getLocalToken();
    if (ltk !== null) {
      callAjax({
        url: ylagent.authUsr.userLogout,
        data: { token: ltk },
      }, () => {
        removeLoginInfo();
        window.location.href = logoutApiUrl;
      });
    } else {
      removeLoginInfo();
      window.location.href = logoutApiUrl;
    }
  }

  scaleItem(type, paused) {
    const newState = {};
    newState[`scale${type}`] = paused;
    switch (type) {
      case type:
        if (this.state[`scale${type}`] === !paused && this.state[`href${type}`] !== 'javascript:void(0);') {
          this.setState(newState);
        }
        break;
      default:
        break;
    }
  }

  handleModalOk() {
    this.setState({ modelIsValid: true });
  }
  // 模态框子页面回调
  callbackValid(oprType, errors, data) {
    this.setState({ modelIsValid: false });
    if (!!errors) {
      // openNotice('error','填写信息有误!','提示',1);
      // return;
    } else {
      this.setState({
        formData: Object.assign({}, this.state.formData, data),
        formReset: false,
        confirmLoading: true,
      }, () => {
        callAjax({
          url: ylagent.authUsr.updatePwd,
          data: { usrPsw: MD5(this.state.formData.newPwd).toString() },
        }, (result) => {
          if (result.rspCod === rspInfo.RSP_SUCCESS) {
            openNotice('success', '修改密码成功', '提示');
            this.setState({
              modalVisible: false,
              confirmLoading: false,
            }, () => {
              const info = getLoginInfo();
              info.isFirstLogin = 0;
              setLoginInfo(info);
            });
          } else {
            openNotice('error', result.rspMsg, '提示');
            this.setState({ confirmLoading: false });
          }
        });
      });
    }
  }
  render() {
    // 开发用代码，生产环境可注释掉，并把紧接的，被注释的代码放开注释
    const devVdom = (
      <div style={{ fontSize: 20, fontWeight: 'bold' }}>
        <p className='br' />
        <a target='_blank' href={ `http://127.0.0.1:3000#/getTkn?tk=${BASE64.stringify(ENC_UTF8.parse(this.state.ltk))}&usr=${this.state.info.usrName}&sys=009` }>用户中心-开发</a>
        <span> &nbsp; | &nbsp; </span>
        <a target='_blank' href={ `http://127.0.0.1:8001#/getTkn?tk=${BASE64.stringify(ENC_UTF8.parse(this.state.ltk))}&usr=${this.state.info.usrName}&sys=010` }>互联网支付-开发</a>
        <span> &nbsp; | &nbsp; </span>
        <a target='_blank' href={ `http://127.0.0.1:8001#/getTkn?tk=${BASE64.stringify(ENC_UTF8.parse(this.state.ltk))}&usr=${this.state.info.usrName}&sys=011` }>线下收单-开发</a>
      </div>
    );
    // const devVdom = null;
    return (
      <div className='td-sys-inf'>
        <div className='td-sys-inf-header-warp' />
        <div className='td-sys-inf-header'>
          <div className='td-sys-inf-header-logo' />
          <ul>
            <li><a href='javascript:void(0);'>欢迎您：{this.state.info.usrName}</a></li>
            <li><a href='javascript:void(0);' onClick={ this.doLogoutClick.bind(this) }>退出</a></li>
          </ul>
          <button id='hiddenLogoutBtn' style={{ display: 'none' }} onClick={ this.doLogout.bind(this) }>退出</button>
        </div>
        <div className='td-sys-inf-list'>
          <div className='td-sys-inf-warp'>
            <div className='td-sys-inf-line-warp' style={{ marginLeft: 0 }}>
              <div className='td-sys-inf-line td-sys-inf-line-card' />
            </div>
            <TweenOne animation={{ scale: 1, yoyo: true, repeat: -1, duration: 500 }} paused={this.state.scaleCard} style={{ transform: 'scale(1.1, 1.1)' }}>
              <a target={this.state.hrefCard === 'javascript:void(0);' ? '' : '_blank'} href={this.state.hrefCard} onMouseEnter={ () => { this.scaleItem('Card', false); } } onMouseLeave={ () => { this.scaleItem('Card', true); } }>
                <div className='td-sys-inf-card'>
                  <div style={{ display: this.state.hrefCard === 'javascript:void(0);' ? 'none' : 'block' }}>
                    <p><Icon type='credit-card' /></p>
                    <span>预付卡</span>
                  </div>
                </div>
              </a>
            </TweenOne>
          </div>
          <div className='td-sys-inf-warp'>
            <div className='td-sys-inf-line-warp'>
              <div className='td-sys-inf-line td-sys-inf-line-pay' />
            </div>
            <TweenOne animation={{ scale: 1, yoyo: true, repeat: -1, duration: 500 }} paused={this.state.scalePay} style={{ transform: 'scale(1.1, 1.1)' }}>
              <a target={this.state.hrefPay === 'javascript:void(0);' ? '' : '_blank'} href={this.state.hrefPay} onMouseEnter={ () => { this.scaleItem('Pay', false); } } onMouseLeave={ () => { this.scaleItem('Pay', true); } }>
                <div className='td-sys-inf-pay'>
                  <div style={{ display: this.state.hrefPay === 'javascript:void(0);' ? 'none' : 'block' }}>
                    <p><Icon type='ie' style={{ marginBottom: 8 }} /></p>
                    <span>互联网支付</span>
                  </div>
                </div>
              </a>
            </TweenOne>
          </div>
          <div className='td-sys-inf-warp'>
            <div className='td-sys-inf-line-warp'>
              <div className='td-sys-inf-line td-sys-inf-line-auth' />
            </div>
            <TweenOne animation={{ scale: 1, yoyo: true, repeat: -1, duration: 500 }} paused={this.state.scaleAuth} style={{ transform: 'scale(1.1, 1.1)' }}>
              <a target={this.state.hrefAuth === 'javascript:void(0);' ? '' : '_blank'} href={this.state.hrefAuth} onMouseEnter={ () => { this.scaleItem('Auth', false); } } onMouseLeave={ () => { this.scaleItem('Auth', true); } }>
                <div className='td-sys-inf-auth'>
                  <div style={{ display: this.state.hrefAuth === 'javascript:void(0);' ? 'none' : 'block' }}>
                    <p><Icon type='team' /></p>
                    <span>用户中心</span>
                  </div>
                </div>
              </a>
            </TweenOne>
          </div>
          <div className='td-sys-inf-warp'>
            <div className='td-sys-inf-line-warp'>
              <div className='td-sys-inf-line td-sys-inf-line-pos' />
            </div>
            <TweenOne animation={{ scale: 1, yoyo: true, repeat: -1, duration: 500 }} paused={this.state.scalePos} style={{ transform: 'scale(1.1, 1.1)' }}>
              <a target={this.state.hrefPos === 'javascript:void(0);' ? '' : '_blank'} href={this.state.hrefPos} onMouseEnter={ () => { this.scaleItem('Pos', false); } } onMouseLeave={ () => { this.scaleItem('Pos', true); } }>
                <div className='td-sys-inf-pos'>
                  <div style={{ display: this.state.hrefPos === 'javascript:void(0);' ? 'none' : 'block' }}>
                    <p><Icon type='calculator' /></p>
                    <span>线下收单</span>
                  </div>
                </div>
              </a>
            </TweenOne>
          </div>
          <div className='td-sys-inf-warp'>
            <div className='td-sys-inf-line-warp'>
              <div className='td-sys-inf-line td-sys-inf-line-more' />
            </div>
            <TweenOne animation={{ scale: 1, yoyo: true, repeat: -1, duration: 500 }} paused={this.state.scaleMore} style={{ transform: 'scale(1.1, 1.1)' }}>
              <a target={this.state.hrefMore === 'javascript:void(0);' ? '' : '_blank'} href={this.state.hrefMore} onMouseEnter={ () => { this.scaleItem('More', false); } } onMouseLeave={ () => { this.scaleItem('More', true); } }>
                <div className='td-sys-inf-more'>
                  <div style={{ display: this.state.hrefMore === 'javascript:void(0);' ? 'none' : 'block' }}>
                    <p><Icon type='ellipsis' /></p>
                    <span>更多</span>
                  </div>
                </div>
              </a>
            </TweenOne>
          </div>
        </div>
        { devVdom }

        <Modal title={this.state.modalTitle} visible={this.state.modalVisible} closable={false}
          footer={[
            <Button key='submit' type='primary' size='large' loading={this.state.confirmLoading} onClick={this.handleModalOk.bind(this) }>
              提 交
            </Button>,
            <Button key="back" type="ghost" size="large" onClick={() => { this.setState({ modalVisible: false }); } }>
              取消
            </Button>
          ]}
          >
          <UpdatePwdForm formReset={this.state.formReset} valid={this.state.modelIsValid}
            formData={this.state.formData} oprType={this.state.modalOprType}
            validCallback={(oprType, errors, data) => {
              this.callbackValid(oprType, errors, data);
            } }
            />
        </Modal>
      </div>
    );
  }
}

// 应用contextTypes(不做手工页面跳转则不需要)
SysInf.contextTypes = {
  router: PropTypes.object.isRequired,
};
export default SysInf;

import './Main.less';
import appConf from '../../config/app.json';
import React, { PropTypes } from 'react';
import { Link } from 'react-router';
import { connect } from 'react-redux';
import { Menu, Breadcrumb, Icon, Modal, Popover } from 'antd';
import TdCard from '../../component/TdCard';
import { clickMenuAction, buildMenuAction, toggleMenuAction } from '../../redux/action/main';
import { removeLoginInfo, removeLocalItem, getLocalToken, getLoginInfo, callAjax } from '../../common/util';
import { openNotice } from '../../common/antdUtil';
import { rspInfo } from '../../common/authConstant';
import { ylagent } from '../../config/server';
import { logoutApiUrl } from '../../config/url';
const confirm = Modal.confirm;


console.log(logoutApiUrl);

/**
 * 工程总体主框架
 *
 * Auth：yujun Time：2016-04-22
 */
class Main extends React.Component {
  constructor(props) {
    super(props);
  }

    // 组件构建完成时触发
  componentDidMount() {
    this.loadMenu();
    const { dispatch } = this.props;
    if (this.getMainState() !== null) {
      dispatch(clickMenuAction({
        keyPath: this.getMainState().updateBreadcrumbState.curPath[0] === '' ? ['HOME'] : this.getMainState().updateBreadcrumbState.curPath,
        key: this.getMainState().updateMenuState.curMenu,
      }));
    } else {
      dispatch(clickMenuAction({ keyPath: ['HOME'] }));
    }
    console.log('22222222222');
    let info = getLoginInfo();
    if (info.isFirstLogin === 1) {
      openNotice('warning', '请及时修改初始密码', '提示');
    }
  }

    // 该方法在vdom中使用bind重新设置this指向Main组件
  handleMenuClick(item) {
        // 调用action中的动作修改组件状态
    const { dispatch } = this.props;
    dispatch(clickMenuAction(item));
  }
  handleLogoClick() {
    const { dispatch } = this.props;
    dispatch(clickMenuAction({ keyPath: ['HOME'] }));
  }
  handleMenuIconClick(e) {
        // 使用jquery实现菜单收缩效果
    parseInt($('.td-layout-aside').css('left')) === 0 ? (() => {
      $('.td-layout-aside').stop().animate({ left: -224 });
      $('.td-layout-container, .td-layout-copyright').stop().animate({ paddingLeft: 0 });
    })() : (() => {
      $('.td-layout-aside').stop().animate({ left: 0 });
      $('.td-layout-container, .td-layout-copyright').stop().animate({ paddingLeft: 224 });
    })();
    e.preventDefault();
    e.stopPropagation();
  }
  loadMenu() {
    const { dispatch } = this.props;
        // action中发送ajax请求获取菜单数据
    dispatch(buildMenuAction({}));
  }
  getMainState() {
    let appState = sessionStorage.getItem('appState'), state = null;
    if (appState !== null) {
      state = JSON.parse(appState).main;
    }
    return state;
  }
  onToggle(item) {
        // 调用action中的动作修改组件状态
    const { dispatch } = this.props;
    dispatch(toggleMenuAction(item));
  }
  doLogout() {
    const obj = this;
    const tk = getLocalToken();
    const opt = {
      url: ylagent.logout.logoutPath,
      data: { token: tk },
    };
    confirm({
      title: '退出',
      content: '确认要注销用户吗？',
      onOk() {
        callAjax(opt, (result) => {
          if (result.rspCod === '000000') {
            window.location.href = logoutApiUrl;
          } else {
            openNotice('error', result.rspMsg, '');
          }
        }, (req, info, opt) => {
          openNotice('error', rspInfo.RSP_NETWORK_ERROR, '提示');
          obj.setState({ confirmLoading: false });
        });

        // obj.logout(1);
      },
    });
  }
  logout(type) {
        // 如果为用户手动点击退出(type === 1), 则关闭当前标签页
    if (type === 1) {
      removeLocalItem('sys');
      window.location.href = logoutApiUrl;  // 跳转登录页
    } else {
      removeLoginInfo();
      window.location.href = logoutApiUrl;
    }
  }
  render() {
    const obj = this, confirm = Modal.confirm;
        // 获取注入的状态(面包屑,菜单)
    const { breadcrumbState, menuState } = this.props;
    const content = (
            <TdCard title={getLoginInfo().usrName} hideHead style={{ width: 320, marginBottom: 0, borderRadius: 6 }}>
                <Link to="main/auth/updPwd" onClick={this.handleLogoClick.bind(this) } >
                    修改密码
                </Link>
            </TdCard>
        );
    const formatBreadcrumb = data => {
      let breadArray = [];
      if (data) {
        breadArray = data.concat();
      }
      return breadArray.reverse();
    };
    const loopBreadcrumb = data => data.map((item) => {
      return (
                <Breadcrumb.Item key={item}>
                    {item}
                </Breadcrumb.Item>
            );
    });
    return (
            <div className="td-layout">
                <div className="td-layout-copyright">
                    <div>
                        <span>{appConf.copyright}</span>
                    </div>
                </div>
                <div className="td-layout-container">
                    <div className="td-layout-breadcrumb">
                        <Breadcrumb>
                            <Breadcrumb.Item>{appConf.app}</Breadcrumb.Item>
                            {loopBreadcrumb(formatBreadcrumb(breadcrumbState.curPath)) }
                        </Breadcrumb>
                    </div>
                    <div className="td-layout-content">
                        {this.props.children}
                    </div>
                </div>
                <div className="td-layout-aside">
                    <div className="td-layout-aside-user overflow-text">
                        <Popover overlayClassName="popover-no-padding" placement="bottomLeft" content={content} trigger="click">
                            <a href="javascript:void(0)" title={getLoginInfo().usrName}><span className="td-layout-aside-welcome">欢迎您：</span>{getLoginInfo().usrName}</a>
                        </Popover>
                    </div>
                    <Menu mode="inline" style={{ borderRight: 0 }} theme="dark"
                      selectedKeys={[menuState.curMenu]}
                      onClick={this.handleMenuClick.bind(this) }
                      onOpen={this.onToggle.bind(this) }
                      onClose={this.onToggle.bind(this) }
                      openKeys={menuState.openKeys}
                      defaultOpenKeys={['DEMO']}
    >
                        {menuState.menuData}
                    </Menu>
                </div>
                <div className="td-layout-header">
                    <Link to="main/home" onClick={this.handleLogoClick.bind(this) }><div className="td-layout-item-logo"></div></Link>
                    <Icon type="logout" className="td-layout-item td-layout-item-logout" onClick={ this.doLogout.bind(this) } />
                    <Icon type="bars" className="td-layout-item td-layout-item-menu" onClick={this.handleMenuIconClick.bind(this) } />
                    <button id="hiddenLogoutBtn" style={{ display: 'none' }} onClick={ this.logout.bind(this) }></button>
                </div>
            </div>
        );
  }
}

// 应用contextTypes(不做手工页面跳转则不需要)
Main.contextTypes = {
  router: PropTypes.object.isRequired,
};

// 当前状态(初始化时为reducer中定义的初始状态)
function mapStateToProps(state) {
  return {
    breadcrumbState: state.main.updateBreadcrumbState,
    menuState: state.main.updateMenuState,
  };
}
// 状态注入至组件Main的props中(初始化时为reducer中定义的初始状态)
export default connect(mapStateToProps)(Main);

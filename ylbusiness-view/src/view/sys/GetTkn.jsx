import React, { PropTypes } from 'react';
import BASE64 from 'crypto-js/enc-base64';
import ENC_UTF8 from 'crypto-js/enc-utf8';
import { setLoginInfo, setLocalToken, setLocalItem, getUrlVal } from '../../common/util';

class GetTkn extends React.Component {
  componentDidMount() {
    const lclUrl = window.location.href;
    const tk = BASE64.parse(decodeURI(getUrlVal(lclUrl, 'tk'))).toString(ENC_UTF8);
    const nm = getUrlVal(lclUrl, 'usr');
    const si = getUrlVal(lclUrl, 'sys');
    setLoginInfo({ usrName: nm, token: tk, sysId: si });
    setLocalToken(tk);
    setLocalItem('sys', si);
    this.context.router.replace('/main');
  }

  render() {
    return (
      <div>Loading...</div>
    );
  }
}
// 应用contextTypes(不做手工页面跳转则不需要)
GetTkn.contextTypes = {
  router: PropTypes.object.isRequired,
};
export default GetTkn;

/**
* 环境配置说明：测试和生产环境分开配置，根据各自需要启用不同环境，默认测试环境
 * 如果需要本地启动前端链接服务端后台测试，只需要把测试环境后台地址baseApiUrl注释，生产环境后台地址baseApiUrl开启即可
 * 理论上来说，权限系统不应调用互联网支付系统的api(循环依赖)
 */
// DEV
// export const baseAuthApiUrl = 'http://139.224.145.56:8092/';
// export const logoutAuthApiUrl = 'http://139.224.145.56:8092/';
// export const logoutApiUrl = 'http://139.224.145.56:3003/#/login/';

//  PRD
export const baseAuthApiUrl = 'http://139.224.147.87:8091/';
export const logoutAuthApiUrl = 'http://127.0.0.1:8080/';
export const logoutApiUrl = 'http://139.224.147.87:25000';
export const baseAgentApiUrl = 'http://127.0.0.1:8080/';

// localhost
// export const baseAuthApiUrl = 'http://192.168.1.104:8080/';
// export const logoutAuthApiUrl = 'http://192.168.1.104:8080/';
// export const logoutApiUrl = 'http://192.168.1.104:8080/#/login/';







/**
 * 默认Table的分页信息
 */
export const defaultPage = {
  pageSize: 10,
  pageNum: 1,
};

/**
 * 后台返回信息标识
 */
export const rspInfo = {
  RSP_SUCCESS: '000000',         // 返回成功标识
  RSP_ERROR: '',                 // 返回错误标识，一般不需要
  RSP_NETWORK_ERROR: '网络错误',  // 网络错误标识
};

export const jobResp = {
  RSP_SUCCESS: '200',            // 成功信息
  RSP_ERROR: '500',              // 失败信息
  RSP_NETWORK_ERROR: '网络错误',  //网络错误标识
};

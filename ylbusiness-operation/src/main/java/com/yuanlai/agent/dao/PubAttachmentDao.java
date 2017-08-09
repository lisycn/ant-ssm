package com.yuanlai.agent.dao;


import java.util.List;
import java.util.Map;

import com.yuanlai.agent.entity.PubAttachment;


public abstract interface PubAttachmentDao {
  public abstract int insertEntity(PubAttachment paramAttachment);

  public abstract int updateNewEntity(Map<String, Object> paramMap);

  public abstract int updateHasSaveEntity(PubAttachment paramAttachment);

  public abstract List<PubAttachment> selectList(Map<String, String> paramMap);

  public abstract int deleteEntity(PubAttachment paramAttachment);

  public abstract int deleteSameTypeEntity(PubAttachment paramAttachment);

  public abstract int updateNewPkId(Map<String, String> paramMap);
  
  public abstract int updateNewPkIdByPkId(Map<String, Object> paramMap);
  public abstract int updAttachById(Map<String, Object> paramMap);
  //可扩展
  public abstract List<PubAttachment> selectListByCondition(Map<String, Object> paramMap);
  
  /**
 * 删除附件记录
 * @param params
 * @return 
 * int
 */
public int delAttRecord(Map<String, Object> params);

/**
 * 查询附件信息列表
 * @param params
 * @return 
 * List<Map<String,Object>>
 */
public List<Map<String,Object>> qryAttachList(Map<String, Object> params);

/**
 * 修改附件信息的状态包括PKID与SFSX等
 * @param params
 * @return
 */
public int updateAttachStsById(Map<String, Object> params);
}
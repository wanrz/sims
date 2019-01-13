package com.sims.service;

import java.util.List;
import java.util.Map;

import com.sims.model.Picture;

/**
 * 图片Service接口
 * 
 * @author Administrator
 *
 */
public interface PictureService {

	int insert(Picture record);

	int deleteByPrimaryKey(String id);

	int updateByPrimaryKey(Picture record);

	List<Picture> selectPictureByPage(Map<String, Object> map);
	
	Picture selectByPrimaryKey(String id);
	
	List<Picture> selectAllByPeopleId(String peopleId);
}

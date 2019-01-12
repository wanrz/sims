package com.sims.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sims.mapper.PictureMapper;
import com.sims.model.Picture;
import com.sims.service.PictureService;

@Service("pictureService")
public class PictureServiceImpl implements PictureService {
	@Autowired
	private PictureMapper pictureMapper;

	@Override
	public List<Picture> selectPictureByPage(Map<String, Object> map) {
		return pictureMapper.selectAllByPage(map);
	}

	@Override
	public int insert(Picture record) {
		return pictureMapper.insert(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return pictureMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Picture record) {
		return pictureMapper.updateByPrimaryKey(record);
	}

}

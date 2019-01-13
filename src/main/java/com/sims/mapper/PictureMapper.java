package com.sims.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.sims.model.Picture;

public interface PictureMapper {
    int deleteByPrimaryKey(String id);

    int insert(Picture record);

    int insertSelective(Picture record);

    Picture selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);
    
    List<Picture> selectAllByPage(Map<String, Object> map) throws DataAccessException; // 分页查询用户信息功能
    
    List<Picture> selectAllByPeopleId(String peopleId) throws DataAccessException;
}
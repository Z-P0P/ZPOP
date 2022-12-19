package com.zpop.web.dao;

import com.zpop.web.entity.Participation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParticipationDao {
    boolean insert(Participation participation);
    Participation get(int id);
    List<Participation> getList();
    //update(Participation participation);
    
    
}

package com.chizhov.spring.dao;

import com.chizhov.spring.entity.RaceList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RaceListDao extends CrudRepository<RaceList,Integer> {
    List<RaceList> getAllByRaceId(int race_id);
    int countAllByIdAndChosenIsTrue();
}

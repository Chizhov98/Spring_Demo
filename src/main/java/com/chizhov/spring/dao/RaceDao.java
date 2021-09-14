package com.chizhov.spring.dao;

import com.chizhov.spring.entity.Race;
import org.springframework.data.repository.CrudRepository;

public interface RaceDao extends CrudRepository<Race, Integer> {
}

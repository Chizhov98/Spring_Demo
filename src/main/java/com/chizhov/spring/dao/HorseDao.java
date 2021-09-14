package com.chizhov.spring.dao;

import com.chizhov.spring.entity.Horse;
import org.springframework.data.repository.CrudRepository;

public interface HorseDao extends CrudRepository<Horse,Integer> {
}

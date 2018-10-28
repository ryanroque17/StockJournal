package com.stocks.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stocks.model.Stats;

@Repository("statsRepository")
public interface StatsRepository extends CrudRepository<Stats, Long>{
	Stats findByDate(Date date);
	@Override
	ArrayList<Stats> findAll();
}
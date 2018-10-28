package com.stocks.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stocks.model.Strategy;

@Repository("strategyRepository")
public interface StrategyRepository extends CrudRepository<Strategy, Long>{
	Strategy findByTitle(String title);
	@Override
	ArrayList<Strategy> findAll();
	
}
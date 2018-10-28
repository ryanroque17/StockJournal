package com.stocks.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stocks.model.Portfolio;
import com.stocks.model.Trade;

@Repository("portfolioRepository")
public interface PortfolioRepository extends CrudRepository<Portfolio, Long>{
	Portfolio findByStock(String stock);
	@Override
	ArrayList<Portfolio> findAll();
}
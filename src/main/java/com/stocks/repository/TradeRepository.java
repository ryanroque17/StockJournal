package com.stocks.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stocks.model.BankTransaction;
import com.stocks.model.Trade;

@Repository("tradeRepository")
public interface TradeRepository extends CrudRepository<Trade, Long>{
	@Override
	ArrayList<Trade> findAll();
}
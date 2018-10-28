package com.stocks.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stocks.model.BankTransaction;

@Repository("bankTransactionRepository")
public interface BankTransactionRepository extends CrudRepository<BankTransaction, Long>{

	@Override
	ArrayList<BankTransaction> findAll();
	
}
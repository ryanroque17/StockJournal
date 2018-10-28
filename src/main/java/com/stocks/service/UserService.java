package com.stocks.service;

import java.util.ArrayList;
import java.util.Date;

import com.stocks.model.BankTransaction;
import com.stocks.model.Portfolio;
import com.stocks.model.Stats;
import com.stocks.model.Strategy;
import com.stocks.model.Trade;

public interface UserService {
	public void importTrade(String ledger);
	public void addTrade(Trade trade);
	public void addStrategy(Strategy strategy);
	public void addBankTransaction(BankTransaction bank_transaction);
	public void initializeStats();
	public int getTotalTrades();
	public int getWins();
	public int getLosses();
	
	public double getAvailableCash();
	public double getEquity();
	public double getTotalDeposits();
	public double getTotalWithdrawals();
	public double getTotalCost();
	public double getTotalMarketValue();
	public double getNetProfitLoss();
	public double getAvgWin();
	public double getAvgLoss();
	public double getAvgTradeLength();
	public double getAccuracy();
	public double getEdgeRatio();
	public double getMaxDrawdown();
	public double getRecoveryFactor();
	public double getProfitFactor();
	public double getTotalProfit();
	public double getTotalLoss();
	public double getWinPctTotal();
	public double getLossPctTotal();
	public double getEquityPerDate(Date date);

	public ArrayList<Trade> getAllTrades();
	public ArrayList<Strategy> getAllStrategies();
	public ArrayList<BankTransaction> getAllBankTransactions();
	public ArrayList<Portfolio> getPortfolio();
	public ArrayList<Stats> getStats();
	
	public double getLatestEquity();
	
	public ArrayList<Portfolio> updatePortfolio(ArrayList<Portfolio> portfolio);
	public void updatePortfolio(Trade trade);

	public ArrayList<Trade> getAllTradesByDate(Date date);
	public ArrayList<BankTransaction> getAllBankTransactionsByDate(Date date);
	
	public String getStrategyPerformance(ArrayList<Trade> trades, String action);
	public String getTopStocks(ArrayList<Trade> trades, String action);
	public String getPortfolioAllocation();

}

package com.stocks.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stocks.model.BankTransaction;
import com.stocks.model.Portfolio;
import com.stocks.model.Stats;
import com.stocks.model.Strategy;
import com.stocks.model.Trade;
import com.stocks.repository.BankTransactionRepository;
import com.stocks.repository.PortfolioRepository;
import com.stocks.repository.StatsRepository;
import com.stocks.repository.StrategyRepository;
import com.stocks.repository.TradeRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private BankTransactionRepository bankTransactionRepository;

	@Autowired
	private StrategyRepository strategyRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private StatsRepository statsRepository;

	private int wins = 0;
	private int losses = 0;

	private double net_profit_loss;
	private double total_profit;
	private double total_loss;

	private double win_pct_total;
	private double loss_pct_total;

	private double total_market_value;
	private double overall_total_cost;

	public void setTotal_market_value(double total_market_value) {
		this.total_market_value = total_market_value;
	}

	public void setOverall_total_cost(double overall_total_cost) {
		this.overall_total_cost = overall_total_cost;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public double getNetProfitLoss() {
		return net_profit_loss;
	}

	public void setNetProfitLoss(double net_profit_loss) {
		this.net_profit_loss = net_profit_loss;
	}

	public double getTotalProfit() {
		return total_profit;
	}

	public void setTotalProfit(double total_profit) {
		this.total_profit = total_profit;
	}

	public double getTotalLoss() {
		return total_loss;
	}

	public void setTotalLoss(double total_loss) {
		this.total_loss = total_loss;
	}

	public double getWinPctTotal() {
		return win_pct_total;
	}

	public void setWinPctTotal(double win_pct_total) {
		this.win_pct_total = win_pct_total;
	}

	public double getLossPctTotal() {
		return loss_pct_total;
	}

	public void setLossPctTotal(double loss_pct_total) {
		this.loss_pct_total = loss_pct_total;
	}

	@Override
	public void importTrade(String ledger) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addTrade(Trade trade) {
		Portfolio portfolio_item;
		double original_cost;
		double trade_cost;

		Stats stats = statsRepository.findByDate(trade.getDate());

		updatePortfolio(trade);

		if (trade.getAction().equals("sell")) {
			portfolio_item = portfolioRepository.findByStock(trade.getStock());
			original_cost = portfolio_item.getAverage_price() * trade.getShares();
			trade_cost = trade.getNet();

			double profit = trade_cost - original_cost;

			trade.setProfit(profit);
			trade.setProfit_pct((trade_cost - original_cost) / original_cost);

			if (profit > 0) {
				stats.setTotalProfit(stats.getTotalProfit() + profit);
				stats.setEquity(stats.getEquity() + profit);
			} else {
				stats.setTotalLoss(stats.getTotalLoss() + profit);
				stats.setEquity(stats.getEquity() - profit);

			}
		}
		tradeRepository.save(trade);

	}

	@Override
	public void updatePortfolio(Trade trade) {
		Portfolio portfolio_entry = portfolioRepository.findByStock(trade.getStock());
		double new_ave_price;

		Date date = trade.getDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(date);
		Stats stats = statsRepository.findByDate(trade.getDate());

		Date date_today = new Date();
		if (portfolio_entry != null) {
			if (trade.getAction().equals("buy")) {
				new_ave_price = (portfolio_entry.getAverage_price() * portfolio_entry.getShares() + trade.getNet())
						/ (portfolio_entry.getShares() + trade.getShares());
				portfolio_entry.setShares(portfolio_entry.getShares() + trade.getShares());
				portfolio_entry.setAverage_price(new_ave_price);

				if (dateString.equals(format.format(date_today))) {
					portfolio_entry.setLast_price(getCurrentPrice(trade.getStock()));
				} else {
					portfolio_entry.setLast_price(getCurrentPrice(trade.getStock() + "." + dateString));
				}
				portfolio_entry.setMarket_value(portfolio_entry.getLast_price() * portfolio_entry.getShares());

				stats.setEquity(stats.getEquity() + portfolio_entry.getLast_price() * portfolio_entry.getShares());
				portfolioRepository.save(portfolio_entry);
			} else {
				portfolio_entry.setShares(portfolio_entry.getShares() - trade.getShares());

				if (portfolio_entry.getShares() == 0) {
					portfolioRepository.delete(portfolio_entry);
				}
			}
		} else {
			portfolio_entry = new Portfolio(trade.getStock(), trade.getNet() / trade.getShares(), trade.getShares());

			if (dateString.equals(format.format(date_today))) {
				portfolio_entry.setLast_price(getCurrentPrice(trade.getStock()));
			} else {
				portfolio_entry.setLast_price(getCurrentPrice(trade.getStock() + "." + dateString));
			}
			portfolio_entry.setMarket_value(portfolio_entry.getLast_price() * portfolio_entry.getShares());
			stats.setEquity(stats.getEquity() + portfolio_entry.getLast_price() * portfolio_entry.getShares());

			portfolioRepository.save(portfolio_entry);
		}
	}

	@Override
	public void addStrategy(Strategy strategy) {
		strategyRepository.save(strategy);
	}

	@Override
	public void addBankTransaction(BankTransaction bank_transaction) {
		Stats stats = statsRepository.findByDate(bank_transaction.getDate());

		if (bank_transaction.getAction().equals("deposit")) {
			stats.setTotalDeposited(stats.getTotalDeposited() + bank_transaction.getAmount());
			stats.setEquity(stats.getEquity() + bank_transaction.getAmount());
		} else {
			stats.setTotalWithdrawals(stats.getTotalWithdrawals() + bank_transaction.getAmount());
			stats.setEquity(stats.getEquity() - bank_transaction.getAmount());

		}
		statsRepository.save(stats);
		bankTransactionRepository.save(bank_transaction);
	}

	@Override
	public ArrayList<Trade> getAllTrades() {
		return (ArrayList<Trade>) tradeRepository.findAll();
	}

	@Override
	public ArrayList<Strategy> getAllStrategies() {
		return (ArrayList<Strategy>) strategyRepository.findAll();
	}

	@Override
	public ArrayList<BankTransaction> getAllBankTransactions() {
		return (ArrayList<BankTransaction>) bankTransactionRepository.findAll();
	}

	@Override
	public ArrayList<Trade> getAllTradesByDate(Date date) {
		ArrayList<Trade> trades = tradeRepository.findAll();
		ArrayList<Trade> trades_by_date = new ArrayList<Trade>();

		for (int i = 0; i < trades.size(); i++) {
			if (trades.get(i).getDate().before(date) || trades.get(i).getDate().equals(date)) {
				trades_by_date.add(trades.get(i));
			}
		}
		return trades_by_date;
	}

	@Override
	public ArrayList<BankTransaction> getAllBankTransactionsByDate(Date date) {
		ArrayList<BankTransaction> bank_transactions = bankTransactionRepository.findAll();
		ArrayList<BankTransaction> bank_transactions_by_date = new ArrayList<BankTransaction>();

		for (int i = 0; i < bank_transactions.size(); i++) {
			if (bank_transactions.get(i).getDate().before(date) || bank_transactions.get(i).getDate().equals(date)) {
				bank_transactions_by_date.add(bank_transactions.get(i));
			}
		}
		return bank_transactions_by_date;
	}

	@Override
	public ArrayList<Portfolio> getPortfolio() {
		return (ArrayList<Portfolio>) portfolioRepository.findAll();
	}

	@Override
	public ArrayList<Stats> getStats() {
		return (ArrayList<Stats>) statsRepository.findAll();
	}

	@Override
	public double getAvailableCash() {
		double cash = getTotalDeposits() - getTotalWithdrawals();

		ArrayList<Portfolio> list_portfolio = getPortfolio();

		for (int i = 0; i < list_portfolio.size(); i++) {
			cash -= list_portfolio.get(i).getTotal_cost();
		}
		return cash;
	}

	@Override
	public double getEquity() {
		double total = 0;
		ArrayList<Portfolio> list_portfolio = getPortfolio();

		for (int i = 0; i < list_portfolio.size(); i++) {
			total += list_portfolio.get(i).getMarket_value();
		}
		return getAvailableCash() + total;
	}

	@Override
	public double getEquityPerDate(Date date) {
		ArrayList<Trade> trades = getAllTradesByDate(date);
		ArrayList<BankTransaction> bank_transactions = getAllBankTransactionsByDate(date);
		ArrayList<String> stocks = new ArrayList<String>();
		Map<String, Portfolio> portfolio = new HashMap<String, Portfolio>();

		Trade trade = null;

		Double equity = 0.0;
		Double new_ave_price;
		BankTransaction transaction;
		Portfolio portfolio_entry = null;

		date = trade.getDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(date);

		for (int i = 0; i < bank_transactions.size(); i++) {
			transaction = bank_transactions.get(i);

			if (transaction.getAction().equals("deposit"))
				equity += transaction.getAmount();
			else
				equity -= transaction.getAmount();
		}

		for (int i = 0; i < trades.size(); i++) {
			if (portfolio.containsKey(trade.getStock())) {
				if (trade.getAction().equals("buy")) {
					portfolio_entry = portfolio.get(trade.getStock());
					new_ave_price = (portfolio_entry.getAverage_price() * portfolio_entry.getShares() + trade.getNet())
							/ (portfolio_entry.getShares() + trade.getShares());
					portfolio_entry.setShares(portfolio_entry.getShares() + trade.getShares());
					portfolio_entry.setAverage_price(new_ave_price);

					portfolioRepository.save(portfolio_entry);
				} else if (trades.get(i).getAction().equals("sell")) {
					equity += trades.get(i).getProfit();
					portfolio_entry.setShares(portfolio_entry.getShares() - trade.getShares());

					if (portfolio_entry.getShares() == 0) {
						portfolio.remove(trade.getStock());
					}
				}
			} else {

				portfolio.put(trade.getStock(),
						new Portfolio(trade.getStock(), trade.getNet() / trade.getShares(), trade.getShares()));
			}
		}
		Date date_today = new Date();
		for (Map.Entry<String, Portfolio> entry : portfolio.entrySet()) {
			portfolio_entry = entry.getValue();
			if (dateString.equals(format.format(date_today))) {
				equity += portfolio_entry.getShares() * getCurrentPrice(portfolio_entry.getStock());
			} else {
				equity += portfolio_entry.getShares() * getCurrentPrice(portfolio_entry.getStock() + "." + dateString);
			}

		}
		return equity;

	}

	@Override
	public double getTotalDeposits() {
		ArrayList<BankTransaction> bank_transactions = (ArrayList<BankTransaction>) bankTransactionRepository.findAll();
		double totalDeposited = 0;
		BankTransaction transaction;

		for (int i = 0; i < bank_transactions.size(); i++) {
			transaction = bank_transactions.get(i);

			if (transaction.getAction().equals("deposit"))
				totalDeposited += transaction.getNet();
		}
		return totalDeposited;
	}

	@Override
	public double getTotalWithdrawals() {
		ArrayList<BankTransaction> bank_transactions = (ArrayList<BankTransaction>) bankTransactionRepository.findAll();
		double totalWithdrawn = 0;
		BankTransaction transaction;

		for (int i = 0; i < bank_transactions.size(); i++) {
			transaction = bank_transactions.get(i);

			if (transaction.getAction().equals("withdraw"))
				totalWithdrawn += transaction.getNet();

		}
		return totalWithdrawn;
	}

	@Override
	public int getTotalTrades() {
		return tradeRepository.findAll().size();
	}

	@Override
	public double getAvgWin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAvgLoss() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAvgTradeLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAccuracy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getEdgeRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMaxDrawdown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRecoveryFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getProfitFactor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStrategyPerformance(ArrayList<Trade> trades, String action) {
		Map<String, Double> trade_map = new HashMap<String, Double>();
		Trade trade;
		for (int i = 0; i < trades.size(); i++) {
			trade = trades.get(i);

			Date date = trade.getDate();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date);
			Double trade_amount;

			Date date_today = new Date();
			if (dateString.equals(format.format(date_today))) {
				trade_amount = trade.getShares() * getCurrentPrice(trade.getStock());
			} else {
				trade_amount = trade.getShares() * getCurrentPrice(trade.getStock() + "." + dateString);
			}

			if (!trade_map.containsKey(trade.getStrategy())) {
				trade_map.put(trade.getStrategy(), trade_amount);
			} else {
				trade_map.replace(trade.getStrategy(), trade_map.get(trade.getStock()) + trade_amount);
			}
		}

		Map<String, Double> sorted_map = sortByValue(trade_map, action);

		return sorted_map.toString();
	}

	@Override
	public String getTopStocks(ArrayList<Trade> trades, String action) {
		Map<String, Double> trade_map = new HashMap<String, Double>();
		Trade trade;
		for (int i = 0; i < trades.size(); i++) {
			trade = trades.get(i);

			Date date = trade.getDate();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = format.format(date);
			Double trade_amount;

			Date date_today = new Date();
			if (dateString.equals(format.format(date_today))) {
				trade_amount = trade.getShares() * getCurrentPrice(trade.getStock());
			} else {
				trade_amount = trade.getShares() * getCurrentPrice(trade.getStock() + "." + dateString);
			}

			if (!trade_map.containsKey(trade.getStock())) {
				trade_map.put(trade.getStock(), trade_amount);
			} else {
				trade_map.replace(trade.getStock(), trade_map.get(trade.getStock()) + trade_amount);
			}
		}

		Map<String, Double> sorted_map = sortByValue(trade_map, action);

		return sorted_map.toString();
	}

	@Override
	public String getPortfolioAllocation() {
		Double equity = getEquity();
		Double availableCash = getAvailableCash();
		ArrayList<Portfolio> portfolio = getPortfolio();
		Map<String, Double> allocation_map = new HashMap<String, Double>();
						
		allocation_map.put("cash", (double) Math.round(((availableCash / equity) * 100)/100));
		for (int i = 0; i < portfolio.size(); i++) {
			allocation_map.put(portfolio.get(i).getStock(), portfolio.get(i).getMarket_value() / equity);
		}
		String string = allocation_map.toString();
		string = "'" + string.substring(1, string.length()-1) + "'";
		
		return string;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, String action) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());
		if (action.equals("gainers"))
			Collections.reverse((List<V>) list);
		int ctr = 0;
		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
			ctr++;
			if (ctr == 5)
				break;
		}

		return result;
	}

	public double getCurrentPrice(String stock) {
		HttpURLConnection connection = null;
		Double price = 0.0;
		String string = "http://phisix-api3.appspot.com/stocks/" + stock + ".json";
		//System.out.println(string);
		try {
			URL url = new URL(string);
			// Make connection
			connection = (HttpURLConnection) url.openConnection();
			// Set request type as HTTP GET
			connection.setRequestMethod("GET");
			connection.connect();
			String inline = "";
			Scanner sc = new Scanner(url.openStream());
			while (sc.hasNext()) {
				inline += sc.nextLine();
			}
			try {
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(inline);
				JSONArray jsonarr = (JSONArray) json.get("stock");

				json = (JSONObject) jsonarr.get(0);
				String symbol = (String) json.get("symbol");

				json = (JSONObject) json.get("price");
				price = (Double) json.get("amount");
				//System.out.println(symbol + " : " + price);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return price;

	}

	@Override
	public ArrayList<Portfolio> updatePortfolio(ArrayList<Portfolio> list_portfolio) {
		Portfolio current_entry;
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(date);
		try {
			date = format.parse(dateString);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date);
		Stats stats = statsRepository.findByDate(date);

		int wins = 0;
		int losses = 0;

		double current_price;
		double market_value;
		double total_cost;
		double net_profit_loss = 0;
		double total_profit = 0;
		double total_loss = 0;
		double total_market_value = 0;
		double overall_total_cost = 0;

		double win_pct_total = 0;
		double loss_pct_total = 0;
		for (int i = 0; i < list_portfolio.size(); i++) {
			current_entry = list_portfolio.get(i);
			current_price = getCurrentPrice(current_entry.getStock());
			market_value = current_entry.getShares() * current_price;
			total_cost = current_entry.getAverage_price() * current_entry.getShares();

			current_entry.setMarket_value(market_value);
			current_entry.setProfit_loss(market_value - total_cost);
			current_entry
					.setProfit_loss_pct((double) Math.round(((market_value - total_cost) / total_cost) * 100));
			System.out.println("current_price" + current_price);
			current_entry.setLast_price(current_price);
			current_entry.setTotal_cost(total_cost);

			net_profit_loss += current_entry.getProfit_loss();
			total_market_value += market_value;
			overall_total_cost += total_cost;

			if (current_entry.getProfit_loss_pct() > 0) {
				wins++;
				win_pct_total += current_entry.getProfit_loss_pct();
				total_profit += current_entry.getProfit_loss();
			} else {
				losses++;
				loss_pct_total += current_entry.getProfit_loss_pct();
				total_loss += current_entry.getProfit_loss();
			}
		}

		stats.setEquity(getAvailableCash() + total_market_value);

		setNetProfitLoss(net_profit_loss);
		setWins(wins);
		setWinPctTotal(win_pct_total);
		setTotalProfit(total_profit);
		setTotalLoss(total_loss);
		setLossPctTotal(loss_pct_total);
		setLosses(losses);
		setTotal_market_value(total_market_value);
		setOverall_total_cost(overall_total_cost);

		return list_portfolio;
	}

	@Override
	public double getTotalCost() {
		return overall_total_cost;
	}

	@Override
	public double getTotalMarketValue() {
		return total_market_value;
	}

	@Override
	public double getLatestEquity() {
		ArrayList<Stats> stats = statsRepository.findAll();
		Stats latest_stat = null;

		if (stats.size() != 0) {
			for (int i = 0; i < stats.size(); i++) {
				if (i == 0)
					latest_stat = stats.get(i);
				else {
					if (latest_stat.getDate().before(stats.get(i).getDate()))
						latest_stat = stats.get(i);
				}
			}
			return latest_stat.getEquity();
		} else
			return 0;
	}

	@Override
	public void initializeStats() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(date);
		try {
			date = format.parse(dateString);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stats stats = new Stats(new Date(), getLatestEquity());
		stats.setDate(date);
		if (statsRepository.findByDate(date) == null)
			statsRepository.save(stats);
		ArrayList<Stats> statrep = statsRepository.findAll();
	}

}


package com.stocks.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.stocks.model.BankTransaction;
import com.stocks.model.Portfolio;
import com.stocks.model.Trade;
import com.stocks.service.UserService;

@Controller
public class AnalyticsController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/analytics", method = RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<Trade> list_trades = userService.getAllTrades();
	

		

		modelAndView.addObject("list_trades", list_trades);
		modelAndView.addObject("equity", userService.getEquity());
		modelAndView.addObject("total_deposits", userService.getTotalDeposits());
		modelAndView.addObject("total_withdrawals", userService.getTotalWithdrawals());
		modelAndView.addObject("total_trades", list_trades.size());
		modelAndView.addObject("net_profit_loss", userService.getNetProfitLoss());
		modelAndView.addObject("avg_win", userService.getWinPctTotal()/userService.getWins());
		modelAndView.addObject("avg_loss", userService.getLossPctTotal()/userService.getLosses());
		modelAndView.addObject("accuracy", userService.getWins()/list_trades.size());
		modelAndView.addObject("edge_ratio", userService.getTotalProfit()/userService.getTotalLoss());
		modelAndView.addObject("max_drawdown", -99.99);
		modelAndView.addObject("profit_factor", -99.99);
		modelAndView.addObject("top_gainers", userService.getTopStocks(list_trades, "gainers"));
		modelAndView.addObject("top_losers", userService.getTopStocks(list_trades, "losers"));
		modelAndView.addObject("strongest_strategies", userService.getStrategyPerformance(list_trades, "gainers"));
		modelAndView.addObject("lowest_strategies", userService.getStrategyPerformance(list_trades, "losers"));
		
		modelAndView.setViewName("analytics");
		return modelAndView;
	}
	
//	@RequestMapping(value="/TradeJournal/transaction/", method = RequestMethod.POST)
//	public void transaction(@Valid @ModelAttribute("newTransaction") BankTransaction bankTransaction){
//		userService.addBankTransaction(bankTransaction);
//	}
//	
//	@RequestMapping(value="/TradeJournal/add_trade/", method = RequestMethod.POST)
//	public void trade(@Valid @ModelAttribute("trade") Trade trade){
//		userService.addTrade(trade);
//	}
//	
//	@RequestMapping(value="/TradeJournal/import_trade/", method = RequestMethod.POST)
//	public void trade(@Valid @ModelAttribute("ledger") String ledger){
//		userService.importTrade(ledger);
//	}
//	
//	public double getCurrentPrice(String stock) {
//		HttpURLConnection connection = null;
//		Double price = 0.0;
//		try {
//			URL url = new URL("http://phisix-api3.appspot.com/stocks/" + stock +".json");
//			// Make connection
//			connection = (HttpURLConnection) url.openConnection();
//			// Set request type as HTTP GET
//			connection.setRequestMethod("GET");
//			connection.connect();
//			String inline = "";
//			Scanner sc = new Scanner(url.openStream());
//			while(sc.hasNext()) {
//				inline+=sc.nextLine();
//			}
//			
//			System.out.println(inline);
//			
//			try {
//				JSONParser parser = new JSONParser();
//
//				JSONObject json = (JSONObject) parser.parse(inline);
//				
//				JSONArray jsonarr = (JSONArray) json.get("stock");
//				
//				json = (JSONObject) jsonarr.get(0);
//				String symbol = (String) json.get("symbol");
//
//				json = (JSONObject) json.get("price");
//				price = (Double) json.get("amount");
//				System.out.println(symbol + " : " +price);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		  
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return price;
//
//	}
}

	package com.stocks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "portfolio")
public class Portfolio {

	public Portfolio() {
		
	}
	public Portfolio(String stock, double average_price, int shares) {
		super();
		this.stock = stock;
		this.average_price = average_price;
		this.shares = shares;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "portfolio_id")
	private int portfolio_id;
	@Column(name = "stock")
	private String stock;
	@Column(name = "average_price")
	private double average_price;
	@Column(name = "shares")
	private int shares;
	@Nullable
	@Column(name = "last_price")
	private double last_price;
	@Nullable
	@Column(name = "total_cost")
	private double total_cost;
	@Nullable
	@Column(name = "market_value")
	private double market_value;
	@Nullable
	@Column(name = "profit_loss")
	private double profit_loss;
	@Nullable

	@Column(name = "profit_loss_pct")
	private double profit_loss_pct;
	
	public double getLast_price() {
		return last_price;
	}
	public void setLast_price(double last_price) {
		this.last_price = last_price;
	}
	public double getTotal_cost() {
		return total_cost;
	}
	public void setTotal_cost(double total_cost) {
		this.total_cost = total_cost;
	}
	
	public double getMarket_value() {
		return market_value;
	}
	public void setMarket_value(double d) {
		this.market_value = d;
	}
	public double getProfit_loss() {
		return profit_loss;
	}
	public void setProfit_loss(double profit_loss) {
		this.profit_loss = profit_loss;
	}
	public double getProfit_loss_pct() {
		return profit_loss_pct;
	}
	public void setProfit_loss_pct(double profit_loss_pct) {
		this.profit_loss_pct = profit_loss_pct;
	}
	public int getPortfolio_id() {
		return portfolio_id;
	}
	public void setPortfolio_id(int portfolio_id) {
		this.portfolio_id = portfolio_id;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public double getAverage_price() {
		return average_price;
	}
	public void setAverage_price(double average_price) {
		this.average_price = average_price;
	}
	public int getShares() {
		return shares;
	}
	public void setShares(int shares) {
		this.shares = shares;
	}

}

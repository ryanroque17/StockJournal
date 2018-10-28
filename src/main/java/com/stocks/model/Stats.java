package com.stocks.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stats")
public class Stats {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stats_id")
	private int stats_id;
	@Column(name = "date")
	private Date date;
	@Column(name = "equity")
	private double equity;
	@Column(name = "drawdown")
	private double drawdown;
	@Column(name = "total_profit")
	private double total_profit;
	@Column(name = "total_loss")
	private double total_loss;
	@Column(name = "total_deposited")
	private double total_deposited;
	@Column(name = "total_withdrawals")
	private double total_withdrawals;
	
	public Stats(){}
	public Stats(Date date, double equity) {
		super();
		this.date = date;
		this.equity = equity;
		this.drawdown = 0;
		this.total_profit = 0;
		this.total_loss = 0;
		this.total_deposited = 0;
		this.total_withdrawals = 0;
	}
	public int getStatsId() {
		return stats_id;
	}
	public void setStatsId(int stats_id) {
		this.stats_id = stats_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
		
	}
	public double getEquity() {
		return equity;
	}
	public void setEquity(double equity) {
		this.equity = equity;
	}
	public double getDrawdown() {
		return drawdown;
	}
	public void setDrawdown(double drawdown) {
		this.drawdown = drawdown;
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
	public double getTotalDeposited() {
		return total_deposited;
	}
	public void setTotalDeposited(double total_deposited) {
		this.total_deposited = total_deposited;
	}
	public double getTotalWithdrawals() {
		return total_withdrawals;
	}
	public void setTotalWithdrawals(double total_withdrawals) {
		this.total_withdrawals = total_withdrawals;
	}
}

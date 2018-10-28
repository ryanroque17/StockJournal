package com.stocks.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "strategy")
public class Strategy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "strategy_id")
	private int strategy_id;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private float description;
	@Column(name = "buy_condition")
	private ArrayList<String> buy_condition;
	@Column(name = "sell_condition")
	private ArrayList<String> sell_condition;
	
	public int getStrategy_id() {
		return strategy_id;
	}
	public void setStrategy_id(int strategy_id) {
		this.strategy_id = strategy_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getDescription() {
		return description;
	}
	public void setDescription(float description) {
		this.description = description;
	}
	public ArrayList<String> getBuy_condition() {
		return buy_condition;
	}
	public void setBuy_condition(ArrayList<String> buy_condition) {
		this.buy_condition = buy_condition;
	}
	public ArrayList<String> getSell_condition() {
		return sell_condition;
	}
	public void setSell_condition(ArrayList<String> sell_condition) {
		this.sell_condition = sell_condition;
	}
	
	
}

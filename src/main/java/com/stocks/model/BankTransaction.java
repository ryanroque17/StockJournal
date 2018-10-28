package com.stocks.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banktransaction")
public class BankTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_transaction_id")
	private String bank_transaction_id;
	@Column(name = "date")
	private Date date;
	@Column(name = "action")
	private String action;
	@Column(name = "amount")
	private float amount;
	@Column(name = "fees")
	private float fees;
	@Column(name = "net")
	private float net;
	
	public String getBank_transaction_id() {
		return bank_transaction_id;
	}
	public void setBank_transaction_id(String bank_transaction_id) {
		this.bank_transaction_id = bank_transaction_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getFees() {
		return fees;
	}
	public void setFees(float fees) {
		this.fees = fees;
	}
	public float getNet() {
		return net;
	}
	public void setNet(float net) {
		this.net = net;
	}
}

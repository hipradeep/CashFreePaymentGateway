package com.example.cashfreepaymentgateway.request;

import com.google.gson.annotations.SerializedName;

public class RequestCashFree {

	@SerializedName("orderAmount")
	private String orderAmount;

	@SerializedName("orderId")
	private String orderId;

	@SerializedName("orderCurrency")
	private String orderCurrency;

	public void setOrderAmount(String orderAmount){
		this.orderAmount = orderAmount;
	}

	public String getOrderAmount(){
		return orderAmount;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setOrderCurrency(String orderCurrency){
		this.orderCurrency = orderCurrency;
	}

	public String getOrderCurrency(){
		return orderCurrency;
	}
}
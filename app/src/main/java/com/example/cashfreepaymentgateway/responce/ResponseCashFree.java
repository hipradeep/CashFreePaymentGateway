package com.example.cashfreepaymentgateway.responce;

import com.google.gson.annotations.SerializedName;

public class ResponseCashFree{

	@SerializedName("cftoken")
	private String cftoken;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setCftoken(String cftoken){
		this.cftoken = cftoken;
	}

	public String getCftoken(){
		return cftoken;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}
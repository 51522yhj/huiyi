package com.easymeeting.entity.dto;

public class PeerConnectionDataDto{
private String token;
private String sendUserId;
private String receiveUserId;
private String signalType;
private String signalData;
public String getToken(){return token;}
public void setToken(String token) { this.token = token; }
public String getsenduserId(){ return sendUserId;}
public void setSendUserId(String sendUserId){ this.sendUserId = sendUserId;}
public String getkeceiveuserId() { return receiveUserId; }
public void setReceiveUserId(String receiveUserId){ this.receiveUserId = receiveUserId; }
public String getsignalType(){ return signalType; }
public void setsignalrype(String signalType){this.signalType = signalType;}
public String getsignalData(){return signalData; }
public void setsignalData(String signalData){ this.signalData = signalData;}
}
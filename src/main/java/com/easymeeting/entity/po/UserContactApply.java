package com.easymeeting.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;


/**
 * 
 */
public class UserContactApply implements Serializable {


	/**
	 * 
	 */
	private Integer applyId;

	/**
	 * 
	 */
	private String applyUserId;

	/**
	 * 
	 */
	private String receiveUserId;

	/**
	 * 
	 */
	private Long lastApplyTime;

	/**
	 * 
	 */
	private Integer status;


	public void setApplyId(Integer applyId){
		this.applyId = applyId;
	}

	public Integer getApplyId(){
		return this.applyId;
	}

	public void setApplyUserId(String applyUserId){
		this.applyUserId = applyUserId;
	}

	public String getApplyUserId(){
		return this.applyUserId;
	}

	public void setReceiveUserId(String receiveUserId){
		this.receiveUserId = receiveUserId;
	}

	public String getReceiveUserId(){
		return this.receiveUserId;
	}

	public void setLastApplyTime(Long lastApplyTime){
		this.lastApplyTime = lastApplyTime;
	}

	public Long getLastApplyTime(){
		return this.lastApplyTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	@Override
	public String toString (){
		return "applyId:"+(applyId == null ? "空" : applyId)+"，applyUserId:"+(applyUserId == null ? "空" : applyUserId)+"，receiveUserId:"+(receiveUserId == null ? "空" : receiveUserId)+"，lastApplyTime:"+(lastApplyTime == null ? "空" : lastApplyTime)+"，status:"+(status == null ? "空" : status);
	}
}

package com.easymeeting.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;


/**
 * 
 */
public class MeetingChatMessage implements Serializable {


	/**
	 * 
	 */
	private String meetingId;

	/**
	 * 
	 */
	private Long messageId;

	/**
	 * 
	 */
	private Integer messageType;

	/**
	 * 
	 */
	private String messageContent;

	/**
	 * 
	 */
	private String sendUserId;

	/**
	 * 
	 */
	private String snedUserNickName;

	/**
	 * 
	 */
	private Long sendTime;

	/**
	 * 
	 */
	private Integer receiveType;

	/**
	 * 
	 */
	private String receiveUserId;

	/**
	 * 
	 */
	private Long fileSize;

	/**
	 * 
	 */
	private String fileName;

	/**
	 * 
	 */
	private Integer fileType;

	/**
	 * 
	 */
	private String fileSuffix;

	/**
	 * 
	 */
	private Integer status;


	public void setMeetingId(String meetingId){
		this.meetingId = meetingId;
	}

	public String getMeetingId(){
		return this.meetingId;
	}

	public void setMessageId(Long messageId){
		this.messageId = messageId;
	}

	public Long getMessageId(){
		return this.messageId;
	}

	public void setMessageType(Integer messageType){
		this.messageType = messageType;
	}

	public Integer getMessageType(){
		return this.messageType;
	}

	public void setMessageContent(String messageContent){
		this.messageContent = messageContent;
	}

	public String getMessageContent(){
		return this.messageContent;
	}

	public void setSendUserId(String sendUserId){
		this.sendUserId = sendUserId;
	}

	public String getSendUserId(){
		return this.sendUserId;
	}

	public void setSnedUserNickName(String snedUserNickName){
		this.snedUserNickName = snedUserNickName;
	}

	public String getSnedUserNickName(){
		return this.snedUserNickName;
	}

	public void setSendTime(Long sendTime){
		this.sendTime = sendTime;
	}

	public Long getSendTime(){
		return this.sendTime;
	}

	public void setReceiveType(Integer receiveType){
		this.receiveType = receiveType;
	}

	public Integer getReceiveType(){
		return this.receiveType;
	}

	public void setReceiveUserId(String receiveUserId){
		this.receiveUserId = receiveUserId;
	}

	public String getReceiveUserId(){
		return this.receiveUserId;
	}

	public void setFileSize(Long fileSize){
		this.fileSize = fileSize;
	}

	public Long getFileSize(){
		return this.fileSize;
	}

	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	public String getFileName(){
		return this.fileName;
	}

	public void setFileType(Integer fileType){
		this.fileType = fileType;
	}

	public Integer getFileType(){
		return this.fileType;
	}

	public void setFileSuffix(String fileSuffix){
		this.fileSuffix = fileSuffix;
	}

	public String getFileSuffix(){
		return this.fileSuffix;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	@Override
	public String toString (){
		return "meetingId:"+(meetingId == null ? "空" : meetingId)+"，messageId:"+(messageId == null ? "空" : messageId)+"，messageType:"+(messageType == null ? "空" : messageType)+"，messageContent:"+(messageContent == null ? "空" : messageContent)+"，sendUserId:"+(sendUserId == null ? "空" : sendUserId)+"，snedUserNickName:"+(snedUserNickName == null ? "空" : snedUserNickName)+"，sendTime:"+(sendTime == null ? "空" : sendTime)+"，receiveType:"+(receiveType == null ? "空" : receiveType)+"，receiveUserId:"+(receiveUserId == null ? "空" : receiveUserId)+"，fileSize:"+(fileSize == null ? "空" : fileSize)+"，fileName:"+(fileName == null ? "空" : fileName)+"，fileType:"+(fileType == null ? "空" : fileType)+"，fileSuffix:"+(fileSuffix == null ? "空" : fileSuffix)+"，status:"+(status == null ? "空" : status);
	}
}

package com.easymeeting.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.easymeeting.entity.enums.DateTimePatternEnum;
import com.easymeeting.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 
 */
public class MeetingInfo implements Serializable {


	/**
	 * 会议ID
	 */
	private String meetingId;

	/**
	 * 会议号
	 */
	private String meetingNo;

	/**
	 * 会议名称
	 */
	private String meetingName;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 创建者ID
	 */
	private String createUserId;

	/**
	 * 入会类型
	 */
	private Integer joinType;

	/**
	 * 入会密码
	 */
	private String joinPassword;

	/**
	 * 会议开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	/**
	 * 会议结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setMeetingId(String meetingId){
		this.meetingId = meetingId;
	}

	public String getMeetingId(){
		return this.meetingId;
	}

	public void setMeetingNo(String meetingNo){
		this.meetingNo = meetingNo;
	}

	public String getMeetingNo(){
		return this.meetingNo;
	}

	public void setMeetingName(String meetingName){
		this.meetingName = meetingName;
	}

	public String getMeetingName(){
		return this.meetingName;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}

	public String getCreateUserId(){
		return this.createUserId;
	}

	public void setJoinType(Integer joinType){
		this.joinType = joinType;
	}

	public Integer getJoinType(){
		return this.joinType;
	}

	public void setJoinPassword(String joinPassword){
		this.joinPassword = joinPassword;
	}

	public String getJoinPassword(){
		return this.joinPassword;
	}

	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}

	public Date getStartTime(){
		return this.startTime;
	}

	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}

	public Date getEndTime(){
		return this.endTime;
	}

	@Override
	public String toString (){
		return "会议ID:"+(meetingId == null ? "空" : meetingId)+"，会议号:"+(meetingNo == null ? "空" : meetingNo)+"，会议名称:"+(meetingName == null ? "空" : meetingName)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，创建者ID:"+(createUserId == null ? "空" : createUserId)+"，入会类型:"+(joinType == null ? "空" : joinType)+"，入会密码:"+(joinPassword == null ? "空" : joinPassword)+"，会议开始时间:"+(startTime == null ? "空" : DateUtil.format(startTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，会议结束时间:"+(endTime == null ? "空" : DateUtil.format(endTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}

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
public class MeetingMember implements Serializable {


	/**
	 * 会议ID
	 */
	private String meetingId;

	/**
	 * 入会用户ID
	 */
	private String userId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 最后加入时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastJoinTime;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 成员类型
	 */
	private Integer memberType;

	/**
	 * 会议状态
	 */
	private Integer meetingStatus;


	public void setMeetingId(String meetingId){
		this.meetingId = meetingId;
	}

	public String getMeetingId(){
		return this.meetingId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getNickName(){
		return this.nickName;
	}

	public void setLastJoinTime(Date lastJoinTime){
		this.lastJoinTime = lastJoinTime;
	}

	public Date getLastJoinTime(){
		return this.lastJoinTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setMemberType(Integer memberType){
		this.memberType = memberType;
	}

	public Integer getMemberType(){
		return this.memberType;
	}

	public void setMeetingStatus(Integer meetingStatus){
		this.meetingStatus = meetingStatus;
	}

	public Integer getMeetingStatus(){
		return this.meetingStatus;
	}

	@Override
	public String toString (){
		return "会议ID:"+(meetingId == null ? "空" : meetingId)+"，入会用户ID:"+(userId == null ? "空" : userId)+"，昵称:"+(nickName == null ? "空" : nickName)+"，最后加入时间:"+(lastJoinTime == null ? "空" : DateUtil.format(lastJoinTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，状态:"+(status == null ? "空" : status)+"，成员类型:"+(memberType == null ? "空" : memberType)+"，会议状态:"+(meetingStatus == null ? "空" : meetingStatus);
	}
}

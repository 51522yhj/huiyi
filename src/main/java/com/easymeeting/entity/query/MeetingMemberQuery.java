package com.easymeeting.entity.query;

import java.util.Date;


/**
 * 参数
 */
public class MeetingMemberQuery extends BaseParam {


	/**
	 * 会议ID
	 */
	private String meetingId;

	private String meetingIdFuzzy;

	/**
	 * 入会用户ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 昵称
	 */
	private String nickName;

	private String nickNameFuzzy;

	/**
	 * 最后加入时间
	 */
	private String lastJoinTime;

	private String lastJoinTimeStart;

	private String lastJoinTimeEnd;

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

	public void setMeetingIdFuzzy(String meetingIdFuzzy){
		this.meetingIdFuzzy = meetingIdFuzzy;
	}

	public String getMeetingIdFuzzy(){
		return this.meetingIdFuzzy;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setUserIdFuzzy(String userIdFuzzy){
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getUserIdFuzzy(){
		return this.userIdFuzzy;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public String getNickName(){
		return this.nickName;
	}

	public void setNickNameFuzzy(String nickNameFuzzy){
		this.nickNameFuzzy = nickNameFuzzy;
	}

	public String getNickNameFuzzy(){
		return this.nickNameFuzzy;
	}

	public void setLastJoinTime(String lastJoinTime){
		this.lastJoinTime = lastJoinTime;
	}

	public String getLastJoinTime(){
		return this.lastJoinTime;
	}

	public void setLastJoinTimeStart(String lastJoinTimeStart){
		this.lastJoinTimeStart = lastJoinTimeStart;
	}

	public String getLastJoinTimeStart(){
		return this.lastJoinTimeStart;
	}
	public void setLastJoinTimeEnd(String lastJoinTimeEnd){
		this.lastJoinTimeEnd = lastJoinTimeEnd;
	}

	public String getLastJoinTimeEnd(){
		return this.lastJoinTimeEnd;
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

}

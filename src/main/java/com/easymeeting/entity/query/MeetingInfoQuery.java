package com.easymeeting.entity.query;

import java.util.Date;


/**
 * 参数
 */
public class MeetingInfoQuery extends BaseParam {


	/**
	 * 会议ID
	 */
	private String meetingId;

	private String meetingIdFuzzy;
	private String userId;
	/**
	 * 会议号
	 */
	private String meetingNo;

	private String meetingNoFuzzy;

	/**
	 * 会议名称
	 */
	private String meetingName;

	private String meetingNameFuzzy;

	/**
	 * 创建时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 创建者ID
	 */
	private String createUserId;

	private String createUserIdFuzzy;

	public Boolean getQueryMemberCount() {
		return queryMemberCount;
	}

	public void setQueryMemberCount(Boolean queryMemberCount) {
		this.queryMemberCount = queryMemberCount;
	}

	private Boolean queryMemberCount;
	/**
	 * 入会类型
	 */
	private Integer joinType;

	/**
	 * 入会密码
	 */
	private String joinPassword;

	private String joinPasswordFuzzy;

	/**
	 * 会议开始时间
	 */
	private String startTime;

	private String startTimeStart;

	private String startTimeEnd;

	/**
	 * 会议结束时间
	 */
	private String endTime;

	private String endTimeStart;

	private String endTimeEnd;


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

	public void setMeetingNo(String meetingNo){
		this.meetingNo = meetingNo;
	}

	public String getMeetingNo(){
		return this.meetingNo;
	}

	public void setMeetingNoFuzzy(String meetingNoFuzzy){
		this.meetingNoFuzzy = meetingNoFuzzy;
	}

	public String getMeetingNoFuzzy(){
		return this.meetingNoFuzzy;
	}

	public void setMeetingName(String meetingName){
		this.meetingName = meetingName;
	}

	public String getMeetingName(){
		return this.meetingName;
	}

	public void setMeetingNameFuzzy(String meetingNameFuzzy){
		this.meetingNameFuzzy = meetingNameFuzzy;
	}

	public String getMeetingNameFuzzy(){
		return this.meetingNameFuzzy;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return this.createTime;
	}

	public void setCreateTimeStart(String createTimeStart){
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart(){
		return this.createTimeStart;
	}
	public void setCreateTimeEnd(String createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd(){
		return this.createTimeEnd;
	}

	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}

	public String getCreateUserId(){
		return this.createUserId;
	}

	public void setCreateUserIdFuzzy(String createUserIdFuzzy){
		this.createUserIdFuzzy = createUserIdFuzzy;
	}

	public String getCreateUserIdFuzzy(){
		return this.createUserIdFuzzy;
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

	public void setJoinPasswordFuzzy(String joinPasswordFuzzy){
		this.joinPasswordFuzzy = joinPasswordFuzzy;
	}

	public String getJoinPasswordFuzzy(){
		return this.joinPasswordFuzzy;
	}

	public void setStartTime(String startTime){
		this.startTime = startTime;
	}

	public String getStartTime(){
		return this.startTime;
	}

	public void setStartTimeStart(String startTimeStart){
		this.startTimeStart = startTimeStart;
	}

	public String getStartTimeStart(){
		return this.startTimeStart;
	}
	public void setStartTimeEnd(String startTimeEnd){
		this.startTimeEnd = startTimeEnd;
	}

	public String getStartTimeEnd(){
		return this.startTimeEnd;
	}

	public void setEndTime(String endTime){
		this.endTime = endTime;
	}

	public String getEndTime(){
		return this.endTime;
	}

	public void setEndTimeStart(String endTimeStart){
		this.endTimeStart = endTimeStart;
	}

	public String getEndTimeStart(){
		return this.endTimeStart;
	}
	public void setEndTimeEnd(String endTimeEnd){
		this.endTimeEnd = endTimeEnd;
	}

	public String getEndTimeEnd(){
		return this.endTimeEnd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

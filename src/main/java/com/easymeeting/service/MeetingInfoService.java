package com.easymeeting.service;

import java.util.List;

import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.enums.MeetingMemberStatusEnum;
import com.easymeeting.entity.query.MeetingInfoQuery;
import com.easymeeting.entity.po.MeetingInfo;
import com.easymeeting.entity.vo.PaginationResultVO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 *  业务接口
 */
public interface MeetingInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<MeetingInfo> findListByParam(MeetingInfoQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(MeetingInfoQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<MeetingInfo> findListByPage(MeetingInfoQuery param);

	/**
	 * 新增
	 */
	Integer add(MeetingInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<MeetingInfo> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<MeetingInfo> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(MeetingInfo bean,MeetingInfoQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(MeetingInfoQuery param);

	/**
	 * 根据MeetingId查询对象
	 */
	MeetingInfo getMeetingInfoByMeetingId(String meetingId);


	/**
	 * 根据MeetingId修改
	 */
	Integer updateMeetingInfoByMeetingId(MeetingInfo bean,String meetingId);


	/**
	 * 根据MeetingId删除
	 */
	Integer deleteMeetingInfoByMeetingId(String meetingId);

    void quickMeeting(MeetingInfo meetingInfo, String nickName);


	void joinMeeting(String currentMeetingId, String userId, String nickName, Integer sex, @NotNull Boolean videoOpen);

	String preJoinMeeting(@NotNull String meetingNo, TokenUserInfoDto tokenUserInfo, String password);

	void exitMeetingRoom(TokenUserInfoDto tokenUserInfoDto, MeetingMemberStatusEnum meetingMemberStatusEnum);

    void forceExitMeeting(TokenUserInfoDto tokenUserInfoDto,String userId, MeetingMemberStatusEnum meetingMemberStatusEnum);

	void finishMeeting(String currentMeetingId, String userId);

    void inviteMember(TokenUserInfoDto tokenUserInfoDto, @NotEmpty String selectContactIds);

	void acceptInvite(TokenUserInfoDto tokenUserInfoDto, @NotEmpty String meetingId);

	void updateMemberOpenVideo(String currentMeetingId, String userId, @NotNull Boolean videoOpen);
}
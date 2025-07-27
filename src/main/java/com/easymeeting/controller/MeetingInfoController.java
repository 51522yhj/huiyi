package com.easymeeting.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.easymeeting.annotation.GlobalInterceptor;
import com.easymeeting.entity.dto.MeetingMemberDto;
import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.enums.MeetingMemberStatusEnum;
import com.easymeeting.entity.enums.MeetingStatusEnum;
import com.easymeeting.entity.enums.ResponseCodeEnum;
import com.easymeeting.entity.po.MeetingMember;
import com.easymeeting.entity.query.MeetingInfoQuery;
import com.easymeeting.entity.po.MeetingInfo;
import com.easymeeting.entity.query.MeetingMemberQuery;
import com.easymeeting.entity.vo.PaginationResultVO;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.exception.BusinessException;
import com.easymeeting.redis.RedisComponent;
import com.easymeeting.service.MeetingInfoService;
import com.easymeeting.service.impl.MeetingMemberServiceImpl;
import com.easymeeting.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *  Controller
 */
@RestController
@RequestMapping("/meeting")
public class MeetingInfoController extends ABaseController{

	@Resource
	private MeetingInfoService meetingInfoService;
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private MeetingMemberServiceImpl meetingMemberService;

	/**
	 * 获取当前会议
	 */
	@RequestMapping("/getCurrentMeeting")
	@GlobalInterceptor
	public ResponseVO getCurrentMeeting(){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		if(StringTools.isEmpty(tokenUserInfo.getCurrentMeetingId())){
			return getSuccessResponseVO(null);
		}
		MeetingInfo meetingInfo = meetingInfoService.getMeetingInfoByMeetingId(tokenUserInfo.getCurrentMeetingId());
		if (MeetingStatusEnum.FINISH.getStatus().equals(meetingInfo.getStatus())){
			return  getSuccessResponseVO(null);
		}
		return getSuccessResponseVO(meetingInfo);
	}
	/**
	 * 快速会议
	 */
	@RequestMapping("/quickMeeting")
	@GlobalInterceptor
	public ResponseVO quickMeeting(@NotNull Integer meetingNoType, @NotEmpty @Size(max = 100) String meetingName,
								   @NotNull Integer joinType, @Max(5) String joinPassword){
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		if (tokenUserInfoDto.getCurrentMeetingId() != null){
			throw new BusinessException(ResponseCodeEnum.CODE_903);
		}
		MeetingInfo meetingInfo = new MeetingInfo();
		meetingInfo.setMeetingName(meetingName);
		meetingInfo.setMeetingNo(meetingNoType == 0 ? tokenUserInfoDto.getMyMeetingNo() : StringTools.getMeetingNoOrMeetingId());
		meetingInfo.setJoinType(joinType);
		meetingInfo.setJoinPassword(joinPassword);
		meetingInfo.setCreateUserId(tokenUserInfoDto.getUserId());
		meetingInfoService.quickMeeting(meetingInfo,tokenUserInfoDto.getNickName());
		tokenUserInfoDto.setCurrentMeetingId(meetingInfo.getMeetingId());
		tokenUserInfoDto.setCurrentNickName(tokenUserInfoDto.getNickName());
		resetTokenUserInfo(tokenUserInfoDto);
		return getSuccessResponseVO(meetingInfo.getMeetingId());
	}
	@RequestMapping("/loadMeeting")
	@GlobalInterceptor
	public ResponseVO loadMeeting(Integer pageNo){
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		MeetingInfoQuery infoQuery = new MeetingInfoQuery();
		infoQuery.setPageNo(pageNo);
		infoQuery.setOrderBy("m.create_time desc");
		infoQuery.setUserId(tokenUserInfoDto.getUserId());
		infoQuery.setQueryMemberCount(true);
		PaginationResultVO resultVO = meetingInfoService.findListByPage(infoQuery);
		return getSuccessResponseVO(resultVO);
	}

	@RequestMapping("/loadMeetingMembers")
	@GlobalInterceptor
	public ResponseVO loadMeetingMembers(String meetingId){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		MeetingMemberQuery meetingMemberQuery = new MeetingMemberQuery();
		meetingMemberQuery.setMeetingId(meetingId);
		List<MeetingMember> meetingMemberList = meetingMemberService.findListByParam(meetingMemberQuery);
		Optional<MeetingMember> first = meetingMemberList.stream().filter(item -> item.getUserId().equals(tokenUserInfo.getUserId())).findFirst();
		if (!first.isPresent()){
			throw  new BusinessException(ResponseCodeEnum.CODE_600);
		}
		return getSuccessResponseVO(meetingMemberList);
	}
	/**
	 * 加入会议
	 */
	@RequestMapping("/joinMeeting")
	@GlobalInterceptor
	public ResponseVO joinMeeting(@NotNull Boolean videoOpen){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		meetingInfoService.joinMeeting(tokenUserInfo.getCurrentMeetingId(),tokenUserInfo.getUserId(),
				tokenUserInfo.getNickName(),tokenUserInfo.getSex(),videoOpen);
		return getSuccessResponseVO(null);
	}
	/**
	 * 预入会
	 */
	@RequestMapping("/preJoinMeeting")
	@GlobalInterceptor
	public ResponseVO preJoinMeeting(@NotNull String meetingNo, @NotEmpty String nickName,String password){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		meetingNo  = meetingNo.replace(" ","");
		tokenUserInfo.setCurrentNickName(nickName);
		String meetingId = meetingInfoService.preJoinMeeting(meetingNo,tokenUserInfo,password);
		return getSuccessResponseVO(meetingId);
	}
	/**
	 * 邀请成员
	 */
	@RequestMapping("/inviteMember")
	@GlobalInterceptor
	public ResponseVO inviteMember(@NotEmpty String selectContactIds){
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		meetingInfoService.inviteMember(tokenUserInfoDto,selectContactIds);
		return getSuccessResponseVO(null);
	}
	/**
	 * 邀请成员
	 */
	@RequestMapping("/acceptInvite")
	@GlobalInterceptor
	public ResponseVO acceptInvite(@NotEmpty String meetingId){
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		meetingInfoService.acceptInvite(tokenUserInfoDto,meetingId);
		return getSuccessResponseVO(null);
	}
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/exitMeeting")
	@GlobalInterceptor
	public ResponseVO exitMeetingRoom(){
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		meetingInfoService.exitMeetingRoom(tokenUserInfoDto , MeetingMemberStatusEnum.EXIT_MEETING);
		return getSuccessResponseVO(null);
	}
	/**
	 * 踢出会议
	 */
	@RequestMapping("/kickOutMeeting")
	@GlobalInterceptor
	public ResponseVO kickOutMeeting(@NotNull String userId){
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		meetingInfoService.forceExitMeeting(tokenUserInfoDto,userId, MeetingMemberStatusEnum.KICK_OUT);
		return getSuccessResponseVO(null);
	}
	/**
	 * 拉黑会议
	 */
	@RequestMapping("/blackMeeting")
	public ResponseVO blackMeeting(@NotNull String userId){
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		meetingInfoService.forceExitMeeting(tokenUserInfoDto, userId,MeetingMemberStatusEnum.KICK_OUT);
		return getSuccessResponseVO(null);
	}

	/**
	 * 结束会议
	 */
	@RequestMapping("/finishMeeting")
	@GlobalInterceptor
	public ResponseVO finishMeeting(){
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		meetingInfoService.finishMeeting(tokenUserInfoDto.getCurrentMeetingId(),tokenUserInfoDto.getUserId());
		return getSuccessResponseVO(null);
	}

	/**
	 * 删除记录
	 */
	@RequestMapping("/delMeetingRecord")
	@GlobalInterceptor
	public ResponseVO delMeetingRecord(@NotNull String meetingId){
		TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		MeetingMember meetingMember = new MeetingMember();
		meetingMember.setStatus(MeetingMemberStatusEnum.DEL_MEETING.getStatus());

		MeetingMemberQuery meetingMemberQuery = new MeetingMemberQuery();
		meetingMemberQuery.setMeetingId(meetingId);
		meetingMemberQuery.setUserId(tokenUserInfoDto.getUserId());
		meetingMemberService.updateByParam(meetingMember,meetingMemberQuery);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/sendOpenVideoChangeMessage")
	@GlobalInterceptor
	public ResponseVO sendOpenVideoChangeMessage(@NotNull Boolean videoOpen){
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfo();
		meetingInfoService.updateMemberOpenVideo(tokenUserInfoDto.getCurrentMeetingId(),tokenUserInfoDto.getUserId(),videoOpen);
		return getSuccessResponseVO(null);
	}


	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(MeetingInfoQuery query){
		return getSuccessResponseVO(meetingInfoService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(MeetingInfo bean) {
		meetingInfoService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<MeetingInfo> listBean) {
		meetingInfoService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<MeetingInfo> listBean) {
		meetingInfoService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MeetingId查询对象
	 */
	@RequestMapping("/getMeetingInfoByMeetingId")
	public ResponseVO getMeetingInfoByMeetingId(String meetingId) {
		return getSuccessResponseVO(meetingInfoService.getMeetingInfoByMeetingId(meetingId));
	}

	/**
	 * 根据MeetingId修改对象
	 */
	@RequestMapping("/updateMeetingInfoByMeetingId")
	public ResponseVO updateMeetingInfoByMeetingId(MeetingInfo bean,String meetingId) {
		meetingInfoService.updateMeetingInfoByMeetingId(bean,meetingId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MeetingId删除
	 */
	@RequestMapping("/deleteMeetingInfoByMeetingId")
	public ResponseVO deleteMeetingInfoByMeetingId(String meetingId) {
		meetingInfoService.deleteMeetingInfoByMeetingId(meetingId);
		return getSuccessResponseVO(null);
	}
}
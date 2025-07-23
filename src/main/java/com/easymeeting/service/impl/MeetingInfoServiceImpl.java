package com.easymeeting.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.easymeeting.entity.dto.*;
import com.easymeeting.entity.enums.*;
import com.easymeeting.entity.po.MeetingMember;
import com.easymeeting.entity.po.MeetingReserve;
import com.easymeeting.entity.query.MeetingMemberQuery;
import com.easymeeting.exception.BusinessException;
import com.easymeeting.mappers.MeetingMemberMapper;
import com.easymeeting.mappers.MeetingReserveMapper;
import com.easymeeting.redis.RedisComponent;
import com.easymeeting.utils.JsonUtils;
import com.easymeeting.websocket.ChannelContextUtils;
import com.easymeeting.websocket.message.MessageHandler;
import com.easymeeting.websocket.message.MessageHandler4Redis;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymeeting.entity.query.MeetingInfoQuery;
import com.easymeeting.entity.po.MeetingInfo;
import com.easymeeting.entity.vo.PaginationResultVO;
import com.easymeeting.entity.query.SimplePage;
import com.easymeeting.mappers.MeetingInfoMapper;
import com.easymeeting.service.MeetingInfoService;
import com.easymeeting.utils.StringTools;
import org.springframework.transaction.annotation.Transactional;


/**
 *  业务接口实现
 */
@Service("meetingInfoService")
@Slf4j
public class MeetingInfoServiceImpl implements MeetingInfoService {

	@Resource
	private MeetingInfoMapper<MeetingInfo, MeetingInfoQuery> meetingInfoMapper;
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private ChannelContextUtils channelContextUtils;
	@Resource
	private MeetingMemberMapper meetingMemberMapper;
    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private MeetingReserveMapper meetingReserveMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<MeetingInfo> findListByParam(MeetingInfoQuery param) {
		return this.meetingInfoMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(MeetingInfoQuery param) {
		return this.meetingInfoMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<MeetingInfo> findListByPage(MeetingInfoQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<MeetingInfo> list = this.findListByParam(param);
		PaginationResultVO<MeetingInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(MeetingInfo bean) {
		return this.meetingInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<MeetingInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.meetingInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<MeetingInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.meetingInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(MeetingInfo bean, MeetingInfoQuery param) {
		StringTools.checkParam(param);
		return this.meetingInfoMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(MeetingInfoQuery param) {
		StringTools.checkParam(param);
		return this.meetingInfoMapper.deleteByParam(param);
	}

	/**
	 * 根据MeetingId获取对象
	 */
	@Override
	public MeetingInfo getMeetingInfoByMeetingId(String meetingId) {
		return this.meetingInfoMapper.selectByMeetingId(meetingId);
	}

	/**
	 * 根据MeetingId修改
	 */
	@Override
	public Integer updateMeetingInfoByMeetingId(MeetingInfo bean, String meetingId) {
		return this.meetingInfoMapper.updateByMeetingId(bean, meetingId);
	}

	/**
	 * 根据MeetingId删除
	 */
	@Override
	public Integer deleteMeetingInfoByMeetingId(String meetingId) {
		return this.meetingInfoMapper.deleteByMeetingId(meetingId);
	}

	@Override
	public void quickMeeting(MeetingInfo meetingInfo, String nickName) {
		Date date = new Date();
		meetingInfo.setCreateTime(date);
		meetingInfo.setMeetingId(StringTools.getMeetingNoOrMeetingId());
		meetingInfo.setStartTime(date);
		meetingInfo.setStatus(MeetingStatusEnum.RUNNING.getStatus());
		meetingInfoMapper.insert(meetingInfo);
	}
	private void checkMeetingJoin(String meetingId,String userId){
		MeetingMemberDto meetingMemberDto = redisComponent.getMeetingMember(meetingId,userId);
		if (meetingMemberDto != null && MeetingMemberStatusEnum.BLACKLIST.getStatus().equals(meetingMemberDto.getStatus())){
			throw  new BusinessException("你已被拉黑，无法加入会议");
		}
	}
	@Override
	public void joinMeeting(String meetingId, String userId, String nickName, Integer sex, Boolean videoOpen) {
		if (StringTools.isEmpty(meetingId)){
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		MeetingInfo meetingInfo = meetingInfoMapper.selectByMeetingId(meetingId);
		if (meetingInfo == null || MeetingStatusEnum.FINISH.getStatus().equals(meetingInfo.getStatus())){
			throw  new BusinessException(ResponseCodeEnum.CODE_600);
		}
		//校验用户
		checkMeetingJoin(meetingId,userId);
		// 加入成员
		MemberTypeEnum memberTypeEnum = meetingInfo.getCreateUserId().equals(userId) ? MemberTypeEnum.COMPERE : MemberTypeEnum.NORMAL;
		addMeetingMember(meetingId,userId,nickName,memberTypeEnum.getType());
		// 加入会议
		add2Meeting(meetingId,userId,nickName,sex,memberTypeEnum.getType(),videoOpen);
		// 加入ws房间
		channelContextUtils.addMeetingRoom(meetingId, userId);
		// 发送ws消息
		MeetingJoinDto meetingJoinDto = new MeetingJoinDto();
		meetingJoinDto.setNewMember(redisComponent.getMeetingMember(meetingId,userId));
		meetingJoinDto.setMeetingMemberList(redisComponent.getMeetingMemberList(meetingId));
		MessageSendDto messageSendDto = new MessageSendDto<>();
		messageSendDto.setMessageType(MessageTypeEnum.ADD_MEETING_ROOM.getType());
		messageSendDto.setMeetingId(meetingId);
		messageSendDto.setMessageSend2Type(MessageSend2TypeEnum.GROUP.getType());
		messageSendDto.setMessageContent(meetingJoinDto);
		messageHandler.sendMessage(messageSendDto);
	}

	private void add2Meeting(String meetingId, String userId, String nickName, Integer sex, Integer memberType, Boolean videoOpen) {
		MeetingMemberDto meetingMemberDto = new MeetingMemberDto();
		meetingMemberDto.setUserId(userId);
		meetingMemberDto.setNickName(nickName);
		meetingMemberDto.setJoinTime(System.currentTimeMillis());
		meetingMemberDto.setMemberType(memberType);
		meetingMemberDto.setStatus(MeetingMemberStatusEnum.NORMAL.getStatus());
		meetingMemberDto.setSex(sex);
		meetingMemberDto.setOpenVideo(videoOpen);
		redisComponent.add2Meeting(meetingId,meetingMemberDto);
	}

	private void addMeetingMember(String meetingId, String userId, String nickName,  Integer memberType) {
		MeetingMember meetingMember = new MeetingMember();
		meetingMember.setMeetingId(meetingId);
		meetingMember.setUserId(userId);
		meetingMember.setNickName(nickName);
		meetingMember.setLastJoinTime(new Date());
		meetingMember.setStatus(MeetingMemberStatusEnum.NORMAL.getStatus());
		meetingMember.setMeetingStatus(MeetingStatusEnum.RUNNING.getStatus());
		meetingMember.setMemberType(memberType);
		meetingMemberMapper.insertOrUpdate(meetingMember);
	}

	@Override
	public String preJoinMeeting(String meetingNo, TokenUserInfoDto tokenUserInfoDto, String password) {
		String userId = tokenUserInfoDto.getUserId();
		MeetingInfoQuery meetingInfoQuery = new MeetingInfoQuery();
		meetingInfoQuery.setMeetingNo(meetingNo);
		meetingInfoQuery.setOrderBy("create_time desc");
		List<MeetingInfo> meetingInfoList = meetingInfoMapper.selectList(meetingInfoQuery);
		if (meetingInfoList.isEmpty()){
			throw  new BusinessException("会议不存在");
		}
		MeetingInfo meetingInfo = meetingInfoList.get(0);
		log.info("{}：{}",MeetingStatusEnum.RUNNING.getStatus(),meetingInfo.getStatus());
		log.info(MeetingStatusEnum.RUNNING.getStatus() ==  meetingInfo.getStatus() ? "dyu":"budengyu");
		if (!MeetingStatusEnum.RUNNING.getStatus().equals(meetingInfo.getStatus())){
			throw new BusinessException("会议已结束");
		}
		if (!StringTools.isEmpty(tokenUserInfoDto.getCurrentMeetingId() )&& !meetingInfo.getMeetingId().equals(tokenUserInfoDto.getCurrentMeetingId())){
			throw  new BusinessException("您有未结束的会议无法加入其他会议");
		}
		checkMeetingJoin(meetingInfo.getMeetingId(),userId);
		if (MeetingJoinTypeEnum.PASSWORD.getType().equals(meetingInfo.getJoinType()) && !meetingInfo.getJoinPassword().equals(password)){
			throw new BusinessException("入会密码不正确");
		}
		tokenUserInfoDto.setCurrentMeetingId(meetingInfo.getMeetingId());
		redisComponent.saveTokenUserInfoDto(tokenUserInfoDto);
		return meetingInfo.getMeetingId();
	}

	@Override
	public void forceExitMeeting(TokenUserInfoDto tokenUserInfoDto,String userId ,MeetingMemberStatusEnum meetingMemberStatusEnum) {
		MeetingInfo meetingInfo = meetingInfoMapper.selectByMeetingId(tokenUserInfoDto.getCurrentMeetingId());
		if (!meetingInfo.getCreateUserId().equals(tokenUserInfoDto.getUserId())){
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		TokenUserInfoDto userInfoDto = redisComponent.getTokenUserInfoDtoByUserId(userId);
		exitMeetingRoom(userInfoDto,meetingMemberStatusEnum);
	}

	@Override
	public void exitMeetingRoom(TokenUserInfoDto tokenUserInfoDto, MeetingMemberStatusEnum meetingMemberStatusEnum) {
		String meetingId = tokenUserInfoDto.getCurrentMeetingId();
        if (StringTools.isEmpty(meetingId)) {
			return;
		}
		String userId = tokenUserInfoDto.getUserId();
		Boolean exit = redisComponent.exitMeeting(meetingId,userId,meetingMemberStatusEnum);
		// 该用户不在会议
		if (!exit) {
			tokenUserInfoDto.setCurrentMeetingId(null);
			redisComponent.saveTokenUserInfoDto(tokenUserInfoDto);
			return;
		}
		MessageSendDto messageSendDto = new MessageSendDto<>();
		messageSendDto.setMessageType(MessageTypeEnum.EXIT_MEETING_ROOM.getType());
		//情况正在进行的会议
		tokenUserInfoDto.setCurrentMeetingId(null);
		redisComponent.saveTokenUserInfoDto(tokenUserInfoDto);
		List<MeetingMemberDto> meetingMemberList = redisComponent.getMeetingMemberList(meetingId);
		MeetingExitDto exitDto = new MeetingExitDto();
		exitDto.setMeetingMemberList(meetingMemberList);
		exitDto.setExitStatus(meetingMemberStatusEnum.getStatus());
		exitDto.setExitUserId(userId);

		messageSendDto.setMessageContent(JsonUtils.convertObj2Json(exitDto));
		messageSendDto.setMeetingId(meetingId);
		messageSendDto.setMessageSend2Type(MessageSend2TypeEnum.GROUP.getType());
		messageHandler.sendMessage(messageSendDto);
		List<MeetingMemberDto> onLineMemberList = meetingMemberList.stream().filter(meetingMemberDto -> MeetingMemberStatusEnum.NORMAL.getStatus().equals(meetingMemberDto.getStatus())).collect(Collectors.toList());
		if(onLineMemberList.size() == 0) {
			// 结束会议
			MeetingReserve meetingReserve = (MeetingReserve) meetingReserveMapper.selectByMeetingId(meetingId);
			if (meetingReserve == null){
				finishMeeting(meetingId,null);
			return;
			}
			if (System.currentTimeMillis()>meetingReserve.getStartTime().getTime() + meetingReserve.getDuration()*60*1000){
				finishMeeting(meetingId,null);
			return;
			}
		}

		if (ArrayUtils.contains(new Integer[]{MeetingMemberStatusEnum.KICK_OUT.getStatus(),MeetingMemberStatusEnum.BLACKLIST.getStatus()},meetingMemberStatusEnum.getStatus())){
			MeetingMember meetingMember = new MeetingMember();
			meetingMember.setStatus(meetingMemberStatusEnum.getStatus());
			meetingMemberMapper.updateByMeetingIdAndUserId(meetingMember,meetingId,userId);
		}


	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void finishMeeting(String currentMeetingId, String userId) {
		MeetingInfo meetingInfo = meetingInfoMapper.selectByMeetingId(currentMeetingId);
		if(userId != null&& !meetingInfo.getCreateUserId().equals(userId)) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);

		}
		MeetingInfo updateInfo = new MeetingInfo();
		updateInfo.setStatus(MeetingStatusEnum.FINISH.getStatus());
		updateInfo.setStatus(MeetingStatusEnum.FINISH.getStatus());
		updateInfo.setEndTime(new Date());
		meetingInfoMapper.updateByMeetingId(updateInfo,currentMeetingId);
		MessageSendDto messageSendDto = new MessageSendDto<>();
		messageSendDto.setMessageType(MessageTypeEnum.FINISH_MEETING.getType());
		messageSendDto.setMessageSend2Type(MessageSend2TypeEnum.GROUP.getType());
		messageSendDto.setMeetingId(currentMeetingId);
		messageHandler.sendMessage(messageSendDto);

		MeetingMember meetingMember = new MeetingMember();
		meetingMember.setMeetingStatus(MeetingStatusEnum.FINISH.getStatus());
		MeetingMemberQuery  meetingMemberQuery = new MeetingMemberQuery();
		meetingMemberQuery.setMeetingId(currentMeetingId);
		meetingMemberMapper.updateByParam(meetingMember,meetingMemberQuery);

		// 更新预约会议状态
		MeetingReserve updateMeetingReserve = new MeetingReserve();
		updateMeetingReserve.setStatus(MeetingReserveStatusEnum.FINISHED.getStatus());
		updateMeetingReserve.setMeetingId(currentMeetingId);
		meetingReserveMapper.updateByMeetingId(updateMeetingReserve,currentMeetingId);

		List<MeetingMemberDto> meetingMemberDtoList = redisComponent.getMeetingMemberList(currentMeetingId);
		for (MeetingMemberDto meetingMemberDto:meetingMemberDtoList){
			TokenUserInfoDto userInfoDto = redisComponent.getTokenUserInfoDtoByUserId(meetingMemberDto.getUserId());
			userInfoDto.setCurrentMeetingId(null);
			redisComponent.saveTokenUserInfoDto(userInfoDto);
		}
		redisComponent.removeAllMeetingMember(currentMeetingId);

	}
}
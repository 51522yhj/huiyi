package com.easymeeting.controller;

import java.util.Date;
import java.util.List;

import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.enums.DateTimePatternEnum;
import com.easymeeting.entity.enums.MeetingReserveStatusEnum;
import com.easymeeting.entity.query.MeetingReserveQuery;
import com.easymeeting.entity.po.MeetingReserve;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.service.MeetingReserveService;
import com.easymeeting.utils.DateUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 *  Controller
 */
@RestController("meetingReserveController")
@RequestMapping("/meetingReserve")
public class MeetingReserveController extends ABaseController{

	@Resource
	private MeetingReserveService meetingReserveService;
	/**
	 * 加载当天预约会议
	 */
	@RequestMapping("/loadTodayMeeting")
	public ResponseVO loadTodayMeeting(){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		MeetingReserveQuery meetingReserveQuery = new MeetingReserveQuery();
		meetingReserveQuery.setUserId(tokenUserInfo.getUserId());
		String curDate = DateUtil.format(new Date(), DateTimePatternEnum.YYYY_MM_DD.getPattern());
		meetingReserveQuery.setStartTimeStart(curDate);
		meetingReserveQuery.setStartTimeEnd(curDate);
		meetingReserveQuery.setStatus(MeetingReserveStatusEnum.NO_START.getStatus());
		meetingReserveQuery.setOrderBy("start_time asc");
		return getSuccessResponseVO(meetingReserveService.findListByParam(meetingReserveQuery));
	}
	/**
	 * 加载预约会议
	 */
	@RequestMapping("/loadMeetingReserve")
	public ResponseVO loadMeetingReserve(){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		MeetingReserveQuery query = new MeetingReserveQuery();
		query.setUserId(tokenUserInfo.getUserId());
		query.setOrderBy("start_time desc");
		query.setStatus(MeetingReserveStatusEnum.NO_START.getStatus());
		query.setQueryUserInfo(true);
		return getSuccessResponseVO(meetingReserveService.findListByParam(query));
	}

	/**
	 * 创建预约会议
	 */
	@RequestMapping("/createMeetingReserve")
	public ResponseVO createMeetingReserve(MeetingReserve meetingReserve){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		meetingReserve.setCreateUserId(tokenUserInfo.getUserId());
		meetingReserveService.createMeetingReserve(meetingReserve);
		return getSuccessResponseVO(null);
	}

	/**
	 * 删除预约会议
	 */
	@RequestMapping("/delMeetingReserve")
	public ResponseVO delMeetingReserve(@NotNull String meetingId){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		meetingReserveService.deleteMeetingReserve(meetingId,tokenUserInfo.getUserId());
		return getSuccessResponseVO(null);
	}

	/**
	 * 删除预约会议
	 */
	@RequestMapping("/delMeetingReserveByUser")
	public ResponseVO delMeetingReserveByUser(@NotNull String meetingId){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		meetingReserveService.deleteMeetingReserveByUser(meetingId,tokenUserInfo.getUserId());
		return getSuccessResponseVO(null);
	}
	/**
	 * 加入预约会议
	 */
	@RequestMapping("/reserveJoinMeeting")
	public ResponseVO reserveJoinMeeting(@NotNull String meetingId,@NotNull String nickName,String joinPassword){

		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		tokenUserInfo.setCurrentNickName(nickName);
		meetingReserveService.reserveJoinMeeting(meetingId,tokenUserInfo,joinPassword);
		//meetingReserveService.deleteMeetingReserveByUser(meetingId,tokenUserInfo.getUserId());
		return getSuccessResponseVO(null);
	}
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(MeetingReserveQuery query){
		return getSuccessResponseVO(meetingReserveService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(MeetingReserve bean) {
		meetingReserveService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<MeetingReserve> listBean) {
		meetingReserveService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<MeetingReserve> listBean) {
		meetingReserveService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MeetingId查询对象
	 */
	@RequestMapping("/getMeetingReserveByMeetingId")
	public ResponseVO getMeetingReserveByMeetingId(String meetingId) {
		return getSuccessResponseVO(meetingReserveService.getMeetingReserveByMeetingId(meetingId));
	}

	/**
	 * 根据MeetingId修改对象
	 */
	@RequestMapping("/updateMeetingReserveByMeetingId")
	public ResponseVO updateMeetingReserveByMeetingId(MeetingReserve bean,String meetingId) {
		meetingReserveService.updateMeetingReserveByMeetingId(bean,meetingId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MeetingId删除
	 */
	@RequestMapping("/deleteMeetingReserveByMeetingId")
	public ResponseVO deleteMeetingReserveByMeetingId(String meetingId) {
		meetingReserveService.deleteMeetingReserveByMeetingId(meetingId);
		return getSuccessResponseVO(null);
	}
}
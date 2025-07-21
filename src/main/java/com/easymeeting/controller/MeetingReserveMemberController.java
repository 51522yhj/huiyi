package com.easymeeting.controller;

import java.util.List;

import com.easymeeting.entity.query.MeetingReserveMemberQuery;
import com.easymeeting.entity.po.MeetingReserveMember;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.service.MeetingReserveMemberService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  Controller
 */
@RestController("meetingReserveMemberController")
@RequestMapping("/meetingReserveMember")
public class MeetingReserveMemberController extends ABaseController{

	@Resource
	private MeetingReserveMemberService meetingReserveMemberService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(MeetingReserveMemberQuery query){
		return getSuccessResponseVO(meetingReserveMemberService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(MeetingReserveMember bean) {
		meetingReserveMemberService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<MeetingReserveMember> listBean) {
		meetingReserveMemberService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<MeetingReserveMember> listBean) {
		meetingReserveMemberService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MeetingIdAndInviteUserId查询对象
	 */
	@RequestMapping("/getMeetingReserveMemberByMeetingIdAndInviteUserId")
	public ResponseVO getMeetingReserveMemberByMeetingIdAndInviteUserId(String meetingId,String inviteUserId) {
		return getSuccessResponseVO(meetingReserveMemberService.getMeetingReserveMemberByMeetingIdAndInviteUserId(meetingId,inviteUserId));
	}

	/**
	 * 根据MeetingIdAndInviteUserId修改对象
	 */
	@RequestMapping("/updateMeetingReserveMemberByMeetingIdAndInviteUserId")
	public ResponseVO updateMeetingReserveMemberByMeetingIdAndInviteUserId(MeetingReserveMember bean,String meetingId,String inviteUserId) {
		meetingReserveMemberService.updateMeetingReserveMemberByMeetingIdAndInviteUserId(bean,meetingId,inviteUserId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MeetingIdAndInviteUserId删除
	 */
	@RequestMapping("/deleteMeetingReserveMemberByMeetingIdAndInviteUserId")
	public ResponseVO deleteMeetingReserveMemberByMeetingIdAndInviteUserId(String meetingId,String inviteUserId) {
		meetingReserveMemberService.deleteMeetingReserveMemberByMeetingIdAndInviteUserId(meetingId,inviteUserId);
		return getSuccessResponseVO(null);
	}
}
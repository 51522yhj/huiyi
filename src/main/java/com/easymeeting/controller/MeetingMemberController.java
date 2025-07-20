package com.easymeeting.controller;

import java.util.List;

import com.easymeeting.entity.query.MeetingMemberQuery;
import com.easymeeting.entity.po.MeetingMember;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.service.MeetingMemberService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  Controller
 */
@RestController("meetingMemberController")
@RequestMapping("/meetingMember")
public class MeetingMemberController extends ABaseController{

	@Resource
	private MeetingMemberService meetingMemberService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(MeetingMemberQuery query){
		return getSuccessResponseVO(meetingMemberService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(MeetingMember bean) {
		meetingMemberService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<MeetingMember> listBean) {
		meetingMemberService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<MeetingMember> listBean) {
		meetingMemberService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MeetingIdAndUserId查询对象
	 */
	@RequestMapping("/getMeetingMemberByMeetingIdAndUserId")
	public ResponseVO getMeetingMemberByMeetingIdAndUserId(String meetingId,String userId) {
		return getSuccessResponseVO(meetingMemberService.getMeetingMemberByMeetingIdAndUserId(meetingId,userId));
	}

	/**
	 * 根据MeetingIdAndUserId修改对象
	 */
	@RequestMapping("/updateMeetingMemberByMeetingIdAndUserId")
	public ResponseVO updateMeetingMemberByMeetingIdAndUserId(MeetingMember bean,String meetingId,String userId) {
		meetingMemberService.updateMeetingMemberByMeetingIdAndUserId(bean,meetingId,userId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MeetingIdAndUserId删除
	 */
	@RequestMapping("/deleteMeetingMemberByMeetingIdAndUserId")
	public ResponseVO deleteMeetingMemberByMeetingIdAndUserId(String meetingId,String userId) {
		meetingMemberService.deleteMeetingMemberByMeetingIdAndUserId(meetingId,userId);
		return getSuccessResponseVO(null);
	}
}
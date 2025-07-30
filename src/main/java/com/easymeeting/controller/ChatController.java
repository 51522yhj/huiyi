package com.easymeeting.controller;

import java.util.List;

import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.query.MeetingChatMessageQuery;
import com.easymeeting.entity.po.MeetingChatMessage;
import com.easymeeting.entity.vo.PaginationResultVO;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.service.MeetingChatMessageService;
import com.easymeeting.utils.TableSplitUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  Controller
 */
@RestController
@Validated
@Slf4j
@RequestMapping("/chat")
public class ChatController extends ABaseController{

	@Resource
	private MeetingChatMessageService meetingChatMessageService;
	/**
	 * 加载消息
	 */
	@RequestMapping("/loadMessage")
	public ResponseVO loadMessage(Long maxMessageId,Integer pageNo){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		String meetingId = tokenUserInfo.getCurrentMeetingId();
		MeetingChatMessageQuery param = new MeetingChatMessageQuery();
		param.setMeetingId(meetingId);
		param.setUserId(tokenUserInfo.getUserId());
		param.setPageNo(pageNo);
		param.setMaxMessageId(maxMessageId);
		param.setOrderBy("m.message_id desc");
		String meetingChatMessageTable = TableSplitUtils.getMeetingChatMessageTable(meetingId);
		PaginationResultVO<MeetingChatMessage> result = meetingChatMessageService.findListByPage(meetingChatMessageTable,param);
		return getSuccessResponseVO(result);
	}
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(MeetingChatMessageQuery query){
		return getSuccessResponseVO(meetingChatMessageService.findListByPage(null,query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(MeetingChatMessage bean) {
		meetingChatMessageService.add(null,bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<MeetingChatMessage> listBean) {
		meetingChatMessageService.addBatch(null,listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<MeetingChatMessage> listBean) {
		meetingChatMessageService.addBatch(null,listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MessageId查询对象
	 */
	@RequestMapping("/getMeetingChatMessageByMessageId")
	public ResponseVO getMeetingChatMessageByMessageId(Long messageId) {
		return getSuccessResponseVO(meetingChatMessageService.getMeetingChatMessageByMessageId(null,messageId));
	}

	/**
	 * 根据MessageId修改对象
	 */
	@RequestMapping("/updateMeetingChatMessageByMessageId")
	public ResponseVO updateMeetingChatMessageByMessageId(MeetingChatMessage bean,Long messageId) {
		meetingChatMessageService.updateMeetingChatMessageByMessageId(null,bean,messageId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据MessageId删除
	 */
	@RequestMapping("/deleteMeetingChatMessageByMessageId")
	public ResponseVO deleteMeetingChatMessageByMessageId(Long messageId) {
		meetingChatMessageService.deleteMeetingChatMessageByMessageId(null,messageId);
		return getSuccessResponseVO(null);
	}
}
package com.easymeeting.controller;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.List;

import com.easymeeting.annotation.GlobalInterceptor;
import com.easymeeting.constants.Constants;
import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.enums.ReceiveTypeEnum;
import com.easymeeting.entity.enums.ResponseCodeEnum;
import com.easymeeting.entity.po.MeetingMember;
import com.easymeeting.entity.query.MeetingChatMessageQuery;
import com.easymeeting.entity.po.MeetingChatMessage;
import com.easymeeting.entity.query.MeetingMemberQuery;
import com.easymeeting.entity.vo.PaginationResultVO;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.exception.BusinessException;
import com.easymeeting.service.MeetingChatMessageService;
import com.easymeeting.service.impl.MeetingMemberServiceImpl;
import com.easymeeting.utils.TableSplitUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @Autowired
    private MeetingMemberServiceImpl meetingMemberService;

	/**
	 * 加载消息
	 */
	@RequestMapping("/loadMessage")
	@GlobalInterceptor
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
	 * 发送消息
	 */
	@RequestMapping("/sendMessage")
	@GlobalInterceptor
	public ResponseVO sendMessage(String message,
								  @NotNull Integer messageType,
								  @NotEmpty String receiveUserId,
								  String fileName,
								  Long fileSize,
								  Integer fileType){
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		MeetingChatMessage chatMessage = new MeetingChatMessage();
		chatMessage.setMeetingId(tokenUserInfo.getCurrentMeetingId());
		chatMessage.setSendUserId(tokenUserInfo.getUserId());
		chatMessage.setFileName(fileName);
		chatMessage.setMessageContent(message);
		chatMessage.setFileSize(fileSize);
		chatMessage.setFileType(fileType);
		chatMessage.setSnedUserNickName(tokenUserInfo.getNickName());
		chatMessage.setMessageType(messageType);
		if (Constants.ZERO_STR.equals(receiveUserId)){
			chatMessage.setReceiveType(ReceiveTypeEnum.ALL.getType());
        }else {
            chatMessage.setReceiveType(ReceiveTypeEnum.USER.getType());
		}
		chatMessage.setReceiveUserId(receiveUserId);
		meetingChatMessageService.saveChatMessage(chatMessage);
		return  getSuccessResponseVO(chatMessage);
	}

	/**
	 * 上传文件
	 */
	@RequestMapping("/uploadFile")
	public ResponseVO uploadFile(@NotNull MultipartFile file,
								 @NotNull Long messageId,
								 @NotNull Long sendTime) throws IOException {
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		meetingChatMessageService.uploadFile(file,tokenUserInfo.getCurrentMeetingId(),messageId,sendTime);
		return getSuccessResponseVO(null);

	}
	/**
	 * 加载历史消息
	 */
	@RequestMapping("/loadHistoryMessage")
	public ResponseVO loadHistoryMessage(String meetingId,
								 Long maxMessageId,
								 Integer pageNo) throws IOException {
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		checkMember(meetingId,tokenUserInfo.getUserId());
		MeetingChatMessageQuery param = new MeetingChatMessageQuery();
		param.setMeetingId(meetingId);
		param.setUserId(tokenUserInfo.getUserId());
		param.setMaxMessageId(maxMessageId);
		param.setPageNo(pageNo);
		param.setOrderBy("m.message_id desc");
		String tableName = TableSplitUtils.getMeetingChatMessageTable(meetingId);
		PaginationResultVO resultVO = meetingChatMessageService.findListByPage(tableName,param);
		return getSuccessResponseVO(resultVO);
	}

	private void checkMember(String meetingId, String userId) {
		MeetingMemberQuery meetingMemberQuery = new MeetingMemberQuery();
		meetingMemberQuery.setMeetingId(meetingId);
		meetingMemberQuery.setUserId(userId);
		Integer countByParam = meetingMemberService.findCountByParam(meetingMemberQuery);
		if (countByParam ==0){
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
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
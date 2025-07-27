package com.easymeeting.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.easymeeting.entity.dto.MessageSendDto;
import com.easymeeting.entity.enums.*;
import com.easymeeting.entity.po.UserContact;
import com.easymeeting.exception.BusinessException;
import com.easymeeting.mappers.UserContactMapper;
import com.easymeeting.websocket.message.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymeeting.entity.query.UserContactApplyQuery;
import com.easymeeting.entity.po.UserContactApply;
import com.easymeeting.entity.vo.PaginationResultVO;
import com.easymeeting.entity.query.SimplePage;
import com.easymeeting.mappers.UserContactApplyMapper;
import com.easymeeting.service.UserContactApplyService;
import com.easymeeting.utils.StringTools;


/**
 *  业务接口实现
 */
@Service("userContactApplyService")
public class UserContactApplyServiceImpl implements UserContactApplyService {

	@Resource
	private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;
    @Autowired
    private UserContactMapper userContactMapper;
	@Resource
	private MessageHandler messageHandler;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<UserContactApply> findListByParam(UserContactApplyQuery param) {
		return this.userContactApplyMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(UserContactApplyQuery param) {
		return this.userContactApplyMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<UserContactApply> findListByPage(UserContactApplyQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<UserContactApply> list = this.findListByParam(param);
		PaginationResultVO<UserContactApply> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(UserContactApply bean) {
		return this.userContactApplyMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<UserContactApply> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userContactApplyMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<UserContactApply> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userContactApplyMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(UserContactApply bean, UserContactApplyQuery param) {
		StringTools.checkParam(param);
		return this.userContactApplyMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(UserContactApplyQuery param) {
		StringTools.checkParam(param);
		return this.userContactApplyMapper.deleteByParam(param);
	}

	/**
	 * 根据ApplyId获取对象
	 */
	@Override
	public UserContactApply getUserContactApplyByApplyId(Integer applyId) {
		return this.userContactApplyMapper.selectByApplyId(applyId);
	}

	/**
	 * 根据ApplyId修改
	 */
	@Override
	public Integer updateUserContactApplyByApplyId(UserContactApply bean, Integer applyId) {
		return this.userContactApplyMapper.updateByApplyId(bean, applyId);
	}

	/**
	 * 根据ApplyId删除
	 */
	@Override
	public Integer deleteUserContactApplyByApplyId(Integer applyId) {
		return this.userContactApplyMapper.deleteByApplyId(applyId);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserId获取对象
	 */
	@Override
	public UserContactApply getUserContactApplyByApplyUserIdAndReceiveUserId(String applyUserId, String receiveUserId) {
		return this.userContactApplyMapper.selectByApplyUserIdAndReceiveUserId(applyUserId, receiveUserId);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserId修改
	 */
	@Override
	public Integer updateUserContactApplyByApplyUserIdAndReceiveUserId(UserContactApply bean, String applyUserId, String receiveUserId) {
		return this.userContactApplyMapper.updateByApplyUserIdAndReceiveUserId(bean, applyUserId, receiveUserId);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserId删除
	 */
	@Override
	public Integer deleteUserContactApplyByApplyUserIdAndReceiveUserId(String applyUserId, String receiveUserId) {
		return this.userContactApplyMapper.deleteByApplyUserIdAndReceiveUserId(applyUserId, receiveUserId);
	}

	@Override
	public void dealWithApply(String applyId, String userId, String nickName, Integer status) {
		UserContactApplyStatusEnum statusEnum = UserContactApplyStatusEnum.getByStatus(status);
		if (statusEnum == null || UserContactApplyStatusEnum.INIT == statusEnum){
			throw  new BusinessException(ResponseCodeEnum.CODE_600);
		}
		UserContactApply apply = userContactApplyMapper.selectByApplyUserIdAndReceiveUserId(applyId, userId);
		if (apply == null){
			throw  new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if(UserContactApplyStatusEnum.PASS.getStatus().equals(status)){
			Date now = new Date();
			UserContact userContact = new UserContact();
			userContact.setUserId(applyId);
			userContact.setContactId(userId);
			userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			userContact.setLastUpdateTime(now);
			userContactMapper.insertOrUpdate(userContact);
		}
		UserContactApply updateApply = new UserContactApply();
		updateApply.setStatus(status);
		userContactApplyMapper.updateByApplyId(updateApply,apply.getApplyId());
		MessageSendDto sendDto = new MessageSendDto();
		sendDto.setReceiveUserId(applyId);
		sendDto.setMessageSend2Type(MessageSend2TypeEnum.USER.getType());
		sendDto.setUserNickName(nickName);
		sendDto.setMessageContent(status);
		sendDto.setMessageType(MessageTypeEnum.USER_CONTACT_DEAL_WITH.getType());
		messageHandler.sendMessage(sendDto);
	}
}
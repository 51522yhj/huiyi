package com.easymeeting.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.easymeeting.entity.dto.MessageSendDto;
import com.easymeeting.entity.enums.*;
import com.easymeeting.entity.po.UserContactApply;
import com.easymeeting.entity.po.UserInfo;
import com.easymeeting.entity.query.UserContactApplyQuery;
import com.easymeeting.entity.query.UserInfoQuery;
import com.easymeeting.entity.vo.UserInfoVO4Search;
import com.easymeeting.exception.BusinessException;
import com.easymeeting.mappers.UserContactApplyMapper;
import com.easymeeting.mappers.UserInfoMapper;
import com.easymeeting.service.UserContactApplyService;
import com.easymeeting.websocket.message.MessageHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymeeting.entity.query.UserContactQuery;
import com.easymeeting.entity.po.UserContact;
import com.easymeeting.entity.vo.PaginationResultVO;
import com.easymeeting.entity.query.SimplePage;
import com.easymeeting.mappers.UserContactMapper;
import com.easymeeting.service.UserContactService;
import com.easymeeting.utils.StringTools;


/**
 *  业务接口实现
 */
@Service("userContactService")
public class UserContactServiceImpl implements UserContactService {

	@Resource
	private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Autowired
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
	@Resource
	private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;
	@Resource
	private MessageHandler messageHandler;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<UserContact> findListByParam(UserContactQuery param) {
		return this.userContactMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(UserContactQuery param) {
		return this.userContactMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<UserContact> findListByPage(UserContactQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<UserContact> list = this.findListByParam(param);
		PaginationResultVO<UserContact> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(UserContact bean) {
		return this.userContactMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<UserContact> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userContactMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<UserContact> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userContactMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(UserContact bean, UserContactQuery param) {
		StringTools.checkParam(param);
		return this.userContactMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(UserContactQuery param) {
		StringTools.checkParam(param);
		return this.userContactMapper.deleteByParam(param);
	}

	/**
	 * 根据UserIdAndContactId获取对象
	 */
	@Override
	public UserContact getUserContactByUserIdAndContactId(String userId, String contactId) {
		return this.userContactMapper.selectByUserIdAndContactId(userId, contactId);
	}

	/**
	 * 根据UserIdAndContactId修改
	 */
	@Override
	public Integer updateUserContactByUserIdAndContactId(UserContact bean, String userId, String contactId) {
		return this.userContactMapper.updateByUserIdAndContactId(bean, userId, contactId);
	}

	/**
	 * 根据UserIdAndContactId删除
	 */
	@Override
	public Integer deleteUserContactByUserIdAndContactId(String userId, String contactId) {
		return this.userContactMapper.deleteByUserIdAndContactId(userId, contactId);
	}

	@Override
	public UserInfoVO4Search searchContact(String myId, String userId) {
		System.out.println("userInfoMapper is null? " + (userInfoMapper == null));
		UserInfo userInfo = userInfoMapper.selectByUserId(userId);
		if (userInfo == null){
			return null;
		}
		UserInfoVO4Search result = new UserInfoVO4Search();
		result.setUserId(userInfo.getUserId());
		result.setNickName(userInfo.getNickName());
		if (myId.equals(userId)){
			result.setStatus(-UserContactApplyStatusEnum.PASS.getStatus());
		}
		UserContactApply contactApply = userContactApplyMapper.selectByApplyUserIdAndReceiveUserId(myId, userId);
		UserContact userContact = userContactMapper.selectByUserIdAndContactId(userId,myId);

		//拉黑
		if(contactApply!= null && UserContactApplyStatusEnum.BLACKLIST.getStatus().equals(contactApply.getStatus())||
		userContact!= null && UserContactApplyStatusEnum.BLACKLIST.getStatus().equals(userContact.getStatus())){
			result.setStatus(UserContactApplyStatusEnum.BLACKLIST.getStatus());
			return result;
        }
        //申请中
        else if(contactApply!= null && UserContactApplyStatusEnum.INIT.getStatus().equals(contactApply.getStatus())){
            result.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
			return result;
        }
		UserContact myUserContact = userContactMapper.selectByUserIdAndContactId(myId, userId);
		if (userContact!= null && UserContactStatusEnum.FRIEND.getStatus().equals(userContact.getStatus()) &&
		myUserContact!= null && UserContactStatusEnum.FRIEND.getStatus().equals(myUserContact.getStatus())){
			result.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			return result;
		}
		return result;
	}

	/**
	 * 发起申请好友
	 * @param bean
	 * @return
	 */
	@Override
	public Integer contactApply(UserContactApply bean) {
		UserContact userContact = userContactMapper.selectByUserIdAndContactId(bean.getReceiveUserId(), bean.getApplyUserId());
		if ( (userContact != null)&&UserContactStatusEnum.BLACKLIST.getStatus().equals(userContact.getStatus()) ){
			throw  new BusinessException("对方已将你拉黑");
		}
		if ((userContact != null) && UserContactStatusEnum.FRIEND.getStatus().equals(userContact.getStatus())){
			UserContact updateInfo = new UserContact();
			updateInfo.setLastUpdateTime(new Date());
			updateInfo.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			userContactMapper.updateByUserIdAndContactId(updateInfo,bean.getApplyUserId(),bean.getReceiveUserId());
			return UserContactStatusEnum.FRIEND.getStatus();
		}
		UserContactApply apply = userContactApplyMapper.selectByApplyUserIdAndReceiveUserId(bean.getApplyUserId(),bean.getReceiveUserId());
		if (apply == null){
			bean.setLastApplyTime(System.currentTimeMillis());
			bean.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
			return userContactApplyMapper.insert(bean);
		}else {
			UserContactApply updateInfo = new UserContactApply();
			updateInfo.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
			updateInfo.setLastApplyTime(System.currentTimeMillis());
			userContactApplyMapper.updateByApplyId(updateInfo,apply.getApplyId());
		}
		MessageSendDto messageSendDto = new MessageSendDto<>();
		messageSendDto.setMessageSend2Type(MessageSend2TypeEnum.USER.getType());
		messageSendDto.setMessageType(MessageTypeEnum.USER_CONTACT_APPLY.getType());
		messageSendDto.setReceiveUserId(bean.getReceiveUserId());
		messageHandler.sendMessage(messageSendDto);
		return UserContactApplyStatusEnum.INIT.getStatus();
	}

	@Override
	public void delContact(String userId, String contactId, Integer status) {
		if (!ArrayUtils.contains(new Integer[]{UserContactStatusEnum.DEL.getStatus(),UserContactStatusEnum.BLACKLIST.getStatus()},status)){
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		UserContact userContact= new UserContact();
		userContact.setLastUpdateTime(new Date());
		userContact.setStatus(status);
		userContactMapper.updateByUserIdAndContactId(userContact,userId,contactId);
	}
}
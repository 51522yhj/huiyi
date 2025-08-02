package com.easymeeting.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.easymeeting.config.AppConfig;
import com.easymeeting.constants.Constants;
import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.enums.ResponseCodeEnum;
import com.easymeeting.entity.enums.UserStatusEnum;
import com.easymeeting.entity.vo.UserInfoVO;
import com.easymeeting.exception.BusinessException;
import com.easymeeting.redis.RedisComponent;
import com.easymeeting.utils.CopyTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymeeting.entity.enums.PageSize;
import com.easymeeting.entity.query.UserInfoQuery;
import com.easymeeting.entity.po.UserInfo;
import com.easymeeting.entity.vo.PaginationResultVO;
import com.easymeeting.entity.query.SimplePage;
import com.easymeeting.mappers.UserInfoMapper;
import com.easymeeting.service.UserInfoService;
import com.easymeeting.utils.StringTools;


/**
 *  业务接口实现
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
	@Resource
	private AppConfig appConfig;
    @Autowired
    private RedisComponent redisComponent;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<UserInfo> findListByParam(UserInfoQuery param) {
		return this.userInfoMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(UserInfoQuery param) {
		return this.userInfoMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<UserInfo> list = this.findListByParam(param);
		PaginationResultVO<UserInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(UserInfo bean) {
		return this.userInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<UserInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<UserInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(UserInfo bean, UserInfoQuery param) {
		StringTools.checkParam(param);
		return this.userInfoMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(UserInfoQuery param) {
		StringTools.checkParam(param);
		return this.userInfoMapper.deleteByParam(param);
	}

	/**
	 * 根据UserId获取对象
	 */
	@Override
	public UserInfo getUserInfoByUserId(String userId) {
		return this.userInfoMapper.selectByUserId(userId);
	}

	/**
	 * 根据UserId修改
	 */
	@Override
	public Integer updateUserInfoByUserId(UserInfo bean, String userId) {
		return this.userInfoMapper.updateByUserId(bean, userId);
	}

	/**
	 * 根据UserId删除
	 */
	@Override
	public Integer deleteUserInfoByUserId(String userId) {
		// 调用userInfoMapper的deleteByUserId方法，根据userId删除用户信息，并返回删除的行数
		return this.userInfoMapper.deleteByUserId(userId);
	}

	@Override
	public void register(String email, String nickName, String password) {
		// 根据邮箱查询用户信息
		UserInfo userInfo= this.userInfoMapper.selectByEmail(email);
		if (userInfo != null) {
			throw new RuntimeException("该邮箱已被注册");
		}
		// 注册用户
		Date curDate = new Date();
		String userId = StringTools.getRandomNumber(Constants.LENGTH_12);
		UserInfo user = new UserInfo();
		user.setUserId(userId);
		user.setEmail(email);
		user.setNickName(nickName);
		user.setPassword(password);
		user.setPassword(StringTools.encodeByMD5(password));
		user.setCreateTime(curDate);
		user.setLastLoginTime(curDate.getTime());
		user.setLastOffTime(curDate.getTime());
		user.setMeetingNo(StringTools.getMeetingNoOrMeetingId());
		user.setStatus(UserStatusEnum.ENABLE.getStatus());
		this.userInfoMapper.insert(user);

	}

	@Override
	public UserInfoVO login(String email, String password) {
		UserInfo userInfo = userInfoMapper.selectByEmail(email);
		if (null == userInfo || !StringTools.encodeByMD5(password).equals(userInfo.getPassword())) {
			throw new BusinessException(ResponseCodeEnum.CODE_501);
		}
		if (UserStatusEnum.DISABLE.getStatus().equals(userInfo.getStatus())) {
			throw new BusinessException(ResponseCodeEnum.CODE_502);
		}
//		if (userInfo.getLastLoginTime()!= null && userInfo.getLastLoginTime() > userInfo.getLastOffTime()) {
//			throw new BusinessException(ResponseCodeEnum.CODE_503);
//		}
		TokenUserInfoDto tokenUserInfoDto = CopyTools.copy(userInfo, TokenUserInfoDto.class);
		String token =  StringTools.encodeByMD5(tokenUserInfoDto.getUserId() + StringTools.getRandomNumber(Constants.LENGTH_20));
		tokenUserInfoDto.setToken(token);
		tokenUserInfoDto.setAdmin(appConfig.getAdminEmails().contains(email));
		tokenUserInfoDto.setMyMeetingNo(userInfo.getMeetingNo());
		redisComponent.saveTokenUserInfoDto(tokenUserInfoDto);
		UserInfoVO userInfoVO = CopyTools.copy(userInfo, UserInfoVO.class);
		userInfoVO.setToken(token);
		userInfoVO.setAdmin(appConfig.getAdminEmails().contains(email));
        return userInfoVO;
	}
}
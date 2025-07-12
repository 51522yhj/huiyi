package com.easymeeting.controller;

import java.util.List;

import com.easymeeting.entity.po.LoginRequest;
import com.easymeeting.entity.po.RegisterRequest;
import com.easymeeting.entity.query.UserInfoQuery;
import com.easymeeting.entity.po.UserInfo;
import com.easymeeting.entity.vo.CheckCodeVo;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.entity.vo.UserInfoVO;
import com.easymeeting.exception.BusinessException;
import com.easymeeting.redis.RedisComponent;
import com.easymeeting.service.UserInfoService;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 *  Controller
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController extends ABaseController{

	@Resource
	private UserInfoService userInfoService;
	@Resource
	private RedisComponent redisComponent;

	@RequestMapping("/checkCode")
	public ResponseVO checkCode() {
		ArithmeticCaptcha captcha = new ArithmeticCaptcha(100,42);
		String code = captcha.text();
		String checkCodeKey = redisComponent.saveCheckCode(code);
		String checkCodeBase64 = captcha.toBase64();
		CheckCodeVo checkCodeVo = new CheckCodeVo();
		checkCodeVo.setCheckCodeKey(checkCodeKey);
		checkCodeVo.setCheckCode(checkCodeBase64);
		return  getSuccessResponseVO(checkCodeVo);
	}

	@RequestMapping("/register")
	public ResponseVO register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result) {

		try {
			if (result.hasErrors()) {
				throw  new BusinessException("参数校验失败");
			}
//			if (!registerRequest.getCheckCode().equalsIgnoreCase(redisComponent.getCheckCode(registerRequest.getCheckCodeKey()))){
//				throw  new BusinessException("图片验证码不正确");
//			}
			this.userInfoService.register(registerRequest.getEmail(),registerRequest.getNickName(),registerRequest.getPassword());
			return getSuccessResponseVO(null);
		} finally {
			log.info("清理验证码");
			redisComponent.cleanCheckCode(registerRequest.getCheckCodeKey());
		}
	}
	@RequestMapping("/login")
	public ResponseVO login(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		try {
			if( result.hasErrors()){
				throw new BusinessException("参数校验失败");
			}
//			if (!loginRequest.getCheckCode().equalsIgnoreCase(redisComponent.getCheckCode(loginRequest.getCheckCodeKey()))){
//				throw  new BusinessException("图片验证码不正确");
//			}
			UserInfoVO userInfoVO = this.userInfoService.login(loginRequest.getEmail(),loginRequest.getPassword());
			return getSuccessResponseVO(userInfoVO);
		}finally {
			redisComponent.cleanCheckCode(loginRequest.getCheckCodeKey());
		}
	}



	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(UserInfoQuery query){
		return getSuccessResponseVO(userInfoService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(UserInfo bean) {
		userInfoService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<UserInfo> listBean) {
		userInfoService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<UserInfo> listBean) {
		userInfoService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据UserId查询对象
	 */
	@RequestMapping("/getUserInfoByUserId")
	public ResponseVO getUserInfoByUserId(String userId) {
		return getSuccessResponseVO(userInfoService.getUserInfoByUserId(userId));
	}

	/**
	 * 根据UserId修改对象
	 */
	@RequestMapping("/updateUserInfoByUserId")
	public ResponseVO updateUserInfoByUserId(UserInfo bean,String userId) {
		userInfoService.updateUserInfoByUserId(bean,userId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据UserId删除
	 */
	@RequestMapping("/deleteUserInfoByUserId")
	public ResponseVO deleteUserInfoByUserId(String userId) {
		userInfoService.deleteUserInfoByUserId(userId);
		return getSuccessResponseVO(null);
	}
}
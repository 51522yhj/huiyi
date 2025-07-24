package com.easymeeting.controller;

import java.util.List;

import com.easymeeting.entity.query.UserContactApplyQuery;
import com.easymeeting.entity.po.UserContactApply;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.service.UserContactApplyService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  Controller
 */
@RestController("userContactApplyController")
@RequestMapping("/userContactApply")
public class UserContactApplyController extends ABaseController{

	@Resource
	private UserContactApplyService userContactApplyService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(UserContactApplyQuery query){
		return getSuccessResponseVO(userContactApplyService.findListByPage(query));
	}
	/**
	 *
	 */

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(UserContactApply bean) {
		userContactApplyService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<UserContactApply> listBean) {
		userContactApplyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<UserContactApply> listBean) {
		userContactApplyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyId查询对象
	 */
	@RequestMapping("/getUserContactApplyByApplyId")
	public ResponseVO getUserContactApplyByApplyId(Integer applyId) {
		return getSuccessResponseVO(userContactApplyService.getUserContactApplyByApplyId(applyId));
	}

	/**
	 * 根据ApplyId修改对象
	 */
	@RequestMapping("/updateUserContactApplyByApplyId")
	public ResponseVO updateUserContactApplyByApplyId(UserContactApply bean,Integer applyId) {
		userContactApplyService.updateUserContactApplyByApplyId(bean,applyId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyId删除
	 */
	@RequestMapping("/deleteUserContactApplyByApplyId")
	public ResponseVO deleteUserContactApplyByApplyId(Integer applyId) {
		userContactApplyService.deleteUserContactApplyByApplyId(applyId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserId查询对象
	 */
	@RequestMapping("/getUserContactApplyByApplyUserIdAndReceiveUserId")
	public ResponseVO getUserContactApplyByApplyUserIdAndReceiveUserId(String applyUserId,String receiveUserId) {
		return getSuccessResponseVO(userContactApplyService.getUserContactApplyByApplyUserIdAndReceiveUserId(applyUserId,receiveUserId));
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserId修改对象
	 */
	@RequestMapping("/updateUserContactApplyByApplyUserIdAndReceiveUserId")
	public ResponseVO updateUserContactApplyByApplyUserIdAndReceiveUserId(UserContactApply bean,String applyUserId,String receiveUserId) {
		userContactApplyService.updateUserContactApplyByApplyUserIdAndReceiveUserId(bean,applyUserId,receiveUserId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserId删除
	 */
	@RequestMapping("/deleteUserContactApplyByApplyUserIdAndReceiveUserId")
	public ResponseVO deleteUserContactApplyByApplyUserIdAndReceiveUserId(String applyUserId,String receiveUserId) {
		userContactApplyService.deleteUserContactApplyByApplyUserIdAndReceiveUserId(applyUserId,receiveUserId);
		return getSuccessResponseVO(null);
	}
}
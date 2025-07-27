package com.easymeeting.controller;

import com.easymeeting.annotation.GlobalInterceptor;
import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.enums.UserContactApplyStatusEnum;
import com.easymeeting.entity.enums.UserContactStatusEnum;
import com.easymeeting.entity.po.UserContact;
import com.easymeeting.entity.po.UserContactApply;
import com.easymeeting.entity.query.UserContactApplyQuery;
import com.easymeeting.entity.query.UserContactQuery;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.entity.vo.UserInfoVO4Search;
import com.easymeeting.service.UserContactApplyService;
import com.easymeeting.service.UserContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController("userContactController")
@RequestMapping("/userContact")
@Slf4j
public class UserContactController extends ABaseController{

    @Resource
    private UserContactService userContactService;
    @Resource
    private UserContactApplyService userContactApplyService;

    /**
     * 搜索联系人
     */
    @RequestMapping("/searchContact")
    @GlobalInterceptor
    public ResponseVO searchContact(@NotNull String userId){
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        UserInfoVO4Search userInfoVO4Search = userContactService.searchContact(tokenUserInfo.getUserId(), userId);
        return getSuccessResponseVO(userInfoVO4Search);
    }
    /**
     *  好友申请
     */
    @RequestMapping("/contactApply")
    @GlobalInterceptor
    public ResponseVO contactApply(@NotNull String receiveUserId){
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        UserContactApply userContactApply = new UserContactApply();
        userContactApply.setApplyUserId(tokenUserInfo.getUserId());
        userContactApply.setReceiveUserId(receiveUserId);
        Integer status = userContactService.contactApply(userContactApply);
        return getSuccessResponseVO(status);
    }
    /**
     * 处理申请
     */
    @RequestMapping("/dealWithApply")
    @GlobalInterceptor
    public ResponseVO dealWithApply(@NotNull String applyUserId,@NotNull Integer status){
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        userContactApplyService.dealWithApply(applyUserId,tokenUserInfo.getUserId(),tokenUserInfo.getNickName(),status);
        return getSuccessResponseVO(null);
    }
    /**
     *  加载申请
     */
    @RequestMapping("/loadContactApply")
    @GlobalInterceptor
    public ResponseVO loadContactApply(){
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        UserContactApplyQuery applyQuery = new UserContactApplyQuery();
        applyQuery.setReceiveUserId(tokenUserInfo.getUserId());
        applyQuery.setOrderBy("last_apply_time desc");
        applyQuery.setQueryUserInfo(true);
        applyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
        log.info(applyQuery.getStatus().toString());
        List<UserContactApply> listByParam = userContactApplyService.findListByParam(applyQuery);
        return getSuccessResponseVO(listByParam);
    }
    /**
     *  加载好友列表
     */
    @RequestMapping("/loadContactUser")
    @GlobalInterceptor
    public ResponseVO loadContactUser(){
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        UserContactQuery contactQuery = new UserContactQuery();
        contactQuery.setUserId(tokenUserInfo.getUserId());
        contactQuery.setQueryUserInfo(true);
        contactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        contactQuery.setOrderBy("last_update_time desc");
        List<UserContact> listByParam = userContactService.findListByParam(contactQuery);
        return getSuccessResponseVO(listByParam);
    }
    /**
     * 刪除好友
     */
    @RequestMapping("/delContact")
    @GlobalInterceptor

    public ResponseVO delContact(@NotNull String contactId,@NotNull Integer status){
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        userContactService.delContact(tokenUserInfo.getUserId(),contactId,status);
        return getSuccessResponseVO(null);
    }
    /**
     * 加載申請小氣泡
     */
    @RequestMapping("/loadContactApplyDealWithCount")
    @GlobalInterceptor
    public ResponseVO loadContactApplyDealWithCount(){
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        UserContactApplyQuery applyQuery = new UserContactApplyQuery();
        applyQuery.setReceiveUserId(tokenUserInfo.getUserId());
        applyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus());
        Integer count = userContactApplyService.findCountByParam(applyQuery);
        return getSuccessResponseVO(count);
    }
    /**
     * 根据条件分页查询
     */
    @RequestMapping("/loadDataList")
    public ResponseVO loadDataList(UserContactQuery query){
        return getSuccessResponseVO(userContactService.findListByPage(query));
    }

    /**
     * 新增
     */
    @RequestMapping("/add")
    public ResponseVO add(UserContact bean) {
        userContactService.add(bean);
        return getSuccessResponseVO(null);
    }

    /**
     * 批量新增
     */
    @RequestMapping("/addBatch")
    public ResponseVO addBatch(@RequestBody List<UserContact> listBean) {
        userContactService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 批量新增/修改
     */
    @RequestMapping("/addOrUpdateBatch")
    public ResponseVO addOrUpdateBatch(@RequestBody List<UserContact> listBean) {
        userContactService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据UserIdAndContactId查询对象
     */
    @RequestMapping("/getUserContactByUserIdAndContactId")
    public ResponseVO getUserContactByUserIdAndContactId(String userId,String contactId) {
        return getSuccessResponseVO(userContactService.getUserContactByUserIdAndContactId(userId,contactId));
    }

    /**
     * 根据UserIdAndContactId修改对象
     */
    @RequestMapping("/updateUserContactByUserIdAndContactId")
    public ResponseVO updateUserContactByUserIdAndContactId(UserContact bean,String userId,String contactId) {
        userContactService.updateUserContactByUserIdAndContactId(bean,userId,contactId);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据UserIdAndContactId删除
     */
    @RequestMapping("/deleteUserContactByUserIdAndContactId")
    public ResponseVO deleteUserContactByUserIdAndContactId(String userId,String contactId) {
        userContactService.deleteUserContactByUserIdAndContactId(userId,contactId);
        return getSuccessResponseVO(null);
    }

}

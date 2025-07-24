package com.easymeeting.controller;

import com.easymeeting.annotation.GlobalInterceptor;
import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.po.UserContact;
import com.easymeeting.entity.po.UserContactApply;
import com.easymeeting.entity.query.UserContactQuery;
import com.easymeeting.entity.vo.ResponseVO;
import com.easymeeting.entity.vo.UserInfoVO4Search;
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
    @RequestMapping("/loadContactApplyDealWithCount")
    public ResponseVO loadContactApplyDealWithCount(){
        return getSuccessResponseVO(null);
    }
}

package com.easymeeting.redis;

import com.easymeeting.constants.Constants;
import com.easymeeting.entity.dto.MeetingMemberDto;
import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.enums.MeetingMemberStatusEnum;
import com.easymeeting.utils.JsonUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RedisComponent {
    @Resource
    private RedisUtils redisUtils;

    public String saveCheckCode(String code){
        String checkCodeKey  = UUID.randomUUID().toString();
        redisUtils.setex(Constants.REDIS_KEY_CHECK_CODE+checkCodeKey,code,60*10);
        return checkCodeKey;
    }

    public String getCheckCode(String checkCodeKey) {
        return (String) redisUtils.get(Constants.REDIS_KEY_CHECK_CODE+checkCodeKey);
    }
    public void cleanCheckCode(String checkCodeKey) {
        redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE+checkCodeKey);
    }

    public void saveTokenUserInfoDto(TokenUserInfoDto tokenUserInfoDto) {
        redisUtils.setex(Constants.REDIS_KEY_WS_TOKEN + tokenUserInfoDto.getToken(), tokenUserInfoDto, Constants.REDIS_KEY_EXPIRES_ONE_DAY);
        redisUtils.setex(Constants.REDIS_KEY_WS_TOEKN_USERID+ tokenUserInfoDto.getUserId(), tokenUserInfoDto.getToken(), Constants.REDIS_KEY_EXPIRES_ONE_DAY);
    }
    public TokenUserInfoDto checkToken(String token) {
        return (TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN + token);
    }
    public TokenUserInfoDto getTokenUserInfoDtoByUserId(String userId) {
        return JsonUtils.convertJson2Obj((String) redisUtils.get(Constants.REDIS_KEY_WS_TOEKN_USERID + userId), TokenUserInfoDto.class);
       // return (TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_WS_TOEKN_USERID + userId);
    }

    public TokenUserInfoDto getTokenUserInfoDto(String token) {
        return (TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_WS_TOKEN + token);
    }

    public MeetingMemberDto getMeetingMember(String meetingId, String userId) {
         return (MeetingMemberDto) redisUtils.hget(Constants.REDIS_KEY_MEETING_ROOM + meetingId,userId);
    }
    public void add2Meeting(String meetingId,MeetingMemberDto meetingMemberDto){
        redisUtils.hset(Constants.REDIS_KEY_MEETING_ROOM + meetingId,meetingMemberDto.getUserId(),meetingMemberDto);
    }
    public List<MeetingMemberDto> getMeetingMemberList(String meetingId){
        List<MeetingMemberDto> meetingMemberDtos = redisUtils.hvals(Constants.REDIS_KEY_MEETING_ROOM + meetingId);
        meetingMemberDtos = meetingMemberDtos.stream().sorted(Comparator.comparing(MeetingMemberDto::getJoinTime)).collect(Collectors.toList());
        return meetingMemberDtos;
    }
    public Boolean exitMeeting(String meetingId, String userId, MeetingMemberStatusEnum statusEnum){
        MeetingMemberDto meetingMemberDto = getMeetingMember(meetingId,userId);
        if (meetingMemberDto == null){
            return false;
        }
        meetingMemberDto.setStatus(statusEnum.getStatus());
        add2Meeting(meetingId,meetingMemberDto);
        return true;
    }

    public void removeAllMeetingMember(String currentMeetingId) {
        List<MeetingMemberDto> meetingMemberDtoList = getMeetingMemberList(currentMeetingId);
        List<String> userIdList = meetingMemberDtoList.stream().map(MeetingMemberDto::getUserId).collect(Collectors.toList());
        if (userIdList.isEmpty()){
            return;
        }
        redisUtils.hdel(Constants.REDIS_KEY_MEETING_ROOM+currentMeetingId,userIdList.toArray(new String[userIdList.size()]));
    }
    public void addInviteInfo(String meetingId, String userId) {
        redisUtils.setex(Constants.REDIS_KEY_INVITE_MEMBER +userId + meetingId,meetingId,Constants.REDIS_KEY_EXPIRES_ONE_MIN*5);
    }
    public String  getInviteInfo(String userId, String meetingId) {
      return (String) redisUtils.get(Constants.REDIS_KEY_INVITE_MEMBER +userId + meetingId);
    }
}

package com.easymeeting.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easymeeting.entity.enums.PageSize;
import com.easymeeting.entity.query.MeetingChatMessageQuery;
import com.easymeeting.entity.po.MeetingChatMessage;
import com.easymeeting.entity.vo.PaginationResultVO;
import com.easymeeting.entity.query.SimplePage;
import com.easymeeting.mappers.MeetingChatMessageMapper;
import com.easymeeting.service.MeetingChatMessageService;
import com.easymeeting.utils.StringTools;


/**
 *  业务接口实现
 */
@Service("meetingChatMessageService")
public class MeetingChatMessageServiceImpl implements MeetingChatMessageService {

	@Resource
	private MeetingChatMessageMapper<MeetingChatMessage, MeetingChatMessageQuery> meetingChatMessageMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<MeetingChatMessage> findListByParam(String tableName,MeetingChatMessageQuery param) {
		return this.meetingChatMessageMapper.selectList(tableName,param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(String tableName,MeetingChatMessageQuery param) {
		return this.meetingChatMessageMapper.selectCount(tableName,param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<MeetingChatMessage> findListByPage(String tableName,MeetingChatMessageQuery param) {
		int count = this.findCountByParam(tableName,param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<MeetingChatMessage> list = this.findListByParam(tableName,param);
		PaginationResultVO<MeetingChatMessage> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(String tableName,MeetingChatMessage bean) {
		return this.meetingChatMessageMapper.insert(tableName,bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(String tableName,List<MeetingChatMessage> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.meetingChatMessageMapper.insertBatch(tableName,listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(String tableName,List<MeetingChatMessage> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.meetingChatMessageMapper.insertOrUpdateBatch(tableName,listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(String tableName,MeetingChatMessage bean, MeetingChatMessageQuery param) {
		StringTools.checkParam(param);
		return this.meetingChatMessageMapper.updateByParam(tableName,bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(String tableName,MeetingChatMessageQuery param) {
		StringTools.checkParam(param);
		return this.meetingChatMessageMapper.deleteByParam(tableName,param);
	}

	/**
	 * 根据MessageId获取对象
	 */
	@Override
	public MeetingChatMessage getMeetingChatMessageByMessageId(String tableName,Long messageId) {
		return this.meetingChatMessageMapper.selectByMessageId(tableName,messageId);
	}

	/**
	 * 根据MessageId修改
	 */
	@Override
	public Integer updateMeetingChatMessageByMessageId(String tableName,MeetingChatMessage bean, Long messageId) {
		return this.meetingChatMessageMapper.updateByMessageId(tableName,bean, messageId);
	}

	/**
	 * 根据MessageId删除
	 */
	@Override
	public Integer deleteMeetingChatMessageByMessageId(String tableName,Long messageId) {
		return this.meetingChatMessageMapper.deleteByMessageId(tableName,messageId);
	}
}
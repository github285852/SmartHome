package com.young.service;

import java.sql.Timestamp;
import java.util.List;

import com.young.annotation.Tran;
import com.young.domain.DoorGuard;

public interface DoorService extends Service {
	/**
	 * 判断记录是否已经存在
	 * 
	 * @param card_id
	 * @return
	 */
	boolean isRecExisted(String card_id);

	/**
	 * 添加门禁记录
	 * 
	 * @param dg
	 */
	void addDoor(DoorGuard dg);

	/**
	 * 查询指定用户 的所有门禁记录
	 * 
	 * @param id
	 * @return 查找到的数据的集合
	 */
	List<DoorGuard> getAllDoorByUserId(int user_id);

	/**
	 * 根据id查找门禁记录
	 * 
	 * @param id
	 * @return 查找到的数据
	 */
	DoorGuard findDoorById(String id);

	/**
	 * 根据卡号查找记录
	 * 
	 * @param card_id
	 * @return
	 */
	DoorGuard findDoorByCardId(String card_id);

	/**
	 * 根据日期查找指定用户的门禁记录
	 * 
	 * @param from_date
	 * @param to_date
	 * @param id
	 * @return 查找到的数据的集合
	 */
	List<DoorGuard> findDoorByDateAndUserId(String from_date, String to_date,
			int id);

	/**
	 * 用户同意开门的操作
	 * 
	 * @param dg
	 */
	@Tran
	void openDoor(String id);

	/**
	 * 用户拒绝开门
	 * 
	 * @param id
	 */
	@Tran
	void refuseToOpen(String id);

	/**
	 * 删除门禁记录
	 * 
	 * @param id
	 */
	void delDoorByID(String id);

	/**
	 * 批量删除门禁记录
	 * 
	 * @param ids
	 */
	void batchDoorDev(String[] ids);

	/**
	 * 更新时间记录
	 * 
	 * @param card_id
	 * @param time
	 */
	void updateTime(String card_id, Timestamp time);
}

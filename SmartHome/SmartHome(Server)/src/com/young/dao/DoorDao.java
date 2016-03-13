package com.young.dao;

import java.sql.Timestamp;
import java.util.List;

import com.young.domain.DoorGuard;

public interface DoorDao extends Dao {
	/**
	 * 判断记录是否存在
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
	 * 查询指定用户所有门禁记录
	 * 
	 * @return
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
	 * 根据日期查找指定用户门禁记录
	 * 
	 * @param from_date
	 * @param to_date
	 * @param id
	 * @return 数据的List集合
	 */
	List<DoorGuard> findDoorByDateAndUserId(String from_date, String to_date,
			int id);

	/**
	 * 根据id删除记录
	 * 
	 * @param id
	 */
	void delDoorByID(String id);

	/**
	 * 改变开门状态
	 * 
	 * @param id
	 * @param i
	 */
	void changeOpenState(String id, int i);

	/**
	 * 改变同意状态
	 * 
	 * @param id
	 * @param i
	 */
	void changeAgreeState(String id, int i);

	/**
	 * 更新访问时间
	 * 
	 * @param card_id
	 */
	void changTime(String card_id, Timestamp time);
}

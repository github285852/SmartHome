package com.young.service;

import java.util.List;

import com.young.annotation.Tran;
import com.young.domain.ExceptionRecord;

public interface RecordService extends Service {
	/**
	 * 添加异常记录
	 * 
	 * @param er
	 */
	void addRec(ExceptionRecord er);

	/**
	 * 查询所有异常记录
	 * 
	 * @return 查找到的数据的List集合
	 */
	List<ExceptionRecord> getAllRec();

	/**
	 * 根据id查找异常记录信息
	 * 
	 * @param id
	 *            异常记录id
	 * @return 查找到的异常记录信息,如果找不到返回null
	 */
	ExceptionRecord findRecById(String id);

	/**
	 * 根据日期查找异常记录
	 * 
	 * @param from_date
	 * @param to_date
	 * @return 查找的数据的集合
	 */
	List<ExceptionRecord> findRecByDate(String from_date, String to_date);

	/**
	 * 根据日期查找指定用户的异常记录
	 * 
	 * @param from_date
	 * @param to_date
	 * @param id
	 * @return 查找的数据的集合
	 */
	List<ExceptionRecord> findRecByDateAndUserId(String from_date,
			String to_date, int id);

	/**
	 * 根据id删除异常记录
	 * 
	 * @param id
	 */
	void delRecByID(String id);

	/**
	 * 批量删除异常记录 其中会进行事务管理
	 * 
	 * @param ids
	 *            所有要删除的id组成的数组
	 */
	@Tran
	void batchDelRec(String[] ids);
}

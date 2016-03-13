package com.young.dao;

import java.util.List;

import com.young.domain.ExceptionRecord;

public interface RecordDao extends Dao {
	/**
	 * 添加异常记录
	 * 
	 * @param er
	 *            要添加的异常记录信息
	 */
	void addRec(ExceptionRecord er);

	/**
	 * 查询所有异常记录信息组成的集合
	 * 
	 * @return 封装了所有异常记录信息的集合,如果没有任何异常记录,返回的集合中内容为空
	 */
	List<ExceptionRecord> getAllRec();

	/**
	 * 根据编号查找异常记录
	 * 
	 * @param id
	 *            编号
	 * @return 根据编号找到的异常记录信息bean,如果没找到返回null
	 */
	ExceptionRecord findRecById(String id);

	/**
	 * 根据日期查找记录
	 * 
	 * @param date
	 * @return 查找到的List集合
	 */
	List<ExceptionRecord> findRecByDate(String from_date, String to_date);

	/**
	 * 根据日期查找指定用户的记录
	 * 
	 * @param from_date
	 * @param to_date
	 * @param id
	 * @return 查找到的List集合
	 */
	List<ExceptionRecord> findRecByDateAndUserId(String from_date,
			String to_date, int id);

	/**
	 * 根据用户信息查找异常记录
	 * 
	 * @param user_id
	 * @return 查找到的List集合
	 */
	public List<ExceptionRecord> findRecByUserId(int user_id);

	/**
	 * 根据id删除记录
	 * 
	 * @param id
	 */
	void delRecByID(String id);
}

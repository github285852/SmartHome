package com.young.dao;

import com.young.domain.User;

public interface UserDao extends Dao {
	/**
	 * 根据用户名查找用户
	 * 
	 * @param username
	 *            用户名
	 * @param conn
	 * @return 查找到的用户,如果找不到返回null
	 */
	User findUserByName(String username);

	/**
	 * 添加用户
	 * 
	 * @param user
	 *            封装了用户信息的bean
	 * @param conn
	 */
	void addUser(User user);

	/**
	 * 根据id删除用户
	 * 
	 * @param id
	 *            要删除的用户id
	 */
	void delUser(int id);

	/**
	 * 根据用户名密码查找用户
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 找到的用户bean
	 */
	User finUserByNameAndPsw(String username, String password);

	/**
	 * 根据id查找用户
	 * 
	 * @param user_id
	 * @return
	 */
	User findUserById(int user_id);

	/**
	 * 根据用户名查找用户是否已经存在
	 * 
	 * @param username
	 * @return
	 */
	boolean isUserExisted(String username);
}

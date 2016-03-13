package com.young.service;

import com.young.domain.User;

public interface UserService extends Service {
	/**
	 * 注册用户
	 * 
	 * @param user
	 *            封装了用户数据的userbean
	 */

	void regist(User user);

	/**
	 * 查找用户是否已经存在
	 * 
	 * @param user
	 * @return
	 */
	boolean isUserExisted(User user);

	/**
	 * 根据用户名密码查找用户
	 * 
	 * @param username
	 * @param password
	 */
	User getUserByNameAndPsw(String username, String password);
}

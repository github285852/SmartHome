package com.young.service;

import com.young.dao.UserDao;
import com.young.domain.User;
import com.young.factory.BasicFactory;

public class UserServiceImpl implements UserService {
	private UserDao dao = BasicFactory.getFactory().getDao(UserDao.class);

	@Override
	public void regist(User user) {
		try {
			dao.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isUserExisted(User user) {
		return dao.isUserExisted(user.getUsername());
	}

	@Override
	public User getUserByNameAndPsw(String username, String password) {
		return dao.finUserByNameAndPsw(username, password);
	}
}

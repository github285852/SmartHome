package com.young.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.young.domain.User;
import com.young.util.TransactionManager;

public class UserDaoImpl implements UserDao {

	@Override
	public User findUserByName(String username) {
		String sql = "select * from users where username = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),
					username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addUser(User user) {
		String sql = "insert into users values(null,?,?,?)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, user.getUsername(), user.getPassword(),
					user.getDoor_id());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delUser(int id) {
		String sql = "delete from users where id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public User finUserByNameAndPsw(String username, String password) {
		String sql = "select * from users where username = ? and password = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),
					username, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findUserById(int user_id) {
		String sql = "select * from users where id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner
					.query(sql, new BeanHandler<User>(User.class), user_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isUserExisted(String username) {
		String sql = "select * from users where username = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<User>(User.class),
					username) != null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}

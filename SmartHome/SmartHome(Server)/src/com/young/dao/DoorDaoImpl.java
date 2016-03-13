package com.young.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.young.domain.DoorGuard;
import com.young.util.TransactionManager;

public class DoorDaoImpl implements DoorDao {
	@Override
	public boolean isRecExisted(String card_id) {
		String sql = "select * from doorguard where card_id = ? for update";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,
					new BeanHandler<DoorGuard>(DoorGuard.class), card_id) != null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addDoor(DoorGuard dg) {
		String sql = "insert into doorguard values (null,?,?,?,?,?)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, dg.getCard_id(), dg.getTime(),
					dg.getOpenstate(), dg.getAgreestate(), dg.getUser_id());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DoorGuard> getAllDoorByUserId(int user_id) {
		String sql = "select * from doorguard where user_id = ? for update";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<DoorGuard>(
					DoorGuard.class), user_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public DoorGuard findDoorById(String id) {
		String sql = "select * from doorguard where id = ? for update";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,
					new BeanHandler<DoorGuard>(DoorGuard.class), id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public DoorGuard findDoorByCardId(String card_id) {
		String sql = "select * from doorguard where card_id = ? for update";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql,
					new BeanHandler<DoorGuard>(DoorGuard.class), card_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DoorGuard> findDoorByDateAndUserId(String from_date,
			String to_date, int id) {
		String sql = "select * from doorguard where user_id = ? and time between ? and ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<DoorGuard>(
					DoorGuard.class), id, from_date, to_date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delDoorByID(String id) {
		String sql = "delete from doorguard where id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void changeOpenState(String id, int i) {
		String sql = "update doorguard set openstate = ? where id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, i, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void changeAgreeState(String id, int i) {
		String sql = "update doorguard set agreestate = ? where id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, i, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void changTime(String card_id, Timestamp time) {
		String sql = "update doorguard set time = ? where card_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, time, card_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

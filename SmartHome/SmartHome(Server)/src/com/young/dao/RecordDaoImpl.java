package com.young.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.young.domain.ExceptionRecord;
import com.young.util.TransactionManager;

public class RecordDaoImpl implements RecordDao {

	@Override
	public void addRec(ExceptionRecord er) {
		String sql = "insert into exceptionrecords values (null,?,null,?)";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, er.getEvent(), er.getUser_id());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ExceptionRecord> getAllRec() {
		String sql = "select * from exceptionrecords";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<ExceptionRecord>(
					ExceptionRecord.class));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public ExceptionRecord findRecById(String id) {
		String sql = "select * from exceptionrecords where id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanHandler<ExceptionRecord>(
					ExceptionRecord.class), id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ExceptionRecord> findRecByDate(String from_date, String to_date) {
		String sql = "select * from exceptionrecords where time between ? and ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<ExceptionRecord>(
					ExceptionRecord.class), from_date, to_date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ExceptionRecord> findRecByDateAndUserId(String from_date,
			String to_date, int id) {
		String sql = "select * from exceptionrecords where user_id = ? and time between ? and ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<ExceptionRecord>(
					ExceptionRecord.class), id, from_date, to_date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ExceptionRecord> findRecByUserId(int user_id) {
		String sql = "select * from exceptionrecords where user_id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			return runner.query(sql, new BeanListHandler<ExceptionRecord>(
					ExceptionRecord.class), user_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delRecByID(String id) {
		String sql = "delete from exceptionrecords where id = ?";
		try {
			QueryRunner runner = new QueryRunner(TransactionManager.getSource());
			runner.update(sql, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}

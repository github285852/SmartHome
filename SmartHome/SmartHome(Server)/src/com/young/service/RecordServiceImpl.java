package com.young.service;

import java.util.List;

import com.young.dao.RecordDao;
import com.young.domain.ExceptionRecord;
import com.young.factory.BasicFactory;

public class RecordServiceImpl implements RecordService {
	RecordDao dao = BasicFactory.getFactory().getDao(RecordDao.class);

	@Override
	public void addRec(ExceptionRecord er) {
		dao.addRec(er);
	}

	@Override
	public List<ExceptionRecord> getAllRec() {
		return dao.getAllRec();
	}

	@Override
	public ExceptionRecord findRecById(String id) {
		return dao.findRecById(id);
	}

	@Override
	public void delRecByID(String id) {
		dao.delRecByID(id);
	}

	@Override
	public List<ExceptionRecord> findRecByDate(String from_date, String to_date) {
		return dao.findRecByDate(from_date, to_date);
	}

	@Override
	public List<ExceptionRecord> findRecByDateAndUserId(String from_date,
			String to_date, int id) {
		return dao.findRecByDateAndUserId(from_date, to_date, id);
	}

	@Override
	public void batchDelRec(String[] ids) {
		for (String id : ids) {
			dao.delRecByID(id);
		}
	}
}

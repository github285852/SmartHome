package com.young.service;

import java.sql.Timestamp;
import java.util.List;

import com.young.dao.DoorDao;
import com.young.domain.DoorGuard;
import com.young.factory.BasicFactory;

public class DoorServiceImpl implements DoorService {
	DoorDao dao = BasicFactory.getFactory().getDao(DoorDao.class);

	@Override
	public boolean isRecExisted(String card_id) {
		return dao.isRecExisted(card_id);
	}

	@Override
	public void addDoor(DoorGuard dg) {
		dao.addDoor(dg);
	}

	@Override
	public List<DoorGuard> getAllDoorByUserId(int user_id) {
		return dao.getAllDoorByUserId(user_id);
	}

	@Override
	public DoorGuard findDoorById(String id) {
		return dao.findDoorById(id);
	}

	@Override
	public DoorGuard findDoorByCardId(String card_id) {
		return dao.findDoorByCardId(card_id);
	}

	@Override
	public List<DoorGuard> findDoorByDateAndUserId(String from_date,
			String to_date, int id) {
		return dao.findDoorByDateAndUserId(from_date, to_date, id);
	}

	@Override
	public void openDoor(String id) {
		dao.changeOpenState(id, 1);
		dao.changeAgreeState(id, 2);
	}

	@Override
	public void refuseToOpen(String id) {
		dao.changeOpenState(id, 0);
		dao.changeAgreeState(id, 1);
	}

	@Override
	public void delDoorByID(String id) {
		dao.delDoorByID(id);
	}

	@Override
	public void batchDoorDev(String[] ids) {
		for (String id : ids) {
			dao.delDoorByID(id);
		}
	}

	@Override
	public void updateTime(String card_id, Timestamp time) {
		dao.changTime(card_id, time);
	}
}

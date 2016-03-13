package com.young.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.young.factory.BasicFactory;
import com.young.service.DoorService;
import com.young.service.RecordService;
import com.young.service.UserService;

public class UserTest {
	UserService userService = BasicFactory.getFactory().getService(
			UserService.class);
	RecordService recordService = BasicFactory.getFactory().getService(
			RecordService.class);
	DoorService doorService = BasicFactory.getFactory().getService(
			DoorService.class);

	//
	// public void testRegist() {
	//
	// User user = new User();
	// user.setUsername("admin");
	// user.setPassword(MD5Utils.md5("admin"));
	// user.setDoor_id("123456");
	// userService.regist(user);
	// }

	// @Test
	// public void RecordTest() {
	// for (int i = 0; i < 20; i++) {
	// ExceptionRecord er = new ExceptionRecord();
	// er.setEvent("»ðÔÖ");
	// er.setUser_id(1);
	// recordService.addRec(er);
	// }
	// for (int i = 0; i < 20; i++) {
	// ExceptionRecord er = new ExceptionRecord();
	// er.setEvent("»ðÔÖ");
	// er.setUser_id(1);
	// Timestamp time = Timestamp.valueOf("2016-02-29 11:19:30.0");
	// er.setTime(time);
	// recordService.addRec(er);
	// }
	// for (int i = 0; i < 20; i++) {
	// ExceptionRecord er = new ExceptionRecord();
	// er.setEvent("µØÕð");
	// er.setUser_id(1);
	// Timestamp time = Timestamp.valueOf("2016-03-01 11:19:30.0");
	// er.setTime(time);
	// recordService.addRec(er);
	// }
	// }

	// @Test
	// public void testDoorGuard() {
	// for (int i = 0; i < 20; i++) {
	// DoorGuard dg = new DoorGuard();
	// dg.setCard_id("12345");
	// dg.setOpenstate(0);
	// dg.setAgreestate(0);
	// dg.setUser_id(1);
	// doorService.addDoor(dg);
	// }
	// }
	// @Test
	// public void testCipher() {
	// try {
	// String Code = "¸ÇÊÀÓ¢ÐÛ";
	// String key = "1q2w3e4r";
	// String codE;
	// codE = AESUtils.encrypt(key, Code);
	//
	// System.out.println("Ô­ÎÄ£º" + Code);
	// System.out.println("ÃÜÔ¿£º" + key);
	// System.out.println("ÃÜÎÄ£º" + codE);
	//
	// System.out.println("½âÃÜ£º" + AESUtils.decrypt(key, codE));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	@Test
	public void testStr() {
		String str = "fjdnsfi22.82alsdg33.453ldkg";
		Pattern p = Pattern.compile("[0-9]+\\.[0-9]+");
		Matcher m = p.matcher(str);
		while (m.find()) {
			String Double = m.group(0);
			System.out.print(Double);
		}
	}
}

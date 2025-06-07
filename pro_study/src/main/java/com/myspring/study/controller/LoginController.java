package com.myspring.study.controller;

import com.myspring.study.service.UserService;
import com.myspring.study.vo.UserVO;

public class LoginController {
	
	private UserService userService = new UserService();
	
	public void login(String username, String password) {
		UserVO user = new UserVO();
		user.setUsername(username);
		user.setPassword(password);
		
		boolean result = userService.authenticate(user);
		
		if(result) {
			System.out.println("�α��� ����");
		} else {
			System.out.println("�α��� ����");
		}
	}
}

/*

Controller: Ŭ���̾�Ʈ ��û�� �ް�, �˸��� Service�� ȣ���� �� ����� View�� ����

-���� ��൵
Client (View)
     ��
Controller �� Service �� DAO �� DB
			 ��
			 VO(������ ��ü)

*/
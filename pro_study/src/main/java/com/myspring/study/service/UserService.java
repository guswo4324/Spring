package com.myspring.study.service;

import com.myspring.study.dao.UserDAO;
import com.myspring.study.vo.UserVO;

public class UserService {
	
	private UserDAO userDAO = new UserDAO();
	
	public boolean authenticate(UserVO user) {
		return userDAO.login(user); // DAO ȣ��
	}
}

/*

Service: �����Ͻ� ���� ó��, ���� DAO ���� ����, Ʈ����� ���� �� ����

*/
package com.myspring.study.dao;

import com.myspring.study.vo.UserVO;

public class UserDAO {
	
	public boolean login(UserVO user) {
		
		if("admin".equals(user.getUsername()) && "1234".equals(user.getPassword())) {
			return true;
		}
		
		return false;
	}
}

/*

DAO(Data Access Object): DB�� ���� ����ϸ� CRUD ����, SQL ������ �����

-CRUD: ������ ó���� �⺻ �� ���� ����� �ǹ�
C(Create): ������ ����(��: ȸ������, �Խñ� �ۼ� ��)
R(Read): ������ ��ȸ(��: ȸ�� ��� ����, �Խñ� �б� ��)
U(Update): ������ ����(��: ȸ�� ���� ����, �� ���� ���� ��)
D(Delete): ������ ����(��: �Խñ� ����, ȸ�� Ż�� ��) 

*/
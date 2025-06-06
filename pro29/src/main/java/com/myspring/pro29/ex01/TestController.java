package com.myspring.pro29.ex01;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/*")
public class TestController {
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	// /hello�� ��û �� �������� ���ڿ��� ����
	@RequestMapping("/hello")
	public String hello() {
		return "Hello REST!!";
	}
	
	// MemberVO ��ü�� �Ӽ� ���� ������ �� JSON���� ����
	@RequestMapping("/member")
	public MemberVO member() {
		MemberVO vo = new MemberVO();
		vo.setId("dooly");
		vo.setPwd("1212");
		vo.setName("�Ѹ�");
		vo.setEmail("dooly@test.com");
		return vo;
	}
	
	// MemberVO ��ü�� ������ ArrayList ��ü�� ����
	@RequestMapping("/membersList")
	public List<MemberVO> listMembers() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		for (int i = 0; i < 10; i++) { 
			MemberVO vo = new MemberVO(); // MemberVO ��ü�� 10�� ������ ArrayList�� ����
			vo.setId("dooly" + i);
			vo.setPwd("123" + i);
			vo.setName("�Ѹ�" + i);
			vo.setEmail("dooly" + i + "@test.com");
			list.add(vo);
		}
		return list;
	}

	@RequestMapping("/membersMap")
	public Map<Integer, MemberVO> membersMap() {
		Map<Integer, MemberVO> map = new HashMap<Integer, MemberVO>();
		for (int i = 0; i < 10; i++) {
			MemberVO vo = new MemberVO();
			vo.setId("dooly" + i + i);
			vo.setPwd("123" + i + i);
			vo.setName("�Ѹ�" + i + i);
			vo.setEmail("dooly" + i + i + "@test.com");
			map.put(i, vo);
		}
		return map;
	}
	
	// ������ ��û �� {num} �κ��� ���� @PathVariable�� ����
	// @PathVariable("num") -> ��û URL���� ������ ���� num�� �ڵ����� �Ҵ�
	// ex) notice/112�� ������ ��� 112�� num�� �Ҵ�
	@RequestMapping(value = "/notice/{num}", method = RequestMethod.GET)
	public int notice(@PathVariable("num") int num) throws Exception {
		return num;
	}
	
	// REST�� Ajax ��ɰ� �����ؼ� ���� ���,
	// ���������� JSON �����͸� ��Ʈ�ѷ��� ������ �� ��Ʈ�ѷ����� JSON�� ��ü�� ��ȯ�ϴ� ��� ����
	
	// @RequestBody�� ����ϸ� ���������� ���޵Ǵ� JSON �����͸� ��ü�� �ڵ� ��ȯ
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	// JSON���� ���۵� �����͸� MemberVO ��ü�� �Ӽ��� �ڵ����� ����
	public void modify(@RequestBody MemberVO vo) {
		System.out.println("===================1");
		logger.info(vo.toString());
		System.out.println("===================2");
	}
	
	// @RestController�� ������ view�� �������� ���� ä �����͸� �����ϹǷ� ���� �������� ���ܰ� �߻��� �� �ִ�.
	// ���ܿ� ���� �� �� ������ ��� �ʿ��� ��� ResponseEntity Ŭ������ ���
	// ResponseEntity�� ����
	@RequestMapping("/membersList2")
	public ResponseEntity<List<MemberVO>> listMembers2() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		for (int i = 0; i < 10; i++) {
			MemberVO vo = new MemberVO();
			vo.setId("dooly" + i);
			vo.setPwd("123" + i);
			vo.setName("�Ѹ�" + i);
			vo.setEmail("dooly" + i + "@test.com");
			list.add(vo);
		}
		
		// �ַ� ���� ���
		// OK(200), BAD_REQUEST(400), UNAUTHORIZED(401), 
		// NOT_FOUND(404), INTERNAL_SERVER_ERROR(500)
		
		// return new ResponseEntity(list, HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity(list, HttpStatus.OK);
	}
	
	// ResponseEntity�� ������ �������� ������ �ѱ� ���ڵ� ����
	@RequestMapping(value = "/res3")
	public ResponseEntity res3() {
		HttpHeaders responseHeaders = new HttpHeaders();
		
		// ������ �������� ������ ���ڵ� ����
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		// ������ �ڹٽ�ũ��Ʈ�ڵ带 ���ڿ��� �ۼ�
		String message = "<script>";
		message += " alert('�� ȸ���� ����մϴ�');";
		message += " location.href='/pro29/test/membersList2'; ";
		message += " </script>";
		
		// ResponseEntity�� �̿��� HTML �������� ����
		return new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	}
}
package com.myspring.pro30.board.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.board.service.BoardService;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.member.vo.MemberVO;

//controller : ��û�޴� ��

@Controller("boardController")
public class BoardControllerImpl implements BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private ArticleVO articleVO;

	@Override
	@RequestMapping(value = "/board/listArticles.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listArticles(
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		// viewName : listArticle
		// ���ͼ��Ϳ��� ���޵� ���̸��� ������
		String viewName = (String) request.getAttribute("viewName");
		System.out.println("=============BoardControllerImpl listArticles viewName:" + viewName);
		
		// ��� �� ������ ��ȸ
		List articlesList = boardService.listArticles();
		ModelAndView mav = new ModelAndView(viewName);
		
		// ��ȸ�� �� ������ ���ε��� �� JSP�� ����
		mav.addObject("articlesList", articlesList);
		return mav;
	}

	@Override
	@RequestMapping(value = "/board/addNewArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(
			MultipartHttpServletRequest multipartRequest, 
			HttpServletResponse response) throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		
		// �� ������ �����ϱ� ���� articleMap�� ����
		Map<String, Object> articleMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		
		// �۾���â���� ���۵� �� ������ Map�� key/value�� ����
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}

		// �̹��� ���� ���ϹǷ� ���ϸ� �κ��� ������
		// String imageFileName= upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		
		// ���ǿ� ����� ȸ�������κ��� ȸ�� ID�� ������
		String id = memberVO.getId();
		
		// ȸ�� ID, �θ� �� ��ȣ�� articleMap�� ����
		articleMap.put("parentNO", 0);
		articleMap.put("id", id);
		// �̹��� ���� ���ϹǷ� ���ϸ� �κ��� ������
		// articleMap.put("imageFileName", imageFileName);

		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			
			// �� ������ ����� articleMap�� Service Ŭ������ addArticle() �޼���� ����
			int articleNO = boardService.addNewArticle(articleMap);
			// �̹��� ���� ���ϹǷ� ���ϸ� �κ��� ������
//			if(imageFileName!=null && imageFileName.length()!=0) {
//				File srcFile = new 
//				File(ARTICLE_IMAGE_REPO+ "\\" + "temp"+ "\\" + imageFileName);
//				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
//				FileUtils.moveFileToDirectory(srcFile, destDir,true);
//			}
			
			// �� ������ �߰��� �� ���ε��� ���� �� ��ȣ�� ����� ������ �̵�
			message = "<script>";
			message += " alert('������ �߰��߽��ϴ�.');";
			message += " location.href='" + multipartRequest.getContextPath() + "/board/listArticles.do'; ";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {

			// File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			// srcFile.delete();
			
			// ���� �߻� �� ���� �޽����� ����
			message = " <script>";
			message += " alert('������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');');";
			message += " location.href='" + multipartRequest.getContextPath() + "/board/articleForm.do'; ";
			message += " </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	// �۾���â�� ��Ÿ��
	@RequestMapping(value = "/board/*Form.do", method = { RequestMethod.GET, RequestMethod.POST })
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//viewName : articleForm
		String viewName = (String) request.getAttribute("viewName");
		System.out.println("=============BoardControllerImpl form viewName:" + viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

}
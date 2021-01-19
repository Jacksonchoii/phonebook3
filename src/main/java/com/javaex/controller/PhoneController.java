package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller
@RequestMapping(value="/phone")
public class PhoneController {

	//필드
	//생성자
	//메소드g/s
	
	/**메소드 일반** 메소드 일반(메소드마다 기능 1개씩) --> 기능마다 url 부여**/
	//리스트
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("list");
		
		//dao를 통해 리스트 가져오기
		PhoneDao phoneDao = new PhoneDao();
		List<PersonVo> personList = phoneDao.getPersonList();
		System.out.println(personList.toString());
		
		//model --> data 를 보내는 방법 --> 담아 놓으면 된다
		model.addAttribute("pList", personList);
		
		
		return "/WEB-INF/views/list.jsp";
	}
	
	//등록폼
	@RequestMapping(value = "/writeForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String writeForm() {
		System.out.println("writeForm");
		
		return "/WEB-INF/views/writeForm.jsp";
	}	
	 //http://localhost:8088/phonebook3/phone/write?name=최태현&hp=010-1111-1111&company=02-2222-2222
	//등록
	@RequestMapping(value = "/write", method = {RequestMethod.GET, RequestMethod.POST})
	public String write(@RequestParam("name") String name, 
						@RequestParam("hp") String hp, 
						@RequestParam("company") String company) {
		
		System.out.println("write");
		
		//vo 묶고 확인
		PersonVo personVo = new PersonVo(name, hp, company);
		System.out.println(personVo.toString());
		
		//Dao에 묶은 vo
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personInsert(personVo);
		
		return "redirect:/phone/list";
	}
	
	//수정폼 --> modifyForm
	@RequestMapping(value = "/modifyForm", method = {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(Model model, @RequestParam("id") int personId) { //일단은 되는데 저렇게 쓰는게 맞나 확인
		System.out.println("modifyForm");
		
		//id로 정보 가져오기
		PhoneDao phoneDao = new PhoneDao();
		PersonVo personVo = phoneDao.getPerson(personId);
		
		//model로 보내줌 personVo를 -->model
		model.addAttribute("pvo", personVo);
		
		//포워드
		return "/WEB-INF/views/modifyForm.jsp";
	}

	//수정 --> modify
	@RequestMapping(value = "/modify", method = {RequestMethod.GET, RequestMethod.POST})
	public String modify(@RequestParam("name") String name,
						 @RequestParam("hp") String hp,
						 @RequestParam("company") String company,
						 @RequestParam("id") int personId) {
							
		System.out.println("modify");
		
		//Vo 묶고
		PersonVo personVo = new PersonVo(personId, name, hp, company);
		System.out.println(personVo.toString());
		
		//Dao
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personUpdate(personVo);
		
		return "redirect:/phone/list";
	}
	
	//삭제 --> delete
	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("id") int personId) {
		System.out.println("delete");
		
		//dao
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personDelete(personId);
		
		return "redirect:/phone/list";
	}

	
}

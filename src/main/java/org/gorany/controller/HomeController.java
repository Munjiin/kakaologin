package org.gorany.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gorany.domain.UserVO;
import org.gorany.service.KakaoLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.java.Log;

@Controller
@Log
public class HomeController {

	
	@GetMapping("/login")
	public void login() {
		
	}
	
	@RequestMapping(value = "/kakaologin" , produces = "application/json", method = {RequestMethod.GET, RequestMethod.POST})
	public String kakaoLogin(@RequestParam("code") String code , HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception{

	  JsonNode token = KakaoLogin.getAccessToken(code);
	  log.info("=====" + code);
	  log.info("=====" + token);
	  
	  JsonNode profile = KakaoLogin.getKakaoUserInfo(token.path("access_token").toString());
	  System.out.println("profile: " + profile);
	  
	  UserVO vo = KakaoLogin.changeData(profile);
	  vo.setUser_snsId("k"+vo.getUser_snsId());
	  vo.setUser_email("k"+vo.getUser_email());

	  System.out.println("session: " + session);
	  session.setAttribute("login", vo);
	  System.out.println(vo.toString());

	  return "login/kakaoLogin";
	}
}

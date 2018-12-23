package com.sims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sims.model.User;
import com.sims.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	/**  
	 * <p>Title: main</p>  
	 * <p>Description: </p>  
	 * @param response
	 * @return
	 * @throws Exception  
	 */  
	@RequestMapping("/in")
	public String in(HttpServletRequest request,HttpServletResponse response,User user) throws Exception {
		User resultUser=userService.login(user);
		ModelMap model=new ModelMap();
		if(resultUser==null){
			request.setAttribute("errorMsg", "用户名或密码错误");
			model.addAttribute("errorMsg", "用户名或密码错误");
			logger.error("用户名{}或密码错误",user.getUsername());
			return "index";
		}else{
			HttpSession session=request.getSession();
			session.setAttribute("currentUser", resultUser);
			return "redirect:/main.jsp";
		}
	}
	
}

package com.sims.controller.admin;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	/**  
	 * <p>Title: main</p>  
	 * <p>Description: </p>  
	 * @param response
	 * @return
	 * @throws Exception  
	 */  
	@RequestMapping("/main")
	public String main(HttpServletResponse response) throws Exception {
		return "admin/main";
	}
}

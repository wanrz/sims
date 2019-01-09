package com.sims.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/print") 
public class PrintController {
	@RequestMapping("/page")
	public String page(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return "print";
	}
}

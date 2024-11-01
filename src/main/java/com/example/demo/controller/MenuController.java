package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * メニューコントローラー
 * 
 * @author syuto
 */

@Controller
public class MenuController {
	
	@GetMapping("/menu")
	public String view() {
		return "menu";
	}
	
}

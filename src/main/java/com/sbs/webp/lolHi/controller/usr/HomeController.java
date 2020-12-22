package com.sbs.webp.lolHi.controller.usr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {
	@RequestMapping("/")
	public String showMain_() {
		return "redirect:/usr/home/main";
	}
	
	@RequestMapping("/usr/home/main")
	public String showMain() {
		return "/usr/home/main";
	}

	@RequestMapping("/usr/home/plus")
	@ResponseBody
	public int showPlus(int a, int b) {
		return a + b;
	}

	@RequestMapping("/usr/home/minus")
	@ResponseBody
	public int showMinus(int a, int b) {
		return a - b;
	}

	@RequestMapping("/usr/home/muliplication")
	@ResponseBody
	public int showMultiplication(int a, int b) {
		return a * b;
	}

	@RequestMapping("/usr/home/division")
	@ResponseBody
	public int showDivision(int a, int b) {
		return a / b;
	}
}

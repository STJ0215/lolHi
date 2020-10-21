package com.sbs.webp.lolHi.controller.usr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {
	@RequestMapping("/usr/home/main")
	@ResponseBody
	public String showMain() {
		return "안녕";
	}

	@RequestMapping("/usr/home/main2")
	@ResponseBody
	public String showMain2() {
		return "개";
	}

	@RequestMapping("/usr/home/main3")
	@ResponseBody
	public String showMain3() {
		return "고양이";
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

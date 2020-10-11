package com.cbbs.funny.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cbbs.funny.api.service.FunnyService;

// 1
@RestController
public class FunnyController {
	private final FunnyService funnyService;

	@Autowired
	public FunnyController(FunnyService funnyService) {
		this.funnyService = funnyService;
	}
	
	// 2
	@GetMapping("/funny/funnyStr={funnyStr}")
	public String getFunnyStr(@PathVariable String funnyStr) {
		if (funnyStr.isEmpty()) {
			return "";
		}
		
		String result;
		try {
			result = this.funnyService.doFunny(funnyStr);
		} catch (IllegalArgumentException exception) {
			result = "Invalid input text.";
		}

		return result;
	}
}

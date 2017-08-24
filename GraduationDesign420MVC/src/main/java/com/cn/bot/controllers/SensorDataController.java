package com.cn.bot.controllers;


import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Controller
@RequestMapping("/sensor")
public class SensorDataController {
	@RequestMapping(value="/getSensorData", method=RequestMethod.GET)
	@ResponseBody
	public String getSensorData(){
		int humi;
		int temp;
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String humiTemp = (String) servletContext.getAttribute("humiTemp");
		if(humiTemp != null){
			humi = Integer.parseInt(humiTemp.split(" ")[3].split("00")[0], 16);
			temp = Integer.parseInt(humiTemp.split(" ")[5].split("00")[0], 16);
			return temp+";"+humi;
		}
		else{
			humi = 0;
			temp = 0;
			return humi+";"+temp;
		}
	}
}

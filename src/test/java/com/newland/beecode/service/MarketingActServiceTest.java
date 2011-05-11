package com.newland.beecode.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.exception.AppException;
import com.newland.beecode.service.MarketingActService;



@ContextConfiguration(locations = {"/applicationContext.xml","/applicationContext-service.xml","/applicationContext-sms-service.xml","/applicationContext-mms-service.xml","/applicationContext_ExcelFile.xml"}) 
public class MarketingActServiceTest extends AbstractJUnit4SpringContextTests{
	@Resource(name = "marketingActService")
	private MarketingActService marketingActService;
	
	@Test 
	public void testCheckMarketingAct(){
		try {
			System.out.println(this.marketingActService.checkMarketingAct(new Long (708), 3));
		} catch (AppException e) {
			System.out.println("erro");
		}
	}
	
}

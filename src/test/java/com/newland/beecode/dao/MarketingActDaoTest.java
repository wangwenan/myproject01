package com.newland.beecode.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.newland.beecode.domain.MarketingAct;

@ContextConfiguration(locations = "/applicationContext.xml") 
public class MarketingActDaoTest extends AbstractJUnit4SpringContextTests{
	@Resource(name = "marketingActDao")
    private MarketingActDao actDao;
    @Test
    public void updateTest(){
    	System.out.println(actDao);
    	MarketingAct act = actDao.findMarketingAct(new Long (1111));
    	act.setActName("sssbbb");
    	actDao.update(act);
    	System.out.println("11111111111");
    }

}

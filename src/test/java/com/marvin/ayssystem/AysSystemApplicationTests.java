package com.marvin.ayssystem;

import com.marvin.utils.HttpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AysSystemApplicationTests {


	@Test
	void contextLoads() {
		HttpUtils httpUtils = new HttpUtils();
		String info = httpUtils.doGetHtml("https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=%E7%94%B5%E5%BD%B1&year_range=2020,2020&start=20");
		System.out.println(info);
		test();




	}

	@Test
	void test(){

	}

}

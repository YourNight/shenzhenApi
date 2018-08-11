package com.bonc.shenzhen;

import com.bonc.shenzhen.util.HttpPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShenzhenInterfaceApplicationTests {

	@Test
	public void contextLoads() {
		try {
			String s = HttpPost.doPost("http://127.0.0.1:8080/restApi/hello/44", "{\"thing\":\"asdasd\"}");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void contextLoads1() {
		try {
			String s = HttpPost.doPost("http://127.0.0.1:8080/restApi/getparam","df");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

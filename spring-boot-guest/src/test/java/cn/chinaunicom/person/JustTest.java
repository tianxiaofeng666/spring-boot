package cn.chinaunicom.person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JustTest {

    @Test
    public void testHello() {
        System.out.println("testHello");
        String h = "hello";
        assertEquals(h, "hello");
    }
}
package com.swz637;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class ExpirationDateApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(UUID.randomUUID().toString().substring(0, 8));
    }

}

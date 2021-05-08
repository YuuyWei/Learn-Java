package com.yuuy.spring.app;

import com.yuuy.spring.app.services.UserService;
import com.yuuy.spring.framework.MyApplicationContext;

public class Application {
    public static void main(String[] args) {
        MyApplicationContext myApplicationContext = new MyApplicationContext(AppConfig.class);

        System.out.println(myApplicationContext.getBean("userService"));
        System.out.println(myApplicationContext.getBean("userService"));
        System.out.println(myApplicationContext.getBean("userService"));
    }
}

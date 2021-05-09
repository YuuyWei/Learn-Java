package com.yuuy.spring.app;

import com.yuuy.spring.app.services.OrderService;
import com.yuuy.spring.app.services.UserService;
import com.yuuy.spring.framework.MyApplicationContext;

public class Application {
    public static void main(String[] args) {
        MyApplicationContext myApplicationContext = new MyApplicationContext(AppConfig.class);

        System.out.println(myApplicationContext.getBean("orderService"));
        UserService userService = (UserService) myApplicationContext.getBean("userService");
        userService.serve();
        System.out.println("userService.getMessage() = " + userService.getMessage());
    }
}

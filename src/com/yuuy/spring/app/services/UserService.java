package com.yuuy.spring.app.services;

import com.yuuy.spring.framework.*;

@Component("userService")
//@Scope("prototype")
public class UserService implements InitializeBean, BeanNameAware{

    @Autowired
    private OrderService orderService;

    private String beanName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public void serve() {
        System.out.println(orderService);
        System.out.println(beanName);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet");
    }
}

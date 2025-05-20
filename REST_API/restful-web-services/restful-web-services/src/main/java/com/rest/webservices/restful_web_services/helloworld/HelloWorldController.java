package com.rest.webservices.restful_web_services.helloworld;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.ResourceBundle;

@RestController
public class HelloWorldController {

    MessageSource messageSrc;

    public HelloWorldController(MessageSource messageSrc) {
        this.messageSrc = messageSrc;
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping("/hello-world-path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean("Hello World " + name);
    }


    /*Internationalization*/
    @GetMapping("/hello-world-Internationalized")
    public String helloWorldInternationalized() {

        Locale locale = LocaleContextHolder.getLocale();
         return messageSrc.getMessage("good.morning.message",null,"default message",locale);
    }


}

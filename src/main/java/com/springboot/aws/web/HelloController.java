package com.springboot.aws.web;

import com.springboot.aws.domain.user.Role;
import com.springboot.aws.domain.user.User;
import com.springboot.aws.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name")String name,
                                     @RequestParam("amount")int amount){
        return new HelloResponseDto(name,amount);

    }

}

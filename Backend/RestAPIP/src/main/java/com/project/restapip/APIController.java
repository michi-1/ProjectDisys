package com.project.restapip;
import org.springframework.web.bind.annotation.*;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@RestController
public class APIController {
Send s1 = new Send();
    @PostMapping("/post/{id}")
    public String post(@RequestBody String id2, @PathVariable String id) {
        System.out.println(id2);
        s1.sender(id);
        return "Hello, " + id + "!";
    }
    @GetMapping("/get/{id}")
    public String get(@PathVariable String id) {
        System.out.println(id);
        return "Hello, " + id + "!";
    }
}

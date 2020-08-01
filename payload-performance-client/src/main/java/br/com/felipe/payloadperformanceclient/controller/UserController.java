package br.com.felipe.payloadperformanceclient.controller;

import br.com.felipe.payloadperformanceclient.client.UserClient;
import br.com.felipe.payloadperformanceclient.controller.response.User;
import br.com.felipe.payloadperformanceclient.controller.response.UserV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserClient client;

    @GetMapping(path = "/v1/users")
    List<User> getUsers() {
        return client.getUsers();
    }

    @GetMapping(path = "/v2/users")
    List<UserV2> getUsersV2() {
        return client.getUsersV2();
    }
}

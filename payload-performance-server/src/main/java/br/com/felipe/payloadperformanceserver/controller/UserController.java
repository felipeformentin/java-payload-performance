package br.com.felipe.payloadperformanceserver.controller;

import br.com.felipe.payloadperformanceserver.controller.contract.response.User;
import br.com.felipe.payloadperformanceserver.controller.contract.response.UserV2;
import br.com.felipe.payloadperformanceserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(path = "/v1/users")
    List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping(path = "/v2/users")
    List<UserV2> getUsersV2() {
        return service.getUsersV2();
    }

}

package br.com.felipe.payloadperformanceserver.service;

import br.com.felipe.payloadperformanceserver.controller.contract.response.User;
import br.com.felipe.payloadperformanceserver.controller.contract.response.UserV2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final List<User> users;
    private static final List<UserV2> usersV2;

    static {
        users = new ArrayList<>();
        usersV2 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            users.add(User.builder().build());
            usersV2.add(UserV2.builder().build());
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public List<UserV2> getUsersV2() {
        return usersV2;
    }
}

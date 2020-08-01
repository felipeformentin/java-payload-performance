package br.com.felipe.payloadperformanceclient.client;

import br.com.felipe.payloadperformanceclient.controller.response.User;
import br.com.felipe.payloadperformanceclient.controller.response.UserV2;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "userClient", url = "localhost:8081")
public interface UserClient {

    @GetMapping(path = "/v1/users")
    List<User> getUsers();

    @GetMapping(path = "/v2/users")
    List<UserV2> getUsersV2();
}

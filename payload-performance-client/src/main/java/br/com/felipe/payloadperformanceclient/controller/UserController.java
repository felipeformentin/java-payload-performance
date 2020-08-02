package br.com.felipe.payloadperformanceclient.controller;

import br.com.felipe.payloadperformanceclient.client.UserClient;
import br.com.felipe.payloadperformanceclient.controller.response.User;
import br.com.felipe.payloadperformanceclient.controller.response.UserV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {

    @Autowired
    private UserClient client;

    @GetMapping(path = "/v1/users")
    List<User> getUsers() throws ExecutionException, InterruptedException {
        CompletableFuture<List<User>> cf1 = CompletableFuture.supplyAsync(() -> client.getUsers());
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> client.getUsers());
        CompletableFuture<Void> cf3 = CompletableFuture.runAsync(() -> client.getUsers());
        CompletableFuture.allOf(cf1, cf2, cf3).get();
        return cf1.get();
    }

    @GetMapping(path = "/v2/users")
    List<UserV2> getUsersV2() throws ExecutionException, InterruptedException {
        CompletableFuture<List<UserV2>> cf1 = CompletableFuture.supplyAsync(() -> client.getUsersV2());
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> client.getUsersV2());
        CompletableFuture<Void> cf3 = CompletableFuture.runAsync(() -> client.getUsersV2());
        CompletableFuture.allOf(cf1, cf2, cf3).get();
        return cf1.get();
    }
}

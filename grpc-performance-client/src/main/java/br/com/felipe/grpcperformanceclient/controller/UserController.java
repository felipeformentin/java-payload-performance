package br.com.felipe.grpcperformanceclient.controller;

import br.com.felipe.grpcperformanceclient.GrpcPerformanceClientApplication;
import br.com.felipe.payloadperformanceserver.grpc.User;
import br.com.felipe.payloadperformanceserver.grpc.UserResponse;
import com.google.protobuf.util.JsonFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private GrpcPerformanceClientApplication grpcPerformanceClientApplication = new GrpcPerformanceClientApplication();

    @GetMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse user() {
        return grpcPerformanceClientApplication.getUser();
    }

}

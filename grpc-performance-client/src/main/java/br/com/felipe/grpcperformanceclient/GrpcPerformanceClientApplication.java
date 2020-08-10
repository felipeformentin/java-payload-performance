package br.com.felipe.grpcperformanceclient;


import br.com.felipe.payloadperformanceserver.grpc.Empty;
import br.com.felipe.payloadperformanceserver.grpc.UserResponse;
import br.com.felipe.payloadperformanceserver.grpc.UserServiceGrpc;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.jackson.datatype.protobuf.ProtobufModule;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Configuration
public class GrpcPerformanceClientApplication {

	public static UserServiceGrpc.UserServiceBlockingStub blockingStub;

	public UserResponse getUser() {
		Empty request = Empty.newBuilder().build();
		UserResponse response;
		try {
			response = blockingStub.getUsers(request);
		} catch (StatusRuntimeException e) {
			return null;
		}
		return response;
	}


	public static void main(String[] args) throws Exception {
		SpringApplication.run(GrpcPerformanceClientApplication.class, args);
		// Access a service running on the local machine on port 50051
		String target = "localhost:50051";

		// Create a communication channel to the server, known as a Channel. Channels are thread-safe
		// and reusable. It is common to create channels at the beginning of your application and reuse
		// them until the application shuts down.
		ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
				// Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
				// needing certificates.
				.usePlaintext()
				.build();
		blockingStub = UserServiceGrpc.newBlockingStub(channel);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new ProtobufModule());
		return mapper;
	}

	@Bean
	@Primary
	ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufJsonFormatHttpMessageConverter();
	}


}

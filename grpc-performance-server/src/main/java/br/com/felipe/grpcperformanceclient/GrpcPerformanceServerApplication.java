package br.com.felipe.grpcperformanceclient;


import br.com.felipe.payloadperformanceserver.grpc.Empty;
import br.com.felipe.payloadperformanceserver.grpc.User;
import br.com.felipe.payloadperformanceserver.grpc.UserResponse;
import br.com.felipe.payloadperformanceserver.grpc.UserServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GrpcPerformanceServerApplication {
    
	private Server server;

	private void start() throws IOException {
		/* The port on which the server should run */
		int port = 50051;
		server = ServerBuilder.forPort(port)
				.addService(new UserImpĺ())
				.build()
				.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				try {
                    GrpcPerformanceServerApplication.this.stop();
				} catch (InterruptedException e) {
					e.printStackTrace(System.err);
				}
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() throws InterruptedException {
		if (server != null) {
			server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	/**
	 * Main launches the server from the command line.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		final GrpcPerformanceServerApplication server = new GrpcPerformanceServerApplication();
		server.start();
		server.blockUntilShutdown();
	}

	static class UserImpĺ extends UserServiceGrpc.UserServiceImplBase {

		private final static UserResponse response;

		static {
			List<User> users = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				users.add(User.newBuilder().build());
			}
			response = UserResponse.newBuilder().addAllUser(users).build();
		}

		@Override
		public void getUsers(Empty req, StreamObserver<UserResponse> responseObserver) {
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}
	}

}

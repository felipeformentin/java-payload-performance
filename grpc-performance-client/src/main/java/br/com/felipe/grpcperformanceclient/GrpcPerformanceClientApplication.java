package br.com.felipe.grpcperformanceclient;


import br.com.felipe.payloadperformanceserver.grpc.Empty;
import br.com.felipe.payloadperformanceserver.grpc.UserResponse;
import br.com.felipe.payloadperformanceserver.grpc.UserServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcPerformanceClientApplication {


	private final UserServiceGrpc.UserServiceBlockingStub blockingStub;

	/** Construct client for accessing HelloWorld server using the existing channel. */
	public GrpcPerformanceClientApplication(Channel channel) {
		// 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
		// shut it down.

		// Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
		blockingStub = UserServiceGrpc.newBlockingStub(channel);
	}

	public void getUser() {
		Empty request = Empty.newBuilder().build();
		UserResponse response;
		try {
			response = blockingStub.getUsers(request);
		} catch (StatusRuntimeException e) {
			return;
		}
	}


	public static void main(String[] args) throws Exception {
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
		try {
            GrpcPerformanceClientApplication client = new GrpcPerformanceClientApplication(channel);
			client.getUser();
		} finally {
			// ManagedChannels use resources like threads and TCP connections. To prevent leaking these
			// resources the channel should be shut down when it will no longer be used. If it may be used
			// again leave it running.
			channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
		}
	}

}

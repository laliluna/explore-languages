package sample.bikes;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import sample.Json;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebSocket
public class BikeWebSocket {

	// Store sessions if you want to, for example, broadcast a message to all users
	private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

	public BikeWebSocket() {
		EventBus.getDefault().register(this);
	}

	@OnWebSocketConnect
	public void connected(Session session) {
		sessions.add(session);
	}

	@OnWebSocketClose
	public void closed(Session session, int statusCode, String reason) {
		sessions.remove(session);
	}

	@OnWebSocketMessage
	public void message(Session session, String message) throws IOException {
		System.out.println("Got: " + message);   // Print message
		session.getRemote().sendString("Greetings: " + message); // and send it back
	}

	@Subscribe(threadMode = ThreadMode.ASYNC)
	public void publishBikeChange(BikeView bikeView) throws IOException {
		for (Session session : sessions) {
			session.getRemote().sendString(Json.encode(bikeView));
		}
	}
}

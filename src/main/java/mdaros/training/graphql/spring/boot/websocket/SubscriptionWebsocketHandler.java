package mdaros.training.graphql.spring.boot.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SubscriptionWebsocketHandler implements WebSocketHandler {

	@Override
	public void afterConnectionEstablished ( WebSocketSession webSocketSession ) throws Exception {

		// TODO ...
	}

	@Override
	public void handleMessage ( WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage ) throws Exception {

		webSocketSession.sendMessage ( webSocketMessage );
	}

	@Override
	public void handleTransportError ( WebSocketSession webSocketSession, Throwable throwable ) throws Exception {

		// TODO ...
	}

	@Override
	public void afterConnectionClosed ( WebSocketSession webSocketSession, CloseStatus closeStatus ) throws Exception {

		// TODO ...
	}

	@Override
	public boolean supportsPartialMessages () {

		return false;
	}
}
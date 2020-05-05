package mdaros.training.graphql.spring.boot.configuration;

import mdaros.training.graphql.spring.boot.websocket.SubscriptionWebsocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
public class SpringConfig implements WebSocketConfigurer  {

	@Override
	public void registerWebSocketHandlers ( WebSocketHandlerRegistry webSocketHandlerRegistry ) {

		webSocketHandlerRegistry.addHandler ( subscriptionsWebSocketHandler (), "/graphql" )
				.setAllowedOrigins( "*" )
				.withSockJS ();
	}

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer () {

		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean ();
		container.setMaxTextMessageBufferSize ( 1024 * 1024 );
		container.setMaxSessionIdleTimeout ( 30L * 1000 );

		return container;
	}

	@Bean
	public WebSocketHandler subscriptionsWebSocketHandler () {

		return new PerConnectionWebSocketHandler ( SubscriptionWebsocketHandler.class );
	}
}
package io.dot.jyp.server.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

/**
 * 이벤트를 사용하기 위한 RabbitMQ 설정
 */
@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {

  @Bean
  public TopicExchange notificationExchange() {
    return new TopicExchange("exchange");
  }


  /**
   * 서비스에 해당하는 큐를 생성합니다.
   * durable = true 이면 서버가 down 되도 queue 값에 데이터를 보존합니다.
   */
  @Bean
  Queue groupQueue(){
    return new Queue("groupQueue", false);
  }

  @Bean
  Queue allQueue(){
    return new Queue("allQueue", false);
  }


  /**
   * 각각의 큐를 바인딩합니다.
   * 메세지를 Send 할 때, queue name 과 routingKey 가 같아야 합니다.
   * topic 방식으로 유연하게 바인딩할 수 있습니다.
   */
  @Bean
  Binding groupBinding(final Queue groupQueue,
      final TopicExchange exchange){
    return BindingBuilder.bind(groupQueue).to(exchange).with("queue.group");
  }

  @Bean
  Binding allBinding(final Queue allQueue,
      final TopicExchange exchange){
    return BindingBuilder.bind(allQueue).to(exchange).with("queue.*");
  }


  @Bean
  public MappingJackson2MessageConverter consumerJackson2MessageConverter(){
    return new MappingJackson2MessageConverter();
  }

  @Bean
  public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory(){
    DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
    factory.setMessageConverter(consumerJackson2MessageConverter());
    return factory;
  }

  @Override
  public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
  }


  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}
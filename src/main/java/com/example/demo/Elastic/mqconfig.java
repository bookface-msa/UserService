package com.example.demo.Elastic;


import ch.qos.logback.classic.pattern.MessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class mqconfig {
    public static final String CATEGORIES_EXCHANGE = "categoryExchange";

    public static final String CATEGORIES_QUEUE_CREATE = "categoryQueueCreate";
    public static final String CATEGORIES_ROUTING_KEY_CREATE = "categoryCreate";

    public static final String CATEGORIES_QUEUE_DELETE = "categoryQueueDelete";
    public static final String CATEGORIES_ROUTING_KEY_DELETE = "categoryDelete";

    @Bean
    public Queue queueCreateCategory() {
        return new Queue(CATEGORIES_QUEUE_CREATE);
    }

    @Bean
    public Queue queueDeleteCategory() {
        return new Queue(CATEGORIES_QUEUE_DELETE);
    }

    @Bean
    public DirectExchange exchangeCategories() {
        return new DirectExchange(CATEGORIES_EXCHANGE);
    }

    @Bean
    public Binding bindingCreateCategories(
            Queue queueCreateCategory,
            DirectExchange exchangeCategories
    ) {
        return BindingBuilder.bind(queueCreateCategory)
                .to(exchangeCategories)
                .with(CATEGORIES_ROUTING_KEY_CREATE);
    }
    @Bean
    public Binding bindingDeleteCategories(
            Queue queueDeleteCategory,
            DirectExchange exchangeCategories
    ) {
        return BindingBuilder.bind(queueDeleteCategory)
                .to(exchangeCategories)
                .with(CATEGORIES_ROUTING_KEY_DELETE);
    }
    public static final String EXCHANGE = "elastic.users";

    public static final String QUEUE_CREATE = "elastic.users.create";
    public static final String ROUTING_KEY_CREATE = "create";

    public static final String QUEUE_DELETE = "elastic.users.delete";
    public static final String ROUTING_KEY_DELETE = "delete";

    public static final String QUEUE_UPDATE = "elastic.users.update";
    public static final String ROUTING_KEY_UPDATE= "update";

    @Bean
    //For created Posts to send to search service.
    public Queue queueCreateUser(){
        return new Queue(QUEUE_CREATE);
    }
    @Bean
    public Queue queueDeleteUser(){
        return new Queue(QUEUE_DELETE);
    }
    @Bean
    public Queue queueUpdateUser(){
        return new Queue(QUEUE_UPDATE);
    }
    @Bean
    public TopicExchange exchangeUsers(){
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public Binding bindingCreateUsers(Queue queueCreateUser, TopicExchange exchange){
        return BindingBuilder.bind(queueCreateUser).to(exchange).with(ROUTING_KEY_CREATE);
    }
    @Bean
    public Binding bindingDeleteUsers(Queue queueDeleteUser, TopicExchange exchange){
        return BindingBuilder.bind(queueDeleteUser).to(exchange).with(ROUTING_KEY_DELETE);
    }
    @Bean
    public Binding bindingUpdateUsers(Queue queueUpdateUser, TopicExchange exchange){
        return BindingBuilder.bind(queueUpdateUser).to(exchange).with(ROUTING_KEY_UPDATE);
    }
    @Bean
    public Jackson2JsonMessageConverter converter(){
        ObjectMapper mapper = JsonMapper.builder() // or different mapper for other format
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .build();

        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

package ru.denisaql.demo;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

@Component
public class MainRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:myRoute")
                .log("Rest API calling")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .toD("http://localhost:8082/api/car/show?id=${header.inPath}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String out = exchange.getIn().getBody(String.class);
                        System.out.println("ouput is: " + out);
                    }
                });

        from("direct:jdbcRoute")
                .routeId("JDBC route")
                .setBody(simple("select count(*) from tab where id > ${header.inPath}"))
                .to("jdbc:dataSource")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        String out = exchange.getIn().getBody(String.class);
                        System.out.println("ouput is: " + out);
                    }
                });
        from("kafka:mySuperRoute:9092?brokers=localhost:9092&groupId=hello")
                .routeId("Kafka consumer")
                .log("${body}")
                .setHeader("storateName", simple("${exchangeId}"))
                .to("bean:myBean?method=bye");
    }
}

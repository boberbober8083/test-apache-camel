package ru.denisaql.demo;

import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/activate")
@RequiredArgsConstructor
public class ActivateController {
    private final MainRoute mainRoute;

    @GetMapping(path = "/1")
    public void activateRest(@RequestParam("id") String id) {
        CamelContext camelContext = mainRoute.getCamelContext();
        Assert.notNull(camelContext);

        FluentProducerTemplate producerTemplate = camelContext.createFluentProducerTemplate();
        Assert.notNull(producerTemplate);

        producerTemplate.withHeader("inPath", id)
                .withBody("hello")
                .to("direct:myRoute")
                .request(String.class);
    }

    @GetMapping(path = "/2")
    public void activateJdbc(@RequestParam("id") String id) {
        CamelContext camelContext = mainRoute.getCamelContext();
        Assert.notNull(camelContext);

        FluentProducerTemplate producerTemplate = camelContext.createFluentProducerTemplate();
        Assert.notNull(producerTemplate);

        producerTemplate.withHeader("inPath", id)
                .withBody("hello")
                .to("direct:jdbcRoute")
                .request(String.class);
    }
}

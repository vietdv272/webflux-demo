package com.demo.service;

import com.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JsonService {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono getDataReactive(long delay) {
        Flux emp = webClientBuilder.build().get().uri("http://www.mocky.io/v2/5e8b21822d0000291a1a4c29?mocky-delay="+delay+"ms")
                .retrieve()
                .bodyToFlux(Employee.class);
        System.out.println("Done request for delay: "+delay);
        return emp
                .collectList();
    }
}

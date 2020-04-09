package com.demo.service;

import com.demo.dao.enity.RuleResult;
import com.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class JsonService {
    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono getDataReactive(long delay) {
        Flux emp = webClientBuilder.build().get().uri("http://www.mocky.io/v2/5e8b21822d0000291a1a4c29?mocky-delay=" + delay + "ms")
                .retrieve()
                .bodyToFlux(Employee.class);
        System.out.println("Done request for delay: " + delay);
        return emp
                .collectList();
    }

    public Mono<RuleResult> processRule(long delay) {
        Flux<Employee> emp = webClientBuilder.build().get().uri("http://www.mocky.io/v2/5e8b21822d0000291a1a4c29?mocky-delay=" + delay + "ms")
                .retrieve()
                .bodyToFlux(Employee.class);
        System.out.println("Done request for delay: " + delay);
        Mono<RuleResult> result = emp.
        return result;
    }

    public Mono<RuleResult> process(Flux<Employee> flux) {
        List<Employee> list = new ArrayList<>();

        list = flux.collectList().doOnNext(l -> {list = l;})
        if (list.size() > 2) {
            return new RuleResult(1, false, -1);
        }
        return new RuleResult(1, true, 1);
    }


}

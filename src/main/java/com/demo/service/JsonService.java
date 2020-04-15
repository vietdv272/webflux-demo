package com.demo.service;

import com.demo.dao.enity.RuleResult;
import com.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
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


    public Mono<RuleResult> processRule() {
        Mono<Employee[]> emp1 = webClientBuilder.build().get().uri("http://www.mocky.io/v2/5e8b21822d0000291a1a4c29?mocky-delay=100ms")
                .retrieve().bodyToMono(Employee[].class);
        Mono<RuleResult> result1 =emp1.flatMap(list -> process(Arrays.asList(list)));
        return result1;
    }

    public Mono<RuleResult> process(List<Employee> list) {
        if (list.size() > 2) {
            return Mono.just(new RuleResult(1, false, -1));
        }
        return Mono.just(new RuleResult(1, true, 1));
    }




}

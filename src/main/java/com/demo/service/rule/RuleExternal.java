package com.demo.service.rule;

import com.demo.controller.EmployeeController;
import com.demo.dao.enity.RuleResult;
import com.demo.model.Employee;
import com.demo.model.ExternalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class RuleExternal {
    @Autowired
    private WebClient.Builder webClientBuilder;

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExternal.class);

    public Mono<RuleResult> processRule() {
        Mono<ExternalResponse> emp = webClientBuilder.build().post()
                .uri("localhost:8200/default/sample")
                .retrieve().bodyToMono(ExternalResponse.class);
        Mono<RuleResult> result = emp.flatMap(res -> process(res.data));
        return result;
    }

    public Mono<RuleResult> process(List<Employee> list) {
        LOGGER.info("Process RuleExternal result size: " + list.size());
        if (list.size() > 2) {
            return Mono.just(new RuleResult(1, false, -1));
        }
        return Mono.just(new RuleResult(1, true, 1));
    }
}

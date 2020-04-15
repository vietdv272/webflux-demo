package com.demo.service.rule;

import com.demo.dao.enity.RuleResult;
import com.demo.model.Employee;
import com.demo.model.User;
import com.demo.service.EmployeeService;
import com.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleDB {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleDB.class);

    public Mono<RuleResult> processRule() {
        Flux<Employee> users = employeeService.findAll();
        Mono<RuleResult> result = users.collectList().flatMap(list -> process(list));
        return result;
    }

    public Mono<RuleResult> process(List<Employee> list) {
        LOGGER.info("Process RuleDB result size: " + list.size());
        if (false) {
            return Mono.just(new RuleResult(2, false, -1));
        }
        return Mono.just(new RuleResult(2, true, 1));
    }


}

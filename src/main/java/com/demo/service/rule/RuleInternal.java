package com.demo.service.rule;

import com.demo.dao.enity.RuleResult;
import com.demo.model.Employee;
import com.demo.service.EmployeeService;
import com.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class RuleInternal {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleInternal.class);

    public Mono<RuleResult> processRule() {
        for (int i = 0; i < 10000; i++) {
            System.out.println("");
        }
        return Mono.just(new RuleResult(3, true, 1));
    }
}

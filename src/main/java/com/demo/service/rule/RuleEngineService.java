package com.demo.service.rule;

import com.demo.dao.enity.RuleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleEngineService {

    @Autowired
    RuleExternal ruleExternal;
    @Autowired
    RuleDB ruleDB;
    @Autowired
    RuleInternal ruleInternal;

    public Mono<List<RuleResult>> process() {
        List<Mono<RuleResult>> list = new ArrayList<>();
        list.add(ruleExternal.processRule());
        list.add(ruleDB.processRule());
        list.add(ruleInternal.processRule());

        Mono<List<RuleResult>> mono = Mono.zip(list, objectArray ->
                Arrays.stream(objectArray)
                        .map(object -> (RuleResult) object)
                        .collect(Collectors.toList()));

        return mono;
    }

}

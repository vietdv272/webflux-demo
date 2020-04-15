package com.demo.controller;

import com.demo.dao.enity.RuleResult;
import com.demo.service.JsonService;
import com.demo.service.rule.RuleEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JsonService jsonService;

    @Autowired
    private RuleEngineService ruleEngineService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

//
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public Mono<Employee> findById(@PathVariable("id") Integer id) {
//        Mono<Employee> e = employeeService.findById(id);
//        return e;
//    }

    @RequestMapping(value = "/parallel", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mono<List<Employee>>> parallel() {
        Mono<List<Employee>> list1 = jsonService.getDataReactive(0);
        Mono<List<Employee>> list2 = jsonService.getDataReactive(1000);
        Mono<List<Employee>> list3 = jsonService.getDataReactive(3000);
        Mono<List<Employee>> list4 = jsonService.getDataReactive(1600);
        Mono<List<Employee>> list5 = jsonService.getDataReactive(1600);
        Mono<List<Employee>> list6 = jsonService.getDataReactive(1600);
        list1.subscribe(s -> {
            LOGGER.info("Print list 1 >> " + s.toString());
        });
        list3.subscribe(s -> {
            LOGGER.info("Print list 3>> " + s.toString());
        });
        list4.subscribe(s -> {
            LOGGER.info("Print list 4>> " + s.toString());
        });
        list5.subscribe(s -> {
            LOGGER.info("Print list 5>> " + s.toString());
        });
        list6.subscribe(s -> {
            LOGGER.info("Print list 6>> " + s.toString());
        });

        HttpStatus status = list2 != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Mono<List<Employee>>>(list2, status);
    }


    @RequestMapping(value = "/testMultiCall", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mono<List<Employee>>> callex5() {
        Mono<List<Employee>> list1 = jsonService.getDataReactive(0);
        Mono<List<Employee>> list2 = jsonService.getDataReactive(1000);
        Mono<List<Employee>> list3 = jsonService.getDataReactive(300);
        Mono<List<Employee>> list4 = jsonService.getDataReactive(1600);
        list1.subscribe(s -> {
            LOGGER.info("Print >> " + s.toString());
        });
        list4.subscribe(s -> {
            LOGGER.info("Print >> " + s.toString());
        });
        HttpStatus status = list2 != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Mono<List<Employee>>>(list2, status);
    }

    @RequestMapping(value = "/process", method = RequestMethod.GET)
    @ResponseBody
    public Mono<List<RuleResult>> process() {
        Mono<List<RuleResult>> result = ruleEngineService.process();
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public Mono<Boolean> add() {
        for (int i = 9; i < 10000; i++) {
            employeeService.create(new Employee(i, "name" + i, 100l * i));
        }

        return Mono.just(true);
    }

}

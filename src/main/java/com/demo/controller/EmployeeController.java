package com.demo.controller;

import com.demo.service.JsonService;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);


    @RequestMapping(value = {"/create", "/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void create(@RequestBody Employee e) {
        employeeService.create(e);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Mono<Employee> findById(@PathVariable("id") Integer id) {
        Mono<Employee> e = employeeService.findById(id);
        return e;
    }

    @RequestMapping(value = "/parallel", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mono<List<Employee>>> parallel() {
        Mono<List<Employee>> list1 = jsonService.getDataReactive(0);
        Mono<List<Employee>> list2 = jsonService.getDataReactive(1000);
        Mono<List<Employee>> list3 = jsonService.getDataReactive(3000);
        Mono<List<Employee>> list4 = jsonService.getDataReactive(1600);
        Mono<List<Employee>> list5 = jsonService.getDataReactive(1600);
        Mono<List<Employee>> list6 = jsonService.getDataReactive(1600);
        list1.subscribe(s -> {LOGGER.info("Print list 1 >> "+s.toString());});
        list3.subscribe(s -> {LOGGER.info("Print list 3>> "+s.toString());});
        list4.subscribe(s -> {LOGGER.info("Print list 4>> "+s.toString());});
        list5.subscribe(s -> {LOGGER.info("Print list 5>> "+s.toString());});
        list6.subscribe(s -> {LOGGER.info("Print list 6>> "+s.toString());});

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
        list1.subscribe(s -> {LOGGER.info("Print >> "+s.toString());});
        list4.subscribe(s -> {LOGGER.info("Print >> "+s.toString());});
        HttpStatus status = list2 != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Mono<List<Employee>>>(list2, status);
    }

    @RequestMapping(value = "/callexblock/{times}", method = RequestMethod.GET)
    @ResponseBody
    public List<Employee> callexblock(@PathVariable("times") Integer times) {
        List<Employee> list = null;
        for (int i = 0; i < times; i++) {
            jsonService.getDataReactive(200).subscribe(System.out::println);
        }
        return list;
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    @ResponseBody
    public Flux<Employee> getall() {
        Flux<Employee> e = employeeService.findAll();
        return e;
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Flux<Employee> findByName(@PathVariable("name") String name) {
        return employeeService.findByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Employee> findAll() {
        Flux<Employee> emps = employeeService.findAll();
        return emps;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Employee> update(@RequestBody Employee e) {
        return employeeService.update(e);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        employeeService.delete(id).subscribe();
    }

}

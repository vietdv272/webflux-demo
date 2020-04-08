package com.howtodoinjava.demo.dao;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.howtodoinjava.demo.model.Employee;

import reactor.core.publisher.Flux;
 
public interface EmployeeRepository extends ReactiveCassandraRepository<Employee, Integer> {
    @AllowFiltering
    Flux<Employee> findByName(final String name);
}
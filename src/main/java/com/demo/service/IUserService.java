package com.demo.service;

import com.demo.model.Employee;
import com.demo.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService
{
    Flux<User> findByUserid(String userID);
}
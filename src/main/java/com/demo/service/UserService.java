package com.demo.service;

import com.demo.dao.EmployeeRepository;
import com.demo.dao.UserRepository;
import com.demo.model.Employee;
import com.demo.model.User;
import com.demo.model.UserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepo;

    public Flux<User> findByUserid(String userID) {
//        return userRepo.findAll();
        return userRepo.findByKeyUserid(userID);
    }

}
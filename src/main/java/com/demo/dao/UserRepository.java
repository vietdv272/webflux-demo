package com.demo.dao;

import com.demo.model.Employee;
import com.demo.model.User;
import com.demo.model.UserKey;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCassandraRepository<User, UserKey> {

    Flux<User> findByKeyUserid(final String userID);
}
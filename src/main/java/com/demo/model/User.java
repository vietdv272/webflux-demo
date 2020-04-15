package com.demo.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;

@Table("userb2csummary")
public class User {

    @PrimaryKey
    public UserKey key;

    @Column("amount")
    public long amount;

    //Getters and setters


    @Override
    public String toString() {
        return "User{" +
                "key=" + key +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return amount == user.amount &&
                key.equals(user.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, amount);
    }
}

package com.demo.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;

@PrimaryKeyClass
public class UserKey implements Serializable {

    @PrimaryKeyColumn(
            name = "userid", type = PrimaryKeyType.PARTITIONED)
    public String userid;

    @PrimaryKeyColumn(
            name = "zpttransid", type = PrimaryKeyType.CLUSTERED)
    public long zpttransid;

    //Getters and setters


    public UserKey(String userid, long zpttransid) {
        this.userid = userid;
        this.zpttransid = zpttransid;
    }

    @Override
    public String toString() {
        return "UserKey{" +
                "userid='" + userid + '\'' +
                ", zpttransid=" + zpttransid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserKey userKey = (UserKey) o;
        return zpttransid == userKey.zpttransid &&
                userid.equals(userKey.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, zpttransid);
    }
}

package com.demo.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

public class ExternalResponse {

    public int returncode = 0;
    public String returnmessage = "";
    public List<Employee> data;
}

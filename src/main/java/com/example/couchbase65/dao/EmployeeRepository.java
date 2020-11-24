package com.example.couchbase65.dao;

import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CouchbaseRepository<Employee, String> {

}

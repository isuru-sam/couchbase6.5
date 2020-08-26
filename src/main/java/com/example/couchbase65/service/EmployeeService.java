package com.example.couchbase65.service;

import com.example.couchbase65.dao.Account;
import com.example.couchbase65.dao.Employee;

public interface EmployeeService {
	
	public void saveAll(Employee e1,Employee e2) ;
		
	public void trasactionAcrossBuckets(Employee e1,Account a1);

}

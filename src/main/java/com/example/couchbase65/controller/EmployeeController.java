package com.example.couchbase65.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.couchbase65.dao.Account;
import com.example.couchbase65.dao.AccountRepository;
import com.example.couchbase65.dao.Employee;
import com.example.couchbase65.dao.EmployeeRepository;
import com.example.couchbase65.service.EmployeeService;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private AccountRepository accountRepository;

	@PostMapping(path = "/save")
	public void saveEmployees() {
		
		Employee e1 = new Employee();
		e1.setId("1");
		e1.setName("Mark");
		
		Employee e2 = new Employee();
		e2.setId("2");
		e2.setName("Taylor");
		
		employeeService.saveAll(e1, e2);
		
	}
	
	
	@PostMapping(path = "/saveEmp")
	public void saveEmployee() {
		
		Employee e1 = new Employee();
		e1.setId("1");
		e1.setName("Mark");
		
		
		
		employeeRepository.save(e1);
		
	}
	
	@PostMapping(path = "/saveAcc")
	public void saveAccount() {
		
		Account e1 = new Account();
		e1.setId("1");
		e1.setAccountId("11");
		
		
		
		accountRepository.save(e1);
		
	}
	@PostMapping(path = "/saveTx")
	public void trasactionAcrossBuckets() {
		Employee e1 = new Employee();
		e1.setId("2");
		e1.setName("Mark2");
		
		Account a1 = new Account();
		a1.setId("2");
		a1.setAccountId("22");
		employeeService.trasactionAcrossBuckets(e1, a1);
	}

}

package com.example.couchbase65.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.mapping.CouchbaseDocument;
import org.springframework.stereotype.Service;

import com.couchbase.transactions.Transactions;
import com.example.couchbase65.dao.Account;
import com.example.couchbase65.dao.AccountRepository;
import com.example.couchbase65.dao.Employee;
import com.example.couchbase65.dao.EmployeeRepository;


@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private Transactions transactions;
	@Autowired
	private CouchbaseClientFactory couchbaseClientFactory;
	@Autowired
	private CouchbaseClientFactory myCouchbaseClientFactory;
	@Autowired
	private MappingCouchbaseConverter mappingCouchbaseConverter;

	@Override
	public void saveAll(Employee e1, Employee e2) {

		transactions.run(ctx -> {
			CouchbaseDocument target = new CouchbaseDocument();
			mappingCouchbaseConverter.write(e1, target);

			ctx.insert(couchbaseClientFactory.getDefaultCollection(), target.getId(), target.getContent());

			CouchbaseDocument target2 = new CouchbaseDocument();
			
			mappingCouchbaseConverter.write(e2, target2);

			ctx.insert(couchbaseClientFactory.getDefaultCollection(), target2.getId(), target2.getContent());
			if(false) {
				throw new RuntimeException();
			}
			ctx.commit();
		});

		

	}

	@Override
	public void trasactionAcrossBuckets(Employee e1, Account a1) {
		transactions.run(ctx -> {
			CouchbaseDocument target = new CouchbaseDocument();
			mappingCouchbaseConverter.write(e1, target);

			ctx.insert(couchbaseClientFactory.getDefaultCollection(), target.getId(), target.getContent());

			CouchbaseDocument target2 = new CouchbaseDocument();
			
			mappingCouchbaseConverter.write(a1, target2);

			ctx.insert(myCouchbaseClientFactory.getDefaultCollection(), target2.getId(), target2.getContent());
		
			ctx.commit();
		});

	}

}

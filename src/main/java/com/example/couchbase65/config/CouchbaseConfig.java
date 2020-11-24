package com.example.couchbase65.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

import com.couchbase.client.java.Cluster;
import com.couchbase.transactions.Transactions;
import com.couchbase.transactions.config.TransactionConfigBuilder;
import com.example.couchbase65.dao.Account;
import com.example.couchbase65.dao.Employee;

@Configuration
@EnableCouchbaseRepositories(basePackages = { "com.example.couchbase65.dao" })
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {
	@Override
	public String getConnectionString() {
		return "couchbase://127.0.0.1";
	}

	@Override
	public String getUserName() {
		return "admin";
	}

	@Override
	public String getPassword() {
		return "password";
	}

	@Override
	public String getBucketName() {
		return "employee";
	}

	@Bean
	public CouchbaseTemplate myCouchbaseTemplate(CouchbaseClientFactory myCouchbaseClientFactory,
			MappingCouchbaseConverter mappingCouchbaseConverter) {
		return new CouchbaseTemplate(myCouchbaseClientFactory, mappingCouchbaseConverter);
	}

	@Bean
	public CouchbaseClientFactory myCouchbaseClientFactory(Cluster couchbaseCluster) {
		
		return new SimpleCouchbaseClientFactory(couchbaseCluster, "account", getScopeName());
	}

	@Override
	protected void configureRepositoryOperationsMapping(RepositoryOperationsMapping mapping) {
		

		CouchbaseTemplate empTemplate = couchbaseTemplate(
				couchbaseClientFactory(couchbaseCluster(couchbaseClusterEnvironment())),
				new MappingCouchbaseConverter());
		
		CouchbaseTemplate accTemplate = myCouchbaseTemplate(
				myCouchbaseClientFactory(couchbaseCluster(couchbaseClusterEnvironment())),
				new MappingCouchbaseConverter());
		mapping.mapEntity(Employee.class,  empTemplate); 
		
		mapping.mapEntity(Account.class,  accTemplate); 
		
	}

	@Bean
	public Transactions transactions(final Cluster couchbaseCluster) {
		return Transactions.create(couchbaseCluster, TransactionConfigBuilder.create()
			
				.build());
	}
}

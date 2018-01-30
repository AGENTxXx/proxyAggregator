package com.enuvid.proxyaggregator.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Test, String > {

}

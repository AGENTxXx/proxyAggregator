package com.enuvid.proxyaggregator.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProxyRepository extends MongoRepository<Proxy, String> {

}

package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IPUtils;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProxyRepository extends MongoRepository<Proxy, String> {
    boolean existsByIpAndPort(Long ip, int port);

    default boolean existsByIpAndPort(String ip, int port) {
        return existsByIpAndPort(IPUtils.convert(ip), port);
    }
}

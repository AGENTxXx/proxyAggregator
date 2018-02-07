package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IPUtils;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockedProxyRepository extends MongoRepository<BlockedProxy, String> {
    BlockedProxy findByIpAndPort(Long ip, int port);

    default BlockedProxy findByIpAndPort(String ip, int port) {
        return findByIpAndPort(IPUtils.convert(ip), port);
    }

    boolean existsByIpAndPort(Long ip, int port);

    default boolean existsByIpAndPort(String ip, int port) {
        return existsByIpAndPort(IPUtils.convert(ip), port);
    }
}

package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IPUtils;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockedProxyRepository extends MongoRepository<BlockedProxy, String> {
    BlockedProxy findBlockedProxyByIpAndPort(Long ip, int port);

    default BlockedProxy findBlockedProxy(String ip, int port) {
        return findBlockedProxyByIpAndPort(IPUtils.convert(ip), port);
    }
}

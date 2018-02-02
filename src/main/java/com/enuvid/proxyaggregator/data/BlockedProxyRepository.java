package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IpUtils;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockedProxyRepository extends MongoRepository<BlockedProxy, String> {
    BlockedProxy findBlockedProxyByIpAndPort(Long ip, int port);

    default BlockedProxy findBlockedProxy(String ip, int port) {
        return findBlockedProxyByIpAndPort(IpUtils.convert(ip), port);
    }
}

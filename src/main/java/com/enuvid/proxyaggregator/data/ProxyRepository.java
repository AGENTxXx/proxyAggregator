package com.enuvid.proxyaggregator.data;

import com.enuvid.proxyaggregator.utils.IPUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProxyRepository extends MongoRepository<Proxy, String> {
    boolean existsByIpAndPort(Long ip, int port);

    default boolean existsByIpAndPort(String ip, int port) {
        return existsByIpAndPort(IPUtils.convert(ip), port);
    }

    Page<SimpleProxy> findByLocationCountryContainingIgnoreCaseAndAvgAvailableAfterAndAvgSpeedBeforeOrderByLastUpdateDateDesc(String country, int avgAvailableAfter, int avgSpeedBefore, Pageable page);

    default Page<SimpleProxy> filter(
            String country,
            int minAvailable,
            int maxSpeed
            , Pageable page) {
        return findByLocationCountryContainingIgnoreCaseAndAvgAvailableAfterAndAvgSpeedBeforeOrderByLastUpdateDateDesc(country, minAvailable, maxSpeed, page);
    }
}

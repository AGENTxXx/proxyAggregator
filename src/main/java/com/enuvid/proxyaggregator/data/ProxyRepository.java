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

    Page<SimpleProxy> findByLocationCountryContainingIgnoreCaseAndAvgAvailableGreaterThanEqualAndAvgSpeedLessThanEqualOrderByLastUpdateDateDesc(String country, int avgAvailableAfter, int avgSpeedBefore, Pageable page);

    Page<SimpleProxy> findByLocationCountryContainingIgnoreCaseAndAvgAvailableGreaterThanEqualAndAvgSpeedLessThanEqualAndTypeOrderByLastUpdateDateDesc(String country, int avgAvailableAfter, int avgSpeedBefore, Pageable page, java.net.Proxy.Type type);

    default Page<SimpleProxy> filter(
            String country,
            int minAvailable,
            int maxSpeed
            , Pageable page, String typeStr) {
        if (typeStr == null || typeStr.equals("all"))
            return findByLocationCountryContainingIgnoreCaseAndAvgAvailableGreaterThanEqualAndAvgSpeedLessThanEqualOrderByLastUpdateDateDesc(country, minAvailable, maxSpeed, page);
        else {
            if (typeStr.equals("none") || typeStr.equals(""))
                typeStr = "DIRECT";
            return findByLocationCountryContainingIgnoreCaseAndAvgAvailableGreaterThanEqualAndAvgSpeedLessThanEqualAndTypeOrderByLastUpdateDateDesc(country, minAvailable, maxSpeed, page, java.net.Proxy.Type.valueOf(typeStr.toUpperCase()));
        }
    }
}

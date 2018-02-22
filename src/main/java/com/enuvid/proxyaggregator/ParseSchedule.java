package com.enuvid.proxyaggregator;

import com.enuvid.proxyaggregator.data.BlockedProxyRepository;
import com.enuvid.proxyaggregator.data.Proxy;
import com.enuvid.proxyaggregator.data.ProxyRepository;
import com.enuvid.proxyaggregator.providers.HidemyName;
import com.enuvid.proxyaggregator.providers.ProxyProvider;
import com.enuvid.proxyaggregator.utils.ProxyUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ParseSchedule {
    private final Logger logger = Logger.getLogger(ParseSchedule.class.getName());
    private final ProxyRepository proxyRepo;
    private final BlockedProxyRepository blockedRepo;
    private final WebDriver driver;
    private ExecutorService pool = Executors.newFixedThreadPool(16);

    @Autowired
    public ParseSchedule(ProxyRepository proxyRepo, BlockedProxyRepository blockedRepo) {
        this.proxyRepo = proxyRepo;
        this.blockedRepo = blockedRepo;

        WebDriverManager.phantomjs().setup();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
        driver = new PhantomJSDriver(caps);
    }

    //    @Scheduled(fixedDelay = 1)
    void parseNewProxies() {
        List<ProxyProvider> providers = Arrays.asList(
                new HidemyName().setThreadPool(pool)
        );

        for (ProxyProvider provider : providers) {
            logger.log(Level.INFO, "Get proxies from " + provider.getClass().getName());
            List<Proxy> result = ProxyUtils.removeDuplicates(provider.find(driver, proxyRepo, blockedRepo));
            logger.log(Level.INFO, "Add new proxies(" + result.size() + ") to repository.");
            proxyRepo.insert(result);
        }
    }
}

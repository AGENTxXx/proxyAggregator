package com.enuvid.proxyaggregator.providers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class HidemyName extends ProxyProvider {
    @Override
    void parse(WebDriver driver) {
        int pageCounter = 0;
        try {
            while (true) {
                List<InetSocketAddress> addresses = parsePage(driver, pageCounter * 64);
                if (addresses.size() == 0) {
                    logger.log(Level.FINE, pageCounter + 1 + " pages successful parsed.");
                    break;
                } else
                    addresses.forEach((it) -> addProxy(it.getHostName(), it.getPort()));

                pageCounter++;
            }
        } catch (Exception ignored) {
        }
    }

    private List<InetSocketAddress> parsePage(WebDriver driver, int startPoint) throws Exception  {
        String baseURL = "https://hidemy.name/ru/proxy-list/?start=";
        driver.get(baseURL + startPoint);
        Thread.sleep(8000);
        List<WebElement> rows = driver.findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        List<InetSocketAddress> proxies = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            proxies.add(new InetSocketAddress(
                            columns.get(0).getText(),
                            Integer.valueOf(columns.get(1).getText())
                    )
            );
        }
        logger.log(Level.INFO, "Page " + (startPoint / 64 + 1) + " done!");
        return proxies;
    }
}

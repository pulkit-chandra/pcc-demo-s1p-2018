package io.pivotal.pcc.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.cache.config.EnableGemfireCaching;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableLogging;

/**
 * Created by nchandrappa on 6/12/18.
 */
@Configuration
@ClientCacheApplication(name="cache-demo")
@EnableGemfireCaching
@EnableEntityDefinedRegions(basePackages = {"io.pivotal.pcc.demo.domain"})
@EnableLogging(logLevel = "info")
public class CloudCacheConfig {
}

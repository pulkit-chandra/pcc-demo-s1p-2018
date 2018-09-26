## Prerequisites

1. Pivotal Cloud Cache service in Marketplace
2. GFSH cli
   ( https://gemfire.docs.pivotal.io/geode/tools_modules/gfsh/starting_gfsh.html )

## Getting Started with PCC Client

###### Step 1: Create PCC Client project

1. Create a spring boot app from Spring Initilizer or through Spring Tool Suite IDE

	http://start.spring.io

2. Add Spring Boot GemFire and Web project dependencies 

 Note: We will be using Spring-data-gemfire (SDG) project for connecting to PCC cluster. Please refer to pom.xml for more details.


3. Add the below Spring cloud GemFire connector dependencies. This will simplify retrieving PCC connection information and creating PCC Client Cache.

```
<!-- Spring Boot Data Geode -->
<dependency>
    <groupId>org.springframework.geode</groupId>
    <artifactId>spring-gemfire-starter</artifactId>
    <version>1.0.0.M1</version>
</dependency>

<!-- Spring Data Geode Test -->
<dependency>
	<groupId>org.springframework.data</groupId>
	<artifactId>spring-data-geode-test</artifactId>
	<version>0.0.1.M3</version>
	<scope>test</scope>
</dependency>
```

###### Step 2: Creation of PCC client cache using Spring Boot Auto-Reconfiguration

```
@Configuration
@ClientCacheApplication(name="s1p-demo")
@EnableGemfireCaching
@EnableEntityDefinedRegions(basePackages = {"io.pivotal.pcc.demo.domain"})
@EnableLogging(logLevel = "info")
public class CloudCacheConfig {
}
```



## Create PCC OnDemand service
Services can be created through Apps Manager Marketplace or by executing cf cli commands

###### Step 1: create a PCC OnDemand service in your org & space

```
cf create-service p-cloudcache extra-small pcc-dev-cluster

```

###### Step 2: Create service key for retrieving connection information for GFSH cli

```
cf create-service-key pcc-dev-cluster devkey
```

###### Step 3: Retrieve url for PCC cli (GFSH) and corresponding credentials 

```
cf service-key pcc-dev-cluster devkey
```

###### Step 4: Login into to PCC cli (GFSH)

```
connect --use-http=true --url=http://gemfire-xxxx-xxx-xx-xxxx.system.excelsiorcloud.com/gemfire/v1 --user=cluster_operator --password=*******
```

###### Step 5: create PCC region with name `Customer`

Note: Region name created on PCC server and client should match

```
create region --name=Customer --type=PARTITION_REDUNDANT
```

## Bind PCC Client with PCC service

###### Bind to PCC service by specifying service name in the manifest.yml

```
---
applications:
- name: PCC-Client
  random-route: false
  path: target/PCC-Client-0.0.1-SNAPSHOT.jar
  services:
  - pcc-dev-cluster
```

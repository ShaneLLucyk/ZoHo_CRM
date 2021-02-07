
## How to Build Salesforce Objects
Add to POM
```xml
        <groupId>org.apache.camel.maven</groupId>
        <artifactId>camel-salesforce-maven-plugin</artifactId>
```


1. Update your pom properties with the appropriate credentials. See the salesforce setup section below for appropriate values:

| Property Name                | Salesforce value                |
|------------------------------|---------------------------------|
| camelSalesforce.clientId     | Consumer Key                    |
| camelSalesforce.clientSecret | Consumer Secret                 |
| camelSalesforce.userName     | Username                        |
| camelSalesforce.password     | Login password                  |
| camelSalesforce.token        | Security Token                  |
| camelSalesforce.loginUrl     | this is the URL shown in step 3 |

2. Update the pom with tables for DTO generation. 
```xml
          <loginUrl default-value="https://login.salesforce.com">${camelSalesforce.loginUrl}</loginUrl>
          <includes>
            <include>Account</include>
            <include>Activity</include>
            <include>Asset</include>
...
```

3. Execute the maven command:
```bash
mvn camel-salesforce:generate
```

4. This will generate the dto files in `target/generated-sources`

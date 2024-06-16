package by.waitaty.apigateway.clients;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthServerDiscovery {

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostConstruct
    public void getAuthServerUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances("auth-server");
        if (!instances.isEmpty()) {
            ServiceInstance authServerInstance = instances.get(0);
            String authServerUrl = authServerInstance.getUri().toString();
            System.setProperty("AUTH_SERVER_URL", authServerUrl);
            System.out.println(authServerUrl);
        }
    }
}

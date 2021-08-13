package com.herokuapp.ebookhub.contact.entities;

import java.security.Security;

import com.herokuapp.ebookhub.CloudkitConfig;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ContactRepository {

    private CloudkitConfig cloudkitConfig;

    public ContactRepository(CloudkitConfig cloudkitConfig) {
        this.cloudkitConfig = cloudkitConfig;
    }
    
    public String findAll() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public CloseableHttpResponse save(String email, String name) {
        try {
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            // this.cloudkitConfig = new CloudkitConfig(
            //     CloudkitConfig.getContainerId(), 
            //     CloudkitConfig.getKeyId(), 
            //     CloudkitConfig.getEnvironment(), 
            //     CloudkitConfig.getPrivateKey());
            // this.cloudkitConfig = new CloudkitConfig();
            this.cloudkitConfig = this.cloudkitConfig.setOperation("POST");
            this.cloudkitConfig = this.cloudkitConfig.setEntity("{\"operations\":[{\"operationType\":\"create\",\"record\":{\"recordType\":\"Contact\",\"fields\":{\"name\":{\"value\":\"A Post From The Server\"}}}}]}");
            this.cloudkitConfig = this.cloudkitConfig.setResource("/public/records/modify");
            
            return this.cloudkitConfig.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
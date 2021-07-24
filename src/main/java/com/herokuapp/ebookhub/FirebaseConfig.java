package com.herokuapp.ebookhub;

import java.util.HashMap;

import org.springframework.core.env.Environment;
// import org.json.simple.JSONObject;
// import org.json.simple.JSONException;
// import org.springframework.boot.configurationprocessor.json.JSONException;
// import org.json.JSONException;
// import org.springframework.boot.configurationprocessor.json.JSONObject;
// import org.springframework.context.EnvironmentAware;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
import java.io.ByteArrayInputStream;
// import java.io.ByteArrayOutputStream;
// import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
// import java.io.ObjectOutputStream;
import java.util.Arrays;

import com.google.auth.oauth2.GoogleCredentials;
// import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
// import com.google.api.client.util.Data;
import com.google.gson.Gson;

// import com.fasterxml.jackson.*;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.codehaus.jackson.JsonGenerationException;
// import org.codehaus.jackson.map.JsonMappingException;
// import org.codehaus.jackson.map.ObjectMapper;

@Configuration
@PropertySource("application.properties")
// @PropertySource("classpath:application.properties")
// @PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = false)
// public class FirebaseConfig implements EnvironmentAware {
public class FirebaseConfig {

    // @Autowired
    // Environment environment;

    private Environment environment;

    private String testEnv;

    private String token;

    public FirebaseConfig() {}

    // @Override
    // public void setEnvironment(final Environment environment) {
    //     this.environment = environment;
    // }

    public FirebaseConfig(Environment environment) {
        this.environment = environment;
    }

    public String getToken() {
        return this.token;
    }

    // @Bean
    public void getFirebaseToken() throws IOException {
    // public void getFirebaseToken() {
        // Load the service account key JSON file
        // try {
            testEnv = environment.getProperty("flyit.firebase.private.key");
            System.out.println("Masuk " + testEnv);
            Map<String, String> serviceAccount = new HashMap<>();
            // JSONObject serviceAccount = new JSONObject();
            serviceAccount.put("type", 
            environment.getProperty("flyit.firebase.type"));
            serviceAccount.put("project_id", 
            environment.getProperty("flyit.firebase.project.id"));
            serviceAccount.put("private_key_id", 
            environment.getProperty("flyit.firebase.private.key.id"));
            serviceAccount.put("private_key", testEnv);
            serviceAccount.put("client_email", 
            environment.getProperty("flyit.firebase.client.email"));
            serviceAccount.put("client_id", 
            environment.getProperty("flyit.firebase.client.id"));
            serviceAccount.put("auth_uri", 
            environment.getProperty("flyit.firebase.auth.url"));
            serviceAccount.put("token_uri", 
            environment.getProperty("flyit.firebase.token.url"));
            serviceAccount.put("auth_provider_x509_cert_url", 
            environment.getProperty("flyit.firebase.auth.provider"));
            serviceAccount.put("client_x509_cert_url", 
            environment.getProperty("flyit.firebase.cert.url"));

            // ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // ObjectOutputStream oos = new ObjectOutputStream(baos);
    
            // oos.writeObject(serviceAccount);
            // oos.flush();
            // oos.close();

            // String json = serviceAccount.toString();
            Gson gson = new Gson();
            String json = gson.toJson(serviceAccount);
            json = json.replaceAll("\\\\\\\\", "\\\\");
            // String json = new JSONObject(serviceAccount).toString();
            System.out.println("Test " + json);
    
            InputStream is = new ByteArrayInputStream(json.getBytes());

            // FileInputStream temp = new FileInputStream(serviceAccount);
    
            // Authenticate a Google credential with the service account
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(is);
            // GoogleCredential googleCred = GoogleCredential.fromStream(is);
    
            // Add the required scopes to the Google credential
            GoogleCredentials scoped = googleCredentials.createScoped(
                Arrays.asList(
                "https://www.googleapis.com/auth/firebase.database",
                "https://www.googleapis.com/auth/userinfo.email"
                )    
            );
            // GoogleCredential scoped = googleCred.createScoped(
            //     Arrays.asList(
            //     "https://www.googleapis.com/auth/firebase.database",
            //     "https://www.googleapis.com/auth/userinfo.email"
            //     )
            // );
    
            // Use the Google credential to generate an access token
            scoped.refreshIfExpired();
            token = scoped.getAccessToken().getTokenValue();
            System.out.println("From config " + token);
        // } catch (IOException ie) {
        //     ie.printStackTrace();
        // }
    }

    // See the "Using the access token" section below for information
    // on how to use the access token to send authenticated requests to the
    // Realtime Database REST API.

}
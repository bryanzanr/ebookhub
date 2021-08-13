package com.herokuapp.ebookhub;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@Component
@PropertySource("classpath:application.properties")
public class CloudkitConfig {

    // private String containerId;
    // private String keyId;
    // private String environment;
    // private PrivateKey privateKey;
    private final String host = "api.apple-cloudkit.com";
    private String cloudKitResource;
    private String payload;
    private String operation;
    private String version = "1";

    private static Environment configVar;
    private static String staticContainerId;
    private static String staticKeyId;
    private static String staticEnvironment;
    private static String staticPrivateKey;

    // private static String privateKeyText =
    //         "-----BEGIN EC PRIVATE KEY-----\n" +
    //         "your prive key is here as from openssl" +
    //         "-----END EC PRIVATE KEY-----";

    // public CloudkitConfig(String containerId, String keyId, String environment, String privateKeyText) {
    //     this.containerId = containerId;
    //     this.keyId = keyId;
    //     this.environment = environment;
    //     this.privateKey = loadPrivateKey(privateKeyText);
    // }

    // public CloudkitConfig() {
    //     System.out.println("Masuk " + staticPrivateKey);
    //     this.containerId = staticContainerId;
    //     this.keyId = staticKeyId;
    //     this.environment = staticEnvironment;
    //     this.privateKey = loadPrivateKey(staticPrivateKey);
    // }

    public CloudkitConfig(Environment configVar) {
        // System.out.println("Masuk " + staticPrivateKey);
        CloudkitConfig.configVar = configVar;
        // CloudkitConfig.staticContainerId = containerId;
        // CloudkitConfig.staticKeyId = keyId;
        // CloudkitConfig.staticEnvironment = environment;
        // CloudkitConfig.staticPrivateKey = privateKeyText;
    }

    public static String getContainerId() {
        return staticContainerId;
    }

    public static String getKeyId() {
        return staticKeyId;
    }

    public static String getEnvironment() {
        return staticEnvironment;
    }

    public static String getPrivateKey() {
        return staticPrivateKey;
    }

    public static void setUpEnv() {
        staticContainerId = configVar.getProperty("cloudkit.container.id");
        staticKeyId = configVar.getProperty("cloudkit.key.id");
        staticEnvironment = configVar.getProperty("cloudkit.env");
        staticPrivateKey = configVar.getProperty("cloudkit.ec.key");
        String temp = "-----BEGIN EC PRIVATE KEY-----\n";
        staticPrivateKey = staticPrivateKey.substring(32, 
        staticPrivateKey.length() - 32);
        // for (int i = 0; i < staticPrivateKey.length(); i++) {
        //     if (staticPrivateKey.charAt(i) == '\\') {
        //         continue;
        //     }
        //     temp += staticPrivateKey;
        // }
        staticPrivateKey = staticPrivateKey.replace("\\n", "");
        temp += staticPrivateKey;
        temp += "-----END EC PRIVATE KEY-----";
        staticPrivateKey = temp;
        // System.out.println("Benar " + staticPrivateKey);
    }

    public CloudkitConfig setResource(String cloudKitResource) {
        this.cloudKitResource = cloudKitResource;
        return this;
    }

    public CloudkitConfig setEntity(String payload) {
        this.payload = payload;
        return this;
    }

    public CloudkitConfig setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public String getOperation() {
        return this.operation;
    }

    private String getIsoDate() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(tz);
        return df.format(new Date())+"Z";
    }

    public static String hashRequestBody(String body) {
        SHA256Digest digest = new SHA256Digest();
        byte[] ret = new byte[digest.getDigestSize()];
        byte[] data = body.getBytes();
        digest.update(data, 0, data.length);
        digest.doFinal(ret, 0);
        return Base64.getEncoder().encodeToString(ret);
    }

    private String signRequest(String request) {
        byte[] requestBytes = new byte[0];
        try {
            requestBytes = request.getBytes("UTF-8");
            Signature ecdsaSignature = Signature.getInstance("SHA256withECDSA", "BC");
            // ecdsaSignature.initSign(privateKey);
            ecdsaSignature.initSign(loadPrivateKey(staticPrivateKey));
            ecdsaSignature.update(requestBytes);
            byte[] signature = ecdsaSignature.sign();
            return Base64.getEncoder().encodeToString(signature);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        } catch (SignatureException e) {
            throw new RuntimeException(e.getMessage());
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public CloseableHttpResponse execute() {
        String dateString = getIsoDate();
        // String requestUrl = "/database/" + version + "/" + containerId + "/" + environment + cloudKitResource;
        String requestUrl = "/database/" + version + "/" + staticContainerId + "/" + staticEnvironment + cloudKitResource;
        String concat = dateString+":"+hashRequestBody(payload)+":"+requestUrl;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPostRequest = new HttpPost("https://" + host + requestUrl);

        // httpPostRequest.setHeader("X-Apple-CloudKit-Request-KeyID", keyId);
        httpPostRequest.setHeader("X-Apple-CloudKit-Request-KeyID", staticKeyId);
        httpPostRequest.setHeader("X-Apple-CloudKit-Request-ISO8601Date", dateString);
        httpPostRequest.setHeader("X-Apple-CloudKit-Request-SignatureV1", signRequest(concat));
        httpPostRequest.setHeader("Content-Type", "text/plain");

        try {
            httpPostRequest.setEntity( new StringEntity(payload));
            return httpclient.execute(httpPostRequest);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private PrivateKey loadPrivateKey(String keyText) {
        // System.out.println("Mantap " + keyText + " " + staticPrivateKey);
        PEMParser pemPrivateKeyReader = new PEMParser(new StringReader(keyText));
        Object pemObject = null;
        try {
            pemObject = pemPrivateKeyReader.readObject();
            if (pemObject instanceof PEMKeyPair) {
                KeyPair pair = new JcaPEMKeyConverter().setProvider("BC").getKeyPair((PEMKeyPair) pemObject);
                return pair.getPrivate();
            } else {
                throw new RuntimeException("Unexpected key spec");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
package org.feathercoin.monitoring.json.rest;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Map;

/**
 * Class to execute JSON requests using springs REST template abstraction
 */
public class JsonRequestExecutor implements Serializable {
    private String protocol;
    private int port;
    private String domain;
    private String username;
    private String password;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String executeJsonRequest(String url, Map vars){
        return executeJsonRequest(getDomain(),url,vars);
    }

    /**
     * Executes the JSON request
     * @param domain Domain to execute the request against
     * @param url The URL part following the domain
     * @param vars Map Name,Value pairs for replacements in the URL
     * @return Json response as string
     * @throws org.springframework.web.client.RestClientException if Json request failed
     */
    public String executeJsonRequest(String domain,String url, Map<String,?> vars){
        HttpHost targetHost = new HttpHost(domain, getPort(), getProtocol());
        HttpComponentsClientHttpRequestFactoryBasicAuth httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactoryBasicAuth(targetHost);

        RestTemplate restTemplate = new RestTemplate();


        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(new PoolingClientConnectionManager());

        if (getUsername()!=null && !getUsername().isEmpty()){
            defaultHttpClient.getCredentialsProvider().setCredentials(
                    new AuthScope(targetHost.getHostName(), targetHost.getPort(), AuthScope.ANY_REALM),
                    new UsernamePasswordCredentials(getUsername(), getPassword()));
        }

        httpComponentsClientHttpRequestFactory.setHttpClient(defaultHttpClient);
        restTemplate.setRequestFactory(httpComponentsClientHttpRequestFactory);

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        if (!url.startsWith("/"))
            url="/"+url;

        HttpComponentsClientHttpRequestFactory rf =
                (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
        rf.setReadTimeout(1 * 3000);
        rf.setConnectTimeout(1 * 3000);

        String jsonResponse = restTemplate.getForObject(getProtocol()+"://"+domain+":"+getPort()+url, String.class,vars);
        return jsonResponse;
    }

}

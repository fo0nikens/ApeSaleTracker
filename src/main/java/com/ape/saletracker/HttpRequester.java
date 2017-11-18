package com.ape.saletracker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;

public class HttpRequester {
    public static String TAG = "HttpRequester";
    private String defaultContentEncoding = Charset.defaultCharset().name();
    private int timeout = 30000;

    public HttpRespons sendGet(String urlString) throws IOException {
        return send(urlString, "GET", null, null);
    }

    public HttpRespons sendGet(String urlString, Map<String, String> params) throws IOException {
        return send(urlString, "GET", params, null);
    }

    public HttpRespons sendGet(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
        return send(urlString, "GET", params, propertys);
    }

    public HttpRespons sendPost(String urlString) throws IOException {
        return send(urlString, "POST", null, null);
    }

    public HttpRespons sendPost(String urlString, Map<String, String> params) throws IOException {
        return send(urlString, "POST", params, null);
    }

    public HttpRespons sendPost(String urlString, Map<String, String> params, Map<String, String> propertys) throws IOException {
        return send(urlString, "POST", params, propertys);
    }

    private HttpRespons send(String urlString, String method, Map<String, String> parameters, Map<String, String> propertys) throws IOException {
        StringBuffer param;
        if (method.equalsIgnoreCase("GET") && parameters != null) {
            param = new StringBuffer();
            int i = 0;
            for (String key : parameters.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append((String) parameters.get(key));
                i++;
            }
            urlString = urlString + param;
        }
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(urlString).openConnection();
        urlConnection.setConnectTimeout(this.timeout);
        urlConnection.setRequestMethod(method);
        urlConnection.setUseCaches(false);
        if (propertys != null) {
            for (String key2 : propertys.keySet()) {
                urlConnection.addRequestProperty(key2, (String) propertys.get(key2));
            }
        }
        if (method.equalsIgnoreCase("POST") && parameters != null) {
            param = new StringBuffer();
            for (String key22 : parameters.keySet()) {
                param.append("&");
                param.append(key22).append("=").append((String) parameters.get(key22));
            }
            urlConnection.getOutputStream().write(param.toString().getBytes());
            urlConnection.getOutputStream().flush();
            urlConnection.getOutputStream().close();
        }
        return makeContent(urlString, urlConnection);
    }

    private HttpRespons makeContent(String urlString, HttpURLConnection urlConnection) throws IOException {
        HttpRespons httpResponser = new HttpRespons();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
            Log.e(TAG, "bufferedReader = " + bufferedReader.toString());
            httpResponser.contentCollection = new Vector();
            StringBuffer temp = new StringBuffer();
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                httpResponser.contentCollection.add(line);
                temp.append(line).append("\r\n");
            }
            bufferedReader.close();
            String ecod = urlConnection.getContentEncoding();
            if (ecod == null) {
                ecod = this.defaultContentEncoding;
            }
            Log.e(TAG, "httpResponser.contentCollection =" + httpResponser.contentCollection.toString());
            httpResponser.urlString = urlString;
            Log.e(TAG, "httpResponser.urlString =" + httpResponser.urlString);
            httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
            Log.e(TAG, "httpResponser.defaultPort =" + httpResponser.defaultPort);
            httpResponser.file = urlConnection.getURL().getFile();
            Log.e(TAG, "httpResponser.file = " + httpResponser.file);
            httpResponser.host = urlConnection.getURL().getHost();
            Log.e(TAG, "httpResponser.host = " + httpResponser.host);
            httpResponser.path = urlConnection.getURL().getPath();
            Log.e(TAG, "httpResponser.path = " + httpResponser.path);
            httpResponser.port = urlConnection.getURL().getPort();
            Log.e(TAG, "httpResponser.port = " + httpResponser.port);
            httpResponser.protocol = urlConnection.getURL().getProtocol();
            Log.e(TAG, "httpResponser.protocol = " + httpResponser.protocol);
            httpResponser.query = urlConnection.getURL().getQuery();
            Log.e(TAG, "httpResponser.query = " + httpResponser.query);
            httpResponser.ref = urlConnection.getURL().getRef();
            Log.e(TAG, "httpResponser.ref = " + httpResponser.ref);
            httpResponser.userInfo = urlConnection.getURL().getUserInfo();
            Log.e(TAG, "httpResponser.userInfo = " + httpResponser.userInfo);
            httpResponser.content = new String(temp.toString().getBytes(), ecod);
            Log.e(TAG, "httpResponser.content = " + httpResponser.content);
            httpResponser.contentEncoding = ecod;
            Log.e(TAG, "httpResponser.contentEncoding = " + httpResponser.contentEncoding);
            httpResponser.code = urlConnection.getResponseCode();
            Log.e(TAG, "httpResponser.code = " + httpResponser.code);
            httpResponser.message = urlConnection.getResponseMessage();
            Log.e(TAG, "httpResponser.message = " + httpResponser.message);
            httpResponser.contentType = urlConnection.getContentType();
            Log.e(TAG, "httpResponser.contentType = " + httpResponser.contentType);
            httpResponser.method = urlConnection.getRequestMethod();
            Log.e(TAG, "httpResponser.method = " + httpResponser.method);
            httpResponser.connectTimeout = urlConnection.getConnectTimeout();
            Log.e(TAG, "httpResponser.connectTimeout = " + httpResponser.connectTimeout);
            httpResponser.readTimeout = urlConnection.getReadTimeout();
            Log.e(TAG, "httpResponser.readTimeout = " + httpResponser.readTimeout);
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            return httpResponser;
        } catch (IOException e) {
            throw e;
        } catch (Throwable th) {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public void setTimeOut(int timeout) {
        this.timeout = timeout;
    }

    public String getDefaultContentEncoding() {
        return this.defaultContentEncoding;
    }

    public void setDefaultContentEncoding(String defaultContentEncoding) {
        this.defaultContentEncoding = defaultContentEncoding;
    }
}

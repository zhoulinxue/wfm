package com.zx.wfm.utils;

import android.content.Context;

import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by 周学 on 2017/1/4.
 */
public class ApiManager {
    private static Context mContext;
    private static ApiManagerService apiManagerService;
    /**
     * 网络请求库初始化
     */
    public static void init(Context context) {
        getUnsafeOkHttpClient();
    }

    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
           OkHttpClient.Builder builder= new OkHttpClient().newBuilder();
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.writeTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.sslSocketFactory(createSSLSocketFactory());
            builder.hostnameVerifier(new TrustAllHostnameVerifier());
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true ;
        }
    }
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }


    /*通用拦截器，用于处理登陆等问题*/
    private static class CommonInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) {
            try {
                Response response = chain.proceed(chain.request());
                Response s = response.networkResponse();
                if (response.code() == 200) {
                }
                return response;
            } catch (Exception i) {
                return null;
            }
        }
    }
    /**
     * 服务接口集合
     */
    private interface ApiManagerService {}
}

package nia.util;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Author: 王俊超
 * Date: 2017-10-12 23:10
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public class BogusTrustManagerFactory extends TrustManagerFactorySpi {
    private static final TrustManager DUMMY_TRUST_MANAGER = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // 总是信任 -- 在例子中总是如此
            // 在真实项目中需要做一些处理
            // 代码运行到这里了，仅当你的开启了客户端鉴权，如SecureChatSslContextFactory中所展示的
            System.err.println("UNKNOWN CLIENT CERTIFICATE: " + chain[0].getSubjectDN());
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // 总是信任 -- 在例子中总是如此
            // 在真实项目中需要做一些处理
            System.err.println("UNKNOWN SERVER CERTIFICATE: " + chain[0].getSubjectDN());
        }
    };

    @Override
    protected TrustManager[] engineGetTrustManagers() {
        return new TrustManager[]{DUMMY_TRUST_MANAGER};
    }

    @Override
    protected void engineInit(KeyStore keystore) throws KeyStoreException {
        // 未使用
    }

    @Override
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        // 未使用
    }
}

package nia.util;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Security;

/**
 * Author: 王俊超
 * Date: 2017-10-12 23:03
 * Blog: http://blog.csdn.net/derrantcm
 * Github: https://github.com/wang-jun-chao
 * All Rights Reserved !!!
 */
public final class BogusSslContextFactory {
    private static final String PROTOCOL = "TLS";
    private static final SSLContext SERVER_CONTEXT;

    static {
        String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SumX509";
        }

        SSLContext serverContext = null;
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(BogusKeyStore.asInputStream(), BogusKeyStore.getKeyStorePassword());

            // 设置密钥管理工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            kmf.init(ks, BogusKeyStore.getCertificatePassword());

            // 初始化SSLContext
            serverContext = SSLContext.getInstance(PROTOCOL);
            serverContext.init(kmf.getKeyManagers(), null, null);

        } catch (Exception e) {
            throw new Error("Failed to initialize the server-side SSLContext", e);
        }

        SERVER_CONTEXT = serverContext;
    }

    private BogusSslContextFactory() {
        // 未使用
    }

    public static SSLContext getServerContext() {
        return SERVER_CONTEXT;
    }
}

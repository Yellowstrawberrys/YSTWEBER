package cf.yellowstrawberry.ystweber.HttpSecureConnection.tls;

import java.security.SecureRandom;

public class StaticSecureRandom extends SecureRandom {

    private final byte[] privateKey;

    public StaticSecureRandom(byte[] privateKey) {
        this.privateKey = privateKey.clone();
    }

    @Override
    public void nextBytes(byte[] bytes) {
        System.arraycopy(privateKey, 0, bytes, 0, privateKey.length);
    }

}
package cf.yellowstrawberry.ystweber.HttpSecureConnection.tls;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.NamedParameterSpec;

public class KeyManager {

    public byte[] pub;
    public byte[] privateKey;

    public KeyManager() {
        try {
            generate();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generate() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException, IOException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("XDH");
        NamedParameterSpec paramSpec = new NamedParameterSpec("X25519");
        kpg.initialize(paramSpec);
        KeyPair kp = kpg.generateKeyPair();

        privateKey = new byte[32];
        System.arraycopy(kp.getPrivate().getEncoded(), 16, privateKey, 0, 32);

        pub = new byte[32];
        System.arraycopy(kp.getPublic().getEncoded(), 12, pub, 0, 32);
    }
}

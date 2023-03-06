package cf.yellowstrawberry.ystweber.HttpSecureConnection.tls;

import cf.yellowstrawberry.ystweber.utils.ByteUtils;

import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHPublicKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.NamedParameterSpec;
import java.util.Arrays;
import java.util.Base64;

public class KeyManager {

    public byte[] pub;
    public byte[] privateKey;
    public byte[] param;

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

    private void generate() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException, IOException {
//        KeyPairGenerator kpg = KeyPairGenerator.getInstance("XDH");
//        NamedParameterSpec paramSpec = new NamedParameterSpec("X25519");
//        kpg.initialize(paramSpec);
//        KeyPair kp = kpg.generateKeyPair();
//        dhPub = (DHPublicKey) kp.getPublic();
//
//        privateKey = new byte[32];
//        System.arraycopy(kp.getPrivate().getEncoded(), 16, privateKey, 0, 32);
//
//        pub = new byte[32];
//        System.arraycopy(dhPub.getEncoded(), 12, pub, 0, 32)
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDH");
        DHPublicKey originalDhPubKey;
        KeyPair kp;
        BigInteger p, g, y;

        kpg.initialize(1024);
        kp = kpg.generateKeyPair();

        originalDhPubKey = (DHPublicKey) kp.getPublic();

        pub = new byte[32];
        System.out.println(originalDhPubKey.getEncoded().length);

        System.arraycopy(kp.getPublic().getEncoded(), originalDhPubKey.getEncoded().length-32, pub, 0, 32);
        System.out.println(new String(Base64.getEncoder().encode(originalDhPubKey.getEncoded())));

        // get P, G and Y specs
        p = originalDhPubKey.getParams().getP();
        g = originalDhPubKey.getParams().getG();
        y = originalDhPubKey.getY();

        generateParam(p.toByteArray(), g.toByteArray(), y.toByteArray());
    }

    private void generateParam(byte[] p, byte[] g, byte[] ys) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] result = new byte[p.length+g.length+ys.length+6];

        System.arraycopy(ByteUtils.getIntegerAsByteArray(2, p.length), 0, result, 0, 2);
        System.arraycopy(p, 0, result, 2, p.length);
        System.arraycopy(ByteUtils.getIntegerAsByteArray(2, g.length), 0, result, p.length+2, 2);
        System.arraycopy(g, 0, result, p.length+4, g.length);
        System.arraycopy(ByteUtils.getIntegerAsByteArray(2, ys.length), 0, result, p.length+g.length+4, 2);
        System.arraycopy(ys, 0, result, p.length+g.length+6, ys.length);

        param = result;
    }
}

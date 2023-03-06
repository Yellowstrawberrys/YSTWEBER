package cf.yellowstrawberry.ystweber.HttpSecureConnection.tls;

import cf.yellowstrawberry.ystweber.utils.ByteUtils;
import org.bouncycastle.jcajce.interfaces.XDHPublicKey;

import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHPublicKeySpec;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.NamedParameterSpec;

public class KeyManager {

    public byte[] pub;
    public byte[] privateKey;
    public byte[] param;

    private PublicKey dhPub;

    public KeyManager() {
        try {
            generate();
            generateParam();
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
        dhPub = kp.getPublic();

        privateKey = new byte[32];
        System.arraycopy(kp.getPrivate().getEncoded(), 16, privateKey, 0, 32);

        pub = new byte[32];
        System.arraycopy(dhPub.getEncoded(), 12, pub, 0, 32);
    }

    private void generateParam() throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory kf = KeyFactory.getInstance("XDH");
        DHPublicKeySpec dhkeySpec = kf.getKeySpec(dhPub, DHPublicKeySpec.class);

        byte[] p = dhkeySpec.getP().toByteArray();
        byte[] g = dhkeySpec.getG().toByteArray();
        byte[] ys = dhkeySpec.getY().toByteArray();

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

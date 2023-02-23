package cf.yellowstrawberry.ystweber.HttpSecureConnection;

import java.io.*;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class CertificateManager {

    private byte[] certificate;
    private byte[] publicKey;
    private byte[] privateKey;
    private PrivateKey pk;

    public CertificateManager(File certificateKeyFile, File publicKeyFile, File privateKeyFile) {
        try {
            certificate = readFile(certificateKeyFile);
            privateKey = readFile(privateKeyFile);
            publicKey = readFile(publicKeyFile);


            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            pk = keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generatePublicKey(byte[] random) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("x25519");

        kpg.initialize(new NamedParameterSpec("x25519"), new StaticSecureRandom(random));
        return kpg.generateKeyPair().getPublic().getEncoded();
    }

    private byte[] readFile(File f) throws IOException {
        FileInputStream ipt = new FileInputStream(f);
        return ipt.readAllBytes();
    }

    public byte[] getCertificate() {
        return certificate;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public int getCertificateLength() {
        return certificate.length;
    }

    public int getPublicKeyLength() {
        return publicKey.length;
    }

    public int getPrivateKeyLength() {
        return privateKey.length;
    }

    public byte[] sign(String algorithm, byte[] bytes) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] id = new byte[] { 0x30, 0x31, 0x30, 0x0d, 0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01, 0x05, 0x00, 0x04, 0x20 };
        byte[] derDigestInfo = new byte[id.length + bytes.length];
        System.arraycopy(id, 0, derDigestInfo, 0, id.length);
        System.arraycopy(bytes, 0, derDigestInfo, id.length, bytes.length);

        Signature sig = Signature.getInstance(algorithm);
        sig.initSign(pk);
        sig.update(bytes);

        return sig.sign();
    }
}

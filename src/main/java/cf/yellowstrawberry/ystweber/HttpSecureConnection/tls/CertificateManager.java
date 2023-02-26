package cf.yellowstrawberry.ystweber.HttpSecureConnection.tls;

import java.io.*;
import java.security.*;
import java.security.spec.*;

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

//    public PublicKey generatePublicKeyFromPrivate(PrivateKey privateKey) throws GeneralSecurityException {
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("X25519");
//        keyPairGenerator.initialize(new NamedParameterSpec("X25519"), new StaticSecureRandom(getScalar(privateKey)));
//        return keyPairGenerator.generateKeyPair().getPublic();
//    }

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

    public byte[] sign(String algorithm, byte[]... bytes) throws Exception {
        Signature signature = Signature.getInstance(algorithm, "BC");
        signature.initSign(pk);
        for(byte[] b : bytes) {
            signature.update(b);
        }
        return signature.sign();
    }
}

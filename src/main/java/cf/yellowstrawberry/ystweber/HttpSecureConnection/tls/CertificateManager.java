package cf.yellowstrawberry.ystweber.HttpSecureConnection.tls;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPublicKey;

public class CertificateManager {
    static KeyLoader loader = new KeyLoader();
    private byte[] certificate;
    private byte[] publicKey;
    private byte[] privateKey;
    private PrivateKey pk;
    private Certificate cert;
    private RSAPublicKey pub;

    MessageDigest md;

    public CertificateManager(File certificateKeyFile, File publicKeyFile, File privateKeyFile) {
        try {
            pk = loader.loadPrivateKeyFromFile(privateKeyFile);
            cert = loader.loadCertificateFromFile(certificateKeyFile);
            pub = (RSAPublicKey) loader.loadPublicKeyFromFile(publicKeyFile);

            certificate = cert.getEncoded();
            privateKey = pk.getEncoded();
            publicKey = pub.getEncoded();
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public byte[] sign(String algorithm, byte[] bytesArray) throws Exception {
        byte[] mod = pub.getModulus().toByteArray();
        byte[] exp = pub.getPublicExponent().toByteArray();
        byte[] fInal = new byte[mod.length+exp.length+bytesArray.length];
        System.arraycopy(mod, 0, fInal, 0, mod.length);
        System.arraycopy(exp, 0, fInal, mod.length, exp.length);
        System.arraycopy(bytesArray, 0, fInal, mod.length+exp.length, bytesArray.length);
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(pk);
        signature.update(md.digest(bytesArray));
        byte[] s = signature.sign();
        return s;
    }

    public boolean verify(String algorithm, byte[] sign, byte[] bytesArray) throws Exception {
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(cert);
        signature.update(md.digest(bytesArray));
        return signature.verify(sign);
    }
}

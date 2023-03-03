package cf.yellowstrawberry.ystweber.HttpSecureConnection.tls;

import org.bouncycastle.util.encoders.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyLoader {

    public PublicKey loadPublicKeyFromFile(File f) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if(f.getAbsolutePath().endsWith(".pem")) return loadPublicKeyFromFileRSA(f);
        else return null;
    }

    public PrivateKey loadPrivateKeyFromFile(File f) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if(f.getAbsolutePath().endsWith(".pem")) return loadPrivateKeyFromFileRSA(f);
        else return null;
    }

    public X509Certificate loadCertificateFromFile(File f) throws CertificateException, FileNotFoundException {
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        X509Certificate cer = (X509Certificate) fact.generateCertificate(new FileInputStream(f));
        return cer;
    }

    private RSAPublicKey loadPublicKeyFromFileRSA(File f) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if(!f.exists()) throw new FileNotFoundException(f.getAbsolutePath()+" does not exists.");

        byte[] encoded = Base64.decode(
                new String(Files.readAllBytes(f.toPath()))
                        .replaceFirst("-----BEGIN PUBLIC KEY-----", "")
                        .replaceAll(System.lineSeparator(), "")
                        .replaceFirst("-----END PUBLIC KEY-----", "")
        );

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    private RSAPrivateKey loadPrivateKeyFromFileRSA(File f) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if(!f.exists()) throw new FileNotFoundException(f.getAbsolutePath()+" does not exists.");

        byte[] encoded = Base64.decode(
                new String(Files.readAllBytes(f.toPath()))
                        .replaceFirst("-----BEGIN PRIVATE KEY-----", "")
                        .replaceAll(System.lineSeparator(), "")
                        .replaceFirst("-----END PRIVATE KEY-----", "")
        );

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
}

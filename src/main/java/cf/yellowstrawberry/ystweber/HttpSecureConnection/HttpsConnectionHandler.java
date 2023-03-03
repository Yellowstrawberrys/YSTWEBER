package cf.yellowstrawberry.ystweber.HttpSecureConnection;

import cf.yellowstrawberry.ystweber.HttpSecureConnection.tls.CertificateManager;
import cf.yellowstrawberry.ystweber.HttpSecureConnection.tls.KeyManager;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class HttpsConnectionHandler extends Thread {

    final Socket soc;
    final BufferedInputStream ipt;
    final OutputStream out;
    final CertificateManager certManager;
    final KeyManager keyman;


    public HttpsConnectionHandler(Socket soc, CertificateManager certManager) throws IOException {
        this.soc = soc;
        this.ipt = new BufferedInputStream(soc.getInputStream());
        this.out = soc.getOutputStream();
        this.certManager = certManager;
        new Random().nextBytes(serverRandom);
        this.keyman = new KeyManager();
        pub = keyman.pub.clone();
    }

    @Override
    public void run() {
        try {
            byte[] b = new byte[ipt.available()];
            ipt.read(b);
            if(handshake(b)){
                System.out.println("Client handshake passed");
                sendHandShake();
                sendCert();
                serverKeyExchange();
                sendServerHelloDone();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        this.interrupt();
    }
    byte[] pub;
    byte[] signature = new byte[256];

    byte[] serverRandom = new byte[32];

    byte[] clientRandom = new byte[32];
    byte sessionID = (byte) 0;

    public boolean handshake(byte[] b) throws RuntimeException {
        if(b.length == 0) return false;
        for (int i=0; i < 11; i++) {
            if(FixedValues.c_tls12[i] != ((byte) -1) && b[i] != FixedValues.c_tls12[i]) return false;
        }
        //Getting Client Random
        for(int i=11; i<43; i++) {
            clientRandom[i-11] = b[i];
        }
        try {
            ByteArrayOutputStream opt = new ByteArrayOutputStream();
            opt.write(clientRandom.clone());
            opt.write(serverRandom.clone());
            opt.write(new byte[]{0x03, 0x00, 0x1d});
            opt.write(pub.clone());

            byte[] data = opt.toByteArray();

            signature = certManager.sign("SHA256withRSA", data.clone());
            System.out.println(certManager.verify("SHA256withRSA", signature, data.clone()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(b[44]!=((byte) 0)){
            sessionID = b[44];
        }
        return true;
    }

    private void sendHandShake() throws IOException {
        byte[] format = FixedValues.s_tls12Hello.clone();
        for(int i=11; i<43; i++) {
            format[i] = serverRandom[i-11];
        }
        format[43] = 0x00;

        out.write(format);
        out.flush();
    }

    private void sendCert() throws IOException {
        byte[] format = FixedValues.s_tls12Cert.clone();
        byte[] length = ByteBuffer.allocate(4).putInt(10+certManager.getCertificateLength()).array();

        format[3] = length[2];
        format[4] = length[3];

        length = ByteBuffer.allocate(4).putInt(6+certManager.getCertificateLength()).array();

        format[6] = length[1];
        format[7] = length[2];
        format[8] = length[3];

        length = ByteBuffer.allocate(4).putInt(3+certManager.getCertificateLength()).array();
        format[9] = length[1];
        format[10] = length[2];
        format[11] = length[3];

        length = ByteBuffer.allocate(4).putInt(certManager.getCertificateLength()).array();
        format[12] = length[1];
        format[13] = length[2];
        format[14] = length[3];

        System.out.println(FixedValues.s_tls12Cert.length);

        out.write(joinByteArray(format, certManager.getCertificate()));
        out.flush();
    }

    private void serverKeyExchange() throws IOException {
        byte[] format = FixedValues.s_tls12KEX.clone();
        byte[] length = ByteBuffer.allocate(4).putInt(8+pub.length+signature.length+4).array();

        format[3] = length[2];
        format[4] = length[3];

        length = ByteBuffer.allocate(4).putInt(4+pub.length+signature.length+4).array();

        format[6] = length[1];
        format[7] = length[2];
        format[8] = length[3];

        format = joinByteArray(format, pub);
        format = joinByteArray(format, joinByteArray(new byte[]{0x04, 0x01, 0x01, 0x00}, signature));

        out.write(format);
        out.flush();
    }

    private void sendServerHelloDone() throws IOException {
        out.write(FixedValues.s_tls12HelloDone.clone());
        out.flush();
    }

    private byte[] joinByteArray(byte[] a, byte[] b) {
        byte[] r = new byte[a.length+b.length];
        System.arraycopy(a, 0, r, 0, a.length);
        System.arraycopy(b, 0, r, a.length, b.length);
        return r;
    }

    // FOR DEBUGGING
    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}

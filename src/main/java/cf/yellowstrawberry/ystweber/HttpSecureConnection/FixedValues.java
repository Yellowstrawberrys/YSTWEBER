package cf.yellowstrawberry.ystweber.HttpSecureConnection;

/*
* Fixed Values in bytes
*
* -1 is meaning 'this value is not fixed value'
*
* */

import java.util.HexFormat;

public class FixedValues {

    public static byte[] c_tls12 = new byte[]{
            0x16, 0x03, 0x01, -1, -1, //RECORD HEADER
            0x01, -1, -1, -1, //HANDSHAKE HEADER
            0x03, 0x03, // CLIENT VERSION
            //SKIPPING Client Random
            -1
            //SKIPPING Cipher Suites and Extensions
    };

    public static byte[] s_tls12Hello = new byte[] {
            0x16, 0x03, 0x03, 0x00, 0x31, // RECORD HEADER
            0x02, 0x00, 0x00, 0x2d, //HANDSHAKE HEADER
            0x03, 0x03, //SERVER VERSION
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, // SERVER RANDOM
            0x00, // SESSION ID
            (byte) 0xc0, 0x13, // Cipher Suite
            0x00, // Compression Method
            0x00, 0x05, // Extension Length
            (byte) 0xff, 0x01, 0x00, 0x01, 0x00 //Extension - Renegotiation Info
    };

    public static byte[] s_tls12Cert = new byte[] {
            0x16, 0x03, 0x03, -1, -1, // Record Header
            0x0b, -1, -1, -1, // Handshake Header
            -1, -1, -1, // Certificates Length
            -1, -1, -1 // Certificate Length (Java detect length as 15 but If I count it's 16)
    };

    public static byte[] s_tls12KEX = new byte[] {
            0x16, 0x03, 0x03, -1, -1, // Record Header
            0x0c, -1, -1, -1, // Handshake Header
            0x03, 0x00, 0x1d, // Curve Info (Curve x25519)
            0x20 // pub length
            //PUB-KEY
            //Signature
    };

    public static byte[] s_tls12HelloDone = new byte[] {
            0x16, 0x03, 0x03, 0x00, 0x04,
            0x0e, 0x00, 0x00, 0x00
    };
}
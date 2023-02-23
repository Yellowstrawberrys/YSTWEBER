package cf.yellowstrawberry.ystweber.HttpSecureConnection.CipherSuites;

import java.util.HashMap;
import java.util.Map;

public class CipherSuite {

    public static Map<byte[], CipherSuite> cipherSuites = Map.ofEntries(
            Map.entry(new byte[]{(byte) 204, (byte) 168}, new CipherSuite(
                    new byte[]{(byte) 204, (byte) 168},
                    KeyExchangeAlgorithm.ECDHE,
                    KeyExchangeAlgorithm.RSA,
                    PassAlgorithm.CHACHA20,
                    "",
                    BlockPassType.POLY1305,
                    "SHA256"
            ))
    );



    public CipherSuite(byte[] id, KeyExchangeAlgorithm keyExchangeAlgorithm, KeyExchangeAlgorithm AuthAlgorithm, PassAlgorithm PassAlgorithm, String PassAlgorithmI, BlockPassType blockPassType, String HashAlgorithm) {

    }
}

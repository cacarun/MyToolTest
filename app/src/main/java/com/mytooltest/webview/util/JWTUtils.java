package com.mytooltest.webview.util;


import com.mytooltest.util.GsonUtil;
import com.mytooltest.webview.data.ClientToJsBaseData;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtils {

    //private static final String SECRET = "XX#$%()(#*!()!KL<><MQQJQK sdfkjsd34545fdf>?N<:{LWPW";
    static String privateKey = "XXXXXXXXXXX";
    private static final String publicKey = "XXXXXXXXXXXXX";

    private static final String EXP = "exp";
    private static final String PAYLOAD = "payload";

    //加密，传入一个对象和有效期
    public static String sign(Object object, long maxAge) {
        String retStr = null;
        String info = GsonUtil.parseBeanToStr(object);
        Map<String, Object> claims = new HashMap<>();
        claims.put(EXP, System.currentTimeMillis() + maxAge);
        claims.put(PAYLOAD, info);
        byte[] encodedKey = android.util.Base64.decode(privateKey, android.util.Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
        try {

            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privKey = kf.generatePrivate(keySpec);

            retStr = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.RS256, getPrivateKey(privateKey))
                    .compact();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retStr;
    }


    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = android.util.Base64.decode(key, android.util.Base64.DEFAULT);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = android.util.Base64.decode(key, android.util.Base64.DEFAULT);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    public static ClientToJsBaseData getClientToJsBaseData() {
        ClientToJsBaseData clientToJsBaseData = new ClientToJsBaseData();
        clientToJsBaseData.jwtToken = sign(clientToJsBaseData, 6000 * 60 * 1000);
        return clientToJsBaseData;

    }

    public static ClientToJsBaseData getClientToJsBaseData(ClientToJsBaseData clientToJsBaseData) {
        clientToJsBaseData.jwtToken = sign(clientToJsBaseData, 6000 * 60 * 1000);
        return clientToJsBaseData;

    }

    public static void main(String args[]) throws Exception {

       /* PublicKey publicKey1 = getPublicKey(publicKey);
        String jwt ="XXXXXXX";
        JWTVerifier verifier = new JWTVerifier(publicKey1);
        Map<String,Object> claims= verifier.verify(jwt);
        System.out.println(claims);*/


//        PointInfo info = new PointInfo();
//        info.setGrade(1);
//        System.out.println(sign(info, 1000));
//        System.out.println( unsign(sign(info, 1000), PointInfo.class));

    }
}

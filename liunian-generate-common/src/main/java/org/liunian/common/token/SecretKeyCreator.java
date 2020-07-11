package org.liunian.common.token;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 生成公钥和秘钥
 */
public class SecretKeyCreator {

    private static final String KEY_ALGORITHM = "RSA";

    private String strPublicKey;
    private String strPrivateKey;

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public String getStrPublicKey() {
        return strPublicKey;
    }

    public String getStrPrivateKey() {
        return strPrivateKey;
    }

    //获得公钥
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    //获得私钥
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    //解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    //使用KeyPairGenerator 生成公私钥，存放于map对象中
    public static SecretKeyCreator getSecretKeyCreator() throws Exception {
        /* RSA算法要求有一个可信任的随机数源 */
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);

        //通过对象 KeyPairGenerator 生成密匙对 KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        SecretKeyCreator secretKeyCreator = new SecretKeyCreator();
        secretKeyCreator.publicKey = (RSAPublicKey) keyPair.getPublic();
        secretKeyCreator.privateKey = (RSAPrivateKey) keyPair.getPrivate();

        secretKeyCreator.strPublicKey = encryptBASE64(secretKeyCreator.publicKey.getEncoded());
        secretKeyCreator.strPrivateKey = encryptBASE64(secretKeyCreator.privateKey.getEncoded());
        return secretKeyCreator;
    }

}

package org.liunian.common.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.liunian.common.ComUtils;
import org.liunian.common.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 基于Token(令牌)实现分布式Session功能
 */
public class JwtUtils {

    private static final String ISSUER = "WUYUWEI_BACK_API";
    private static SecretKeyCreator secretKeyCreator;

    static {
        try {
            secretKeyCreator = SecretKeyCreator.getSecretKeyCreator();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /*------------------------------Using RS256---------------------------------*/
    /*获取签发的token，返回给前端*/
    public static String createTokenByRS256(String identity) {
        try {
            Algorithm algorithm = Algorithm.RSA256(secretKeyCreator.getPublicKey(), secretKeyCreator.getPrivateKey());
            return createToken(algorithm, identity);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /*获取签发的token，返回给前端*/
    public static String createToken() {
        try {
            Algorithm algorithm = Algorithm.RSA256(secretKeyCreator.getPublicKey(), secretKeyCreator.getPrivateKey());
            return createToken(algorithm);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /*签发token*/
    private static String createToken(Algorithm algorithm, Object data) {
        String[] audience = {"app", "web"};
        return JWT.create()
                .withIssuer(ISSUER)        //发布者
                .withAudience(audience)     //观众，相当于接受者
                .withIssuedAt(new Date())   // 生成签名的时间
                .withExpiresAt(DateUtils.offset(new Date(), 30, Calendar.MINUTE))    // 生成签名的有效期
                .withClaim("data", ComUtils.str(data)) //存数据
                .withNotBefore(new Date())  //生效时间
                .withJWTId(UUID.randomUUID().toString())    //编号
                .sign(algorithm);                            //签入
    }

    /*签发token*/
    private static String createToken(Algorithm algorithm) {
        String[] audience = {"app", "web"};
        return JWT.create()
                .withIssuer(ISSUER)        //发布者
                .withAudience(audience)     //观众，相当于接受者
                .withIssuedAt(new Date())   // 生成签名的时间
                .withExpiresAt(DateUtils.offset(new Date(), 30, Calendar.MINUTE))    // 生成签名的有效期
                .withNotBefore(new Date())  //生效时间
                .withJWTId(UUID.randomUUID().toString())    //编号
                .sign(algorithm);                            //签入
    }

    /*验证token*/
    public static boolean verifyToken(String token) {
        try {
            //其实按照规定只需要传递 publicKey 来校验即可，这可能是auth0 的缺点
            Algorithm algorithm = Algorithm.RSA256(secretKeyCreator.getPublicKey(), secretKeyCreator.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance 可复用的验证实例
            verifier.verify(token);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static DecodedJWT parseToken(String token) {
        try {
            //其实按照规定只需要传递 publicKey 来校验即可，这可能是auth0 的缺点
            Algorithm algorithm = Algorithm.RSA256(secretKeyCreator.getPublicKey(), secretKeyCreator.getPrivateKey());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build(); //Reusable verifier instance 可复用的验证实例
            return verifier.verify(token);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

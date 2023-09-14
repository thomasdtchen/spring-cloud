package thomas.spring;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {
    //7 days expire
    private static long expire = 604800;
    //32 bit secret
    private static String secret = "abcdfghiabcdfghiabcdfghiabcdfghi";

    public static String generateToken(String username, String authorities){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * expire);
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(username)
                .setIssuer("System")
                .setIssuedAt(now)
                .setAudience("System_Audience")
                .setExpiration(expiration)
                .claim("granted", authorities)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static Claims getClaimsByToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}

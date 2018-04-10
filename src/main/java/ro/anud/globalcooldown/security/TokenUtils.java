package ro.anud.globalcooldown.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ro.anud.globalcooldown.security.model.SecurityRole;
import ro.anud.globalcooldown.security.model.SpringSecurityUser;

import java.util.*;

@Component
public class TokenUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtils.class);
	private static final String AUDIENCE_WEB = "web";

	private final String secretKey;
	private final Long validity;

	public TokenUtils(@Value("${security.authentication.jwt.secretKey}") final String secretKey,
	                  @Value("${security.authentication.jwt.tokenValidity}") final Long validity) {
		this.secretKey = Objects.requireNonNull(secretKey, "secretKey must not be null");
		this.validity = Objects.requireNonNull(validity, "validity must not be null");
	}

	public String getUsernameFromToken(final String token) {
		String username = null;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			if (claims != null) {
				username = claims.getSubject();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return username;
	}

	public Date getExpirationDateFromToken(final String token) {
		Date expiration = null;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			if (claims != null) {
				expiration = claims.getExpiration();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return expiration;
	}

	public String generateToken(final SpringSecurityUser userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", userDetails.getUsername());
		claims.put("id", userDetails.getId());
		claims.put("audience", AUDIENCE_WEB);
		claims.put("created", this.generateCurrentDate());
		claims.put("role", getRole(userDetails));
		return this.generateToken(claims);
	}

	public Boolean validateToken(final String token, final UserDetails userDetails) {
		SpringSecurityUser user = (SpringSecurityUser) userDetails;
		final String username = this.getUsernameFromToken(token);
		return (username.equals(user.getUsername())
				&& !(this.isTokenExpired(token)));
	}

	private Claims getClaimsFromToken(final String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(this.secretKey)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.validity * 1000);
	}

	private Boolean isTokenExpired(final String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(this.generateCurrentDate());
	}

	private String getRole(final UserDetails userDetails) {
		List<SecurityRole> authoritiesList = new ArrayList(userDetails.getAuthorities());
		return authoritiesList.get(0).getAuthority();
	}

	private String generateToken(final Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(this.generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, this.secretKey)
				.compact();
	}
}

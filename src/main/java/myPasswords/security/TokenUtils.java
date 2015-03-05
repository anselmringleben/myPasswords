package myPasswords.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
	private final String MAGIC_KEY = "obfuscate";
	private final String SEPARATOR = ":";

	public String createToken(UserDetails userDetails) {
		long expires = System.currentTimeMillis() + 1000L * 60 * 60;

		return userDetails.getUsername() + this.SEPARATOR + expires
				+ this.SEPARATOR + computeSignature(userDetails, expires);
	}

	private String computeSignature(UserDetails userDetails, long expires) {
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(userDetails.getUsername()).append(
				this.SEPARATOR);
		signatureBuilder.append(expires).append(this.SEPARATOR);
		signatureBuilder.append(userDetails.getPassword()).append(
				this.SEPARATOR);
		signatureBuilder.append(this.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encode(digest.digest(signatureBuilder.toString()
				.getBytes())));
	}

	public String getUserNameFromToken(String authToken) {
		if (null == authToken)
			return null;
		
		String[] parts = authToken.split(this.SEPARATOR);
		return parts[0];
	}

	public boolean validateToken(String authToken, UserDetails userDetails) {
		String[] parts = authToken.split(this.SEPARATOR);
		long expires = Long.parseLong(parts[1]);
		String signature = parts[2];
		String signatureToCompare = computeSignature(userDetails, expires);

		return expires >= System.currentTimeMillis()
				&& signature.equals(signatureToCompare);
	}
}

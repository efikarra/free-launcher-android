package app.competitions.spring.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

public class TokenProvider {

	public static final String MAGIC_KEY = "obfuscate";


	public static String createToken(MyUserDetails userDetails)
	{
		/* Expires in one hour */
		long expires = System.currentTimeMillis() + 1000L * 60 * 60;

		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(userDetails.getUsername());
		tokenBuilder.append(":");
		tokenBuilder.append(userDetails.getUser().getUserId());
		tokenBuilder.append(":");
		tokenBuilder.append(userDetails.getAuthorities().get(0).getAuthority());
		tokenBuilder.append(":");
		tokenBuilder.append(expires);
		tokenBuilder.append(":");
		tokenBuilder.append(TokenProvider.computeSignature(userDetails, expires));

		return tokenBuilder.toString();
	}


	public static String computeSignature(MyUserDetails userDetails, long expires)
	{
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(userDetails.getUsername());
		signatureBuilder.append(":");
		signatureBuilder.append(userDetails.getUser().getUserId());
		signatureBuilder.append(":");
		signatureBuilder.append(userDetails.getAuthorities().get(0).getAuthority());
		signatureBuilder.append(":");
		signatureBuilder.append(expires);
		signatureBuilder.append(":");
		signatureBuilder.append(userDetails.getPassword());
		signatureBuilder.append(":");
		signatureBuilder.append(TokenProvider.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}


	public static String getUserNameFromToken(String authToken)
	{
		if (null == authToken) {
			return null;
		}

		String[] parts = authToken.split(":");
		return parts[0];
	}


	public static boolean validateToken(String authToken, MyUserDetails userDetails)
	{
		String[] parts = authToken.split(":");
		long expires = Long.parseLong(parts[3]);
		String signature = parts[4];

		if (expires < System.currentTimeMillis()) {
			return false;
		}

		return signature.equals(TokenProvider.computeSignature(userDetails, expires));
	}
}

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class methodAES {
	private static final String key = "aesEncryptionKey";
	private static final String initVector = "encryptionIntVec";

	public static String encrypt(String value,String mode) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = null;
			if(mode.equals("CBC")) {
				cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			}
			else if(mode.equals("OFB")) {
				cipher = Cipher.getInstance("AES/OFB/PKCS5PADDING");
			}
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public static String decrypt(String encrypted,String mode) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			Cipher cipher = null;
			if(mode.equals("CBC")) {
				cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			}
			else if(mode.equals("OFB")) {
				cipher = Cipher.getInstance("AES/OFB/PKCS5PADDING");
			}
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	public static void main(String[] args) {
		
	}
	
}

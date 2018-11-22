import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

public class methodDES{    
	public static String desKey = "0123456789abcdef0123456789abcdef0123456789abcdef";
	public static byte[] iv = new byte[8];
	public static byte[] keyBytes = DatatypeConverter.parseHexBinary(desKey);
	public static IvParameterSpec iv2 = new IvParameterSpec(iv);
	

	public static String encryptDES(String text,String mode) {
		try {
			DESKeySpec dks = new DESKeySpec(desKey.getBytes());
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey desKey = skf.generateSecret(dks);
			Cipher cipher = null;
			if(mode.equals("CBC")) {
				cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			}
			else if(mode.equals("OFB")) {
				cipher = Cipher.getInstance("DES/OFB/PKCS5Padding");
			}
			cipher.init(Cipher.ENCRYPT_MODE,desKey,iv2);
			byte[] encrypted = cipher.doFinal(text.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String decryptDes(String crypto,String mode) {
		try {
			DESKeySpec dks = new DESKeySpec(desKey.getBytes());
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey desKey = skf.generateSecret(dks);
			Cipher decipher = null;
			if(mode.equals("CBC")) {
				decipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			}
			else if(mode.equals("OFB")) {
				decipher = Cipher.getInstance("DES/OFB/PKCS5Padding");
			}
			decipher.init(Cipher.DECRYPT_MODE,desKey,iv2);
			byte[] original = decipher.doFinal(Base64.getDecoder().decode(crypto));
			return new String(original);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public static void main(String[] argv)  {
	


	}
}
package es.unileon.ulebank;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Codificador {

	private String translateToMD5(
			String pass)
	{
		String passw = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");	
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}		
		md.reset();
		md.update(pass.getBytes());
		byte[] mb = md.digest();
		passw = translateToHex(mb);
		return passw;
	}
	
	//M�todo para transformar el array de bytes en una cadena hexadecimal
	private static String translateToHex(byte buf[]) {
	    StringBuilder strbuf = new StringBuilder(buf.length * 2);
	    for (int i = 0; i < buf.length; i++) {
	        if (((int) buf[i] & 0xff) < 0x10) {
	            strbuf.append("0");
	        }
	        strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
	    }
	    return strbuf.toString();
	}
	
	public String encriptarPass(String pass){
		return translateToMD5(pass);
		
	}
}

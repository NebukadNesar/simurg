package com.example.helper;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import android.util.Base64;

public class DESedeEncryption {

	private static final String UNICODE_FORMAT = "UTF8";
	private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private KeySpec myKeySpec;
	private SecretKeyFactory mySecretKeyFactory;
	private Cipher cipher;
	private byte[] keyAsBytes;
	private String myEncryptionKey;
	private String myEncryptionScheme;
	private SecretKey key;

	public DESedeEncryption() throws Exception {
		myEncryptionKey = "<j5/48<3X36Eb=g>xTG13{o{I`^zAKHMqp/5ofx{q2:d]1.1U45/b1GgiV/:5";
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		myKeySpec = new DESedeKeySpec(keyAsBytes);
		mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = mySecretKeyFactory.generateSecret(myKeySpec);
	}

	/**
	 * Method To Encrypt The String
	 */
	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = Base64.encodeToString(encryptedText,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	/**
	 * Method To Decrypt the Ecrypted String
	 */
	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decode(encryptedString,0);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = bytes2String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

	/**
	 * Returns String From An Array Of Bytes
	 */
	private static String bytes2String(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}
}

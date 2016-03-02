package com.crack.me.active;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

public class RSAKey {
	public static String PRIVATE_KEY_FILENAME = "privateKey.bytes";
	public static String PUBLIC_KEY_FILENAME = "publicKey.bytes";

	public void generateKeyFile() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();

			PublicKey pubkey = keyPair.getPublic();

			PrivateKey prikey = keyPair.getPrivate();

			saveFile(new File(PUBLIC_KEY_FILENAME), pubkey.getEncoded());
			saveFile(new File(PRIVATE_KEY_FILENAME), prikey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public void saveFile(File file, byte[] data) {
		try {
			FileOutputStream localFileOutputStream = new FileOutputStream(file);
			localFileOutputStream.write(data);
			localFileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String encryption(String text) {
		return encryption("", text);
	}

	public String encryption(String privateKeyFilename, String text) {
		if ((privateKeyFilename == null) || ("".equals(privateKeyFilename))) {
			privateKeyFilename = PRIVATE_KEY_FILENAME;
		}
		return encryption(new File(privateKeyFilename), text);
	}

	public String encryption(File privateKeyFile, String text) {
		String enText = null;
		try {
			if ((privateKeyFile == null) || (!privateKeyFile.exists())) {
				privateKeyFile = new File(PRIVATE_KEY_FILENAME);
			}
			InputStream in = new FileInputStream(privateKeyFile);
			byte[] data = new byte[in.available()];
			in.read(data);
			in.close();

			Key key = RSAPrivateCrtKeyImpl.newKey(data);

			enText = encryption(key, text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enText;
	}

	public String encryption(Key privateKey, String text) {
		String enText = null;
		if (privateKey != null) {
			try {
				Cipher localCipher = Cipher.getInstance("rsa");
				localCipher.init(1, privateKey);
				byte[] arrayOfByte = localCipher.doFinal(text.getBytes());
				enText = new String(Hex.encodeHex(arrayOfByte));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return enText;
	}

	public String decryption(String text) {
		return decryption("", text);
	}

	public String decryption(String publicKeyFilename, String text) {
		if ((publicKeyFilename == null) || ("".equals(publicKeyFilename))) {
			publicKeyFilename = PUBLIC_KEY_FILENAME;
		}
		return decryption(new File(publicKeyFilename), text);
	}

	public String decryption(File publicKeyFile, String text) {
		String deText = null;
		try {
			if ((publicKeyFile == null) || (!publicKeyFile.exists())) {
				publicKeyFile = new File(PUBLIC_KEY_FILENAME);
			}
			InputStream in = new FileInputStream(publicKeyFile);
			byte[] data = new byte[in.available()];
			in.read(data);
			in.close();

			Key key = new RSAPublicKeyImpl(data);
			deText = decryption(key, text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deText;
	}

	public String decryption(Key publicKey, String text) {
		String deText = null;
		if (publicKey != null) {
			try {
				Cipher rsaCipher = Cipher.getInstance("rsa");
				rsaCipher.init(2, publicKey);
				byte[] handleData = Hex.decodeHex(text.toCharArray());
				byte[] bytes = rsaCipher.doFinal(handleData);
				deText = new String(bytes);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deText;
	}
}

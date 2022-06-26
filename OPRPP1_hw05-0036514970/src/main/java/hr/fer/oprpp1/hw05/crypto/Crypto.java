
package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	public static void main(String[] args) {
		//System.out.println("Working Directory = " + System.getProperty("user.dir"));
		if(args.length < 2) throw new IllegalArgumentException("Unesite minimalno 2 argumenta");

		Scanner sc = new Scanner(System.in);
		String job = args[0];
		switch (job) {
			case "checksha" : {
				if(args.length != 2) throw new IllegalArgumentException("Iza naredbe checksha mora slijediti jedan argument");

				String file = args[1];
				System.out.print("Please provide expected sha-256 digest for "
						+file
						+ ":\n> ");
				String inputSha256 = sc.nextLine();
				String FileSha256= checkSha(file);
				if(inputSha256.equals(FileSha256)) {
					System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
				}else {
					System.out.println("Digesting completed. Digest of " + file + " does "
							+ "not match the expected digest. Digest was: "
							+ FileSha256);
				}
				return;
			}
			case "encrypt", "decrypt" : {
				if(args.length != 3 ) throw new IllegalArgumentException("iza naredbe encrypte mora slijediti dva argumenta");
				String inFile = args[1];
				String outFile = args[2];
				System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
				String password = sc.nextLine();
				System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
				String vector = sc.nextLine();
				encryp_decrypte(inFile, outFile, job, password, vector);
				return;
			}
			default : throw new IllegalArgumentException("Nepoznata naredba: " + job);
		}

	}
	private static String checkSha(String file) {

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");


			try(BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Paths.get(file)))){

				while(true) {
					byte[] buffer = new byte[4096];
					int readedBytes = bis.read(buffer, 0, 4096);

					if(readedBytes == -1) {
						byte[] sha256 = md.digest();
						return Util.bytetohex(sha256);
					}

					md.update(buffer, 0, readedBytes);

				}

			} catch (IOException e) {
				System.err.println("Datoteka "+ file + " nije pronađena");
				return null;
			}
			
		} catch (NoSuchAlgorithmException e1) {
			System.err.println(e1.getMessage());
			return null;
		}
	}




	private static void encryp_decrypte(String inFile, String outFile,
			String job, String password, String vector) {
		Cipher cipher = null;
		try {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(password), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(vector));
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init((job.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE), keySpec, paramSpec);
		}catch (Exception e) {
			System.err.println("Greška u parametrima Cipher");
			return;
		}
		try(BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Paths.get(inFile)))){

			if(!Files.exists(Paths.get(outFile))) Files.createFile(Paths.get(outFile));

			try(BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(Paths.get(outFile)))){

				while(true) {
					byte[] buffer = new byte[4096];
					int readedBytes = bis.read(buffer, 0, 4096);

					if(readedBytes == -1) {
						bos.write(cipher.doFinal());
						break;
					}

					bos.write(cipher.update(buffer, 0, readedBytes));

				}

			} catch (IOException e) {
				System.err.println("Datoteku "+ outFile + " nije moguće otvoriti");
				return;
			} catch (IllegalBlockSizeException e) {
				System.err.println(e.getMessage());
				return;
			} catch (BadPaddingException e) {
				System.err.println(e.getMessage());
				return;
			}

		} catch (IOException e) {
			System.err.println("Datoteka "+ inFile + " nije pronađena");
			return;
		} 
		System.out.println("Decryption completed. Generated file " + outFile + " based on file " + inFile + ".");
	}
}

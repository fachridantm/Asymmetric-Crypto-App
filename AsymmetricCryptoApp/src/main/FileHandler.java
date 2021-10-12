package main;


import java.util.Scanner;
import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ChathuraDR
 */
public class FileHandler {
        private Scanner x;
        
        public void readFile(String path,javax.swing.JTextArea textField){
            
            Scanner sc = new Scanner(System.in);
            BufferedReader br = null;  //used BufferedReader to set Data to the textField line by line that passed as a parameter.
            String line;
            
            try{
                br = new BufferedReader(new FileReader(path));    //FileReader read the content of the file from that given path,and return value pass to the BufferedReader.
                while((line = br.readLine()) != null){            // reading line by line untill end of the lines
                    textField.append(line+'\n');                  //append that line get at a time and move the cursor to the next line
                }
                
                
            }catch(Exception e){
                System.out.println(e);
            }
        }
        
        // function to encrypt the data that given,here's i get 3 parameters.
        public void encrypt(String message, String secretKey,javax.swing.JTextArea ciphertextField) throws Exception {
	
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
		byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		
		SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// Encode the string into bytes using utf-8
		byte[] plainTextBytes = message.getBytes("utf-8");
                // Encrypt
                byte[] buf = cipher.doFinal(plainTextBytes);
                // Encode bytes to base64 to get a string
                byte [] base64Bytes = Base64.encodeBase64(buf);
                String base64EncryptedString = new String(base64Bytes);
	    
                ciphertextField.setText(base64EncryptedString);
	}

	public void decrypt(String encryptedText, String secretKey,javax.swing.JTextArea plainTextField) throws Exception {
	
                byte[] message = Base64.decodeBase64(encryptedText.getBytes("utf-8"));
		
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
		byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		
                SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		Cipher decipher = Cipher.getInstance("DESede");
		decipher.init(Cipher.DECRYPT_MODE, key);
		
		byte[] plainText = decipher.doFinal(message);
		plainTextField.setText(new String(plainText, "UTF-8"));
	}
        
        public void writeFile(String path,javax.swing.JTextArea textField) throws IOException{

            FileWriter fw = new FileWriter(path);               //create a file writer to write to the path
            try (PrintWriter pw = new PrintWriter(fw)) {

                String s[] = textField.getText().split("\\r?\\n"); //get the text fileds characters line by line and put them into an arry so that we can write those text fields as it is.
                ArrayList<String>arrList = new ArrayList<>(Arrays.asList(s)) ;  
                for(int i = 0 ; i < textField.getLineCount(); i++){
                    pw.println(arrList.get(i));    //get the string in that array one by one and write them into the file that opened.
                }
                pw.close(); // close the file when writting is done.
            }catch(Exception e){
                System.out.println(e);
            }
        
        }
        
        
        
}

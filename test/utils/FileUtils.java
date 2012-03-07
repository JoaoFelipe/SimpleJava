/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Joao
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.*;
import java.util.Scanner;

public class FileUtils {

    public static byte[] readFile(File file) throws IOException {

        InputStream is = new FileInputStream(file);

        byte[] bytes = new byte[(int) file.length()];

        int offset = 0;
        int numRead = 0;

        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {

            offset += numRead;
        }

        is.close();

        return bytes;
    }

    public static boolean isFileValid(String file, String digest) {
        return calculateMD5(file).equalsIgnoreCase(digest);
    }
    
    public static String calculateMD5(File file) {

        try {

            byte[] buffer = readFile(file);

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            byte[] thedigest = md.digest(buffer);
            BigInteger bigInt = new BigInteger(1, thedigest);
            String hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Unable to get MD5 Algorithm", ex);
        } catch (IOException e) {
            throw new RuntimeException("Unable to process file for MD5 comparison.", e);
        }
    }
    
    public static String calculateMD5(String file) {
        return calculateMD5(new File(file));
    }

    public static boolean isEqual(String verified, String correct) throws IOException {
        File file = new File(verified);
        File valid = new File(correct);
        Scanner fileScanner = new Scanner(file);
        Scanner validScanner = new Scanner(valid);
        String fileChar;
        String validChar;
//        int line = 0;
        while (validScanner.hasNextLine() && fileScanner.hasNextLine()) {
//            line ++;
            fileChar = fileScanner.nextLine();
            validChar = validScanner.nextLine();
            if (!fileChar.equals(validChar)) {
                fileScanner.close();
                validScanner.close();
                return false;
            }
        }
        
        fileScanner.close();
        validScanner.close();
        return true;

    }
}

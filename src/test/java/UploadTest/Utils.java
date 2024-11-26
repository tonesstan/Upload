package UploadTest;

import java.io.FileInputStream;
import java.io.IOException;

import static org.apache.commons.codec.digest.DigestUtils.*;

public class Utils {

    public static boolean areFilesEqual(String filePath1, String filePath2) throws IOException {
        try (FileInputStream fis1 = new FileInputStream(filePath1);
             FileInputStream fis2 = new FileInputStream(filePath2))
        {
            String hash1 = sha256Hex(fis1);
            String hash2 = sha256Hex(fis2);
            System.out.println("Хэш-сумма первого файла: " + hash1);
            System.out.println("Хэш-сумма второго файла: " + hash2);
            return hash1.equals(hash2);
        }
    }

}
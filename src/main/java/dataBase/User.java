package dataBase;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class User implements Serializable {
    private final String name;
    private final String password;
    private static Scanner in = new Scanner(System.in);
    public User() {
        System.out.print("Введите логин: ");
        this.name = in.nextLine();
        System.out.print("Введите пароль: ");
        this.password = encryptPassword(in.nextLine());
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    private String encryptPassword(final String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-224");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest.update(password.getBytes());
        byte byteBuffer[] = messageDigest.digest();
        StringBuffer strHexString = new StringBuffer();

        for (int i = 0; i < byteBuffer.length; i++) {
            String hex = Integer.toHexString(0xff & byteBuffer[i]);
            if (hex.length() == 1) {
                strHexString.append('0');
            }
            strHexString.append(hex);
        }
        return strHexString.toString();
    }

}

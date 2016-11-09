package com.softgen.gate.provider;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by mahesha on 07-09-16.
 */
public class OTP_Generation {

    public static void main(String[] args) {
        int otpLength = 6;
        int otp = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println("OTP using SecureRandomGenerator: " + (i + 1) + "==>" + generateOTP(otpLength));
            otp = Integer.parseInt(generateOTP(otpLength));
        }
        System.out.println("==========================");
        for (int i = 0; i < 10; i++) {
            otp = Integer.parseInt(generateRandomOTP(otpLength));
            System.out.println("OTP using RandomNumberGenerator: " + (i + 1) + "==>" + generateRandomOTP(otpLength));
        }
    }

    public static String generateOTP(int otpLengthNumber) {
        String otp = new String();
        int otpSample = 0;
        for (int i = 0; i < otpLengthNumber; i++) {
            otp = otp + "9";
        }

        otpSample = Integer.parseInt(otp);

        SecureRandom prng;
        try {
            prng = SecureRandom.getInstance("SHA1PRNG"); //Number Generation Algorithm
            otp = new Integer(prng.nextInt(otpSample)).toString();
            otp = (otp.length() < otpLengthNumber) ? padleft(otp, otpLengthNumber, '0') : otp;
        } catch (NoSuchAlgorithmException e) {
        }

//        If generated OTP exists in DB -regenerate OTP again
        boolean otpExists = false;
        if (otpExists) {
            generateOTP(otpLengthNumber);
        } else {
            return otp;
        }
        return otp;
    }

    public static String padleft(String s, int len, char c) { //Fill with some char or put some logic to make more secure
        s = s.trim();
        StringBuffer d = new StringBuffer(len);
        int fill = len - s.length();
        while (fill-- > 0)
            d.append(c);
        d.append(s);
        return d.toString();
    }

    public static String generateRandomOTP(int otpLengthNumber) {
        String otp = RandomStringUtils.randomNumeric(otpLengthNumber);
        return otp;
    }

}

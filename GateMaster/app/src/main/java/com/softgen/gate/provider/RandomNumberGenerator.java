package com.softgen.gate.provider;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by mahesha on 07-09-16.
 */
public class RandomNumberGenerator {
    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            System.out.println(generateRandomNumber(6));
            System.out.println(generateRandomNumber(12));
        }

    }
    public static String generateRandomNumber(int length){
        String randomNumber="";
        randomNumber = RandomStringUtils.randomNumeric(length);
        return randomNumber;
    }
}

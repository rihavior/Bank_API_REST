package com.rihavior.Bank_API_REST.others;

import java.util.UUID;

public class HashCreator {

    public static void main(String[] args) {
        System.out.println(generateString());
    }

    public static String generateString() {
        return UUID.randomUUID().toString();
    }

}

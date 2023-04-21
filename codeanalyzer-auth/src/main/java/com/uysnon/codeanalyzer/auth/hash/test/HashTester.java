package com.uysnon.codeanalyzer.auth.hash.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.UUID;
import java.util.function.Function;

public class HashTester {
    private static final int TIMES = 1_000_000;

    public static void main(String[] args) {
        System.out.println("example input string: " + generateStringToHash());
        System.out.println("MD5: " + testHashingFunction(DigestUtils::md5Hex) + " ms");
        System.out.println("SHA-1: " + testHashingFunction(DigestUtils::sha1Hex) + " ms");
        System.out.println("SHA-256: " + testHashingFunction(DigestUtils::sha256Hex) + " ms");
        System.out.println("SHA-512: " + testHashingFunction(DigestUtils::sha512Hex) + " ms");
    }

    public static long testHashingFunction(Function<String, String> hashingFunction) {
        StopWatch watch = new StopWatch();
        watch.start();
        for (int i = 0; i < TIMES; i++) {
            hashingFunction.apply(generateStringToHash());
        }
        watch.stop();
        return watch.getTime();
    }

    public static String generateStringToHash() {
        return UUID.randomUUID().toString() + System.currentTimeMillis();
    }
}


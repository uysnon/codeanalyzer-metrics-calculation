package com.uysnon.codeanalyzer.teacherui.service;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class Base64Util {
    public String encodeString(String s){
        return new String(Base64.getEncoder().encode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public String decodeString(String s){
        byte[] decodedBytes = Base64.getDecoder().decode(s);
        String sDecoded = new String(decodedBytes, StandardCharsets.UTF_8);
        return sDecoded;
    }
}

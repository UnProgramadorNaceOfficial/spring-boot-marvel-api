package com.api.marvel.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MD5Encoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence unencodeText) {
        return DigestUtils.md5DigestAsHex(unencodeText.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence unencodeText, String encodedText) {
        return encodedText.equals(this.encode(unencodeText));
    }
}

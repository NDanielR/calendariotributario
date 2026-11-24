package com.dasther.calendariotributario.service.impl;

import com.dasther.calendariotributario.service.EncryptionService;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {
    private static final String PREFIX = "enc:v1:";
    private static final int IV_LENGTH = 12;
    private static final SecureRandom RANDOM = new SecureRandom();
    private final SecretKeySpec key;

    public EncryptionServiceImpl(@Value("${app.security.encryption-key}") String encodedKey) {
        if (encodedKey == null || encodedKey.isBlank()) {
            throw new IllegalStateException("Configure PERSON_CREDENTIALS_KEY with a Base64-encoded 32-byte key");
        }
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(encodedKey);
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException("PERSON_CREDENTIALS_KEY must be valid Base64");
        }
        if (bytes.length != 32) {
            throw new IllegalStateException("PERSON_CREDENTIALS_KEY must contain exactly 32 decoded bytes");
        }
        key = new SecretKeySpec(bytes, "AES");
    }


    @Override
    public String encrypt(String password) {
        if (password == null) {
            return null;
        }
        try {
            byte[] iv = new byte[IV_LENGTH];
            RANDOM.nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));
            byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            byte[] payload = ByteBuffer.allocate(iv.length + encrypted.length)
                    .put(iv).put(encrypted).array();
            return PREFIX + Base64.getEncoder().encodeToString(payload);
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Unable to encrypt portal credential", exception);
        }
    }


    @Override
    public String decrypt(String storedValue) {
        if (storedValue == null) {
            return null;
        }
        try {
            if (!storedValue.startsWith(PREFIX)) {
                throw new IllegalArgumentException("Unsupported credential format; migration required");
            }
            byte[] payload = Base64.getDecoder().decode(storedValue.substring(PREFIX.length()));
            if (payload.length < IV_LENGTH + 16) {
                throw new IllegalArgumentException("Invalid encrypted credential length");
            }
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, payload, 0, IV_LENGTH));
            return new String(cipher.doFinal(payload, IV_LENGTH, payload.length - IV_LENGTH),
                    StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IllegalArgumentException exception) {
            throw new IllegalStateException("Unable to decrypt portal credential", exception);
        }
    }
}


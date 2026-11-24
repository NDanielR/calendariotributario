package com.dasther.calendariotributario.service;

import java.util.Base64;
import com.dasther.calendariotributario.service.impl.EncryptionServiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {
    private final EncryptionService converter =
            new EncryptionServiceImpl(Base64.getEncoder().encodeToString(new byte[32]));

    @Test
    void roundTripUsesRandomIvAndSupportsUnicodeAndEmptyPasswords() {
        for (String password : new String[] {"contraseña-123!", "", "enc:v1:literal"}) {
            String encrypted = converter.encrypt(password);
            assertNotEquals(password, encrypted);
            assertNotEquals(encrypted, converter.encrypt(password));
            assertEquals(password, converter.decrypt(encrypted));
        }
        assertNull(converter.encrypt(null));
        assertNull(converter.decrypt(null));
    }

    @Test
    void rejectsTamperingWrongKeysAndPlaintext() {
        String encrypted = converter.encrypt("test-password");
        byte[] payload = Base64.getDecoder().decode(encrypted.substring("enc:v1:".length()));
        payload[payload.length - 1] ^= 1;
        String tampered = "enc:v1:" + Base64.getEncoder().encodeToString(payload);
        assertThrows(IllegalStateException.class, () -> converter.decrypt(tampered));
        byte[] otherKey = new byte[32];
        otherKey[0] = 1;
        EncryptionService other = new EncryptionServiceImpl(Base64.getEncoder().encodeToString(otherKey));
        assertThrows(IllegalStateException.class, () -> other.decrypt(encrypted));
        for (String invalid : new String[] {"plaintext", "enc:v1:!", "enc:v1:AA=="}) {
            assertThrows(IllegalStateException.class, () -> converter.decrypt(invalid));
        }
    }

    @Test
    void requiresValidExternalKey() {
        for (String invalid : new String[] {null, "", "!", "AA=="}) {
            assertThrows(IllegalStateException.class, () -> new EncryptionServiceImpl(invalid));
        }
    }
}


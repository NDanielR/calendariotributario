package com.dasther.calendariotributario.service;

public interface EncryptionService {
    //Encriptacion de password
    String encrypt(String password);

    //Desencriptacion de password 
    String decrypt(String storedValue);
}

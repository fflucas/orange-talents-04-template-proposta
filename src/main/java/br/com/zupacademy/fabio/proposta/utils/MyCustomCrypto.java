package br.com.zupacademy.fabio.proposta.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MyCustomCrypto implements AttributeConverter<String, String> {

    private static String password;
    private static String salt;

    @Value("${mycustom.crypto.password}")
    private void setPassword(String password){
        MyCustomCrypto.password = password;
    }

    @Value("${mycustom.crypto.salt}")
    public void setSalt(String salt) {
        MyCustomCrypto.salt = salt;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        TextEncryptor textEncryptor = Encryptors.queryableText(password, salt);
        return textEncryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        TextEncryptor textEncryptor = Encryptors.queryableText(password, salt);
        return textEncryptor.decrypt(dbData);
    }

    public static String encrypt(String plainText){
        TextEncryptor textEncryptor = Encryptors.queryableText(password, salt);
        return textEncryptor.encrypt(plainText);
    }
}

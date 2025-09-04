/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package criptor;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author anton
 */
public class Criptografia {
    public static String getSHA256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            byte[] hash = md.digest(texto.getBytes());
            
            BigInteger no = new BigInteger(1, hash);
            
            // converte os bytes para string em hexa
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

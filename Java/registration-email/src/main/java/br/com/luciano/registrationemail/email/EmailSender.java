package br.com.luciano.registrationemail.email;

public interface EmailSender {
    void send(String to, String email);
}

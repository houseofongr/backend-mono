package com.aoo.aar.application.port.out.mail;

public interface SendAuthnCodePort {
    void sendAuthnCode(String emailAddress, String message);
}

package com.example.mdb;

import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import jakarta.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/ExampleQueue")
})
public class MessageProcessorBean implements MessageListener {

    @Resource(lookup = "java:jboss/datasources/SQLServerDS")
    private DataSource dataSource;

    @Override
    public void onMessage(Message message) {
        try (Connection connection = dataSource.getConnection()) {
            String text;
            if (message instanceof TextMessage) {
                text = ((TextMessage) message).getText();
            } else {
                text = message.getBody(String.class);
            }
            PreparedStatement ps = connection.prepareStatement("INSERT INTO messages(content) VALUES (?)");
            ps.setString(1, text);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

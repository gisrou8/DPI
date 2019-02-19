package Chained.Gateway;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Producer;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

public class MessageSenderGateway {
    private Connection connection;
    private Session session;
    private Destination destination;
    private Producer producer;




    public MessageSenderGateway(String channelName){
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connectionFactory.setTrustAllPackages(true);
            // Create a Connection
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

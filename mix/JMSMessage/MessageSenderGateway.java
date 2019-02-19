package JMSMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageSenderGateway {
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;




    public MessageSenderGateway(String channelName){
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connectionFactory.setTrustAllPackages(true);
            // Create a Connection
            connection = connectionFactory.createConnection();

            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            destination = session.createQueue(channelName);

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public Message createTextMessage(String body){
        try {
            TextMessage message = session.createTextMessage(body);
            return message;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void send(Message msg){
        //msg.setJMSCorrelationID(message.getJMSCorrelationID());
        try {
            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

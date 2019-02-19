package model;

import model.bank.BankInterestReply;
import model.bank.BankInterestRequest;
import model.loan.LoanReply;
import model.loan.LoanRequest;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSMessaging {

    Connection connection; // to connect to the ActiveMQ
    Session session; // session for creating messages, producers and

    Destination sendDestination; // reference to a queue/topic destination
    MessageProducer producer; // for sending messages


    public JMSMessaging(){
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

    public void sendJMSMessage(String queuePath, BankInterestReply bankreply){
        try {
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            sendDestination = session.createQueue(queuePath);

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(sendDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            ObjectMessage message = session.createObjectMessage(bankreply);

            // Tell the producer to send the message
            message.setJMSCorrelationID(message.getJMSCorrelationID());
            System.out.println("Sent message: "+ bankreply.toString());
            producer.send(message);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendJMSMessage(String queuePath, LoanReply loanreply){
        try {
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            sendDestination = session.createQueue(queuePath);

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(sendDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            ObjectMessage message = session.createObjectMessage(loanreply);

            // Tell the producer to send the message
            System.out.println("Sent message: "+ loanreply.toString());
            producer.send(message);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public void sendJMSMessage(String queuePath, LoanRequest request){
        try {
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            sendDestination = session.createQueue(queuePath);

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(sendDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            ObjectMessage message = session.createObjectMessage(request);

            // Tell the producer to send the message
            System.out.println("Sent message: "+ request.toString());
            producer.send(message);



        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendJSMMessage(String queuePath, BankInterestRequest bankrequest){
        try {
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            sendDestination = session.createQueue(queuePath);

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(sendDestination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            ObjectMessage message = session.createObjectMessage(bankrequest);

            // Tell the producer to send the message
            System.out.println("Sent message: "+ bankrequest.toString());
            producer.send(message);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public MessageConsumer getConsumer(String queuePath){
        LoanRequest request = null;
        MessageConsumer consumer = null;
        try {
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            sendDestination = session.createQueue(queuePath);

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer = session.createConsumer(sendDestination);


        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
        return consumer;
    }

}

package Chained.Gateway.LoanBank;

import JMSMessage.MessageReceiverGateway;
import JMSMessage.MessageSenderGateway;
import model.bank.BankInterestReply;
import model.bank.BankInterestRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public abstract class LoanBrokerAppGateway {
       private MessageSenderGateway sender;
       private MessageReceiverGateway receiver;
       private BankSerializer serializer;

       public LoanBrokerAppGateway(){
           sender = new MessageSenderGateway("bankreply");
           receiver = new MessageReceiverGateway("Bankrequest");
           serializer = new BankSerializer();
           receiver.setListener(new MessageListener() {
               @Override
               public void onMessage(Message message) {
                   try {
                       TextMessage textmessage = (TextMessage) message;
                       String tmessage = textmessage.getText();
                       BankInterestRequest BIrequest = serializer.requestFromString(tmessage);
                       onBankInterestRequestArrived(BIrequest);
                   } catch (JMSException e) {
                       e.printStackTrace();
                   }
               }
           });
       }


       public void sendReply(BankInterestReply reply){
           String json = serializer.replyToString(reply);
           Message message = sender.createTextMessage(json);
           sender.send(message);
       }

       public abstract void onBankInterestRequestArrived(BankInterestRequest request);
}

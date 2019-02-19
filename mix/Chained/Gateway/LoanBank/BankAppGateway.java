package Chained.Gateway.LoanBank;

import JMSMessage.MessageReceiverGateway;
import JMSMessage.MessageSenderGateway;
import model.bank.BankInterestReply;
import model.bank.BankInterestRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public abstract class BankAppGateway {
    private MessageSenderGateway sender;
    private MessageReceiverGateway receiver;
    private BankSerializer serializer;
    private BankInterestRequest brequest;


    public BankAppGateway(){
        sender = new MessageSenderGateway("Bankrequest");
        serializer = new BankSerializer();
        receiver = new MessageReceiverGateway("bankreply");
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage textmessage = (TextMessage) message;
                    String tmessage = textmessage.getText();
                    BankInterestReply BIReply = serializer.replyFromString(tmessage);
                    onBankReplyArrived(BIReply, brequest);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public abstract void onBankReplyArrived(BankInterestReply reply, BankInterestRequest request);

    public void sendBankRequest(BankInterestRequest request){
        brequest = request;
        String body = serializer.requestToString(request);
        Message msg = sender.createTextMessage(body);
        sender.send(msg);
    }
}

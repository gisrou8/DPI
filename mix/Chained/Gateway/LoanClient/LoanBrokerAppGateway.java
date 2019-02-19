package Chained.Gateway.LoanClient;

import JMSMessage.MessageReceiverGateway;
import JMSMessage.MessageSenderGateway;
import messaging.requestreply.RequestReply;
import model.loan.LoanReply;
import model.loan.LoanRequest;

import javax.jms.*;

public abstract class LoanBrokerAppGateway {
    private MessageSenderGateway sender;
    private MessageReceiverGateway receiver;
    private LoanSerializer serializer;
    private LoanRequest lrequest;


    public LoanBrokerAppGateway(){
        sender = new MessageSenderGateway("Loanrequest");
        serializer = new LoanSerializer();
        receiver = new MessageReceiverGateway("loanreply");
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                try {
                    TextMessage textmessage = (TextMessage) message;
                    String tmessage = textmessage.getText();
                    LoanReply reply = serializer.replyFromString(tmessage);
                    onLoanReplyArrived(lrequest, reply);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    public void applyForLoan(LoanRequest request){
        this.lrequest = request;
        String body = serializer.requestToString(lrequest);
        Message message = sender.createTextMessage(body);
        sender.send(message);
    }

    public abstract void onLoanReplyArrived(LoanRequest request, LoanReply reply);
}

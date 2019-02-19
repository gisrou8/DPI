package Chained.Gateway.LoanClient;

import com.owlike.genson.Genson;
import jdk.jshell.spi.ExecutionControl;
import model.loan.LoanReply;
import model.loan.LoanRequest;

public class LoanSerializer {
    private Genson genson;


    public LoanSerializer(){
        genson = new Genson();
    }


    public String requestToString(LoanRequest request){
        String json = genson.serialize(request);
        return json;
    }


    public LoanRequest requestFromString(String st){
        LoanRequest lrequest = genson.deserialize(st, LoanRequest.class);
        return lrequest;
    }

    public String replyToString(LoanReply reply){
        String json = genson.serialize(reply);
        return json;
    }


    public LoanReply  replyFromString(String str){
        LoanReply lreply = genson.deserialize(str, LoanReply.class);
        return lreply;
    }






}

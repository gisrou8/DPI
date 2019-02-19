package Chained.Gateway.LoanBank;

import com.owlike.genson.Genson;
import model.bank.BankInterestReply;
import model.bank.BankInterestRequest;
import model.loan.LoanRequest;

public class BankSerializer {
    private Genson genson;


    public BankSerializer(){
        genson = new Genson();
    }


    public String requestToString(BankInterestRequest request){
        String json = genson.serialize(request);
        return json;
    }

    public BankInterestRequest requestFromString(String str){
        BankInterestRequest BIrequest = genson.deserialize(str, BankInterestRequest.class);
        return BIrequest;
    }

    public String replyToString(BankInterestReply reply){
        String json = genson.serialize(reply);
        return json;
    }


    public BankInterestReply replyFromString(String str){
        BankInterestReply BRrequest = genson.deserialize(str, BankInterestReply.class);
        return BRrequest;
    }

}

package loanbroker;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.jms.*;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import Chained.Gateway.LoanBank.BankAppGateway;
import model.JMSMessaging;
import model.bank.*;
import model.loan.LoanReply;
import model.loan.LoanRequest;

import org.apache.activemq.ActiveMQConnectionFactory;


public class LoanBrokerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<JListLine> listModel = new DefaultListModel<JListLine>();
	private JList<JListLine> list;
	private LoanRequest loanRequest;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanBrokerFrame frame = new LoanBrokerFrame();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the frame.
	 */
	public LoanBrokerFrame() {

//		try {
//			JMSMessaging jms = new JMSMessaging();
//			MessageConsumer consumer = jms.getConsumer("Loanrequest");
//			consumer.setMessageListener(new MessageListener() {
//
//				@Override
//				public void onMessage(Message msg) {
//					try {
//						ObjectMessage objectmessage = (ObjectMessage) msg;
//						request = (LoanRequest) objectmessage.getObject();
//						BankInterestRequest bankRequest = new BankInterestRequest(request.getAmount(), request.getTime());
//						add(request);
//						jms.sendJSMMessage("Bankrequest", bankRequest);
//					} catch (JMSException e) {
//						e.printStackTrace();
//					}
//
//
//				}
//			});
//		} catch (JMSException e) {
//			e.printStackTrace();
//		}

		BankAppGateway bankGateway = new BankAppGateway() {
			@Override
			public void onBankReplyArrived(BankInterestReply reply, BankInterestRequest request) {
				add(loanRequest, reply);
				add(loanRequest, request);
				LoanReply loanreply = new LoanReply(reply.getInterest(), reply.getQuoteId());
				// send with clientgateway
			}
		};




//		try {
//			JMSMessaging jms = new JMSMessaging();
//			MessageConsumer bankconsumer = jms.getConsumer("bankreply");
//			bankconsumer.setMessageListener(new MessageListener() {
//
//				@Override
//				public void onMessage(Message msg) {
//					try {
//						ObjectMessage objectmessage = (ObjectMessage) msg;
//						BankInterestReply bankreply = (BankInterestReply) objectmessage.getObject();
//						add(request, bankreply);
//						LoanReply loanreply = new LoanReply(bankreply.getInterest(), bankreply.getQuoteId());
//						jms.sendJMSMessage("loanreply", loanreply);
//						System.out.println("loanreply = "  + loanreply.toString());
//					} catch (JMSException e) {
//						e.printStackTrace();
//					}
//
//
//				}
//			});
//		} catch (JMSException e) {
//			e.printStackTrace();
//		}


		setTitle("Loan Broker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{46, 31, 86, 30, 89, 0};
		gbl_contentPane.rowHeights = new int[]{233, 23, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		list = new JList<JListLine>(listModel);
		scrollPane.setViewportView(list);


	}
	
	 private JListLine getRequestReply(LoanRequest request){    
	     
	     for (int i = 0; i < listModel.getSize(); i++){
	    	 JListLine rr =listModel.get(i);
	    	 if (rr.getLoanRequest() == request){
	    		 return rr;
	    	 }
	     }
	     
	     return null;
	   }
	
	public void add(LoanRequest loanRequest){
		listModel.addElement(new JListLine(loanRequest));		
	}
	

	public void add(LoanRequest loanRequest,BankInterestRequest bankRequest){
		JListLine rr = getRequestReply(loanRequest);
		if (rr!= null && bankRequest != null){
			rr.setBankRequest(bankRequest);
            list.repaint();
		}		
	}

	
	public void add(LoanRequest loanRequest, BankInterestReply bankReply){
		JListLine rr = getRequestReply(loanRequest);
		if (rr!= null && bankReply != null){
			rr.setBankReply(bankReply);;
            list.repaint();
		}		
	}


}

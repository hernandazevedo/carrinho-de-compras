package br.com.carrinho.carrinhocomprasapp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Vector;

import com.paypal.android.MEP.MEPAddress;
import com.paypal.android.MEP.MEPReceiverAmounts;
import com.paypal.android.MEP.PaymentAdjuster;
import com.paypal.android.MEP.MEPAmounts;

public class Adjuster implements PaymentAdjuster, Serializable {

	private static final long serialVersionUID = 9001L;
	
	// Returning null from either adjuster will signal
	// the library to cancel the whole payment. Applications
	// may want to call PayPal.setAdjustPaymentError (String s)
	// to establish a reason for the cancellation prior to
	// failing an adjustment. This will be presented to the 
	// user during the cancel.
	
	public MEPAmounts adjustAmount(MEPAddress address, String currency, String amount, String tax, String shipping) {
		MEPAmounts amounts = new MEPAmounts();
		
		//demo adjust amounts implementation that makes the tax 10% of the amount if 
		//the state is California, 8% otherwise, and leaves the rest the same
		amounts.setCurrency(currency);
		amounts.setPaymentAmount(amount);
		
		BigDecimal bdAmount = new BigDecimal(amount);
		
		//make sure we do the null checks on the state string...
		if (address.getState() != null && address.getState().length() > 0 && address.getState().indexOf("CA") > -1) {
			BigDecimal taxAmount = bdAmount.multiply(new BigDecimal("0.1"));
			taxAmount = taxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			amounts.setTax(taxAmount.toString());
		}
		else {
			BigDecimal taxAmount = bdAmount.multiply(new BigDecimal("0.08"));
			taxAmount = taxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			amounts.setTax(taxAmount.toString());
		}
		amounts.setShipping(shipping);
			
		return amounts;
	}

	public Vector<MEPReceiverAmounts> adjustAmountsAdvanced(MEPAddress address, String currency, Vector<MEPReceiverAmounts> receivers) {
		if (receivers != null) {
			for (int i=0; i<receivers.size(); ++i){
				MEPReceiverAmounts person = receivers.elementAt(i);
				BigDecimal amt = person.amounts.getPaymentAmount();
				BigDecimal tax = amt;
				if (address.getState() != null && address.getState().length() > 0 && address.getState().indexOf("CA") > -1)
					tax = amt.multiply(new BigDecimal("0.1"));
				else
					tax = amt.multiply(new BigDecimal("0.08"));
				BigDecimal ship = amt.multiply(new BigDecimal("0.15"));
				tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
				ship = ship.setScale(2, BigDecimal.ROUND_HALF_UP);
				person.amounts.setPaymentAmount(amt);
				person.amounts.setTax(tax);
				person.amounts.setShipping(ship);
			}
		}
		return receivers;
	}
}

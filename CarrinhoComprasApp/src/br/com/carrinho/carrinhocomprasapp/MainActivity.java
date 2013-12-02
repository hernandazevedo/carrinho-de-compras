package br.com.carrinho.carrinhocomprasapp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import br.com.carrinho.dao.ProdutoDAO;
import br.com.carrinho.model.Produto;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalAdvancedPayment;
import com.paypal.android.MEP.PayPalInvoiceData;
import com.paypal.android.MEP.PayPalInvoiceItem;
import com.paypal.android.MEP.PayPalReceiverDetails;

public class MainActivity extends Activity implements OnClickListener {

	// The PayPal server to be used - can also be ENV_NONE and ENV_LIVE
		private static final int server = PayPal.ENV_SANDBOX;
		// The ID of your application that you received from PayPal
		private static final String appID = "APP-80W284485P519543T";
	
		protected static final int INITIALIZE_SUCCESS = 0;
		protected static final int INITIALIZE_FAILURE = 1;
		private static final int request = 1;
		
		// These are used to display the results of the transaction
		public static String resultTitle;
		public static String resultInfo;
		public static String resultExtra;
		
	List<Produto> carrinho = new ArrayList<Produto>();
	ListView listView;
	
	private TextView contentTxt;
	ProdutoAdapter adapter;
	
	// You will need at least one CheckoutButton, this application has four for examples
	CheckoutButton launchSimplePayment;
	
	// This handler will allow us to properly update the UI. You cannot touch Views from a non-UI thread.
	Handler hRefresh = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
		    	case INITIALIZE_SUCCESS:
		    		setupButtons();
		            break;
		    	case INITIALIZE_FAILURE:
		    		showFailure();
		    		break;
			}
		}
	};
	
	/**
	 * Show a failure message because initialization failed.
	 */
	public void showFailure() {
		
		
		contentTxt.setText("Falha ao inicializar o PayPal");
	}
	
	/**
	 * Create our CheckoutButton and update the UI.
	 */
	public void setupButtons() {
		PayPal pp = PayPal.getInstance();
		// Get the CheckoutButton. There are five different sizes. The text on the button can either be of type TEXT_PAY or TEXT_DONATE.
		launchSimplePayment = pp.getCheckoutButton(this, PayPal.BUTTON_194x37, CheckoutButton.TEXT_PAY);
		// You'll need to have an OnClickListener for the CheckoutButton. For this application, MPL_Example implements OnClickListener and we
		// have the onClick() method below.
		launchSimplePayment.setOnClickListener(this);
		// The CheckoutButton is an android LinearLayout so we can add it to our display like any other View.
		RelativeLayout ll = (RelativeLayout)findViewById(R.id.viewMain);
		LayoutParams lp = new LayoutParams(300,68);
		lp.addRule(RelativeLayout.RIGHT_OF, R.id.btn_sync);
		
		ll.addView(launchSimplePayment,lp);
		
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		contentTxt = (TextView)findViewById(R.id.scan_content);
		
		listView = (ListView) findViewById(R.id.listView_produtos);
		
		// Initialize the library. We'll do it in a separate thread because it requires communication with the server
				// which may take some time depending on the connection strength/speed.
				Thread libraryInitializationThread = new Thread() {
					public void run() {
						initLibrary();
						
						// The library is initialized so let's create our CheckoutButton and update the UI.
						if (PayPal.getInstance().isLibraryInitialized()) {
							hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
						}
						else {
							hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
						}
					}
				};
				libraryInitializationThread.start();
//	            	  listar os produtos depois
//	            	  carrinho = parseJsonToListProduto(new String(response.getBytes(),"UTF-8"));
	            	  adapter = new ProdutoAdapter();
	            	  listView.setAdapter(adapter);
	            	
	        
		
		scheduleAlarmReceiver();
	}

	/**
	 * The initLibrary function takes care of all the basic Library initialization.
	 * 
	 * @return The return will be true if the initialization was successful and false if 
	 */
	private void initLibrary() {
		PayPal pp = PayPal.getInstance();
		// If the library is already initialized, then we don't need to initialize it again.
		if(pp == null) {
			// This is the main initialization call that takes in your Context, the Application ID, and the server you would like to connect to.
			pp = PayPal.initWithAppID(this, appID, server);
   			
			// -- These are required settings.
        	pp.setLanguage("pt_BR"); // Sets the language for the library.
        	// --
        	
        	// -- These are a few of the optional settings.
        	// Sets the fees payer. If there are fees for the transaction, this person will pay for them. Possible values are FEEPAYER_SENDER,
        	// FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and FEEPAYER_SECONDARYONLY.
        	pp.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER); 
        	// Set to true if the transaction will require shipping.
        	pp.setShippingEnabled(true);
        	// Dynamic Amount Calculation allows you to set tax and shipping amounts based on the user's shipping address. Shipping must be
        	// enabled for Dynamic Amount Calculation. This also requires you to create a class that implements PaymentAdjuster and Serializable.
        	pp.setDynamicAmountCalculationEnabled(true);
        	// --
		}
	}
	
	class ProdutoAdapter extends BaseAdapter{

		public int getCount() {
			return carrinho.size();
		}

		public Produto getItem(int position) {
			return carrinho.get(position);
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view = convertView;
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.linha_produto, null);
			}
			
			ImageView image = (ImageView) view.findViewById(R.id.imageView1);
			TextView txNome = (TextView) view.findViewById(R.id.textView1);
			TextView txPreco = (TextView) view.findViewById(R.id.textView2);
			
			Produto p = getItem(position);
			
			image.setImageResource(p.getImageID());
			UrlImageViewHelper.setUrlDrawable(image, p.getUrlImage());
			
			txNome.setText(p.getNomeProduto());
			txPreco.setText(p.getPreco());
			
			return view;
		}
    	
    }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private String getImei() {
		String imei = null;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			imei = telephonyManager.getDeviceId();
		} catch (Exception e) {
			imei = "11233455";
		}
		if (imei == null) {
			imei = "11233455";
		}
		return imei;
	}

	public void processSync(View v) {
		new SyncService().doSync(getImei(), this);
	}

	// Schedule AlarmManager to invoke DealAlarmReceiver and cancel any existing
	// current PendingIntent
	// we do this because we *also* invoke the receiver from a BOOT_COMPLETED
	// receiver
	// so that we make sure the service runs either when app is
	// installed/started, or when device boots
	private void scheduleAlarmReceiver() {
		AlarmManager alarmMgr = (AlarmManager) this
				.getSystemService(Context.ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				new Intent(this, CarrinhoAlarmReceiver.class),
				PendingIntent.FLAG_CANCEL_CURRENT);

		// Use inexact repeating which is easier on battery (system can phase
		// events and not wake at exact times)
		alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				Constants.ALARM_TRIGGER_AT_TIME, Constants.ALARM_INTERVAL,
				pendingIntent);
	}
	
	public void scan(View view){
		
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
//		
		scanIntegrator.initiateScan();
		
//		adicionarProdutoCarrinho("123456");
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		
		if(requestCode == request){
			//Resposta do pagamento do paypal
			launchSimplePayment.updateButton();
			
			contentTxt.setText(resultInfo);
			
//	    	title.setText(resultTitle);
//	    	title.setVisibility(View.VISIBLE);
//	    	info.setText(resultInfo);
//	    	info.setVisibility(View.VISIBLE);
//	    	extra.setText(resultExtra);
//	    	extra.setVisibility(View.VISIBLE);
//			
		}else{
			//Resposta do codigo de barras
			IntentResult scanningResult =  IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
			
			String scanContent = scanningResult.getContents();
	
	//		contentTxt.setText("Resultado adicionando no carrinho");
			adicionarProdutoCarrinho(scanContent);
	//		String scanFormat = scanningResult.getFormatName();
		}
		
		
		
	}
	
	public void adicionarProdutoCarrinho(String codigoBarras){
		
		ProdutoDAO dao = new ProdutoDAO(this);
		Log.i(Constants.LOG_TAG,
				"Buscando o produto com codigo de barras "+codigoBarras);
		contentTxt.setText("");
		
		Produto p = dao.get(codigoBarras);
		
		if(p != null){
			Log.i(Constants.LOG_TAG,
					"Produto encontrado adicionando no carrinho");
//			contentTxt.setText("Produto encontrado adicionando no carrinho");
			carrinho.add(p);
			adapter.notifyDataSetChanged();

//			contentTxt.setText("CONTENT: " + codigoBarras + "Produto: "+p.getNomeProduto());
		}else{
			contentTxt.setText("Produto nao encontrado");
		}
	}

	@Override
	public void onClick(View v) {
		if(v == launchSimplePayment) {
			// Use our helper function to create the simple payment.
			PayPalAdvancedPayment payment = getPayment();	
			// Use checkout to create our Intent.
//			Intent checkoutIntent = PayPal.getInstance().checkout(payment, this, new ResultDelegate());
			// Use the android's startActivityForResult() and pass in our Intent. This will start the library.
//	    	startActivityForResult(checkoutIntent, request);
	    	
	    	//TODO fazer o Adjuster para o calculo de frete IMPORTANTE!!!!!!!!!!!!!!!!
	    	startActivityForResult(PayPal.getInstance().checkout(payment, this, new Adjuster(), new ResultDelegate()), request);
		}
		
	}
	
	/**
	 * Create a PayPalPayment which is used for simple payments.
	 * 
	 * @return Returns a PayPalPayment. 
	 */
	private PayPalAdvancedPayment exampleSimplePayment() {
		
		//TODO falta fazer aqui o preenchimento dos dados do pagamento conforme a lista de produtos do carrinho
		
		// Create a basic PayPalPayment.
		// Create the PayPalAdvancedPayment.
				PayPalAdvancedPayment payment = new PayPalAdvancedPayment();
				// Sets the currency type for this payment.
		    	payment.setCurrencyType("BRL");
		    	// Sets the Instant Payment Notification url. This url will be hit by the PayPal server upon completion of the payment.
		    	payment.setIpnUrl("http://www.exampleapp.com/ipn");
		    	// Sets the memo. This memo will be part of the notification sent by PayPal to the necessary parties.
		    	payment.setMemo("This sure is a swell memo for a parallel payment.");
		    	
		    	// Create the PayPalReceiverDetails. You must have at least one of these to make an advanced payment and you should have
		    	// more than one for a Parallel or Chained payment.
				PayPalReceiverDetails receiver1 = new PayPalReceiverDetails();
				// Sets the recipient for the PayPalReceiverDetails. This can also be a phone number.
				
				//TODO pegar o recipient do atual parceiro onde o cliente esta comprando
				receiver1.setRecipient("hernand.azevedo-facilitator@gmail.com");
				// Sets the subtotal of the payment for this receiver, not including tax and shipping amounts. 
				receiver1.setSubtotal(new BigDecimal("13.50"));
				// Sets the primary flag for this receiver. This is defaulted to false. No receiver can be a primary for a parallel payment.
				receiver1.setIsPrimary(false);
				// Sets the payment type. This can be PAYMENT_TYPE_GOODS, PAYMENT_TYPE_SERVICE, PAYMENT_TYPE_PERSONAL, or PAYMENT_TYPE_NONE.
				receiver1.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);
				
				// PayPalInvoiceData can contain tax and shipping amounts. It also contains an ArrayList of PayPalInvoiceItem which can
		    	// be filled out. These are not required for any transaction.
				PayPalInvoiceData invoice1 = new PayPalInvoiceData();
				// Sets the tax amount.
				invoice1.setTax(new BigDecimal("2.20"));
				// Sets the shipping amount.
				invoice1.setShipping(BigDecimal.ZERO);
				
				// PayPalInvoiceItem has several parameters available to it. None of these parameters is required.
				PayPalInvoiceItem item1 = new PayPalInvoiceItem();
				// Sets the name of the item.
		    	item1.setName("Laser Show");
		    	// Sets the ID. This is any ID that you would like to have associated with the item.
		    	item1.setID("4211");
		    	// Sets the total price which should be (quantity * unit price). The total prices of all PayPalInvoiceItem should add up
		    	// to less than or equal the subtotal of the payment.
		    	item1.setTotalPrice(new BigDecimal("7.30"));
		    	// Sets the unit price.
		    	item1.setUnitPrice(new BigDecimal("7.30"));
		    	// Sets the quantity.
		    	item1.setQuantity(1);
		    	// Add the PayPalInvoiceItem to the PayPalInvoiceData. Alternatively, you can create an ArrayList<PayPalInvoiceItem>
		    	// and pass it to the PayPalInvoiceData function setInvoiceItems().
		    	invoice1.getInvoiceItems().add(item1);
		    	
		    	// Create and add another PayPalInvoiceItem to add to the PayPalInvoiceData.
				PayPalInvoiceItem item2 = new PayPalInvoiceItem();
		    	item2.setName("Fog Machine");
		    	item2.setID("6325");
		    	item2.setTotalPrice(new BigDecimal("4.80"));
		    	item2.setUnitPrice(new BigDecimal("1.20"));
		    	item2.setQuantity(4);
		    	invoice1.getInvoiceItems().add(item2);
		    	
		    	// Create and add another PayPalInvoiceItem to add to the PayPalInvoiceData.
				PayPalInvoiceItem item3 = new PayPalInvoiceItem();
		    	item3.setName("Fog Liquid");
		    	item3.setID("2196");
		    	item3.setTotalPrice(new BigDecimal("1.40"));
		    	item3.setUnitPrice(new BigDecimal("0.20"));
		    	item3.setQuantity(7);
		    	invoice1.getInvoiceItems().add(item3);
				
		    	// Sets the PayPalReceiverDetails invoice data.
		    	receiver1.setInvoiceData(invoice1);
		    	// Sets the merchant name. This is the name of your Application or Company.
		    	
		    	//Pegar o nome do mercado
		    	receiver1.setMerchantName("Laser Shop");
		    	// Sets the description of the payment.
		    	receiver1.setDescription("The first of two party guys");
		    	// Sets the Custom ID. This is any ID that you would like to have associated with the PayPalReceiverDetails.
		    	
		    	//TODO verificar o que fazer com este id
		    	receiver1.setCustomID("001813");
		    	// Add the receiver to the payment. Alternatively, you can create an ArrayList<PayPalReceiverOptions>
		    	// and pass it to the PayPalAdvancedPayment function setReceivers().
				payment.getReceivers().add(receiver1);
    	
    	return payment;
	}
	
	/**
	 * Create a PayPalPayment which is used for simple payments.
	 * 
	 * @return Returns a PayPalPayment. 
	 */
	private PayPalAdvancedPayment getPayment() {
		
		//TODO falta fazer aqui o preenchimento dos dados do pagamento conforme a lista de produtos do carrinho
		
		// Create a basic PayPalPayment.
		// Create the PayPalAdvancedPayment.
				PayPalAdvancedPayment payment = new PayPalAdvancedPayment();
				// Sets the currency type for this payment.
		    	payment.setCurrencyType("BRL");
		    	// Sets the Instant Payment Notification url. This url will be hit by the PayPal server upon completion of the payment.
		    	payment.setIpnUrl("http://www.exampleapp.com/ipn");
		    	// Sets the memo. This memo will be part of the notification sent by PayPal to the necessary parties.
		    	payment.setMemo("This sure is a swell memo for a parallel payment.");
		    	
		    	// Create the PayPalReceiverDetails. You must have at least one of these to make an advanced payment and you should have
		    	// more than one for a Parallel or Chained payment.
				PayPalReceiverDetails receiver1 = new PayPalReceiverDetails();
				// Sets the recipient for the PayPalReceiverDetails. This can also be a phone number.
				
				//TODO pegar o recipient do atual parceiro onde o cliente esta comprando
				receiver1.setRecipient("hernand.azevedo-facilitator@gmail.com");
				// Sets the subtotal of the payment for this receiver, not including tax and shipping amounts. 
				
				// Sets the primary flag for this receiver. This is defaulted to false. No receiver can be a primary for a parallel payment.
				receiver1.setIsPrimary(false);
				// Sets the payment type. This can be PAYMENT_TYPE_GOODS, PAYMENT_TYPE_SERVICE, PAYMENT_TYPE_PERSONAL, or PAYMENT_TYPE_NONE.
				receiver1.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);
				
				// PayPalInvoiceData can contain tax and shipping amounts. It also contains an ArrayList of PayPalInvoiceItem which can
		    	// be filled out. These are not required for any transaction.
				PayPalInvoiceData invoice1 = new PayPalInvoiceData();
				// Sets the tax amount.
//				invoice1.setTax(new BigDecimal("2.20"));
				// Sets the shipping amount.
				invoice1.setShipping(BigDecimal.ZERO);
				
				double subTotal = 0.0;
				
				for(Produto p : carrinho){
					// PayPalInvoiceItem has several parameters available to it. None of these parameters is required.
					PayPalInvoiceItem item1 = new PayPalInvoiceItem();
					// Sets the name of the item.
			    	item1.setName(p.getNomeProduto());
			    	// Sets the ID. This is any ID that you would like to have associated with the item.
			    	item1.setID(p.getCodigoBarras());
			    	// Sets the total price which should be (quantity * unit price). The total prices of all PayPalInvoiceItem should add up
			    	// to less than or equal the subtotal of the payment.
			    	
			    	subTotal += Double.parseDouble(p.getPreco()); 
			    	item1.setTotalPrice(new BigDecimal(p.getPreco()));
			    	// Sets the unit price.
			    	item1.setUnitPrice(new BigDecimal(p.getPreco()));
			    	// Sets the quantity.
			    	item1.setQuantity(1);
			    	// Add the PayPalInvoiceItem to the PayPalInvoiceData. Alternatively, you can create an ArrayList<PayPalInvoiceItem>
			    	// and pass it to the PayPalInvoiceData function setInvoiceItems().
			    	invoice1.getInvoiceItems().add(item1);
				}
		    
				receiver1.setSubtotal(new BigDecimal(subTotal));
				
		    	// Sets the PayPalReceiverDetails invoice data.
		    	receiver1.setInvoiceData(invoice1);
		    	// Sets the merchant name. This is the name of your Application or Company.
		    	
		    	//Pegar o nome do mercado
		    	receiver1.setMerchantName("Mercadinho do Senhor José");
		    	// Sets the description of the payment.
		    	receiver1.setDescription("O mercado que você precisa");
		    	// Sets the Custom ID. This is any ID that you would like to have associated with the PayPalReceiverDetails.
		    	
		    	//TODO verificar o que fazer com este id
		    	receiver1.setCustomID("001813");
		    	// Add the receiver to the payment. Alternatively, you can create an ArrayList<PayPalReceiverOptions>
		    	// and pass it to the PayPalAdvancedPayment function setReceivers().
				payment.getReceivers().add(receiver1);
    	
    	return payment;
	}
	
	

}

	
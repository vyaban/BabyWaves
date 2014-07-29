package vik.linx.babywaves;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class ContactPick extends Activity {
	private final static int REQUEST_CONTACTPICKER = 1;
	TextView contactnum;
	Button nextbtn, pickcontactbtn;
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		contactnum = (TextView)findViewById(R.id.contactnum);
		nextbtn = (Button)findViewById(R.id.nextbtn);
		pickcontactbtn = (Button)findViewById(R.id.pickcontactbtn);
		readcontact();
		onclicks();
	}


	public void readcontact() {
		try{
			Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
			startActivityForResult(intent, REQUEST_CONTACTPICKER);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("deprecation")
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	      super.onActivityResult(reqCode, resultCode, data);

	      switch (reqCode) {
	        case (REQUEST_CONTACTPICKER) :
	          if (resultCode == Activity.RESULT_OK) {
	              Uri contactData = data.getData();
	                Cursor c =  getContentResolver().query(contactData, null, null, null, null);
	                startManagingCursor(c);
	                if (c.moveToFirst()) {
	                  name = c.getString(c.getColumnIndexOrThrow(People.NAME));  
	                  String number = c.getString(c.getColumnIndexOrThrow(People.NUMBER));
	                  contactnum.setText(name);
	                  Toast.makeText(this,  name + " has number " + number, Toast.LENGTH_LONG).show();
	                 }
	           }
	         break;
	      }
	}
	
	private void onclicks() {
		// TODO Auto-generated method stub
		nextbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(ContactPick.this, MainActivity.class);
				myIntent.putExtra("key", name); //Optional parameters
				ContactPick.this.startActivity(myIntent);
			}
		});
		pickcontactbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				readcontact();
			}
		});
	}
}

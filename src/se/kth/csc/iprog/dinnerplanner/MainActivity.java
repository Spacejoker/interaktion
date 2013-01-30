package se.kth.csc.iprog.dinnerplanner;

import java.text.NumberFormat;
import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.Ingredient;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {
	String nr_guests = "0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupStart();
		
	}

	private void setupStart() {
		setContentView(R.layout.activity_main);
		
		//To get the dinner model you can use the following code:
		DinnerModel model = ((DinnerPlannerApplication) this.getApplication()).getModel();
		
		Button btnDownload = (Button) findViewById(R.id.create_menu);
		btnDownload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.number_guests);
				dialog.setTitle("Nr of guests");
	
				
				Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//EditText input = (EditText) findViewById(R.id.num_guests_input);
						nr_guests = "5";//input.getEditableText().toString();
						dialog.dismiss();
						showAppetizer();
						
						//back from ingredients
//						Button backIngridient = (Button) findViewById(R.id.back_ingredient);
//						backIngridient.setOnClickListener(new View.OnClickListener() {
//							
//							@Override
//							public void onClick(View v) {
//								setupStart();
//								setContentView(R.layout.activity_main);
//							}
//
//						});
					}

				});
	 
				dialog.show();
			}
		});
		
	}
	private void showAppetizer() {
		setContentView(R.layout.appetizer);
		
		Button nrGuests = (Button) findViewById(R.id.changeNumberOfGuests);
		nrGuests.setText("Guests: " + nr_guests);
		
		Button next = (Button) findViewById(R.id.next_appetizer);
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getBaseContext(), "abc", 1900);
				toast.show();
				setupIngredients();
			}

		});
		
		Button back = (Button) findViewById(R.id.back_appetizer);
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getBaseContext(), "back", 1900);
				toast.show();
				setupStart();
			}
		});
		
	}

	private void setupIngredients() {
		setContentView(R.layout.ingredient);
		DinnerModel model = ((DinnerPlannerApplication) this.getApplication()).getModel();
		Set<Ingredient> allIngredients = model.getAllIngredients();
		
		//build table from model
		TableLayout findViewById = (TableLayout) findViewById(R.id.tableLayout1);
		for (Ingredient ingredient : allIngredients) {
			
			NumberFormat format = NumberFormat.getInstance();
			TableRow row = new TableRow(getBaseContext());
			TextView textView = new TextView(getBaseContext());
			textView.setText(ingredient.getName());
			row.addView(textView);
			textView = new TextView(getBaseContext());
			textView.setText(format.format(ingredient.getQuantity() ));
			row.addView(textView);
			textView = new TextView(getBaseContext());
			textView.setText(format.format(ingredient.getPrice()));
			row.addView(textView);
			findViewById.addView(row);
		}
		
//		Button next = (Button) findViewById(R.id.next_appetizer);
//		
//		next.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast toast = Toast.makeText(getBaseContext(), "abc", 1900);
//				toast.show();
//				setupIngredients();
//			}
//
//		});
//		
//		Button back = (Button) findViewById(R.id.back_appetizer);
//		
//		back.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast toast = Toast.makeText(getBaseContext(), "back", 1900);
//				toast.show();
//				setupStart();
//			}
//		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}

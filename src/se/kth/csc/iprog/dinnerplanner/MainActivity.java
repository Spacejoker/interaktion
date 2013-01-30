package se.kth.csc.iprog.dinnerplanner;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.Dish;
import se.kth.csc.iprog.dinnerplanner.model.Ingredient;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.EventLogTags.Description;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupStart();
		
	}

	private void setupStart() {
		setContentView(R.layout.activity_main);

		final DinnerModel model = ((DinnerPlannerApplication) this.getApplication()).getModel();
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
	 
				dialog.show();
				
				final EditText input = (EditText) dialog.findViewById(R.id.num_guests_input);
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String str = "0";
						if(input.getEditableText().toString().length() > 0){
							str = input.getEditableText().toString();
						}
						model.setNumberOfGuests(Integer.parseInt(str));
						dialog.dismiss();
						setupAppetizer(Dish.STARTER);
					}
					
				});
			}
		});
		
	}
	private void setupAppetizer(final int dishtype) {
		setContentView(R.layout.appetizer);
		
		View shopplistImage = findViewById(R.id.shopplistImage);
		shopplistImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setupIngredients();	
			}
		});
		DinnerModel model = ((DinnerPlannerApplication) this.getApplication()).getModel();
		((TextView)findViewById(R.id.sumPrice)).setText("$" + NumberFormat.getInstance().format(model.getTotalMenuPrice()) );
		
		Button nrGuests = (Button) findViewById(R.id.changeNumberOfGuests);
		nrGuests.setText("Guests: " + model.getNumberOfGuests());
		
		TextView header = (TextView) findViewById(R.id.choose_header);
		header.setTextSize(32);
		header.setGravity(Gravity.CENTER);
		
		switch (dishtype) {
		case Dish.STARTER:
			header.setText("Choose Appetizer");
			break;
		case Dish.MAIN:
			header.setText("Choose Main Dish");
			break;
		case Dish.DESERT:
			header.setText("Choose Dessert");
			break;
		default:
			break;
		}
		Button next = (Button) findViewById(R.id.next_appetizer);
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (dishtype) {
				case Dish.STARTER:
					setupAppetizer(Dish.MAIN);
					break;
				case Dish.MAIN:
					setupAppetizer(Dish.DESERT);
					break;
				case Dish.DESERT:
					setupPreparation();
					break;
					
				default:
					break;
				}
			}


		});
		
		Button back = (Button) findViewById(R.id.back_appetizer);
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (dishtype) {
				case Dish.STARTER:
				setupStart();
					break;
				case Dish.MAIN:
				setupAppetizer(Dish.STARTER);
					break;

				case Dish.DESERT:
				setupAppetizer(Dish.MAIN);
					break;

				default:
					break;
				}
			}
		});
		
		TableLayout table = (TableLayout) findViewById(R.id.starters_table);
		Set<Dish> food = model.getDishesOfType(dishtype);
		
		for (final Dish dish : food) {
			TableRow row = new TableRow(getBaseContext());
			
			CheckBox radioButton = new CheckBox(getBaseContext());
			radioButton.setGravity(Gravity.CENTER);
			row.addView(radioButton);
			
			ImageView imageView = new ImageView(getBaseContext());
			imageView.setImageResource(dish.getImage());
			row.addView(imageView);
			
			TextView t = new TextView(getApplicationContext());
			t.setText(dish.getName());
			t.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setupDescription(dish);
				}

			});
			row.addView(t);
			
			TextView t2 = new TextView(getBaseContext());
			t2.setText(dish.getDescription());
			t2.setWidth(200);
			row.addView(t2);
			table.addView(row);
		}
	}

	private void setupPreparation() {
		setContentView(R.layout.preparations);
		
		DinnerModel model = ((DinnerPlannerApplication) this.getApplication()).getModel();
		((TextView)findViewById(R.id.sumPrice)).setText("$" + NumberFormat.getInstance().format(model.getTotalMenuPrice()) );
		
		Button nrGuests = (Button) findViewById(R.id.changeNumberOfGuests);
		nrGuests.setText("Guests: " + model.getNumberOfGuests());
		
		
		List<Dish> menu = new ArrayList<Dish>();
		menu.addAll(model.getFullMenu());
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		int size = 32;
		String text = "Starter";
		
		TextView header = getTextView(size, text);
		layout.addView(header);
		
		layout.addView(getTextView(12, menu.get(0).getDescription()));
		
		layout.addView(getTextView(32, "Main Dish"));
		
		layout.addView(getTextView(12, menu.get(1).getDescription()));
		
		Button back = (Button) findViewById(R.id.back_prep);
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setupAppetizer(Dish.DESERT);
			}
		});
		
		View shopplistImage = findViewById(R.id.shopplistImage);
		shopplistImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setupIngredients();	
			}
		});
	}

	private TextView getTextView(int size, String text) {
		TextView header = new TextView(getApplicationContext());
		header.setTextSize(size);
		header.setText(text);
		return header;
	}

	private void setupDescription(Dish dish) {
		setContentView(R.layout.description);
		
		DinnerModel model = ((DinnerPlannerApplication) this.getApplication()).getModel();
		((TextView)findViewById(R.id.sumPrice)).setText("$" + NumberFormat.getInstance().format(model.getTotalMenuPrice()) );
		
		Button nrGuests = (Button) findViewById(R.id.changeNumberOfGuests);
		nrGuests.setText("Guests: " + model.getNumberOfGuests());
		
		
		TextView recipe = (TextView) findViewById(R.id.recept);
		recipe.setText(dish.getDescription());
		String totalIngredient = "";
		for (Ingredient ingredient: dish.getIngredients()) {
			totalIngredient += ingredient.getQuantity() + " " + ingredient.getUnit() + " " + ingredient.getName() + "\n";//.getName() + " " + ingredient.getQuantity() + "\n";
		}
		TextView ingr = (TextView) findViewById(R.id.ingredienser);
		ingr.setText(totalIngredient);
		
		ImageView img = (ImageView) findViewById(R.id.dishImage);
		img.setImageResource(dish.getImage());
		
		
		Button back = (Button) findViewById(R.id.back_desc);
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setupAppetizer(Dish.STARTER);
			}
		});
	}
	
	private void setupIngredients() {
		setContentView(R.layout.ingredient);
		DinnerModel model = ((DinnerPlannerApplication) this.getApplication()).getModel();
		Set<Ingredient> allIngredients = model.getAllIngredients();
		
		//build table from model
		TableLayout findViewById = (TableLayout) findViewById(R.id.tableLayout1);
		TableRow row = new TableRow(getBaseContext());
		TextView textView = new TextView(getBaseContext());
		textView.setText("Ingridient");
		row.addView(textView);
		textView = new TextView(getBaseContext());
		textView.setText("Quantity");
		row.addView(textView);
		textView = new TextView(getBaseContext());
		textView.setText("Cost");
		row.addView(textView);
		findViewById.addView(row);
		
		for (Ingredient ingredient : allIngredients) {
			
			NumberFormat format = NumberFormat.getInstance();
			row = new TableRow(getBaseContext());
			textView = new TextView(getBaseContext());
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
		
		Button back = (Button) findViewById(R.id.back_ingredient);
		
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setupAppetizer(Dish.STARTER);//Start();
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}

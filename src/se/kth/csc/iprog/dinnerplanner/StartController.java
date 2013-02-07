package se.kth.csc.iprog.dinnerplanner;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import se.kth.csc.iprog.dinnerplanner.model.Dish;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartController {

	private StartView view;

	public StartController(StartView view) {
		this.view = view;
		final MainActivity activity = (MainActivity) view.getActivity();
		final DinnerModel model = view.getModel();
		
		Button btnDownload = (Button) activity.findViewById(R.id.create_menu);
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
						activity.setupAppetizer(Dish.STARTER);
					}
				});
			}
		});
	}
}

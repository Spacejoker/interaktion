package se.kth.csc.iprog.dinnerplanner;

import java.text.NumberFormat;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TopBarView extends View {

	public TopBarView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		
//		final DinnerModel model = ((DinnerPlannerApplication) activity.getApplication()).getModel();
//		((TextView)findViewById(R.id.sumPrice)).setText("$" + NumberFormat.getInstance().format(model.getTotalMenuPrice()) );
		
		Button nrGuests = (Button) findViewById(R.id.changeNumberOfGuests);
		nrGuests.setText("Guests: " + "100");
	}

}

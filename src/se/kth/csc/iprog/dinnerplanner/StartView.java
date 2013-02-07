package se.kth.csc.iprog.dinnerplanner;

import java.util.Observable;
import java.util.Observer;

import se.kth.csc.iprog.dinnerplanner.model.DinnerModel;
import android.app.Activity;

public class StartView implements Observer {

	private Activity activity;
	private DinnerModel model;

	public StartView(Activity activity, DinnerModel model) {
		this.activity = activity;
		this.model = model;
		
		//load shit
	}

	public Activity getActivity() {
		return activity;
	}

	public DinnerModel getModel() {
		return model;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		//handle update
	}
	
	
}

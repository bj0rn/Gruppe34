package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import no.ntnu.fp.gui.timepicker.DateModel;
import no.ntnu.fp.util.Log;

public class WeekNavigator extends Navigator {

	public WeekNavigator(DateModel model) {
		super(model);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(nextButton)){
			model.setDay(model.getDay()+7);
		}else if(e.getSource().equals(previousButton)){
			model.setDay(model.getDay()-7);
		}
		Log.out(model);
		super.actionPerformed(e);
	}
	
	public String getText() {
		return ""+model.getWeek();
	}
	
}

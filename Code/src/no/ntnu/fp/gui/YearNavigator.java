package no.ntnu.fp.gui;

import java.awt.event.ActionEvent;

import no.ntnu.fp.gui.timepicker.DateModel;
import no.ntnu.fp.util.Log;

public class YearNavigator extends Navigator {

	public YearNavigator(DateModel model) {
		super(model);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(nextButton)){
			model.setYear((model.getYear()+1)-1900);
		}else if(e.getSource().equals(previousButton)){
			model.setYear((model.getYear()-1)-1900);
		}
		Log.out(model);
		super.actionPerformed(e);
	}
	
	public String getText() {
		return ""+model.getYear();
	}
	
}

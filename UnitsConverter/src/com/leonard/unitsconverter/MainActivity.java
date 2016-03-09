package com.leonard.unitsconverter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemSelectedListener {
	
	private static final int MEASURE_LENGTH = 0;
	private static final int MEASURE_SPEED = 1;
	private static final int MEASURE_TEMPERATURE = 2;
	
	private static final DecimalFormat df = new DecimalFormat("0.##########");
	
	
	private Object[] adapters = new Object[3];
	
	// state key
	private static final String STATE_KEY = "state";
	
	private EditText editTextTo;
	private EditText editTextFrom;
	private Spinner spinnerToUnits;
	private Spinner spinnerMeasures;
	private Spinner spinnerFromUnits;
	private State state = null;
	
	private static class State implements Serializable{
		private static final long serialVersionUID = 1L;
		int currentMeasure = MEASURE_LENGTH;
		int currentFromUnit = 0;
		int currentToUnit = 0;
		BigDecimal fromValue = null;
		BigDecimal toValue = null;
		
		@Override
		public String toString() {
			return "State [currentMeasure=" + currentMeasure
					+ ", currentFromUnit=" + currentFromUnit
					+ ", currentToUnit=" + currentToUnit + ", fromString="
					+ fromValue + ", toString=" + toValue + "]";
		}
		
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		state = savedInstanceState==null?new State():
			(State)savedInstanceState.getSerializable(STATE_KEY);
		initInputFieldsFromTo();
		initSpinnerMeasures();
		initSpinnersFromToForMeasure();
	}

	private void initInputFieldsFromTo() {
		editTextFrom= (EditText)findViewById(R.id.editText_from);
		setFromValue(state.fromValue);
		editTextFrom.addTextChangedListener(new TextWatcher() {

	          public void afterTextChanged(Editable s) {
	        	  if(editTextFrom.hasFocus()){
	        		  state.fromValue = getValue(s);
	        		  updateFromToValues(true);
	        	  }
	          }

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	          public void onTextChanged(CharSequence s, int start, int before, int count) {}
	    });
		editTextTo = (EditText)findViewById(R.id.editText_to);
		setToValue(state.toValue);
		editTextTo.addTextChangedListener(new TextWatcher() {

	          public void afterTextChanged(Editable s) {
	        	  if(editTextTo.hasFocus()){
	        		  state.toValue = getValue(s);
	        		  updateFromToValues(false);
	        	  }
	          }

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	          public void onTextChanged(CharSequence s, int start, int before, int count) {}
	    });
	}

	private void initSpinnerMeasures() {
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.measures_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerMeasures = (Spinner) findViewById(R.id.spinner_measures);
		spinnerMeasures.setAdapter(adapter);
		spinnerMeasures.setSelection(state.currentMeasure);
		spinnerMeasures.setOnItemSelectedListener(this);
	}
	
	
	private void initSpinnersFromToForMeasure() {
		ArrayAdapter<CharSequence> adapter = getAdapterForMeasure();
		
		spinnerFromUnits = (Spinner) findViewById(R.id.spinner_from_units);
		spinnerFromUnits.setAdapter(adapter);
		spinnerFromUnits.setSelection(state.currentFromUnit);
		spinnerFromUnits.setOnItemSelectedListener(this);
		
		spinnerToUnits = (Spinner) findViewById(R.id.spinner_to_units);
		spinnerToUnits.setSelection(state.currentToUnit);
		spinnerToUnits.setAdapter(adapter);
		spinnerToUnits.setOnItemSelectedListener(this);
	}

	private void resetSpinnersFromToForMeasure() {
		ArrayAdapter<CharSequence> adapter = getAdapterForMeasure();
		spinnerFromUnits.setSelection(0);
		spinnerFromUnits.setAdapter(adapter);
		
		spinnerToUnits.setSelection(0);
		spinnerToUnits.setAdapter(adapter);
		
	}
	

	@SuppressWarnings("unchecked")
	private ArrayAdapter<CharSequence> getAdapterForMeasure() {
		int unitsId = -1;
		int currentMeasure = getCurrentMeasure();
		switch (currentMeasure) {
			case MEASURE_LENGTH: {
				unitsId = R.array.length_units_array;
				break;
			}
			case MEASURE_TEMPERATURE:{
				unitsId = R.array.temperature_units_array;
				break;
			}
			case MEASURE_SPEED:{
				unitsId = R.array.speed_units_array;
				break;
			}
		}
		ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>)adapters[currentMeasure];
		if(adapter==null){
			adapter = ArrayAdapter.createFromResource(this,
					unitsId,
					android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapters[currentMeasure] = adapter;
		}
		return adapter;
	}

	private int getCurrentMeasure() {
		return spinnerMeasures.getSelectedItemPosition();
	}

	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		int spinnerId = parent.getId();
		if (spinnerId == R.id.spinner_measures) {
			if(pos!=state.currentMeasure){
				state.currentMeasure =pos;
				resetSpinnersFromToForMeasure();
			}
		} else if (spinnerId == R.id.spinner_from_units) {
			if(pos!=state.currentFromUnit){
				state.currentFromUnit = pos;
				updateFromToValues(false);
			}
		}else if(spinnerId == R.id.spinner_to_units){
			if(pos != state.currentToUnit){
				state.currentToUnit = pos;
				updateFromToValues(true);
			}
		}

	}

	private void updateFromToValues(boolean from) {
		BigDecimal fromValue = state.fromValue;
		BigDecimal toValue = state.toValue;
		int currentMeasure = state.currentMeasure;
		int currentFromUnit = state.currentFromUnit;
		int currentToUnit = state.currentToUnit;
		if(currentMeasure == MEASURE_LENGTH){
			LengthMeasure lm = new LengthMeasure();
			if(from){
				BigDecimal rez = lm.fromUnitToUnit(fromValue, currentFromUnit, currentToUnit);
				setToValue(rez);
			}else{
				BigDecimal rez = lm.fromUnitToUnit(toValue, currentToUnit, currentFromUnit);
				setFromValue(rez);
			}
		}else if(currentMeasure == MEASURE_SPEED){
			SpeedMeasure sm = new SpeedMeasure();
			if(from){
				BigDecimal rez =sm.fromUnitToUnit(fromValue, currentFromUnit, currentToUnit);
				setToValue(rez);
			}else{
				BigDecimal rez = sm.fromUnitToUnit(toValue, currentToUnit, currentFromUnit);
				setFromValue(rez);
			}
		}else if(currentMeasure == MEASURE_TEMPERATURE){
			TemperatureMeasure tm = new TemperatureMeasure();
			if(from){
				BigDecimal rez =tm.fromUnitToUnit(fromValue, currentFromUnit, currentToUnit);
				setToValue(rez);
			}else{
				BigDecimal rez = tm.fromUnitToUnit(toValue, currentToUnit, currentFromUnit);
				setFromValue(rez);
			}
		}
	}

	
	private void setToValue(BigDecimal value) {
		state.toValue = value;
		if(value==null){
			editTextTo.setText("",TextView.BufferType.EDITABLE);
		}else{
			editTextTo.setText(df.format(value.stripTrailingZeros()),TextView.BufferType.EDITABLE);
		}
	}

	
	private void setFromValue(BigDecimal value) {
		state.fromValue = value;
		if(value==null){
			editTextFrom.setText("",TextView.BufferType.EDITABLE);
		}else{
			editTextFrom.setText(df.format(value.stripTrailingZeros()),TextView.BufferType.EDITABLE);
		}
	}
	

	private BigDecimal getValue(Editable s) {
		String value = s.toString();
		if(value == null) return null;
		if(value.length()>0){
			try{
				return new BigDecimal(value);
			}catch(NumberFormatException e){
				return null;
			}
		}else{
			return null;
		}
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	   
	    //Log.d("==================", "onSaveInstanceState: "+state);
	    outState.putSerializable(STATE_KEY, state);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

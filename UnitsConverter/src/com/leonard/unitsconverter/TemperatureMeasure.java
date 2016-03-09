package com.leonard.unitsconverter;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <item>Celsius</item>
        <item>Fahrenheit</item>
        <item>Kelvin</item>
        
         °C  x  9/5 + 32 = °F

          (°F  -  32)  x  5/9 = °C
 * @author lenard
 *
 */
public class TemperatureMeasure {
	private static final int CELSIUS_UNIT = 0;
	private static final int FAHRENHEIT_UNIT = 1;
	private static final int KELVIN_UNIT = 2;
	
	private static final double ZERO_CELSIUS_TO_KELVIN = 273.15;
	
	private static final MathContext PRECIZION = MathContext.DECIMAL64;
	
	public BigDecimal cToF(BigDecimal c){
		return c.multiply(new BigDecimal(1.8), PRECIZION).add(new BigDecimal(32),PRECIZION);
	}
	public BigDecimal fToC(BigDecimal f){
		return f.subtract(new BigDecimal(32), PRECIZION).divide(new BigDecimal(1.8),PRECIZION);
	}
	
	public BigDecimal cToK(BigDecimal c){
		return c.add(new BigDecimal(ZERO_CELSIUS_TO_KELVIN),PRECIZION);
	}
	
	public BigDecimal kToC(BigDecimal k){
		return k.subtract(new BigDecimal(ZERO_CELSIUS_TO_KELVIN),PRECIZION);
	}
	
	
	public BigDecimal fromUnitToUnit(BigDecimal value, int fromUnit, int toUnit){
		if(value == null) return null;
		switch(fromUnit){
			case CELSIUS_UNIT:
				switch(toUnit){
				case FAHRENHEIT_UNIT: return cToF(value);
				case KELVIN_UNIT: return cToK(value);
				default: return value;
				}
			case FAHRENHEIT_UNIT:{
				BigDecimal c = fToC(value);
				switch(toUnit){
				case CELSIUS_UNIT: return c;
				case KELVIN_UNIT: return cToK(c);
				default: return value;
				}
			}
			case KELVIN_UNIT:{
				BigDecimal c = kToC(value);
				switch(toUnit){
				case CELSIUS_UNIT: return c;
				case FAHRENHEIT_UNIT: return cToF(c);
				default: return value;
				}
			}
			
			default: return value;
		}
	}
	
}

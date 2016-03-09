package com.leonard.unitsconverter;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * <string-array name="speed_units_array">
        <item>Kilometers/Hour</item>
        <item>Miles/Hour</item>
        <item>Feet/Second</item>
        <item>Knots</item>
    </string-array>
 * @author lenard
 *
 */
public class SpeedMeasure {
	private static final int KILOMETERS_HOUR_UNIT = 0;
	private static final int MILES_HOUR_UNIT = 1;
	private static final int FEET_SECOND_UNIT = 2;
	private static final int KNOTS_UNIT = 3;
	
	private static final double ONE_MILES_HOUR_TO_KILOMETERS_HOUR = 1.609344;
	private static final double ONE_FEET_SECOND_TO_KILOMETERS_HOUR = 1.09728;
	private static final double ONE_KNOT_TO_KILOMETERS_HOUR = 1.852;
	
	private static final MathContext PRECIZION = MathContext.DECIMAL64;
	
	public BigDecimal milesToKm(BigDecimal miles){
		return miles.multiply(new BigDecimal(ONE_MILES_HOUR_TO_KILOMETERS_HOUR), PRECIZION);
	}
	public BigDecimal kmToMiles(BigDecimal km){
		return km.divide(new BigDecimal(ONE_MILES_HOUR_TO_KILOMETERS_HOUR), PRECIZION);
	}
	
	public BigDecimal feetToKm(BigDecimal feet){
		return feet.multiply(new BigDecimal(ONE_FEET_SECOND_TO_KILOMETERS_HOUR),PRECIZION);
	}
	
	public BigDecimal kmToFeet(BigDecimal km){
		return km.divide(new BigDecimal(ONE_FEET_SECOND_TO_KILOMETERS_HOUR),PRECIZION);
	}
	
	public BigDecimal knotToKm(BigDecimal knot){
		return knot.multiply(new BigDecimal(ONE_KNOT_TO_KILOMETERS_HOUR),PRECIZION);
	}
	
	public BigDecimal kmToKnot(BigDecimal km){
		return km.divide(new BigDecimal(ONE_KNOT_TO_KILOMETERS_HOUR),PRECIZION);
	}
	
	public BigDecimal fromUnitToUnit(BigDecimal value, int fromUnit, int toUnit){
		if(value == null) return null;
		switch(fromUnit){
			case KILOMETERS_HOUR_UNIT:
				switch(toUnit){
				case MILES_HOUR_UNIT: return kmToMiles(value);
				case FEET_SECOND_UNIT: return kmToFeet(value);
				case KNOTS_UNIT: return kmToKnot(value);
				default: return value;
				}
			case MILES_HOUR_UNIT:{
				BigDecimal inmeter = milesToKm(value);
				switch(toUnit){
				case KILOMETERS_HOUR_UNIT: return inmeter;
				case FEET_SECOND_UNIT: return kmToFeet(inmeter);
				case KNOTS_UNIT: return kmToKnot(inmeter);
				default: return value;
				}
			}
			case FEET_SECOND_UNIT:{
				BigDecimal inmeter = feetToKm(value);
				switch(toUnit){
				case KILOMETERS_HOUR_UNIT: return inmeter;
				case MILES_HOUR_UNIT: return kmToMiles(inmeter);
				case KNOTS_UNIT: return kmToKnot(inmeter);
				default: return value;
				}
			}
			case KNOTS_UNIT:{
				BigDecimal inmeter = knotToKm(value);
				switch(toUnit){
				case KILOMETERS_HOUR_UNIT: return inmeter;
				case MILES_HOUR_UNIT: return kmToMiles(inmeter);
				case FEET_SECOND_UNIT: return kmToFeet(inmeter);
				default: return value;
				}
			}
			
			default: return value;
		}
	}
	
}

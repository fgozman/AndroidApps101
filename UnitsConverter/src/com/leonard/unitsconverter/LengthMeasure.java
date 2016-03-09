package com.leonard.unitsconverter;

import java.math.BigDecimal;
import java.math.MathContext;

public class LengthMeasure {
	private static final MathContext PRECIZION = MathContext.DECIMAL64;
	private static final double ONE_FOOT_TO_METER = 0.3048;
	private static final double ONE_INCH_TO_METER = 0.0254;
	private static final double ONE_MILE_TO_METER = 1609.344;
	private static final double ONE_YARD_TO_METER = 0.9144;
	
	public static final int METER_UNIT = 0;
	public static final int FOOT_UNIT = 1;
	public static final int INCH_UNIT = 2;
	public static final int YARD_UNIT = 3;
	public static final int MILE_UNIT = 4;
	
	public BigDecimal meterToFoot(BigDecimal meter){
		return meter.divide(new BigDecimal(ONE_FOOT_TO_METER), PRECIZION);
	}
	public BigDecimal footToMeter(BigDecimal foot){
		return foot.multiply(new BigDecimal(ONE_FOOT_TO_METER), PRECIZION);
	}
	
	public BigDecimal inchToMeter(BigDecimal inch){
		return inch.multiply(new BigDecimal(ONE_INCH_TO_METER), PRECIZION);
	}
	
	public BigDecimal meterToInch(BigDecimal meter){
		return meter.divide(new BigDecimal(ONE_INCH_TO_METER), PRECIZION);
	}
	
	public BigDecimal mileToMeter(BigDecimal mile){
		return mile.multiply(new BigDecimal(ONE_MILE_TO_METER), PRECIZION);
	}
	
	public BigDecimal meterToMile(BigDecimal meter){
		return meter.divide(new BigDecimal(ONE_MILE_TO_METER), PRECIZION);
	}
	
	
	public BigDecimal yardToMeter(BigDecimal yard){
		return yard.multiply(new BigDecimal(ONE_YARD_TO_METER), PRECIZION);
	}
	
	public BigDecimal meterToYard(BigDecimal meter){
		return meter.divide(new BigDecimal(ONE_YARD_TO_METER), PRECIZION);
	}
	
	public BigDecimal fromUnitToUnit(BigDecimal value, int fromUnit, int toUnit){
		if(value == null) return null;
		switch(fromUnit){
			case METER_UNIT:
				switch(toUnit){
				case FOOT_UNIT: return meterToFoot(value);
				case INCH_UNIT: return meterToInch(value);
				case MILE_UNIT: return meterToMile(value);
				case YARD_UNIT: return meterToYard(value);
				default: return value;
				}
			case FOOT_UNIT:{
				BigDecimal inmeter = footToMeter(value);
				switch(toUnit){
				case METER_UNIT: return inmeter;
				case INCH_UNIT: return meterToInch(inmeter);
				case MILE_UNIT: return meterToMile(inmeter);
				case YARD_UNIT: return meterToYard(inmeter);
				default: return value;
				}
			}
			case INCH_UNIT:{
				BigDecimal inmeter = inchToMeter(value);
				switch(toUnit){
				case METER_UNIT: return inmeter;
				case FOOT_UNIT: return meterToFoot(inmeter);
				case MILE_UNIT: return meterToMile(inmeter);
				case YARD_UNIT: return meterToYard(inmeter);
				default: return value;
				}
			}
			case MILE_UNIT:{
				BigDecimal inmeter = mileToMeter(value);
				switch(toUnit){
				case METER_UNIT: return inmeter;
				case FOOT_UNIT: return meterToFoot(inmeter);
				case INCH_UNIT: return meterToInch(inmeter);
				case YARD_UNIT: return meterToYard(inmeter);
				default: return value;
				}
			}
			case YARD_UNIT:{
				BigDecimal inmeter = yardToMeter(value);
				switch(toUnit){
				case METER_UNIT: return inmeter;
				case FOOT_UNIT: return meterToFoot(inmeter);
				case INCH_UNIT: return meterToInch(inmeter);
				case MILE_UNIT: return meterToMile(inmeter);
				default: return value;
				}
			}
				
			default: return value;
		}
	}
}

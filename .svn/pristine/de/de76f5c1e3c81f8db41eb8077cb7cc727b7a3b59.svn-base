package org.marmots.simulator.objects;

import java.util.Calendar;
import java.util.Date;

public class DateObjectFactory extends ObjectFactory{
	private Date min;
	private Date max;
	
	public DateObjectFactory() {
		
	}

	public DateObjectFactory(Date min, Date max) {
		this.min = min;
		this.max = max;
	}
	
	public DateObjectFactory(int calendar_field, int value) {
		Calendar calendar = Calendar.getInstance();
		if(value > 0){
			this.min = calendar.getTime();
			calendar.set(calendar_field, calendar.get(calendar_field) + value);
			this.max = calendar.getTime();
		}else{
			this.max = calendar.getTime();
			calendar.set(calendar_field, calendar.get(calendar_field) + value);
			this.min = calendar.getTime();
		}
	}
	
	public DateObjectFactory(int calendar_field, int initial_value, int final_value) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar_field, initial_value);
		this.min = calendar.getTime();
		calendar.set(calendar_field, final_value);
		this.max = calendar.getTime();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> O getInstance(Class<O> returnClass) throws Exception {
		return (O)new Date((long) (min.getTime() + (Math.random() * (max.getTime() - min.getTime()))));		
	}
	
	public static void main(String[] args) throws Exception{
		DateObjectFactory factory = new DateObjectFactory(Calendar.YEAR, 20);
		System.out.println(factory.getInstance(Date.class));
		System.out.println(Calendar.YEAR);
	}

	public Date getMin() {
		return min;
	}

	public void setMin(Date min) {
		this.min = min;
	}

	public Date getMax() {
		return max;
	}

	public void setMax(Date max) {
		this.max = max;
	}

}

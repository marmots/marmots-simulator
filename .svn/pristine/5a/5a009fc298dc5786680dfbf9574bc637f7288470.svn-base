package org.marmots.simulator.objects;

import java.text.NumberFormat;

import org.apache.commons.lang3.RandomUtils;

public class NumberObjectFactory extends ObjectFactory {
	private double min;
	private double max;

	public NumberObjectFactory() {

	}

	public NumberObjectFactory(double min, double max) {
		this.min = min;
		this.max = max;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> O getInstance(Class<O> returnClass) throws Exception {
		Object bean = null;
		if (returnClass.isAssignableFrom(Integer.class) || returnClass.isAssignableFrom(int.class)) {
			if (min == 0 && max == 0) {
				max = Integer.MAX_VALUE;
			}
			bean = (int) min + RandomUtils.nextInt(0, (int) (max - min + 1));
		} else if (returnClass.isAssignableFrom(Short.class) || returnClass.isAssignableFrom(short.class)) {
			if (min == 0 && max == 0) {
				max = Short.MAX_VALUE;
			}
			bean = (short) (min + (Math.random() * (max - min + 1)));
		} else if (returnClass.isAssignableFrom(Long.class) || returnClass.isAssignableFrom(long.class)) {
			if (min == 0 && max == 0) {
				max = Long.MAX_VALUE;
			}
			bean = (long) (min + (Math.random() * (max - min + 1)));
		} else if (returnClass.isAssignableFrom(Float.class) || returnClass.isAssignableFrom(float.class)) {
			if (min == 0 && max == 0) {
				max = Float.MAX_VALUE;
			}
			bean = (float) (min + (Math.random() * (max - min + 1)));
		} else if (returnClass.isAssignableFrom(Double.class) || returnClass.isAssignableFrom(double.class)) {
			if (min == 0 && max == 0) {
				max = Double.MAX_VALUE;
			}
			bean = min + (Math.random() * (max - min));
		}
		return (O) bean;
	}

	public static void main(String[] args) throws Exception {
		NumberFormat format = NumberFormat.getInstance();
		NumberObjectFactory factory = new NumberObjectFactory(20.02, 20.08);
		for (int i = 0; i < 20; i++) {
			System.out.println(format.format(factory.getInstance(Double.class)));
		}
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

}

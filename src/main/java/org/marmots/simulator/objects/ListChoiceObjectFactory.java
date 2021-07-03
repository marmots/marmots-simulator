package org.marmots.simulator.objects;

import org.apache.commons.lang3.RandomUtils;

public class ListChoiceObjectFactory extends ObjectFactory{
	private Object[] values = null;

	public ListChoiceObjectFactory() {
	}

	public ListChoiceObjectFactory(Object[] values) {
		this.values = values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <O> O getInstance(Class<O> returnClass) throws Exception {
		if (values != null && values.length > 0) {
			return (O) values[RandomUtils.nextInt(0, values.length)];
		} else {
			return null;
		}
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

}

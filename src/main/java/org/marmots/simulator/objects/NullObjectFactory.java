package org.marmots.simulator.objects;

public class NullObjectFactory extends ObjectFactory {

	@Override
	public <O> O getInstance(Class<O> returnClass) throws Exception {
		return null;
	}

}

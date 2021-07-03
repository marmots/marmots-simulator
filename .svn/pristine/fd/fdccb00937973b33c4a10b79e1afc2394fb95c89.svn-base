package org.marmots.simulator.objects;

public class StringObjectFactory extends ObjectFactory {
	public static final short RETURN_TYPE_EMPTY = 0;
	public static final short RETURN_TYPE_DNI = 1;
	public static final short RETURN_TYPE_NIF = 2;
	public static final short RETURN_TYPE_FIRSTNAME = 3;
	public static final short RETURN_TYPE_LASTNAME = 4;
	public static final short RETURN_TYPE_FULLNAME = 5;
	public static final short RETURN_TYPE_POSTALCODE = 6;
	public static final short RETURN_TYPE_ADDRESS = 7;
	public static final short RETURN_TYPE_PHRASE = 8;
	public static final short RETURN_TYPE_DESCRIPTION = 9;
	public static final short RETURN_TYPE_PHONE = 10;
	public static final short RETURN_TYPE_MOBILE = 11;

	private short returnType;

	public StringObjectFactory() {

	}

	public StringObjectFactory(short returnType) {
		this.returnType = returnType;
	}

	public StringObjectFactory(String returnType) {
		if (returnType.equalsIgnoreCase("empty")) {
			this.returnType = RETURN_TYPE_EMPTY;
		} else if (returnType.equalsIgnoreCase("dni")) {
			this.returnType = RETURN_TYPE_DNI;
		} else if (returnType.equalsIgnoreCase("nif")) {
			this.returnType = RETURN_TYPE_NIF;
		} else if (returnType.equalsIgnoreCase("firstname")) {
			this.returnType = RETURN_TYPE_FIRSTNAME;
		} else if (returnType.equalsIgnoreCase("lastname")) {
			this.returnType = RETURN_TYPE_LASTNAME;
		} else if (returnType.equalsIgnoreCase("fullname")) {
			this.returnType = RETURN_TYPE_FULLNAME;
		} else if (returnType.equalsIgnoreCase("postalcode")) {
			this.returnType = RETURN_TYPE_POSTALCODE;
		} else if (returnType.equalsIgnoreCase("address")) {
			this.returnType = RETURN_TYPE_ADDRESS;
		} else if (returnType.equalsIgnoreCase("phrase")) {
			this.returnType = RETURN_TYPE_PHRASE;
		} else if (returnType.equalsIgnoreCase("description")) {
			this.returnType = RETURN_TYPE_DESCRIPTION;
		} else if (returnType.equalsIgnoreCase("phone")) {
			this.returnType = RETURN_TYPE_PHONE;
		} else if (returnType.equalsIgnoreCase("mobile")) {
			this.returnType = RETURN_TYPE_MOBILE;
		}
	}

	@Override
	public <O> O getInstance(Class<O> returnClass) throws Exception {
		Object s = returnClass.newInstance();
		switch (returnType) {
		case RETURN_TYPE_DNI:
			s = SimpleObjectFactory.getRandomDni();
			break;
		case RETURN_TYPE_NIF:
			s = SimpleObjectFactory.getRandomNif();
			break;
		case RETURN_TYPE_FIRSTNAME:
			s = SimpleObjectFactory.getRandomFirstName();
			break;
		case RETURN_TYPE_LASTNAME:
			s = SimpleObjectFactory.getRandomLastName();
			break;
		case RETURN_TYPE_FULLNAME:
			s = SimpleObjectFactory.getRandomFullname();
			break;
		case RETURN_TYPE_POSTALCODE:
			s = SimpleObjectFactory.getRandomPostalCode();
			break;
		case RETURN_TYPE_ADDRESS:
			s = SimpleObjectFactory.getRandomAddress();
			break;
		case RETURN_TYPE_PHRASE:
			s = SimpleObjectFactory.getRandomPhrase();
			break;
		case RETURN_TYPE_DESCRIPTION:
			s = SimpleObjectFactory.getRandomDescription();
			break;
		case RETURN_TYPE_PHONE:
			s = new RegExpObjectFactory(RegExpObjectFactory.SPANISH_PHONE_REGEXP).getInstance(String.class);
			break;
		case RETURN_TYPE_MOBILE:
			s = new RegExpObjectFactory(RegExpObjectFactory.SPANISH_MOBILE_REGEXP).getInstance(String.class);
			break;
		}
		return returnClass.cast(s);
	}

	public short getReturnType() {
		return returnType;
	}

	public void setReturnType(short returnType) {
		this.returnType = returnType;
	}

}

package org.marmots.simulator.objects;

import com.mifmif.common.regex.Generex;

public class RegExpObjectFactory extends ObjectFactory {
	public static final String SPANISH_PHONE_REGEXP = "((981|950|945|967|920|924|971|947|927|956|956|926|942|957|964|969|972|949|958|943|959|974|953|941|928|982|987|968|948|988|979|986|923|922|975|921|977|925|978|983|980|976)[0-9]{6})|((91|93|94|95|96|98)[0-9]{7})";
	public static final String SPANISH_MOBILE_REGEXP = "(((600|605|606|607|608|609|610|615|616|617|618|619|620|622|625|626|627|628|629|630|6321|633|634|635|636|637|638|639|645|646|647|648|649|650|651|652|653|654|655|656|657|658|659|660|661|662|663|664|665|666|667|669|670|671|675|676|677|678|679|680|685|686|687|689|690|691|692|695|696|697|6981|699)[0-9]{6})|((6012|6011|6010|6021|6030|6116|6111|6401|6400|6422|6441|6444|6443|6442|6688|6724|6722|6721|6723|6725|6720|6846|6886|6888|6932|6939|6938|6937|6936|6931|6933|6934|6935)[0-9]{5}))";
	public static final String SPANISH_DNI_REGEXP = "[0-9]{8}";
	public static final String SPANISH_NIF_REGEXP = "[0-9]{8}[A-Z]";
	public static final String IP_V4_REGEXP = "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

	private Generex xeger;
	private String regexp;

	public RegExpObjectFactory() {
	}

	public RegExpObjectFactory(String regexp) {
		xeger = new Generex(regexp);
	}

	public static void main(String[] args) {
		System.out.println("Phone: " + new RegExpObjectFactory(SPANISH_PHONE_REGEXP).getInstance(String.class));
		System.out.println("Mobile: " + new RegExpObjectFactory(SPANISH_MOBILE_REGEXP).getInstance(String.class));
		System.out.println("DNI: " + new RegExpObjectFactory(SPANISH_DNI_REGEXP).getInstance(String.class));
		System.out.println("NIF: " + new RegExpObjectFactory(SPANISH_NIF_REGEXP).getInstance(String.class));

		System.out.println(new RegExpObjectFactory("[A-Z]{2}-[0-9]{10}").getInstance(String.class));
	}

	@SuppressWarnings("unchecked")
	public <O> O getInstance(Class<O> returnClass) {
		return (O) xeger.random();
	}

	public String getRegexp() {
		return regexp;
	}

	public void setRegexp(String regexp) {
		xeger = new Generex(regexp);
	}

}

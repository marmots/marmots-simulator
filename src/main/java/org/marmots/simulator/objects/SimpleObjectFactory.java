package org.marmots.simulator.objects;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.marmots.exceptions.SimulatorException;
import org.marmots.simulator.lipsum.LoremIpsum4J;
import org.marmots.simulator.utils.Slugify;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class for generating and retrieving random or fixed simulated data.
 * 
 * @author David Valls
 */
public class SimpleObjectFactory {
  public static final int MAX_AGE = 100;

  private static List<String> maleFirstNames;
  private static List<String> femaleFirstNames;
  private static List<String> lastNames;
  private static List<String> names;
  private static List<String> dnis;
  private static List<String> descriptions;
  private static List<String> phones;
  private static List<String> postalCodes;
  private static List<String> addresses;
  private static RegExpObjectFactory colorFactory = new RegExpObjectFactory("\\#([A-Fa-f0-9]{6})");

  /**
   * returns the male first name identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the male first name
   * @throws Exception
   */
  public static String getMaleFirstName(int id) {
    if (maleFirstNames == null) {
      maleFirstNames = initializeList("male_first_names.properties");
    }
    return maleFirstNames.get(id);
  }

  /**
   * returns the female first name identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the female first name
   * @throws Exception
   */
  public static String getFemaleFirstName(int id) {
    if (femaleFirstNames == null) {
      femaleFirstNames = initializeList("female_first_names.properties");
    }
    return femaleFirstNames.get(id);
  }

  /**
   * returns the last name identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the last name
   * @throws Exception
   */
  public static String getLastName(int id) {
    if (lastNames == null) {
      lastNames = initializeList("last_names.properties");
    }
    return lastNames.get(id);
  }

  /**
   * returns the full name identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the full name
   * @throws Exception
   */
  public static String getName(int id) {
    if (names == null) {
      names = initializeList("names.properties");
    }
    return names.get(id);
  }

  /**
   * returns the description (a lipsum based paragraph) identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the description
   * @throws Exception
   */
  public static String getDescription(int id) {
    if (descriptions == null) {
      descriptions = initializeList("descriptions.properties");
    }
    return descriptions.get(id);
  }

  /**
   * returns the phone identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the phone
   * @throws Exception
   */
  public static String getPhone(int id) {
    if (phones == null) {
      phones = initializeList("phones.properties");
    }
    return phones.get(id);
  }

  /**
   * returns the dni identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the dni
   * @throws Exception
   */
  public static String getDni(int id) {
    if (dnis == null) {
      dnis = initializeList("dnis.properties");
    }
    return dnis.get(id);
  }

  /**
   * returns the nif identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the nif
   * @throws Exception
   */
  public static String getNif(int id) {
    String dni = getDni(id);
    return dni + calculateNifKey(dni);
  }

  /**
   * returns the spanish postal code identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the spanish postal code
   * @throws Exception
   */
  public static String getPostalCode(int id) {
    if (postalCodes == null) {
      postalCodes = initializeList("spanish_postal_codes.properties");
    }
    return postalCodes.get(id);
  }

  /**
   * returns the address identified by parameter id (always the same)
   * 
   * @param id
   *          identifier
   * @return String the address
   * @throws Exception
   */
  public static String getAddress(int id) {
    if (addresses == null) {
      addresses = initializeList("addresses.properties");
    }
    String address = addresses.get(id);
    int pos = address.lastIndexOf(",");
    return address.substring(pos + 2) + " " + address.substring(0, pos) + ", " + (RandomUtils.nextInt(0, 550) + 1);
  }

  // ---------------------------------------------------------
  // (RECOMENDED) PUBLIC METHODS
  // ---------------------------------------------------------

  public static String getRandomListChoiceValue(String[] choices) {
    return choices[RandomUtils.nextInt(0, choices.length)];
  }

  public static String getRandomColor() {
    return colorFactory.getInstance(String.class);
  }

  public static Date getRandomDateTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, RandomUtils.nextInt(0, 60));
    calendar.set(Calendar.MINUTE, RandomUtils.nextInt(0, 60));
    calendar.set(Calendar.HOUR_OF_DAY, RandomUtils.nextInt(0, 24));
    calendar.set(Calendar.DAY_OF_MONTH, RandomUtils.nextInt(1, 31));
    calendar.set(Calendar.MONTH, RandomUtils.nextInt(1, 12));

    if (getRandomBoolean()) {
      calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - RandomUtils.nextInt(0, 10));
    } else {
      calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + RandomUtils.nextInt(0, 10));
    }

    return new Date(calendar.getTimeInMillis());
  }

  public static Date getRandomDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.DAY_OF_MONTH, RandomUtils.nextInt(1, 31));
    calendar.set(Calendar.MONTH, RandomUtils.nextInt(1, 12));

    if (getRandomBoolean()) {
      calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - RandomUtils.nextInt(0, 10));
    } else {
      calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + RandomUtils.nextInt(0, 10));
    }

    return new Date(calendar.getTimeInMillis());
  }

  public static Date getRandomBirthDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.MILLISECOND, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - RandomUtils.nextInt(1, 31));
    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - RandomUtils.nextInt(1, 12));
    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - RandomUtils.nextInt(0, MAX_AGE));

    return new Date(calendar.getTimeInMillis());
  }

  public static boolean getRandomBoolean() {
    return RandomUtils.nextInt(0, 1) == 0;
  }

  public static String getRandomString(int limit) {
    return RandomStringUtils.randomAlphanumeric(limit);
  }

  public static short getRandomShort() {
    return (short) RandomUtils.nextInt(0, Short.MAX_VALUE);
  }

  public static int getRandomInt() {
    return getRandomInt(Integer.MAX_VALUE);
  }

  public static int getRandomInt(int max) {
    return RandomUtils.nextInt(0, max);
  }

  public static long getRandomLong() {
    return RandomUtils.nextLong(0, Long.MAX_VALUE);
  }

  public static float getRandomFloat() {
    return RandomUtils.nextFloat(0, Float.MAX_VALUE);
  }

  public static double getRandomDouble() {
    return getRandomDouble(0, Double.MAX_VALUE);
  }

  public static double getRandomDouble(double min, double max) {
    if (min < 0) {
      return RandomUtils.nextDouble(0, max) - RandomUtils.nextDouble(0, Math.abs(min));
    } else {
      return RandomUtils.nextDouble(min, max);
    }
  }

  public static BigDecimal getRandomBigDecimal(int precision, int scale) {
    return new BigDecimal(RandomUtils.nextInt(0, precision - scale) + "." + RandomUtils.nextInt(0, scale));
  }

  public static BigDecimal getRandomBigDecimal() {
    return new BigDecimal(RandomUtils.nextDouble(0, Double.MAX_VALUE));
  }

  /**
   * Returns a random spanish dni from a list of generated dnis
   * 
   * @return String the spanish dni
   * @throws Exception
   */
  public static String getRandomDni() {
    if (dnis == null) {
      dnis = initializeList("dnis.properties");
    }
    return getDni(RandomUtils.nextInt(0, dnis.size()));
  }

  /**
   * Returns a random spanish nif from a list of generated dnis and concatenating corresponding key
   * 
   * @return String the nif
   * @throws Exception
   */
  public static String getRandomNif() {
    String dni = getRandomDni();
    return dni + calculateNifKey(dni);
  }

  /**
   * Returns a random spanish postal code from a list of all valid postal codes
   * 
   * @return String the spanish postal code
   * @throws Exception
   */
  public static String getRandomPostalCode() {
    if (postalCodes == null) {
      postalCodes = initializeList("spanish_postal_codes.properties");
    }
    return getPostalCode(RandomUtils.nextInt(0, postalCodes.size()));
  }

  /**
   * Returns a random address from a list of all valid addresses
   * 
   * @return String the spanish postal code
   * @throws Exception
   */
  public static String getRandomAddress() {
    if (addresses == null) {
      addresses = initializeList("addresses.properties");
    }
    return getAddress(RandomUtils.nextInt(0, addresses.size()));
  }

  public static String getRandomMaleFirstName() {
    if (maleFirstNames == null) {
      maleFirstNames = initializeList("male_first_names.properties");
    }
    return getMaleFirstName(RandomUtils.nextInt(0, maleFirstNames.size()));
  }

  public static String getRandomFemaleFirstName() {
    if (femaleFirstNames == null) {
      femaleFirstNames = initializeList("female_first_names.properties");
    }
    return getFemaleFirstName(RandomUtils.nextInt(0, femaleFirstNames.size()));
  }

  public static String getRandomFirstName() {
    int gender = RandomUtils.nextInt(0, 2); // 0 -> Male, 1 -> Female
    String name = "";
    switch (gender) {
    case 0:
      name = getRandomFemaleFirstName();
      break;

    case 1:
      name = getRandomMaleFirstName();
      break;
    }
    return name;
  }

  /**
   * Returns a random full name generated from a list of male names or female names and a list of last names
   * 
   * @return String the full name
   * @throws Exception
   */
  public static String getRandomFullname() {
    String name = getRandomFirstName();
    if (lastNames == null) {
      lastNames = initializeList("last_names.properties");
    }
    String last_name1 = getRandomLastName();
    String last_name2 = getRandomLastName();
    return name + " " + last_name1 + " " + last_name2;
  }

  public static String getRandomLastName() {
    if (lastNames == null) {
      lastNames = initializeList("last_names.properties");
    }
    return getLastName(RandomUtils.nextInt(0, lastNames.size()));
  }

  /**
   * Returns a random description (lipsum based paragraph) using lipsum web service.
   * 
   * @return the description
   */
  public static String getLipsumRandomDescription() {
    return initLoremIpsum().getParagraphs(1)[0];
  }

  private static LoremIpsum4J loremIpsum;

  private static LoremIpsum4J initLoremIpsum() {
    if (loremIpsum == null) {
      LoremIpsum4J loremIpsum = new LoremIpsum4J();
      loremIpsum.setStartWithLoremIpsum(false);
    }
    return loremIpsum;
  }

  public static String getRandomDescription() {
    if (descriptions == null) {
      descriptions = initializeList("descriptions.properties");
    }
    return getDescription(RandomUtils.nextInt(0, descriptions.size()));
  }

  public static String getRandomDescription(int limit) {
    String description = getRandomDescription();
    return description.length() > limit ? description.substring(0, limit).trim() : description;
  }

  public static String getRandomTitle() {
    String title = getRandomDescription();
    int index = Math.min(title.indexOf(","), Math.min(title.indexOf("."), title.length()));
    return title.substring(0, index);
  }

  public static String getRandomTitle(int limit) {
    String title = getRandomTitle();
    return title.length() > limit ? title.substring(0, limit) : title;
  }

  // TODO multileveled
  public static String getRandomHTMLDescription() {
    return getRandomHTMLDescription(1);
  }

  // TODO check limit correctly
  public static String getRandomHTML(int limit) {
    String html = "";
    int s = RandomUtils.nextInt(2, 5);
    for (int i = 0; i <= s && limit > html.length(); i++) {
      html += "<p>" + getRandomDescription() + "</p>";
    }
    html = html.length() <= limit ? html : html.substring(0, html.lastIndexOf("<p>"));
    return html.length() == 0 ? "<p>" + getRandomDescription(limit - 7) + "</p>" : html;
  }

  public static String getRandomHTMLDescription(int level) {
    String html = "";
    int s1 = RandomUtils.nextInt(2, 4);
    for (int i = 1; i <= s1; i++) {
      html += "<h" + (i + level) + ">" + getRandomTitle() + "</h" + (i + level) + ">";

      int s2 = RandomUtils.nextInt(1, 5);
      for (int j = 1; j <= s2; j++) {
        html += "<p>" + getRandomDescription() + "</p>";
      }
    }
    return html;
  }

  public static String getRandomPhone() {
    return RandomStringUtils.randomNumeric(9);
  }

  public static String getUuid() {
    return UUID.randomUUID().toString();
  }

  public static String getRandomEmail() {
    return Slugify.slugify(getRandomFirstName().replaceAll(" ", "").toLowerCase()) + "@" + Slugify.slugify(getRandomLastName().replaceAll(" ", "").toLowerCase()) + ".com";
  }

  public static String getRandomUniqueEmail() {
    return Slugify.slugify(getRandomFirstName().replaceAll(" ", "").toLowerCase() + "_" + getRandomInt(99999)) + "@" + Slugify.slugify(getRandomLastName().replaceAll(" ", "").toLowerCase()) + ".com";
  }

  public static String getRandomContentType() {
    return "text/plain";
  }

  /**
   * Returns a random phrase (lipsum based phrase) using lipsum web service.
   * 
   * @return the phrase
   */
  public static String getRandomPhrase() {
    return initLoremIpsum().getWords(1)[0];
  }

  /**
   * Generates a file containing a list of random phones (9 length numeric strings)
   * 
   * @throws Exception
   */
  public static void generateRandomPhones() {
    File file = new File("./src/org/marmots/simulator/objects/phones.properties");
    try (PrintWriter out = new PrintWriter(file)) {
      for (int i = 0; i < 10000; i++) {
        String phone = Integer.toString(RandomUtils.nextInt(0, 999999999));
        while (phone.length() < 9) {
          phone += "0";
        }
        out.println(phone);
      }
    } catch (Exception e) {
      throw new SimulatorException(e);
    }
  }

  /**
   * Generates a file containing a list of random dnis (8 length numeric strings)
   * 
   * @throws Exception
   */
  public static void generateRandomDnis() {
    File file = new File("./src/org/marmots/simulator/objects/dnis.properties");
    try (PrintWriter out = new PrintWriter(file)) {
      for (int i = 0; i < 10000; i++) {
        String dni = Integer.toString(RandomUtils.nextInt(0, 99999999));
        while (dni.length() < 8) {
          dni += "0";
        }
        out.println(dni);
      }
    } catch (Exception e) {
      throw new SimulatorException(e);
    }
  }

  /**
   * Generates a file containing the list of autonomies in spain (using geonames web service)
   * 
   * @throws Exception
   */
  public static void generateAutonomies() {
    try {
      URL url = new URL("http://ws.geonames.org/children?geonameId=2510769");
      File file = new File("./src/org/marmots/simulator/objects/autonomies.properties");
      try (PrintWriter out = new PrintWriter(file)) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(url.openStream());
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("geoname");
        for (int s = 0; s < nodeList.getLength(); s++) {
          Node fstNode = nodeList.item(s);
          if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
            Element fstElmnt = (Element) fstNode;
            String comunitat = fstElmnt.getElementsByTagName("toponymName").item(0).getFirstChild().getNodeValue();
            out.println(comunitat);
          }
        }
      }
    } catch (Exception e) {
      throw new SimulatorException(e);
    }
  }

  /**
   * Generates a file containing the list of provinces in spain (using geonames web service)
   * 
   * @throws Exception
   */
  public static void generateProvinces() {
    try {
      URL url = new URL("http://ws.geonames.org/children?geonameId=2510769");
      File file = new File("./src/org/marmots/simulator/objects/provinces.properties");
      try (PrintWriter out = new PrintWriter(file)) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(url.openStream());
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("geoname");
        for (int s = 0; s < nodeList.getLength(); s++) {
          Node fstNode = nodeList.item(s);
          if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
            Element fstElmnt = (Element) fstNode;
            String autonomyId = fstElmnt.getElementsByTagName("geonameId").item(0).getFirstChild().getNodeValue();
            String autonomyUrlString = "http://ws.geonames.org/children?geonameId=" + autonomyId;
            System.out.println("parsing: " + autonomyUrlString);
            URL autonomyUrl = new URL(autonomyUrlString);
            Document autonomyDoc = builder.parse(autonomyUrl.openStream());
            autonomyDoc.getDocumentElement().normalize();
            NodeList autonomyNodeList = autonomyDoc.getElementsByTagName("geoname");
            for (int i = 0; i < autonomyNodeList.getLength(); i++) {
              Node autonomyFstNode = autonomyNodeList.item(i);
              if (autonomyFstNode.getNodeType() == Node.ELEMENT_NODE) {
                Element autonomyFstElmnt = (Element) autonomyFstNode;
                String province = autonomyFstElmnt.getElementsByTagName("toponymName").item(0).getFirstChild().getNodeValue();
                out.println(province);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      throw new SimulatorException(e);
    }
  }

  /**
   * Generates three files:
   * <ul>
   * <li>a file containing the list of autonomies in spain (using geonames web service)</li>
   * <li>a file containing the list of provinces in spain (using geonames web service)</li>
   * <li>a file containing the list of cities in spain (using geonames web service)</li>
   * </ul>
   * 
   * @throws Exception
   */
  public static void generateLocations() {
    try {
      URL url = new URL("http://ws.geonames.org/children?geonameId=2510769");
      System.out.println("parsing country (spain: 2510769)");

      File autonomiesFile = new File("./src/org/marmots/simulator/objects/autonomies.properties");
      PrintWriter autonomies = new PrintWriter(autonomiesFile);
      File provincesFile = new File("./src/org/marmots/simulator/objects/provinces.properties");
      PrintWriter provinces = new PrintWriter(provincesFile);
      File citiesFile = new File("./src/org/marmots/simulator/objects/cities.properties");
      PrintWriter cities = new PrintWriter(citiesFile);

      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(url.openStream());
      doc.getDocumentElement().normalize();
      NodeList nodeList = doc.getElementsByTagName("geoname");
      for (int s = 0; s < nodeList.getLength(); s++) {
        Node fstNode = nodeList.item(s);
        if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
          Element fstElmnt = (Element) fstNode;
          String autonomy = fstElmnt.getElementsByTagName("toponymName").item(0).getFirstChild().getNodeValue();
          autonomies.println(autonomy);

          String autonomyId = fstElmnt.getElementsByTagName("geonameId").item(0).getFirstChild().getNodeValue();
          String autonomyUrlString = "http://ws.geonames.org/children?geonameId=" + autonomyId;
          System.out.println("parsing autonomy: " + autonomyUrlString);
          URL autonomyUrl = new URL(autonomyUrlString);
          Document autonomyDoc = builder.parse(autonomyUrl.openStream());
          autonomyDoc.getDocumentElement().normalize();
          NodeList autonomyNodeList = autonomyDoc.getElementsByTagName("geoname");
          for (int i = 0; i < autonomyNodeList.getLength(); i++) {
            Node autonomyFstNode = autonomyNodeList.item(i);
            if (autonomyFstNode.getNodeType() == Node.ELEMENT_NODE) {
              Element autonomyFstElmnt = (Element) autonomyFstNode;
              String province = autonomyFstElmnt.getElementsByTagName("toponymName").item(0).getFirstChild().getNodeValue();
              provinces.println(province);

              String provinceId = autonomyFstElmnt.getElementsByTagName("geonameId").item(0).getFirstChild().getNodeValue();
              String provinceUrlString = "http://ws.geonames.org/children?geonameId=" + provinceId;
              System.out.println("parsing province: " + provinceUrlString);
              URL provinceUrl = new URL(provinceUrlString);
              Document provinceDoc = builder.parse(provinceUrl.openStream());
              provinceDoc.getDocumentElement().normalize();
              NodeList provinceNodeList = provinceDoc.getElementsByTagName("geoname");
              for (int j = 0; j < provinceNodeList.getLength(); j++) {
                Node provinceFstNode = provinceNodeList.item(j);
                if (provinceFstNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element provinceFstElmnt = (Element) provinceFstNode;
                  String city = provinceFstElmnt.getElementsByTagName("toponymName").item(0).getFirstChild().getNodeValue();
                  cities.println(city);
                }
              }
            }
          }
        }
      }
      autonomies.flush();
      autonomies.close();
      provinces.flush();
      provinces.close();
      cities.flush();
      cities.close();
    } catch (Exception e) {
      throw new SimulatorException(e);
    }
  }

  /**
   * Returns a string concatenating autonomy, province and city for postal code parameter.
   * 
   * @param postalCode
   *          String the spanish postal code
   * @return the location string
   * @throws Exception
   */
  public static String[] getLocation(String postalCode) {
    try {
      URL url = new URL("http://ws.geonames.org/postalCodeSearch?postalcode=" + postalCode + "&country=ES&maxRows=10");
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(url.openStream());
      doc.getDocumentElement().normalize();
      NodeList nodeList = doc.getElementsByTagName("code");
      for (int s = 0; s < nodeList.getLength(); s++) {
        Node fstNode = nodeList.item(s);
        if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
          Element fstElmnt = (Element) fstNode;

          String poblacio = fstElmnt.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
          String provincia = null;
          try {
            provincia = fstElmnt.getElementsByTagName("adminName2").item(0).getFirstChild().getNodeValue();
          } catch (Exception e) {
            // Sometimes province is in adminName2 attribute, but
            // sometimes on adminName3...
            provincia = fstElmnt.getElementsByTagName("adminName3").item(0).getFirstChild().getNodeValue();
          }
          String comunitat = fstElmnt.getElementsByTagName("adminName1").item(0).getFirstChild().getNodeValue();
          String[] locations = { poblacio, provincia, comunitat };
          return locations;
        }
      }
      // throw new NotFoundException("Locations not found using: " +
      // postalCode);
      // Encara que em pesi...
      String[] locations = { "", "", "" };
      return locations;
    } catch (Exception e) {
      throw new SimulatorException(e);
    }
  }

  private static List<String> initializeList(String resource) throws SimulatorException {
    try {
      InputStream input = SimpleObjectFactory.class.getResourceAsStream(resource);
      return IOUtils.readLines(input);
    } catch (Exception e) {
      throw new SimulatorException(e);
    }
  }

  private static final String[] keys = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E" };

  public static String calculateNifKey(int dni) {
    return keys[dni % 23];
  }

  public static String calculateNifKey(String dni) {
    return calculateNifKey(Integer.parseInt(dni));
  }
}

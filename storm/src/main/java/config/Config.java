package config;

public class Config {

	public static int DomainMostPerMin;
	public static int DomainEveryIPMostPerMin;
	public static String DomainWhiteFile;

	public static int IPMostPerMin;
	public static String IPWhiteFile;

	public static int PathMostPerMin;

	public static int UAMostPerMin;
	public static String DangerousUAFile;

	public static void getConfigInfo () {
		ConfigReader reader = new ConfigReader("../../../../config.ini");
		DomainMostPerMin = Integer.parseInt(reader.get("Domain", "DomainMostPerMin"));
		DomainEveryIPMostPerMin = Integer.parseInt(reader.get("Domain", "DomainEveryIPMostPerMin"));
		DomainWhiteFile = reader.get("Domain", "DomainWhiteFile");

		IPMostPerMin = Integer.parseInt(reader.get("IP", "IPMostPerMin"));
		IPWhiteFile = reader.get("IP", "IPWhiteFile");

		PathMostPerMin = Integer.parseInt(reader.get("Path", "PathMostPerMin"));

		UAMostPerMin = Integer.parseInt(reader.get("UA", "UAMostPerMin"));
		DangerousUAFile = reader.get("UA", "DangerousUAFile");
	}


}

public class Setting {

	String AppKey;
	String AppSecret;
	String WordPressUserName;
	String WordPressPassword;
	String WordPressURL;
	String LineFormat;
	
	public Boolean isEveryThingLoaded()
	{
		if (AppKey == null || AppSecret == null)
			return false;
		else
			return true;
	}
}

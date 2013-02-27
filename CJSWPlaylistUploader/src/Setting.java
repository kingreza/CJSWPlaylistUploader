
public class Setting {

	String AppKey;
	String AppSecret;
	String DropBoxFolder;
	String WordPressUserName;
	String WordPressPassword;
	String WordPressURL;
	String LineFormat;
	Boolean OutputAsIs = true;
	String TrackPattern;
	String ArtistPattern;
	String AlbumPattern;
	String LabelPattern;
	String ExtraPattern;
	
	public Boolean isDropBoxAppKeysSet()
	{
		if (AppKey == null || AppSecret == null)
			return false;
		else
			return true;
	}
}

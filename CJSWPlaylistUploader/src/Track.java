


public class Track {
	
	String Title;
	String Artist;
	String Album;
	String Label;
	String Extra;
	String OriginalLineFromPlaylist;
	String FormattedLine;

	Track(String LineFromPlaylist, Boolean setPatterns)
	{
		OriginalLineFromPlaylist = LineFromPlaylist;
		if (setPatterns)
		{
			//TODO: implement setting patterns for parsing out different properties from the playlist file.
			SetFormattedLine();
		}
		
	}
	
	public void SetFormattedLine()
	{
		//TODO: Create the formatted line entry for the track.
		
		FormattedLine = OriginalLineFromPlaylist;
		FormattedLine = FormattedLine.replace("--", "");
		FormattedLine = FormattedLine.replace("–\t(\t\t)", "");
		FormattedLine = FormattedLine.replace("(\t\t)", "");
		FormattedLine = FormattedLine.replace("(\t", "(");
		FormattedLine = FormattedLine.replace("\t)", ")");
		FormattedLine = FormattedLine.replace("\t - <i>\t\t</i>", "");
		
	}
	public void SetTitleFromLine(String Line)
	{
		//final Pattern pattern = Pattern.compile("<b>");
		
	}
	
}

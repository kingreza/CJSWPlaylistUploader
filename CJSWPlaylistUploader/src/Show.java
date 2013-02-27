
public class Show {
	
	String ShowTitle;
	String ShowTime;
	Boolean FailedToLoad = false;
	Track[] Tracks;
	
	Show(String FileName, String PlayListContent, Boolean useFormat) throws Exception
	{
		try
		{
			ShowTitle = FileName.split("_")[0];
			ShowTitle = ShowTitle.replace("click-here-to-select-your-show-", "");
			ShowTitle = ShowTitle.replace("-"," ");
			
			if (ShowTitle.length() < 3)
			{
				throw new Exception("Show title not valid");
			}
			
					
			ShowTime = FileName.split("_")[1];
			String[] _tracks = PlayListContent.split("\r");
			Tracks = new Track[_tracks.length];
			for (int i = 0; i < _tracks.length-1; i++)
			{
				Tracks[i] = new Track(_tracks[i], useFormat);
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			FailedToLoad =true;
		
		}
		
	}
	public String GetTheTextForAllTheTracks(Boolean useFormat)
	{
		String result = "";
		if (!useFormat)
		{
			for (int i = 0 ; i <Tracks.length-1; i++)
			{
				result = result +"\r"+ Tracks[i].OriginalLineFromPlaylist;
			}
			
		}
		else
		{
			for (int i = 0 ; i <Tracks.length-1; i++)
			{
				result = result  +"\r"+ Tracks[i].FormattedLine;
			}
		}
		return result;
		
	}

}

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;



import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;


import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession.WebAuthInfo;



public enum SettingManager{
	INSTANCE;
	private Setting _setting;
	public Boolean isSettingSet = false;
	DropboxAPI<WebAuthSession> _mDBApi; 
	private  AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	Wordpress wp;
	
	
	
	public void CreateBlankSettingConfig() throws IOException
	{
		File f = new File("setting.config");
		if (!f.exists())
		{
			f.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("setting.config"));
		try
		{
			writer.write("AppKey=\n");
			writer.write("AppSecret=\n");
			writer.write("DropBoxFolder=\n");
			writer.write("WordPressUsername=\n");
			writer.write("WordPressPassword=\n");
			writer.write("WordPressURL=\n");
			writer.write("OutputAsIs=true\n");
			writer.write("LineFormat=\n");
			writer.write("TrackPattern=\n");
			writer.write("ArtistPattern=\n");
			writer.write("AlbumPattern=\n");
			writer.write("LabelPattern=\n");
			writer.write("ExtraPattern=\n");

			
		}finally
		{
			writer.close();
		}
		
	}
	public Boolean isLineValid(String st)
	{
		if (st.split("=").length ==2)
			return true;
		else
			return false;
	}
	public int LoadSettings() throws IOException
	{
		File f = new File("setting.config");
		_setting = new Setting();
		if(!f.exists())
		{
			 return -2;
		}
			
		BufferedReader brd = new BufferedReader( new FileReader("setting.config"));
		 try{
			 
			 String line;
			 while((line = brd.readLine()) != null) {
				 
			if (line.startsWith("AppKey="))
			{
				if (isLineValid(line))
				_setting.AppKey = line.split("=")[1];
			}
																
			if (line.startsWith("AppSecret="))
			{
				if (isLineValid(line))
					_setting.AppSecret = line.split("=")[1];
			}
			if (line.startsWith("DropBoxFolder="))
			{
				if (isLineValid(line))
					_setting.DropBoxFolder = line.split("=")[1];
			}
			if (line.startsWith("WordPressUsername="))
			{
				if (isLineValid(line))
					_setting.WordPressUserName = line.split("=")[1];
				else
					return -3;

			}
			if (line.startsWith("WordPressPassword="))
			{
				if (isLineValid(line))
					_setting.WordPressPassword = line.split("=")[1];
				else
					return -3;
	
			}
			if (line.startsWith("WordPressURL="))
			{
				if (isLineValid(line))
					_setting.WordPressURL = line.split("=")[1];
				else
					return -3;

			}
			if (line.startsWith("OutputAsIs="))
			{
				if (isLineValid(line))
					_setting.OutputAsIs = Boolean.parseBoolean(line.split("=")[1]);
				else
					return -3;
			}
			if (line.startsWith("LineFormat="))
			{
				if (isLineValid(line))
					_setting.LineFormat = line.split("=")[1];
				else
					return -3;
			}
			if (line.startsWith("TrackPattern="))
			{
				if (isLineValid(line))
					_setting.TrackPattern = line.split("=")[1];
				else
					return -3;
			}
			if (line.startsWith("ArtistPattern="))
			{
				if (isLineValid(line))
					_setting.ArtistPattern = line.split("=")[1];
				else
					return -3;
			}
			if (line.startsWith("AlbumPattern="))
			{
				if (isLineValid(line))
					_setting.AlbumPattern = line.split("=")[1];
				else
					return -3;
			}
			if (line.startsWith("LabelPattern="))
			{
				if (isLineValid(line))
					_setting.LabelPattern = line.split("=")[1];
				else
					return -3;
			}
			if (line.startsWith("ExtraPattern="))
			{
				if (isLineValid(line))
					_setting.ExtraPattern = line.split("=")[1];
				else
					return -3;
			}
			
			
		 }
	isSettingSet = _setting.isDropBoxAppKeysSet();
			
	return 1;
		
 }
 catch(Exception e)
 {
	 System.out.print(e.getMessage());
	 return -1;
 }
 finally
{
			brd.close();
		}
	}
	public Boolean SaveSetting()throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter("setting.config"));
		try
		{
			if(isSettingSet)
			{
				//String content = _setting.AppKey+"~"+_setting.AppSecret;
				writer.write("AppKey="+_setting.AppKey+"\n");
				writer.write("AppSecret="+_setting.AppSecret+"\n");
				writer.write("DropBoxFolder="+_setting.DropBoxFolder+"\n");
				writer.write("WordPressUsername="+_setting.WordPressUserName+"\n");
				writer.write("WordPressPassword="+_setting.WordPressPassword+"\n");
				writer.write("WordPressURL="+_setting.WordPressURL+"\n");
				writer.write("OutputAsIs="+_setting.OutputAsIs.toString()+"\n");
				writer.write("LineFormat="+_setting.LineFormat+"\n");
				writer.write("TrackPattern="+_setting.TrackPattern+"\n");
				writer.write("ArtistPattern="+_setting.ArtistPattern+"\n");
				writer.write("AlbumPattern="+_setting.AlbumPattern+"\n");
				writer.write("LabelPattern="+_setting.LabelPattern+"\n");
				writer.write("ExtraPattern="+_setting.ExtraPattern+"\n");

				
				return true;
			}else
			{
				return false;
			}
			
		}catch (Exception e)
		{
			 System.out.print(e.getMessage());
			 return false;
		}finally
		{
			writer.close();
		}
		
		
	}
	public Boolean HasUserAuthBefore()
	{
		if (isSettingSet)
		{
			return true;
		}else
		return false;
	}
	public DropboxAPI<WebAuthSession>  AuthUser (String key, String secret)   throws Exception
	{
		AppKeyPair appKeys = new AppKeyPair(key, secret);
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
		WebAuthInfo authInfo = session.getAuthInfo();
		
		RequestTokenPair pair = authInfo.requestTokenPair;
		String url = authInfo.url;
		

		Desktop.getDesktop().browse(new URL(url).toURI());
		//JOptionPane.showMessageDialog(null, "Press ok once you have authenticated.");
		
		
		DisplayManager.WEBSITETOVISIT(url);
		
		DisplayManager.AUTHENTICATINGUSER();
		
		session.retrieveWebAccessToken(pair);
		AccessTokenPair tokens = session.getAccessTokenPair();
		
		
		_setting.AppKey = tokens.key;
		_setting.AppSecret = tokens.secret;
		isSettingSet = true;
		
		SaveSetting();
		
		_mDBApi = new DropboxAPI<WebAuthSession>(session);
		
		return _mDBApi;
	}
	public DropboxAPI<WebAuthSession> reAuthUser(String key, String secret)throws Exception
	{//this stuff is the same from above
		
		if(isSettingSet)
		{
			
			AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
		
			AppKeyPair appKeys = new AppKeyPair(key, secret);
			WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
			_mDBApi = new DropboxAPI<WebAuthSession>(session);

			// re-auth specific stuff
			// ACCESS_TOKEN_KEY & SECRET both correspond from the two values that
			// you should have stored in the initial auth from above
			AccessTokenPair reAuthTokens = new AccessTokenPair(_setting.AppKey, _setting.AppSecret);
			_mDBApi.getSession().setAccessTokenPair(reAuthTokens);
			
			return _mDBApi;
			
		}else
		{
			throw new Exception("the app needs to be authorized before the user can use it");
		}
		
		
	
	}
	public Boolean CheckFolderStructure() throws Exception
	{
		
		try
		{
			String path = _setting.DropBoxFolder;
			
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();
			
			Boolean procFolderFound = false;
			Boolean errFolderFound = false;
			
			for (int i=0; i <listOfFiles.length;i++)
			{
				if (listOfFiles[i].isDirectory())
				{
					if (listOfFiles[i].getName().equals("Processed"))
					{
						procFolderFound = true;
					}
					if (listOfFiles[i].getName().equals("Error"))
					{
						errFolderFound = true;
					}
					
				}
			}
			if (!procFolderFound)
			{
				new File(folder.getPath()+"/Processed").mkdir();
			}
			if (!errFolderFound)
			{
				new File(folder.getPath()+"/Error").mkdir();	
			}
			return true;
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	public Boolean CheckFolderStructure( DropboxAPI<WebAuthSession> mDBApi) throws DropboxException
	{
		try {
			Entry rootFolder = mDBApi.metadata("/", 1000, null, true, null);
			Boolean procFolderFound = false;
			Boolean errFolderFound = false;
			
			for (int i = 0 ; i < rootFolder.contents.size();i++)
			{
				if (rootFolder.contents.get(i).isDir)
				{
					if(rootFolder.contents.get(i).path.equals("/Error"))
					{
						errFolderFound = true;
					}
					if(rootFolder.contents.get(i).path.equals("/Processed"))
					{
						procFolderFound = true;
					}
		
				}
			}
			
			if (!procFolderFound)
			{
				mDBApi.createFolder("/Processed");
			}
	
			if (!errFolderFound)
			{
				mDBApi.createFolder("/Error");
			}
			
			return true;
		} catch (DropboxException e) {
	
			e.printStackTrace();
			return false;
		}
	
	}
	public static String convertStreamToString(java.io.InputStream is) throws IOException {
		java.util.Scanner  s = new Scanner(is,"UTF-8");
		s.useDelimiter("\\A");
		try
		{
			
			 if(s.hasNext())
			 {
				 return s.next();
			 }
			 else
			 {
				 return "";
			 }
					    
		}
	    finally
	    {
	    	s.close();
	    }
	}
	public Boolean WordPressAuthenticate()throws Exception
	{
		String UserName = _setting.WordPressUserName;
		String Password = _setting.WordPressPassword;
		String xmlRpcUrl = _setting.WordPressURL;
		
		
		wp = new Wordpress(UserName,Password,xmlRpcUrl);
	
		return true;
	}
	
	public Boolean MoveFileFromSourceToDestination(File sourceFile, String folder) throws Exception
	{
		try
		{
			 Date date = new Date();
			  SimpleDateFormat ft = 
				      new SimpleDateFormat ("yyyy.MM.dd-hh-mm-ss");
			  
			
			  
			String sourcePath = sourceFile.getAbsolutePath();
			  String filePath = sourcePath.
					    substring(0,sourcePath.lastIndexOf(File.separator));
			  
			String destinPath = filePath+"\\"+folder;
			sourceFile.renameTo(new File(destinPath+"\\"+ft.format(date)+"-"+sourceFile.getName()));
			return true;
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	public Boolean PostShowsContentOnWordPress(Show newShow)throws Exception
	{
		try
		{
		//Boolean useFormatting = !_setting.OutputAsIs;
		Page newPage = new Page();
		newPage.setTitle(newShow.ShowTitle);
		String content = newShow.GetTheTextForAllTheTracks(true);
		if (content == null || content.length() < 5)
		{
			System.out.println(newShow.ShowTitle+ " doesn't have a tracklist?");
			return false;
		}
		newPage.setDescription(content);
		wp.newPost(newPage, true);
		return true;
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	public Boolean ProcessFiles() throws Exception 
	{
		InputStream is;
		try
		{
			
			File rootFolder = new File(_setting.DropBoxFolder);
			
			File[] files = rootFolder.listFiles();
		
			  for (int i = 0 ; i < files.length;i++)
			  {
				  
				  if (files[i].isFile()&&!files[i].isHidden() )
				  {
					  Thread.sleep(5 * 1000);
					   is = new FileInputStream(files[i]);
					   try
					   {
						   boolean erro = false;
						   String content =  convertStreamToString(is);
						   String fileName = files[i].getName();
						   Show newShow = new Show(fileName,content,_setting.OutputAsIs);
						   
						   if(newShow.FailedToLoad)
						   {
							   erro = true;
						   }
						   
						   if (!PostShowsContentOnWordPress(newShow))
						   {
							   erro = true;
						   }
						   if (!erro)
							{
								System.out.println(fileName+" was proccess and posted to wordpress");
								MoveFileFromSourceToDestination(files[i],"Processed");
							//
							}else
							{
								System.out.println("There was a problem with processing "+fileName);
								MoveFileFromSourceToDestination(files[i],"Error");
								
								
							}
						   
					   }finally
					   {
						   is.close();
					   }
					  
					  
					  
				  }
					  
			  }
			
			return true;
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	public Boolean ProcessFiles( DropboxAPI<WebAuthSession> mDBApi) throws DropboxException, IOException
	{
		try
		{
			Entry rootFolder = mDBApi.metadata("/", 1000, null, true, null);
			 Date date = new Date();
			  SimpleDateFormat ft = 
				      new SimpleDateFormat ("yyyy.MM.dd-hh-mm-ss");
			
			for (int i = 0 ; i < rootFolder.contents.size();i++)
			{
				if (!rootFolder.contents.get(i).isDir)
				{
					 DropboxAPI.DropboxInputStream st = mDBApi.getFileStream(rootFolder.contents.get(i).path,null);

					String content = convertStreamToString(st);
					
					Show newShow = new Show(rootFolder.contents.get(i).fileName(),content,_setting.OutputAsIs);
					if (PostShowsContentOnWordPress(newShow))
					{
						System.out.println(rootFolder.contents.get(i).fileName()+" was proccess and posted to wordpress");
						mDBApi.move("/"+rootFolder.contents.get(i).fileName(), "/Processed/"+rootFolder.contents.get(i).fileName()+"-"+ft.format(date));
					}else
					{
						System.out.println("There was a problem with processing "+rootFolder.contents.get(i).fileName());
						mDBApi.move("/"+rootFolder.contents.get(i).fileName(), "/Error/"+rootFolder.contents.get(i).fileName()+"-"+ft.format(date));
						
					}
				
				}
			}
			
			return true;
			
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}

	
}

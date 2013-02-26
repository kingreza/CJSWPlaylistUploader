import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

import net.bican.wordpress.Page;
import net.bican.wordpress.Wordpress;
import redstone.xmlrpc.XmlRpcFault;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession.WebAuthInfo;
import java.net.MalformedURLException;


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
			writer.write("WordPressUsername=\n");
			writer.write("WordPressPassword=\n");
			writer.write("WordPressURL=\n");
			writer.write("LineFormat=\n");

			
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
			if (line.startsWith("LineFormat="))
			{
				if (isLineValid(line))
					_setting.LineFormat = line.split("=")[1];
				else
					return -3;
			
			}
			
		 }
	isSettingSet = _setting.isEveryThingLoaded();
			
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
				String content = _setting.AppKey+"~"+_setting.AppSecret;
				writer.write(content);
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
		_setting = new Setting();
		
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
		String UserName = "cjswtest1";
		String Password = "cjswtest11";
		String xmlRpcUrl = "http://cjswtests.wordpress.com/xmlrpc.php";
		String l = "/";
		
		Page newPage = new Page();
		newPage.setTitle("TEST");
	
		newPage.setDescription("description");
		
		wp = new Wordpress(UserName,Password,xmlRpcUrl);
		wp.newPost(newPage, true);
		
		
		
				
		return true;
	}
	
	public Boolean ProcessFiles( DropboxAPI<WebAuthSession> mDBApi) throws DropboxException, IOException
	{
		try
		{
			Entry rootFolder = mDBApi.metadata("/", 1000, null, true, null);
			
			
			for (int i = 0 ; i < rootFolder.contents.size();i++)
			{
				if (!rootFolder.contents.get(i).isDir)
				{
					 DropboxAPI.DropboxInputStream st = mDBApi.getFileStream(rootFolder.contents.get(i).path,null);

					String content = convertStreamToString(st);
					System.out.println(rootFolder.contents.get(i).fileName());
					System.out.println(content);
					System.out.println();
					
					
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

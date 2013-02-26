import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.WebAuthSession.WebAuthInfo;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;

import com.dropbox.client2.jsonextract.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;


public class CJSWPlaylistUploader {

	/**
	 * @param args
	 */
	    private static final String APP_KEY = "chlldweqja93fzq";
	    private static final String APP_SECRET = "5930ax64vqmtz95";
	    private static DropboxAPI<WebAuthSession> mDBApi;
	    public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		DisplayManager.WELCOME();
		
		int loadResult = SettingManager.INSTANCE.LoadSettings();
		if (loadResult <= 0 )
		{
			System.out.println("There was a problem loading the setting.config file");
			if (loadResult == -2)
			{
				SettingManager.INSTANCE.CreateBlankSettingConfig();
				System.out.println("setting.config file could not be located. A blank setting.config file is now created. Please fill it out and run the program again");
				System.exit(1);
			}
			if (loadResult == -1)
			{
				System.out.println("setting.config file is malformed, please delete and run the program again");
			}
			
		}
		
		if (SettingManager.INSTANCE.HasUserAuthBefore())
		{
			
			DisplayManager.AUTHENTICATINGUSER();
			mDBApi = SettingManager.INSTANCE.reAuthUser(APP_KEY,APP_SECRET);
			DisplayManager.DONE();
			
		}else
		{
			DisplayManager.INITALSETUP();
			DisplayManager.PRESSENTER();
		
			mDBApi = SettingManager.INSTANCE.AuthUser(APP_KEY,APP_SECRET);
			DisplayManager.DONE();
		}
		
		DisplayManager.SETUPWORDPRESS();
		if(SettingManager.INSTANCE.WordPressAuthenticate())
		{
			DisplayManager.DONE();
		}else
		{
			System.exit(1);
		}
		
		DisplayManager.SETUPFOLDERSTRUCTURE();
		if (SettingManager.INSTANCE.CheckFolderStructure(mDBApi))
		{
			DisplayManager.DONE();
		}else
		{
			 
			 System.exit(1);
		}
		
		DisplayManager.STARTMONITOR();
		DisplayManager.PRESSENTER();
		
		try {
	        while (true) {
	           
	         SettingManager.INSTANCE.ProcessFiles(mDBApi);
	         Thread.sleep(60 * 1000);
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		

		System.out.println();
		System.out.print("Uploading file ");
		
		String content = "I will  make it to the dropbox. I promise you that!";
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
		Entry newEntry = mDBApi.putFile("/newFile.txt", inputStream, content.length(), null, null);
		System.out.print("Done. \nRevision of file: "+newEntry.rev);
	    }	

}	
		
		
		


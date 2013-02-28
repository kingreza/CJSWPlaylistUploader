import java.io.IOException;


public class DisplayManager {
	
	public static void WELCOME()
	{
		System.out.println("****************************************");
		System.out.println("*CJSW Dropbox to Wordpress uploader 1.0*");
		System.out.println("*Email: reza.shirazian@gmail.com       *");
		System.out.println("****************************************");
		System.out.println();
	}
	public static void INITALSETUP()
	{
		System.out.println("It seems like the program has not been authenticated with dropbox yet");
		System.out.println("This first step will forward you to dropbox's website where you will be asked");
		System.out.println("to give premission to the program so it can monitor your dropbox. ");
		System.out.println("The only place this program will monitor will be in 'Apps/CJSWPlaylists/");
		
		System.out.println();
	}
	public static void WEBSITETOVISIT(String site)
	{
		System.out.println("If your browser did not open, visit the following site to give permission");
		System.out.println("Once you have done that press enter to continue");
		System.out.println(site);
		System.out.println();
		try {
            while (System.in.read() != '\n') {}
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}
	public static void AUTHENTICATINGUSER()
	{
		System.out.print("Authenticating User.....");
		
	}
	public static void DONE()
	{
		System.out.println("Done.");
	}
	public static void PRESSENTER()
	{
		System.out.println("Press enter to continue");
		
		try {
            while (System.in.read() != '\n') {}
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}
	public static void SETUPWORDPRESS()
	{
		System.out.print("Connecting to Wordpress.....");
	}
	public static void SETUPFOLDERSTRUCTURE()
	{
		System.out.print("Setting up folder structure.....");		
	}
	
	public static void STARTMONITOR()
	{
		System.out.println("The setup is now completed. The program will now monitor /CJSWPlaylist folder for new files");
		System.out.println("These files are then processed and either moved to /Processed or /Error");
		System.out.println("When you're ready press Enter to look for playlists");
		
	}
	public static void PROBLEMWITHCONFIG()
	{
		System.out.println("There was a problem loading the setting.config file");
	}
	public static void CREATEBLANKCOFIG()
	{
		System.out.println("setting.config file could not be located. A blank setting.config file is now created. Please fill it out and run the program again");
	}
	public static void DELETECONFIGFILE()
	{
		System.out.println("setting.config file is malformed, please delete the file and run the program again");
	}
	public static void PROBLEMWITHFOLDERS()
	{
		System.out.println("There was a problem setting up the intial folder structure on dropbox");
	}
	public static void PROBLEMPARSINGTHECONFIGGILE()
	{
		
	}
	
	

}

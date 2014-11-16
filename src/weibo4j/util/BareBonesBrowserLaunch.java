package weibo4j.util;

/////////////////////////////////////////////////////////
//Bare Bones Browser Launch                            //
//Version 1.5 (December 10, 2005)                      //
//By Dem Pilafian                                      //
//Supports: Mac OS X, GNU/Linux, Unix, Windows XP      //
//Example Usage:                                       //
// String url = "http://www.centerkey.com/";           //
// BareBonesBrowserLaunch.openURL(url);                //
//Public Domain Software -- Free to Use as You Like    //
/////////////////////////////////////////////////////////

/**
 * @author Dem Pilafian
 * @author John Kristian
 */
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JOptionPane;

public class BareBonesBrowserLaunch {

    public static void openURL(String url) {
        try {
            browse(url);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error attempting to launch web browser:\n" + e.getLocalizedMessage());
        }
    }

    private static void browse(String url) throws ClassNotFoundException, IllegalAccessException,
            IllegalArgumentException, InterruptedException, InvocationTargetException, IOException,
            NoSuchMethodException {
        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Mac OS")) {
            Class fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
            openURL.invoke(null, new Object[] { url });
        } else if (osName.startsWith("Windows")) {
        	openURL1(url);
            //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else { // assume Unix or Linux
            String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
            String browser = null;
            for (int count = 0; count < browsers.length && browser == null; count++)
                if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
                    browser = browsers[count];
            if (browser == null)
                throw new NoSuchMethodException("Could not find web browser");
            else
                Runtime.getRuntime().exec(new String[] { browser, url });
        }
    }
    public static void  openURL1(String url) {
	    try {
	        java.net.URI uri = java.net.URI.create(url);
	        //获取当前系统桌面扩展
	        java.awt.Desktop dp = java.awt.Desktop.getDesktop();
	        //判断系统桌面是否支持要执行的功能
	        if(dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
	            //获取系统默认浏览器打开链接
	            dp.browse(uri);
	        }
	    } catch(java.lang.NullPointerException e) {
	        //此为uri为空时抛出异常
	        e.printStackTrace();
	    } catch (java.io.IOException e) {
	        //此为无法获取系统默认浏览器
	        e.printStackTrace();
	    }
	}
   
}

package weibo4j;

import javax.jms.Session;

import weibo4j.Users;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

public class ShowUser {

	public static void main(String[] args) {
		String access_token = "2.00Kf4mSD0im74196fe54b52bu5QNJB";
		String uid = "唉不想想太多";
		Users um = new Users(access_token);
		try {
			User user = um.showUserByScreenName(uid);
			System.out.print(user.getId());
			//Log.logInfo("hello"+user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
	public String getscreen_name(String access_token,String uid){
		 Users um = new Users(access_token);
		 String screen_name = new String();
			try {
				User user = um.showUserById(uid);
				screen_name = user.getScreenName();
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			return screen_name;
	}
	
}

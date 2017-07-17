
public class UserFeedObserver implements Observer{
	
	private User user;
	
	public UserFeedObserver( User user ){
		this.user = user;
	}
	
	@Override
	public void update(String post) {
		user.addToNewsFeed(post);
	}
	
}

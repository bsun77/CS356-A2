import java.util.ArrayList;
import java.util.List;

public class User implements TwitterObject {

	private String name;
	private List<User> followings;
	private List<String> newsFeed;
	private List<Observer> userObservers;
	private List<Observer> guiObservers;
	private List<String> posts;
	
	public User(String userid){
		this.name = userid;
		followings = new ArrayList<User>();
		userObservers = new ArrayList<Observer>();
		guiObservers = new ArrayList<Observer>();
		newsFeed = new ArrayList<String>();
		posts = new ArrayList<String>();
		userObservers.add(new UserFeedObserver(this));
	}

	@Override
	public String getID() {
		return this.name;
	}
	
	public void follow(User user){
		//To be apart of someone else's follow list
		followings.add(user);
	}
	
	public void post(String msg){
		// add to the list
		posts.add(msg);
		msg = "-  " + name +": " + msg;
		notifyUserObservers(msg);
	}
	
	public List<String> getPosts(){
		return posts;
	}
	
	public void notifyUserObservers(String msg){
		for ( Observer ob : userObservers ){
			ob.update(msg);
		}
	}
	
	public void addUserObserver( Observer ob ){
		userObservers.add(ob);
	}
	
	public void addGUIObserver( Observer ob ){
		guiObservers.add(ob);
	}
	
	public void removeGUIObserver( Observer ob ){
		guiObservers.remove(ob);
	}
	
	public List<User> getFollowings(){
		return this.followings;
	}

	public void addToNewsFeed(String post) {
		newsFeed.add(post);
		notifyGUIObservers(post);
	}
	public void notifyGUIObservers(String msg){
		for (Observer ob : guiObservers){
			ob.update(msg);
		}
	}	

	public List<String> getNewsFeed(){
		return newsFeed;
	}
	
	public void acceptVisitor(CountingVisitor visitor){
		visitor.visit(this);
	}
	
	public void acceptVisitor(NiceWordsVisitor visitor){
		visitor.visit(this);
	}
}


public class lastUpdateVisitor implements visitor {

	private User latestUser;
	
	@Override
	public void visit(User user) {
		if ( latestUser == null ){
			latestUser = user;
		} else {
			if ( latestUser.getLastUpdateTime() > user.getLastUpdateTime() ){
				latestUser = user;
			}
		}
	}
	
	public void visit(UserGroup user) {
		//Groups don't make posts
	}

	public User getlastUpdate(){
		return latestUser;
	}
	
}

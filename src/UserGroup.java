import java.util.ArrayList;
import java.util.List;

public class UserGroup implements TwitterObject {

	private String name;
	private List<TwitterObject> users;
	private long creationTime;
	
	public UserGroup(String groupname) {
		this.name = groupname;
		users = new ArrayList<TwitterObject>();
		creationTime = System.currentTimeMillis();
	}
	
	@Override
	public String getID() {
		return this.name;
	}

	public void add(TwitterObject item){
			users.add(item);
	}
	
	@Override
	public void acceptVisitor(CountingVisitor visitor) {
		for(int i = 0; i < users.size(); i++){
			users.get(i).acceptVisitor(visitor);
		}
		visitor.visit(this);
	}
	
	@Override
	public void acceptVisitor(NiceWordsVisitor visitor) {
		for(int i = 0; i < users.size(); i++){
			users.get(i).acceptVisitor(visitor);
		}
	}

	@Override
	public void acceptVisitor(DuplicateVisitor visitor) {
		for(int i = 0; i < users.size(); i++){
			users.get(i).acceptVisitor(visitor);
		}
		visitor.visit(this);
	}
	
	@Override
	public void acceptVisitor(lastUpdateVisitor visitor) {
		for(int i = 0; i < users.size(); i++){
			users.get(i).acceptVisitor(visitor);
		}
	}

	@Override
	public long getCreationtime() {
		return creationTime;
	}
}


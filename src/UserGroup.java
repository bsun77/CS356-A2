import java.util.ArrayList;
import java.util.List;

public class UserGroup implements TwitterObject {

	private String name;
	private List<TwitterObject> users;
	
	public UserGroup(String groupname) {
		this.name = groupname;
		users = new ArrayList<TwitterObject>();
	}
	
	@Override
	public String getID() {
		return this.name;
	}

	public void add(TwitterObject item){
			users.add(item);
	}
	
	public void acceptVisitor(CountingVisitor visitor) {
		for(int i = 0; i < users.size(); i++){
			users.get(i).acceptVisitor(visitor);
		}
		visitor.visit(this);
	}
	
	public void acceptVisitor(NiceWordsVisitor visitor) {
		for(int i = 0; i < users.size(); i++){
			users.get(i).acceptVisitor(visitor);
		}
	}
}


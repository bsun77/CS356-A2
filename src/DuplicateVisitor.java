import java.util.HashSet;

public class DuplicateVisitor implements visitor {

	private HashSet<User> names = new HashSet<>();
	private HashSet<UserGroup> groups = new HashSet<>();
	private int duplicateNames;
	private int duplicateGroups;
	
	@Override
	public void visit(User user) {
		if(names.contains(user)){
			duplicateNames++;
		} else if (user.getID().contains(" ")){
			duplicateNames++;
		} else if (!groups.contains(user)){
			names.add(user);
		}
	}

	public void visit(UserGroup group) {
		if(groups.contains(group)){
			duplicateGroups++;
		} else if (group.getID().contains(" ")){
			duplicateGroups++;
		} else if (!groups.contains(group)){
			groups.add(group);
		}
	}
	
	public int getDuplicateNames(){
		return duplicateNames;
	}
	
	public int getDuplicateGroups(){
		return duplicateGroups;
	}

}

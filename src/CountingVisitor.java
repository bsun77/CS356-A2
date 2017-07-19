
public class CountingVisitor implements visitor {

	int totalusers;
	int totalgroups;
	int totalmsg;
	
	public CountingVisitor(){
		totalusers = 0;
		totalgroups = 0;
		totalmsg = 0;
	}
	
	@Override
	public void visit(User user){
		totalusers++;
		totalmsg += user.getPosts().size();
	}
	
	public void visit(UserGroup group){
		totalgroups++;
	}
	
	public int getTotalUsers(){
		return totalusers;
	}
	
	public int getTotalGroups(){
		return totalgroups;
	}
	
	public int getTotalMsg(){
		return totalmsg;
	}
}
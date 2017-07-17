import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NiceWordsVisitor {
	
	/*
	 * Separated from CountingVisitor due to the runtime 
	 * necessary to search the strings
	 */
	private int nicewords;
	private int total;
	private Set<String> check = new HashSet<String>() {{
		add("cool");
	    add("awesome");
	    add("cute");
	    add("nice");
	    add("great");
	}};
	
	public NiceWordsVisitor(){
		nicewords = 0;
		total = 0;
	}
	
	public void visit(User user){
		total += user.getPosts().size();
		for(int i = 0; i < user.getPosts().size(); i++){
		String[] splitted = user.getPosts().get(i).split("\\s+");
			for(int j = 0; j < splitted.length; j++) {
				if(check.contains(splitted[j])){
					nicewords++;
					break;
				}
			}
		}		
	}
	
	public double getRatio(){
		return (double)nicewords/total*100;
	}
}

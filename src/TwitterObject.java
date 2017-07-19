
public interface TwitterObject {

	String getID();
	void acceptVisitor(CountingVisitor v);
	void acceptVisitor(NiceWordsVisitor v);
	void acceptVisitor(DuplicateVisitor v);
	void acceptVisitor(lastUpdateVisitor v);
	long getCreationtime();
}

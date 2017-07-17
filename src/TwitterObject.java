
public interface TwitterObject {

	String getID();
	void acceptVisitor(CountingVisitor v);
	void acceptVisitor(NiceWordsVisitor v);
}

import javax.swing.DefaultListModel;


public class GUIFeedObserver implements Observer{
	private DefaultListModel<String> model;
	
	public GUIFeedObserver( DefaultListModel<String> model ){
		this.model = model;
	}
	
	@Override
	public void update(String post) {
		model.addElement( post);
	}
}

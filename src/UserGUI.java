import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JList;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class UserGUI {

	private JFrame frame;
	private JTextField UserToFollow;
	private JTextField tweetMessage;
	private User user;
	private HashMap<String, User> checkusers;
	private JList<String> NewsFeed;
	private JList<String> followingList;
	private Observer guiObserver;

	/**
	 * Create the application.
	 */
	public UserGUI(User user, HashMap<String, User> users) {
		this.user = user;
		this.checkusers = users;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				user.removeGUIObserver(guiObserver);
			}
		});
		frame.setTitle("User " + user.getID());
		ImageIcon img = new ImageIcon("TwitterIcon.jpg");
		frame.setIconImage(img.getImage());
		frame.setBounds(100, 100, 375, 363);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		UserToFollow = new JTextField();
		UserToFollow.setBounds(10, 11, 161, 37);
		frame.getContentPane().add(UserToFollow);
		UserToFollow.setColumns(10);

		JButton btnNewButton = new JButton("Follow User");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userid = UserToFollow.getText();
				User followee = checkusers.get(userid);
				if(!user.getFollowings().contains(followee)){
					if (followee == null){
						JOptionPane.showMessageDialog(null, userid +" is not a user");
					} else if (user.equals(followee)){
						JOptionPane.showMessageDialog(null, "You can't follow yourself");
					} else {
						user.follow(followee);
						DefaultListModel<String> model = (DefaultListModel<String>)followingList.getModel();
						model.addElement(followee.getID());
						followee.addUserObserver(  new UserFeedObserver( user ) );
					}	
				} else {
					JOptionPane.showMessageDialog(null, "You're already following " +userid);
				}
			}
		});
		btnNewButton.setBounds(189, 11, 161, 37);
		frame.getContentPane().add(btnNewButton);

		tweetMessage = new JTextField();
		tweetMessage.setBounds(10, 167, 161, 37);
		frame.getContentPane().add(tweetMessage);
		tweetMessage.setColumns(10);

		JButton Tweet = new JButton("Tweet");
		Tweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = tweetMessage.getText();
				user.post(msg);
			}
		});

		Tweet.setBounds(189, 167, 161, 37);
		frame.getContentPane().add(Tweet);

		DefaultListModel<String> newsFeedModel = new DefaultListModel<>();
		for ( String s : user.getNewsFeed()){
			newsFeedModel.addElement(s);
		}
		guiObserver = new GUIFeedObserver( newsFeedModel ); 
		user.addGUIObserver( guiObserver );
		NewsFeed = new JList<String>(newsFeedModel);
		NewsFeed.setBounds(10, 215, 340, 98);
		frame.getContentPane().add(NewsFeed);

		DefaultListModel<String> followingListModel = new DefaultListModel<>();
		for (  User u : user.getFollowings()){
			followingListModel.addElement(u.getID());
		}

		followingList = new JList<String>(followingListModel);
		followingList.setBounds(10, 59, 340, 98);
		frame.getContentPane().add(followingList);

	}

}

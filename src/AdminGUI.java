import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class AdminGUI {

	private JFrame frmAdminControlPanel;
	private JTextField txtUserId;
	private JTextField txtGroupId;
	private DefaultMutableTreeNode root;
	private HashMap<String, User> checkuser = new HashMap<>();
	private HashMap<String, UserGroup> checkgroup = new HashMap<>();
	private static AdminGUI singleton = new AdminGUI();
	
	/**
	 * Create the application.
	 */
	private AdminGUI() {
		initialize();
		frmAdminControlPanel.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdminControlPanel = new JFrame();
		ImageIcon img = new ImageIcon("TwitterIcon.jpg");
		frmAdminControlPanel.setIconImage(img.getImage());
		frmAdminControlPanel.setTitle("Admin Control Panel");
		frmAdminControlPanel.setBounds(100, 100, 580, 367);
		frmAdminControlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdminControlPanel.getContentPane().setLayout(null);

		txtUserId = new JTextField();
		txtUserId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtUserId.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtUserId.getText().equals("")){
					txtUserId.setText("User ID");
				}
			}
		});
		txtUserId.setText("User ID");
		txtUserId.setBounds(254, 12, 120, 20);
		frmAdminControlPanel.getContentPane().add(txtUserId);
		txtUserId.setColumns(10);

		JTree tree = new JTree();
		root = new DefaultMutableTreeNode("Root");
		UserGroup rootGroup = new UserGroup("root");
		checkgroup.put("Root", rootGroup);
		tree.setModel(new DefaultTreeModel(root));

		tree.setBounds(10, 14, 200, 307);
		frmAdminControlPanel.getContentPane().add(tree);

		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String userid = txtUserId.getText();
				TwitterObject user = new User(userid);
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(txtUserId.getText());
				DefaultMutableTreeNode lastAccessed = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if( lastAccessed == null ){
					lastAccessed = root;
				} else if( checkuser.containsKey((String)(lastAccessed.getUserObject())) ){
					lastAccessed = (DefaultMutableTreeNode)lastAccessed.getParent();
				} 
				if(!checkuser.containsKey(userid)){
					lastAccessed.add(newNode);
					((DefaultTreeModel)tree.getModel()).reload();
					tree.expandPath( new TreePath(lastAccessed.getPath()) );
					checkuser.put(userid, (User)user);
					String grpid = (String)lastAccessed.getUserObject();					
					UserGroup grp = checkgroup.get(grpid);
					grp.add(user);
				}
			}
		});
		btnAddUser.setBounds(384, 11, 144, 23);
		frmAdminControlPanel.getContentPane().add(btnAddUser);

		JButton btnAddGroup = new JButton("Add Group");
		btnAddGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String groupid = txtGroupId.getText();
				UserGroup group = new UserGroup(groupid);
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(txtGroupId.getText());
				DefaultMutableTreeNode lastAccessed = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if( lastAccessed == null ){
					lastAccessed = root;
				} else if( checkuser.containsKey((String)(lastAccessed.getUserObject())) ){
					lastAccessed = (DefaultMutableTreeNode)lastAccessed.getParent();
				}
				if(!checkgroup.containsKey(groupid)){
					lastAccessed.add(newNode);
					((DefaultTreeModel)tree.getModel()).reload(); 	
					tree.expandPath(new TreePath(lastAccessed.getPath()));
					checkgroup.put(groupid, group);
					String grpid = (String)lastAccessed.getUserObject();
					UserGroup grp = checkgroup.get(grpid);
					grp.add(group);
				}
			}
		});
		btnAddGroup.setBounds(384, 45, 144, 23);
		frmAdminControlPanel.getContentPane().add(btnAddGroup);

		txtGroupId = new JTextField();
		txtGroupId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtGroupId.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtGroupId.getText().equals("")){
					txtGroupId.setText("Group ID");
				}
			}
		});
		
		txtGroupId.setText("Group ID");
		txtGroupId.setBounds(254, 46, 120, 20);
		frmAdminControlPanel.getContentPane().add(txtGroupId);
		txtGroupId.setColumns(10);

		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultMutableTreeNode lastAccessed = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(checkuser.containsKey((String)(lastAccessed.getUserObject()))){
					if(lastAccessed != null){
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									new UserGUI(checkuser.get((String)(lastAccessed.getUserObject())), checkuser);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				} else {
					JOptionPane.showMessageDialog(null, "That's not a user");
				}
			}
		});
		btnOpenUserView.setBounds(254, 79, 274, 48);
		frmAdminControlPanel.getContentPane().add(btnOpenUserView);

		JButton btnShowUserTotal = new JButton("User Total");
		btnShowUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserGroup usgrp = checkgroup.get((String)root.getUserObject());
				CountingVisitor visitor = new CountingVisitor();
				usgrp.acceptVisitor(visitor);
				JOptionPane.showMessageDialog(null, "The total number of users is "+visitor.getTotalUsers());
			}
		});
		btnShowUserTotal.setBounds(254, 202, 120, 40);
		frmAdminControlPanel.getContentPane().add(btnShowUserTotal);

		JButton btnShowGroupTotal = new JButton("Group Total");
		btnShowGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					UserGroup usgrp = checkgroup.get((String)root.getUserObject());
					CountingVisitor visitor = new CountingVisitor();
					usgrp.acceptVisitor(visitor);
					JOptionPane.showMessageDialog(null, "The total number of groups is "+visitor.getTotalGroups());
			}
		});
		btnShowGroupTotal.setBounds(395, 202, 120, 40);
		frmAdminControlPanel.getContentPane().add(btnShowGroupTotal);

		JButton btnShowMessagesTotal = new JButton("Messages Total");
		btnShowMessagesTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGroup usgrp = checkgroup.get((String)root.getUserObject());
				CountingVisitor visitor = new CountingVisitor();
				usgrp.acceptVisitor(visitor);
				JOptionPane.showMessageDialog(null, "The total number of messages is "+visitor.getTotalMsg());
			}
		});
		btnShowMessagesTotal.setBounds(254, 254, 120, 40);
		frmAdminControlPanel.getContentPane().add(btnShowMessagesTotal);

		JButton btnPositivePercentage = new JButton("Positive Percentage");
		btnPositivePercentage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGroup usgrp = checkgroup.get((String)root.getUserObject());
				NiceWordsVisitor visitor = new NiceWordsVisitor();
				usgrp.acceptVisitor(visitor);
				JOptionPane.showMessageDialog(null, "The percentage of positive posts is "+visitor.getRatio()+"%");
			}
		});
		btnPositivePercentage.setBounds(395, 253, 120, 40);
		frmAdminControlPanel.getContentPane().add(btnPositivePercentage);
		
		JButton btnNewButton = new JButton("Duplicate IDs");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserGroup usgrp = checkgroup.get((String)root.getUserObject());
				DuplicateVisitor visitor = new DuplicateVisitor();
				usgrp.acceptVisitor(visitor);
				JOptionPane.showMessageDialog(null, "The number of invalid users is: " +visitor.getDuplicateNames() 
						+"\n" +"The number of invalid groups is: "+visitor.getDuplicateGroups());
			}
		});
		btnNewButton.setBounds(254, 150, 120, 40);
		frmAdminControlPanel.getContentPane().add(btnNewButton);
		
		JButton btnLastUserUpdated = new JButton("Last Updated");
		btnLastUserUpdated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGroup usgrp = checkgroup.get((String)root.getUserObject());
				lastUpdateVisitor visitor = new lastUpdateVisitor();
				usgrp.acceptVisitor(visitor);
				JOptionPane.showMessageDialog(null, "The last update was made by: "+visitor.getlastUpdate().getID());
			}
		});
		btnLastUserUpdated.setBounds(395, 150, 120, 41);
		frmAdminControlPanel.getContentPane().add(btnLastUserUpdated);
	}
	
	public static AdminGUI getInstance(){
		return singleton;
	}

}

package ia.gui3d;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Player extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private JButton up, down, left, right, rotleft, rotright;

	private HumanController controller;
	
	public Player()
	{
		super(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));		
		
		up = new JButton(new ImageIcon("src/icons/48x48/up.png"));
		up.setSize(50, 50);
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createHuman();
				controller.wrapper().cimaBracoDireito(10);
			}
		});
		add(up, BorderLayout.NORTH);
		
		left = new JButton(new ImageIcon("src/icons/48x48/left.png"));
		left.setSize(50, 50);
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createHuman();
				controller.wrapper().lateralBracoDireito(-10);
			}
		});
		add(left, BorderLayout.EAST);
		
		right = new JButton(new ImageIcon("src/icons/48x48/right.png"));
		right.setSize(50, 50);
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createHuman();
				controller.wrapper().lateralBracoDireito(10);
			}
		});
		add(right, BorderLayout.WEST);
		
		// down-panel
		JPanel southPanel = new JPanel(new GridLayout(1,0));
		
		down = new JButton(new ImageIcon("src/icons/48x48/down.png"));
		down.setSize(50, 50);
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createHuman();
				controller.wrapper().cimaBracoDireito(-10);
			}
		});
		
		rotleft = new JButton(new ImageIcon("src/icons/48x48/rotleft.png"));
		rotleft.setSize(50, 50);
		rotleft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createHuman();
				controller.wrapper().rotacionaBracoDireito(10);
			}
		});
		
		rotright = new JButton(new ImageIcon("src/icons/48x48/rotright.png"));
		rotright.setSize(50, 50);
		rotright.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createHuman();
				controller.wrapper().rotacionaBracoDireito(-10);
			}
		});

		southPanel.add(rotright);
		southPanel.add(down);
		southPanel.add(rotleft);

		add(southPanel, BorderLayout.SOUTH);
	}

	private void createHuman() {
		controller = HumanController.get();
		controller.human().setLocation(450, 100);
		controller.human().setVisible(true);
	}
}

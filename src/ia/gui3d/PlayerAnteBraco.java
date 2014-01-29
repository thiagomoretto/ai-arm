package ia.gui3d;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayerAnteBraco extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private JButton up, down;

	private HumanController controller;
	
	public PlayerAnteBraco()
	{
		super(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));		
		
		up = new JButton(new ImageIcon("src/icons/48x48/up.png"));
		up.setSize(50, 50);
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createHuman();
				controller.wrapper().anteBracoDireito(+10);
			}
		});
		add(up, BorderLayout.NORTH);
		
		down = new JButton(new ImageIcon("src/icons/48x48/down.png"));
		down.setSize(50, 50);
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createHuman();
				controller.wrapper().anteBracoDireito(-10);
			}
		});
		
		add(down, BorderLayout.SOUTH);
	}

	private void createHuman() {
		controller = HumanController.get();
		controller.human().setLocation(450, 100);
		controller.human().setVisible(true);
	}
}

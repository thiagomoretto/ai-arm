package ia.gui3d;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class Toolbox implements ItemListener {

	private JFrame frame;
	
	private Console console;

	private JMenuBar menubar;

	private String message = "None";

	private JLabel stLabel = new JLabel(message);
	
	private JFrame confframe, playerFrame, playerAnteBracoFrame, playerAlvoFrame;
	
	JCheckBoxMenuItem confCk, consoleCk, playerCk, playerAnteBracoCk, playerAlvoCk;
	
	private Player player;
	
	private PlayerAnteBraco playerAnteBraco;
	
	private PlayerAlvo playerAlvo;
	
	private void initConsole()
	{
		console = new Console();
		console.setVisible(true);
		console.addWindowListener(new SubWindowListener());
		
		IO.setDefaultMessageOutput(console);
	}
	
	public class SubWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent event) {
			JFrame frame = (JFrame) event.getSource();
			frame.setVisible(false);
			
			if (confframe == event.getSource()) {
				confCk.setSelected(false);
			}

			else if (playerFrame == event.getSource()) {
				playerCk.setSelected(false);
			}

			else if (playerAlvoFrame == event.getSource()) {
				playerAlvoCk.setSelected(false);
			}
			
			else if (console == event.getSource()) {
				consoleCk.setSelected(false);
			}
			
			else if (playerAnteBraco == event.getSource()) {
				playerAnteBracoCk.setSelected(false);
			}
		}
	}
	
	private void initConfigurator()
	{
		confframe = new JFrame("Configuration");
		Configurator configurator = new Configurator();
		
		confframe.add(configurator);
		confframe.setSize(350, 350);
		confframe.setLocation(100,400);
		confframe.setVisible(true);
		confframe.addWindowListener(new SubWindowListener());
	}
	
	private void initPlayer() 
	{
		playerFrame = new JFrame("Arm");
		player = new Player();
		
		playerFrame.add(player);
		playerFrame.setSize(190, 230);
		playerFrame.setLocation(470,400);
		playerFrame.addWindowListener(new SubWindowListener());
	}
	
	private void initPlayerAnteBraco() 
	{
		playerAnteBracoFrame  = new JFrame("Forearm");
		playerAnteBraco = new PlayerAnteBraco();
		
		playerAnteBracoFrame.add(playerAnteBraco);
		playerAnteBracoFrame.setSize(100,190);
		playerAnteBracoFrame.setLocation(570,600);
		playerAnteBracoFrame.addWindowListener(new SubWindowListener());
	}
	
	
	private void initPlayerAlvo() 
	{
		playerAlvoFrame  = new JFrame("Target");
		playerAlvo = new PlayerAlvo();
		
		playerAlvoFrame.add(playerAlvo);
		playerAlvoFrame.setSize(190, 230);
		playerAlvoFrame.setLocation(470,600);
		playerAlvoFrame.addWindowListener(new SubWindowListener());
	}
	
	public void init() {
		frame = new JFrame( "Toolbox" );
		frame.setSize(400, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initComponents();

		// Status bar
		JPanel jp_status = new JPanel();
		jp_status.setLayout(new BorderLayout());
		jp_status.add(stLabel, BorderLayout.CENTER);

		jp_status.setBorder(new EtchedBorder());

		frame.add(jp_status,BorderLayout.SOUTH);
		frame.setJMenuBar(menubar);
		frame.setVisible(true);
		frame.setLocation(100, 100);
		
		initConsole();
		
		initConfigurator();
		
		initPlayer();
	
		initPlayerAnteBraco();
		
		initPlayerAlvo();
	}

	public void setMessage( String message ) {
		this.message = message;
	}

	private void initComponents() {
		menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu window = new JMenu("Window");
		JMenu about = new JMenu("About");
		JMenuItem temp;

		temp = new JMenuItem("Sair");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				frame.dispose();
				System.exit(0);
			}
		});
		file.add(temp);
		
		// Window
		confCk = new JCheckBoxMenuItem("Configuration");
		confCk.setSelected(true);
		window.add(confCk);
		confCk.addItemListener(this);
		
		consoleCk = new JCheckBoxMenuItem("Console");
		consoleCk.setSelected(true);
		window.add(consoleCk);
		consoleCk.addItemListener(this);
		
		playerCk = new JCheckBoxMenuItem("Controller Panel (Arm)");
		playerCk.setSelected(false);
		window.add(playerCk);
		playerCk.addItemListener(this);
		
		playerAnteBracoCk = new JCheckBoxMenuItem("Controller Panel (Forearm)");
		playerAnteBracoCk.setSelected(false);
		window.add(playerAnteBracoCk);
		playerAnteBracoCk.addItemListener(this);

		playerAlvoCk = new JCheckBoxMenuItem("Controller Panel (Target)");
		playerAlvoCk.setSelected(false);
		window.add(playerAlvoCk);
		playerAlvoCk.addItemListener(this);
		
		menubar.add(file);
		menubar.add(window);
		menubar.add(about);
	}

	public void itemStateChanged(ItemEvent e) {
		if (consoleCk == e.getSource()) {
			console.setVisible(consoleCk.isSelected());
		}
		
		if (confCk == e.getSource()) {
			confframe.setVisible(confCk.isSelected());
		}
		
		if (playerCk == e.getSource()) {
			playerFrame.setVisible(playerCk.isSelected());
		}
		
		if (playerAlvoCk == e.getSource()) {
			playerAlvoFrame.setVisible(playerAlvoCk.isSelected());
		}
		
		if (playerAnteBracoCk == e.getSource()) {
			playerAnteBracoFrame.setVisible(playerAnteBracoCk.isSelected());
		}
	}
	
	public static void main(String...args) {
		Toolbox box = new Toolbox();
		box.init();
	}
}


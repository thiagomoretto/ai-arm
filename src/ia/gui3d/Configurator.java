package ia.gui3d;

import ia.braco.BracoRunner;
import ia.searchs.saf.DecrTemperature;
import ia.searchs.saf.ExpTemperature;
import ia.searchs.saf.SqrtTemperature;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Configurator extends JPanel implements ItemListener {

	private JTextField tflimit, tffactor, tfbeamWidth, tfinitTemp, tfmaxSteps, tfboltzman, tfthreshold, tfsleep, tfangle;
	
	private JComboBox temperatureFunction, combo;
	
	private JCheckBox ckrepeat, ckio;
	
	private Algorithm algorithm = Algorithm.BREADTH_FIRST;
	
	private BracoRunner runner;
	
	private String tempFunction = "f(t)=Ti=T=T-1";
	
	private String[] options = new String[] {
			"Breadth-First",
			"Depth-First/Limited/ID",
			"A*/IDA*",
			"Greedy",
			"Beam Search",
			"Simulated Annealing",
			"Gradient Descent",
			"Gradient Steepest Descent"
	};
	
	public Algorithm getAlgorithm()
	{
		return algorithm;
	}
	
	public Configurator()
	{
		super(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		combo = new JComboBox(options);

		Insets insets;
		insets = new Insets(0, 0, 5, 5);
		combo.setBorder(new EmptyBorder(insets));
		combo.addItemListener(this);
		
		add(combo, BorderLayout.NORTH);
		setVisible(true);
		createComponents();
	}

	private String[] availableFunctions = new String[]{ "f(t)=Ti=T=T-1", "f(t)=Ti*exp(-t/50)", "f(t)=-sqrt(t)+Ti/2" };
	
	private void createComponents()
	{
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		labelPane.add(new JLabel("Limit (0=unlimited):"));
		labelPane.add(new JLabel("Increment factor (0=off):"));
		labelPane.add(new JLabel("Restart?"));
		labelPane.add(new JLabel("Temperature function:"));
		labelPane.add(new JLabel("Initial temperature (Ti):"));
		labelPane.add(new JLabel("Boltzman:"));
		labelPane.add(new JLabel("Beam width:"));
		labelPane.add(new JLabel("Max. steps.:"));
		labelPane.add(new JLabel("Threshold:"));
		labelPane.add(new JLabel("Moviment delay (ms):"));
		labelPane.add(new JLabel("Moviment angle (degree):"));
		labelPane.add(new JLabel("Show output on search:"));
		
		
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		tflimit = new JTextField("0");
		tflimit.setColumns(7);
		
		tffactor = new JTextField("0");
		tffactor.setColumns(7);
		
		tfbeamWidth = new JTextField("10");
		tfbeamWidth.setColumns(7);
		
		tfinitTemp = new JTextField("200");
		tfinitTemp.setColumns(7);
		
		tfboltzman = new JTextField("200.00");
		tfboltzman.setColumns(7);
		
		tfmaxSteps = new JTextField("100");
		tfmaxSteps.setColumns(7);
		
		tfthreshold = new JTextField("0.15");
		tfthreshold.setColumns(7);
		
		tfsleep = new JTextField("200");
		tfsleep .setColumns(7);
		
		tfangle = new JTextField("10.0");
		tfangle.setColumns(7);
		
		ckrepeat = new JCheckBox();
		ckrepeat.addItemListener(this);
		
		ckio = new JCheckBox();
		ckio.setSelected(true);
		ckio.addItemListener(this);
		
		temperatureFunction = new JComboBox(availableFunctions);
		temperatureFunction.addItemListener(this);
		
		fieldPane.add(tflimit);
		fieldPane.add(tffactor);
		fieldPane.add(ckrepeat);
		fieldPane.add(temperatureFunction);
		fieldPane.add(tfinitTemp);
		fieldPane.add(tfboltzman);
		fieldPane.add(tfbeamWidth);
		fieldPane.add(tfmaxSteps);
		fieldPane.add(tfthreshold);
		fieldPane.add(tfsleep);
		fieldPane.add(tfangle);
		fieldPane.add(ckio);
		
		tflimit.setEnabled(true);
		tfthreshold.setEnabled(true);
		tffactor.setEnabled(true);
		ckrepeat.setEnabled(true); //Enable by default
		
		tfbeamWidth.setEnabled(false);
		tfinitTemp.setEnabled(false);
		tfboltzman.setEnabled(false);
		tfmaxSteps.setEnabled(false);
		temperatureFunction.setEnabled(false);
		algorithm = Algorithm.BREADTH_FIRST;
		
		JPanel buttonPane = new JPanel(new GridLayout(1,0));
		JButton temp;
		
		temp = new JButton("Reset");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HumanController controller;
				controller = HumanController.get();
				controller.reset();
				controller.human().setLocation(450, 100);
				controller.human().setVisible(true);
			}
		});
		
		buttonPane.add(temp);
		
		temp = new JButton("Search");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HumanController controller;
				controller = HumanController.get();
				
				controller.human().setLocation(450, 100);
				controller.human().setVisible(true);
				
				runner = new BracoRunner(controller.wrapper());
				
				new Thread() {
					public void run()
					{
						int limit = -1, beamWidth = 10;
						
						try {
							limit = Integer.parseInt(tflimit.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(tflimit, "Error, only numeric values in limit field.");
							return;
						}
						
						try {
							BracoRunner.global_threshold = Double.parseDouble(tfthreshold.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(tflimit, "Error, only numeric values in threshold field.");
							return;
						}
						
						try {
							BracoRunner.global_sleep = Integer.parseInt(tfsleep.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(tflimit, "Error, only numeric values in delay field.");
							return;
						}
						
						try {
							BracoRunner.global_angle = Double.parseDouble(tfangle.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(tflimit, "Error, only numeric values in angle field.");
							return;
						}
						
						try {
							beamWidth = Integer.parseInt(tfbeamWidth.getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(tflimit, "Error, only numeric values in beam-width field.");
							return;
						}
						
						IO.printf("Searching...\n");
						IO.enabled(ckio.isSelected());
						
						if (algorithm == Algorithm.BREADTH_FIRST) {
							runner.searchBreadth(limit, ckrepeat.isSelected());
						}
						if (algorithm == Algorithm.DEPTH_FIRST) {
							runner.searchDepth(limit, ckrepeat.isSelected());
						}
						if (algorithm == Algorithm.ASTAR) {
							runner.searchAstar(limit, ckrepeat.isSelected());
						}
						if (algorithm == Algorithm.GREEDY) {
							runner.searchGreedy(limit, ckrepeat.isSelected());
						}
						if (algorithm == Algorithm.BEAM_SEARCH) {
							runner.searchBeam(limit, ckrepeat.isSelected(), beamWidth);
						}
						if (algorithm == Algorithm.GRADIENT_DESCENT) {
							int maxSteps = 100;
							
							try {
								maxSteps = Integer.parseInt(tfmaxSteps.getText());
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(tflimit, "Error, only numeric values in max steps field.");
								return;
							}
							runner.searchGradient(maxSteps);
						}
						if (algorithm == Algorithm.GRADIENT_STEEPEST_DESCENT) {
							int maxSteps = 100;
							
							try {
								maxSteps = Integer.parseInt(tfmaxSteps.getText());
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(tflimit, "Error, only numeric values in max steps field.");
								return;
							}
							runner.searchGradientSteepest(maxSteps);
						}
						if (algorithm == Algorithm.SIMULATED_ANNEALING) {
							double initTemp = 100.0, boltzman = 200.0;
							
							try {
								initTemp = Double.parseDouble(tfinitTemp.getText());
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(tflimit, "Error, only numeric values in initial temperature field.");
								return;
							}
							
							try {
								boltzman = Double.parseDouble(tfboltzman.getText());
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(tflimit, "Error, only numeric values in boltzman.");
								return;
							}
							
							if (tempFunction == "f(t)=Ti=T=T-1") {
								runner.searchSimulatedAnnealing(new DecrTemperature(), initTemp, boltzman);
							} else if (tempFunction == "f(t)=Ti*exp(-t/50)") {
								runner.searchSimulatedAnnealing(new ExpTemperature(), initTemp, boltzman);
							} else if (tempFunction == "f(t)=-sqrt(t)+Ti/2") {
								runner.searchSimulatedAnnealing(new SqrtTemperature(), initTemp, boltzman);
							}
						}
						
						IO.enabled(true);
						IO.printf("Finished\n");
						
						runner.showResults();
					}
				}.start();
			}
		});
		
		buttonPane.add(temp);
		
		add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
        add(buttonPane, BorderLayout.SOUTH);
		
		repaint();
		validate();
	}
	
	public void itemStateChanged(ItemEvent event) 
	{
		if(event.getSource() == ckio)
		{
			// do nothing.
		}
		
		if(event.getSource() == ckrepeat) 
		{
			// do nothing.
		}
		
		if(event.getSource() == combo)
		{
			tflimit.setEnabled(false);
			ckrepeat.setEnabled(false);
			tfbeamWidth.setEnabled(false);
			tffactor.setEnabled(false);
			tfinitTemp.setEnabled(false);
			tfboltzman.setEnabled(false);
			tfmaxSteps.setEnabled(false);
			temperatureFunction.setEnabled(false);
			
			if(event.getStateChange() == ItemEvent.SELECTED) 
			{
				JComboBox combo = (JComboBox ) event.getSource();
				IO.println(combo.getSelectedItem());
				String item = (String) combo.getSelectedItem();
				
				if ("Breadth-First".equals(item)) 
				{
					tflimit.setEnabled(true);
					ckrepeat.setEnabled(true);
					tffactor.setEnabled(true);
					algorithm = Algorithm.BREADTH_FIRST;
				}
				
				if ("Depth-First/Limited/ID".equals(item)) 
				{
					tflimit.setEnabled(true);
					ckrepeat.setEnabled(true);
					tffactor.setEnabled(true);
					algorithm = Algorithm.DEPTH_FIRST;
				}
				
				if ("A*/IDA*".equals(item)) 
				{
					tflimit.setEnabled(true);
					ckrepeat.setEnabled(true);
					tffactor.setEnabled(true);
					algorithm = Algorithm.ASTAR;
				}
				
				if ("Greedy".equals(item)) 
				{
					tflimit.setEnabled(true);
					ckrepeat.setEnabled(true);
					tffactor.setEnabled(true);
					algorithm = Algorithm.GREEDY;
				}
				
	
				if ("Simulated Annealing".equals(item)) 
				{
					temperatureFunction.setEnabled(true);
					tfinitTemp.setEnabled(true);
					tfboltzman.setEnabled(true);
					algorithm = Algorithm.SIMULATED_ANNEALING;
				}
				
				if ("Gradient Descent".equals(item)) 
				{
					algorithm = Algorithm.GRADIENT_DESCENT;
					tfmaxSteps.setEnabled(true);
				}
				
				if ("Gradient Steepest Descent".equals(item))
				{
					algorithm = Algorithm.GRADIENT_STEEPEST_DESCENT;
					tfmaxSteps.setEnabled(true);
				}
				
				if ("Beam Search".equals(item)) 
				{
					tfbeamWidth.setEnabled(true);
					algorithm = Algorithm.BEAM_SEARCH;
				}
			}
		}
		else if (event.getSource() == temperatureFunction && event.getStateChange() == ItemEvent.SELECTED)
		{
			tempFunction = (String) temperatureFunction.getSelectedItem();
			IO.println("Selected funcion: "+tempFunction);
		}
	}
}
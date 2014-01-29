package ia.searchs.saf;

import ia.searchs.SimulatedAnnealingTemperatureInTime;

public class ExpTemperature implements SimulatedAnnealingTemperatureInTime {
	
	private double x;
	
	public double getTemperature(int time) 
	{
		return x * Math.exp(-(time/50));
	}
	public void initialTemperature(double ti) 
	{
		this.x = ti;
	}
}

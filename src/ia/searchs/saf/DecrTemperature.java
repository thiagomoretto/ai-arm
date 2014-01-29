package ia.searchs.saf;

import ia.searchs.SimulatedAnnealingTemperatureInTime;

public class DecrTemperature implements SimulatedAnnealingTemperatureInTime {
	
	private double x;
	
	public double getTemperature(int time) 
	{
		return x -= 1;
	}
	public void initialTemperature(double ti) 
	{
		this.x = ti;
	}
}

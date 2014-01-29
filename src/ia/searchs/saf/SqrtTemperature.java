package ia.searchs.saf;

import ia.searchs.SimulatedAnnealingTemperatureInTime;

public class SqrtTemperature implements SimulatedAnnealingTemperatureInTime {
	
	private double x;
	
	public double getTemperature(int time) 
	{
		return -Math.sqrt(time) + (x/2);
	}
	public void initialTemperature(double ti) 
	{
		this.x = ti;
	}
}

package gorgoneyez.star.simulation;

public class Supernova extends Star
{
	public Supernova()
	{
		super("S");
		
		// mass can not exceed SUN_RADIUS * 1.4f by Chandrasekhar limit
		// if mass exceeds Chandrasekhar limit, it becomes a supernova
		this.setMass(this.rand.nextFloat() * SUN_RADIUS * 9.9f + SUN_RADIUS);
		this.setRemainingLife(1);		// will exploade immediately
		this.setHydrogen(0);
		this.setRadius(SUN_RADIUS * 4.5f);
	}
}

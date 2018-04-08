package gorgoneyez.star.simulation;

public class WhiteDwarf extends Star
{
	public WhiteDwarf()
	{
		super("W");
		
		// mass can not exceed SUN_RADIUS * 1.4f by Chandrasekhar limit
		// if mass exceeds Chandrasekhar limit, it becomes a supernova
		this.setMass(this.rand.nextFloat() * SUN_RADIUS * 1.4f);
		this.setHydrogen(0);
		this.setRadius(this.rand.nextFloat() * this.getMass() / 1.4f);
	}
}

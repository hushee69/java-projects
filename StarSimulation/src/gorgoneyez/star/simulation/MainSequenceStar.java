package gorgoneyez.star.simulation;

public class MainSequenceStar extends Star
{
	public MainSequenceStar()
	{
		super("M");
		
		this.mass = this.rand.nextInt((int)(SUN_MASS * 9.5f)) + SUN_GRAVITATIONAL_PULL;
		this.setGravitationalPull(this.getMass() * 3.2f);
		this.setHydrogen(100);
		this.setRadius(this.getMass() / 2.4f);
	}
}

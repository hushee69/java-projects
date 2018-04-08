package gorgoneyez.star.simulation;

import java.util.UUID;

public abstract class Star
{
	private static final int MIN_LIFE = 2;
	private static final int MAX_LIFE = 7;
	
	public static final float SUN_RADIUS = 6.96f;
	public static final float SUN_MASS = 19.89f;
	public static final float SUN_GRAVITATIONAL_PULL = 2.49f;
	
	protected MTRandom rand;
	private UUID identifier;		// uniquely identify each object
	
	protected String name;
	protected float radius;
	protected float mass;
	protected float gravitational_pull;
	protected int[] coords;
	protected int hydrogen;
	protected int tours;
	private int remainingLife;
	
	public Star(String name)
	{
		// set first parameter to true to use C compatibility
		// for Mersenne Twister
		this.rand = new MTRandom();
		this.identifier = UUID.randomUUID();
		
		this.name = name;
		this.coords = new int[2];		// 0->x, 1->y
		this.coords[0] = 0;
		this.coords[1] = 0;
		this.radius = SUN_RADIUS;
		this.mass = SUN_MASS;
		this.gravitational_pull = SUN_GRAVITATIONAL_PULL;
		this.remainingLife = rand.nextInt((MAX_LIFE - MIN_LIFE)) + MIN_LIFE;
	}
	
	public UUID getIdentifier()
	{
		return this.identifier;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public float getRadius()
	{
		return radius;
	}

	public void setRadius(float radius)
	{
		this.radius = radius;
	}

	public float getMass()
	{
		return mass;
	}

	public void setMass(float mass)
	{
		this.mass = mass;
	}

	public float getGravitationalPull()
	{
		return gravitational_pull;
	}

	public void setGravitationalPull(float gravitational_pull)
	{
		this.gravitational_pull = gravitational_pull;
	}

	public int[] getCoords()
	{
		return coords;
	}
	
	public int getX()
	{
		return this.coords[0];
	}
	
	public int getY()
	{
		return this.coords[1];
	}

	public void setCoords(int[] coords)
	{
		this.coords = coords;
	}
	
	public void setX(int x)
	{
		this.coords[0] = x;
	}
	
	public void setY(int y)
	{
		this.coords[1] = y;
	}

	public int getHydrogen()
	{
		return hydrogen;
	}

	public void setHydrogen(int hydrogen)
	{
		this.hydrogen = hydrogen;
	}

	public int getTours()
	{
		return tours;
	}

	public void setTours(int tours)
	{
		this.tours = tours;
	}

	public static float getSunRadius()
	{
		return SUN_RADIUS;
	}

	public static float getSunMass()
	{
		return SUN_MASS;
	}

	public static float getSunGravitationalPull()
	{
		return SUN_GRAVITATIONAL_PULL;
	}

	public boolean getRemainingLife()
	{
		if( this.remainingLife < 1 )
		{
			return false;
		}
		
		return true;
	}
	
	public void setRemainingLife(int life)
	{
		this.remainingLife = life;
	}
	
	public int getAge()
	{
		return this.remainingLife;
	}
	
	public void decrementLife()
	{
		if( this.remainingLife >= 0 )
		{
			this.remainingLife--;
		}
	}
	
	public boolean willBeSupernova()
	{
		return ( this.mass >= ( SUN_MASS * 5.4f ));
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
}

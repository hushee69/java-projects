package gorgoneyez.star.simulation;

import java.util.ArrayList;
import java.util.ListIterator;

public class StarSystem
{
	// singleton model for star system
	// constructor private
	private static volatile StarSystem starSystem;
	
	private ArrayList<? super Star> ms_stars;
	private ArrayList<? super Star> sn_stars;
	private ArrayList<? super Star> wd_stars;
	private ArrayList<? super Star> bh_stars;
	private ArrayList<? super Star> nebulae;
	
	private StarSystem()
	{
		ms_stars = new ArrayList<>();
		wd_stars = new ArrayList<>();
		sn_stars = new ArrayList<>();
		bh_stars = new ArrayList<>();
		nebulae = new ArrayList<>();
	}
	
	// only way to get instance of this object
	public static StarSystem getStarSystem()
	{
		if( starSystem == null )
		{
			synchronized (StarSystem.class )
			{
				starSystem = new StarSystem();
			}
		}
		
		return starSystem;
	}
	
	// return starlist based on the type of class demanded
	public ArrayList<? super Star> getStarList(String class_name)
	{
		if( class_name.equals("MainSequenceStar") )
		{
			return this.ms_stars;
		}
		
		if( class_name.equals("WhiteDwarf") )
		{
			return this.wd_stars;
		}
		
		if( class_name.equals("BlackHole") )
		{
			return this.bh_stars;
		}
		
		if( class_name.equals("Nebula") )
		{
			return this.nebulae;
		}
		
		if( class_name.equals("Supernova") )
		{
			return this.sn_stars;
		}
		
		return null;
	}
	// end getStarList
	
	// method to decrement lifespan of each star
	// depending on the type passed in the class param
	public void agingStar(String class_name)
	{
		if( class_name.equals("MainSequenceStar") )
		{
			ListIterator<? super Star> ms_iter = this.ms_stars.listIterator();
			
			while( ms_iter.hasNext() )
			{
				Star s = (Star)ms_iter.next();
				
				s.decrementLife();
			}
		}
		
		if( class_name.equals("WhiteDwarf") )
		{
			ListIterator<? super Star> wd_iter = this.wd_stars.listIterator();
			
			while( wd_iter.hasNext() )
			{
				Star s = (Star)wd_iter.next();
				
				s.decrementLife();
			}
		}
		
		if( class_name.equals("BlackHole") )
		{
			ListIterator<? super Star> bh_iter = this.bh_stars.listIterator();
			
			while( bh_iter.hasNext() )
			{
				Star s = (Star)bh_iter.next();
				
				s.decrementLife();
			}
		}
		
		if( class_name.equals("Supernova") )
		{
			ListIterator<? super Star> sn_iter = this.sn_stars.listIterator();
			
			while( sn_iter.hasNext() )
			{
				Star s = (Star)sn_iter.next();
				
				s.decrementLife();
			}
		}
	}
	// end agingStar
	
	public void addStarToList(String star)
	{
		if( star.equals("MainSequenceStar") )
		{
			this.ms_stars.add(new MainSequenceStar());
		}
		
		if( star.equals("WhiteDwarf") )
		{
			this.wd_stars.add(new WhiteDwarf());
		}
		
		if( star.equals("Supernova") )
		{
			this.sn_stars.add(new Supernova());
		}
		
		if( star.equals("BlackHole") )
		{
			this.bh_stars.add(new BlackHole());
		}
		
		if( star.equals("Nebula") )
		{
			this.nebulae.add(new Nebula());
		}
	}
	
	public void removeStarFromList(String star, int x, int y)
	{
		if( star.equals("MainSequenceStar") )
		{
			ListIterator<? super Star> ms_iter = this.ms_stars.listIterator();
			
			while( ms_iter.hasNext() )
			{
				Star s = (Star)ms_iter.next();
				
				if( s.getX() == x && s.getY() == y )
				{
					ms_iter.remove();
					
					return;
				}
			}
		}
		
		if( star.equals("WhiteDwarf") )
		{
			ListIterator<? super Star> wd_iter = this.wd_stars.listIterator();
			
			while( wd_iter.hasNext() )
			{
				Star s = (Star)wd_iter.next();
				
				if( s.getX() == x && s.getY() == y )
				{
					wd_iter.remove();
					
					return;
				}
			}
		}
		
		if( star.equals("Supernova") )
		{
			ListIterator<? super Star> sn_iter = this.sn_stars.listIterator();
			
			while( sn_iter.hasNext() )
			{
				Star s = (Star)sn_iter.next();
				
				if( s.getX() == x && s.getY() == y )
				{
					sn_iter.remove();
					
					return;
				}
			}
		}
		
		if( star.equals("BlackHole") )
		{
			ListIterator<? super Star> bh_iter = this.bh_stars.listIterator();
			
			while( bh_iter.hasNext() )
			{
				Star s = (Star)bh_iter.next();
				
				if( s.getX() == x && s.getY() == y )
				{
					bh_iter.remove();
					
					return;
				}
			}
		}
		
		if( star.equals("Nebula") )
		{
			ListIterator<? super Star> nebula_iter = this.nebulae.listIterator();
			
			while( nebula_iter.hasNext() )
			{
				Star s = (Star)nebula_iter.next();
				
				if( s.getX() == x && s.getY() == y )
				{
					nebula_iter.remove();
					
					return;
				}
			}
		}
	}
}

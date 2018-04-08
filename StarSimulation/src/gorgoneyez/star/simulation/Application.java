package gorgoneyez.star.simulation;

import java.util.ListIterator;

public class Application
{
	private static final int NUM_ROWS = 5;
	private static final int NUM_COLS = 5;
	
	public static void main(String[] args)
	{
		SpaceMatrix spaceMatrix = new SpaceMatrix(NUM_ROWS, NUM_COLS);
		StarSystem starSystem = StarSystem.getStarSystem();
		
		int i = 3;
		
		while( i > 0 )
		{
			try
			{
				System.out.print(i + "..");
				Thread.sleep(700);
				i--;
			} catch( InterruptedException e )
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		}
		System.out.println("EXPLOSION");
		
		// main loop for simulation
		while( true )
		{
			// make program as thread-safe as possible
			synchronized( System.out )
			{
				// logging information to screen
				System.out.println("No of main sequence stars : " + starSystem.getStarList("MainSequenceStar").size());
				
				System.out.println("No of white dwarf stars : " + starSystem.getStarList("WhiteDwarf").size());

				System.out.println("No of supernova stars : " + starSystem.getStarList("Supernova").size());
				
				System.out.println("No of black holes : " + starSystem.getStarList("BlackHole").size());
				
				System.out.println("At t = " + spaceMatrix.getSpaceAge());
				// end logging
				
				// show matrix
				spaceMatrix.showSpaceMatrix();
			}
			
			// destroy star
			// condition in method to check
			// if star life has come to an end
			
			ListIterator<? super Star> ms_iter = starSystem.getStarList("MainSequenceStar").listIterator();
			String evolved;
			
			while( ms_iter.hasNext() )
			{
				Star s = (Star)ms_iter.next();
				
				if( !s.getRemainingLife() )
				{
					evolved = spaceMatrix.evolveStar(s);
					if( evolved.length() > 0 )
					{
						ms_iter.remove();
						System.out.println("Main sequence star evolved to " + evolved);
						starSystem.addStarToList(evolved);
					}
				}
			}
			
			ListIterator<? super Star> wd_iter = starSystem.getStarList("WhiteDwarf").listIterator();
			
			while( wd_iter.hasNext() )
			{
				Star s = (Star)wd_iter.next();
				
				if( !s.getRemainingLife() )
				{
					evolved = spaceMatrix.evolveStar(s);
					if( evolved.length() > 0 )
					{
						wd_iter.remove();
						System.out.println("White dwarf evolved to " + evolved);
						starSystem.addStarToList(evolved);
					}
				}
			}
			
			ListIterator<? super Star> sn_iter = starSystem.getStarList("Supernova").listIterator();
			
			while( sn_iter.hasNext() )
			{
				Star s = (Star)sn_iter.next();
				
				if( !s.getRemainingLife() )
				{
					evolved = spaceMatrix.evolveStar(s);
					if( evolved.length() > 0 )
					{
						sn_iter.remove();
						System.out.println("Supernova evolved to " + evolved);
						starSystem.addStarToList(evolved);
					}
				}
			}
			
			ListIterator<? super Star> bh_iter = starSystem.getStarList("BlackHole").listIterator();
			
			while( bh_iter.hasNext() )
			{
				Star s = (Star)bh_iter.next();
				
				// black hole kills every star in its vicinity
				int[] stars_around = ((BlackHole)s).neighboringStars();
				int xPos, yPos;
				
				if( !s.getRemainingLife() )
				{
					evolved = spaceMatrix.evolveStar(s);
					if( evolved.length() > 0 )
					{
						bh_iter.remove();
						System.out.println("Blackhole evolved to " + evolved);
						starSystem.addStarToList(evolved);
					}
				}
				
				for( int k = 0; k < stars_around.length; ++k )
				{
					if( ( k % 2 ) == 0 )
					{
						xPos = ( stars_around[k] < 0 || stars_around[k] >= spaceMatrix.getHeight() ) ? -1 : stars_around[k];
						yPos = ( stars_around[k + 1] < 0 || stars_around[k + 1] >= spaceMatrix.getHeight() ) ? -1 : stars_around[k + 1];
						
						if( xPos >= 0 && yPos >= 0 )
						{
							String star_type = spaceMatrix.getStarAt(xPos, yPos).toString();
							
							if( !star_type.equals(".") )
							{
								spaceMatrix.killStarAt(xPos, yPos);
								starSystem.removeStarFromList(star_type, xPos, yPos);
								System.out.println("black hole just killed " + star_type + " at position (" + xPos + ", " + yPos + ")");
							}
						}
					}
				}
			}
			
			// sleep thread
			try
			{
				Thread.sleep(1500);
				spaceMatrix.incrementAge();
				
				// create star
				spaceMatrix.createStar(MainSequenceStar.class, starSystem.getStarList("MainSequenceStar"), 1);
				
				// make all stars in the star system age
				starSystem.agingStar("Supernova");
				starSystem.agingStar("BlackHole");
				starSystem.agingStar("WhiteDwarf");
				starSystem.agingStar("MainSequenceStar");
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}

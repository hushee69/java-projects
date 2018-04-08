package gorgoneyez.star.simulation;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class SpaceMatrix
{
	private Random rand;
	
	private Star[][] stars;
	private int rows, columns;
	
	// number of years passed in space
	// equivalent to the number of tours
	private int space_age;
	
	// constructor for space matrix, fixed number of rows and columns
	public SpaceMatrix(int rows, int cols)
	{
		this.rand = new Random();
		
		this.rows = rows;
		this.columns = cols;
		
		stars = new Star[this.rows][this.columns];
		
		this.space_age = 0;
		
		for( int i = 0; i < this.rows; ++i )
		{
			for( int j = 0; j < this.columns; ++j )
			{
				stars[i][j] = new Nebula();
			}
		}
	}
	
	// show the world on the console
	public void showSpaceMatrix()
	{
		System.out.print(" ");
		for( int i = 0; i < this.rows; ++i )
		{
			System.out.print("   " + i + "   ");
		}
		
		System.out.println();
		for( int i = 0; i < this.rows; ++i )
		{
			System.out.print(i);
			for( int j = 0; j < this.columns; ++j )
			{
				System.out.print("   " + this.stars[i][j].toString() + "   ");
			}
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
	
	// create new star here and add it to
	// one of the classes belonging to the stars
	// the star is automatically added to the star matrix
	public boolean createStar(Class<? extends Star> star_class, ArrayList<? super Star> star_list, int no_of_stars)
	{
		Star s = null;
		
		int x_cord, y_cord;
		
		try
		{
			for( int i = 0; i < no_of_stars; ++i )
			{
				while( true )
				{
					x_cord = rand.nextInt(this.rows);
					y_cord = rand.nextInt(this.columns);
					
					if( !stars[x_cord][y_cord].getClass().equals(star_class.getClass()) && stars[x_cord][y_cord].toString().equals(".") )
					{
						break;
					}
				}
				
				s = star_class.newInstance();
				
				s.setX(x_cord);
				s.setY(y_cord);
				
				System.out.println("star added with coord " + s.getX() + ", " + s.getY());
				System.out.println("star remaining years : " + s.getAge());
				
				star_list.add(s);
				stars[x_cord][y_cord] = s;
			}
			
			return true;
		// end try block
		}
		catch (InstantiationException e)
		{
			System.err.println("Error : " + e.getMessage());
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			System.err.println("Error : " + e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	// end createStar
	
	// remove star from corresponding arraylist
	// and from the space matrix
	// and add the evolved star to the corresponding arraylist
	// and into the space matrix
	public void evolveStar(ArrayList<? super Star> star_list, ArrayList<? super Star> star_list_evolved)
	{
		ListIterator<? super Star> star_iter = star_list.listIterator();
		
		int x_cord, y_cord;		// coordinates to remove star from
		
		while( star_iter.hasNext() )
		{
			Star s = (Star)star_iter.next();
			
			if( !s.getRemainingLife() )
			{
				x_cord = s.getX();
				y_cord = s.getY();
				
				// debug lines
				System.out.println(s.getX() + ", " + s.getY());
//				System.out.println("uid = " + s.getIdentifier().toString());
//				System.out.println("uid in matrix = " + stars[x_cord][y_cord].getIdentifier());
				// end debug lines
				
				if( s.toString().equals("M") )
				{
					// verify if the star will become a white dwarf
					// or a supernova depending on the mass
					if( s.willBeSupernova() )
					{
						System.out.println("Supernova : " + s.getMass() + "(" + s.getX() + ", " + s.getY() + ")");
						Supernova sn = new Supernova();
						
						sn.setX(x_cord);
						sn.setY(y_cord);
						
						stars[x_cord][y_cord] = sn;
						star_list_evolved.add(sn);
					}
					else
					{
						System.out.println("White dwarf : " + s.getMass() + "(" + s.getX() + ", " + s.getY() + ")");
						WhiteDwarf wd = new WhiteDwarf();
						
						wd.setX(x_cord);
						wd.setY(y_cord);
						
						stars[x_cord][y_cord] = wd;
						star_list_evolved.add(wd);
					}
					
					star_iter.remove();
				}

				if( s.toString().equals("W") )
				{
					Nebula n = new Nebula();
					
					n.setX(x_cord);
					n.setY(y_cord);
					
					stars[x_cord][y_cord] = n;
					
					star_iter.remove();
					star_list_evolved.add(n);
				}

				if( s.toString().equals("S") )
				{
					System.out.println("Supernova burst..");
					BlackHole bh = new BlackHole();
					
					bh.setX(x_cord);
					bh.setY(y_cord);
					
					stars[x_cord][y_cord] = bh;
					
					star_iter.remove();
					star_list_evolved.add(bh);
				}
				
				if( s.toString().equals("B") )
				{
					System.out.println("Black hole dying..");
					Nebula n = new Nebula();
					
					n.setX(x_cord);
					n.setY(y_cord);
					
					stars[x_cord][y_cord] = n;
					
					star_iter.remove();
					star_list_evolved.add(n);
				}
			}
		}
		
	}
	// end evolveStar
	
	public String evolveStar(Star currentState)
	{
		int x_cord, y_cord;
		Star s = currentState;
		
		if( !s.getRemainingLife() )
		{
			x_cord = s.getX();
			y_cord = s.getY();
			
			// debug lines
//			System.out.println(s.getX() + ", " + s.getY());
//			System.out.println("uid = " + s.getIdentifier().toString());
//			System.out.println("uid in matrix = " + stars[x_cord][y_cord].getIdentifier());
			// end debug lines
			
			if( s.toString().equals("M") )
			{
				// verify if the star will become a white dwarf
				// or a supernova depending on the mass
				if( s.willBeSupernova() )
				{
					System.out.println("Supernova : " + s.getMass() + "(" + s.getX() + ", " + s.getY() + ")");
					Supernova sn = new Supernova();
					
					sn.setX(x_cord);
					sn.setY(y_cord);
					
					stars[x_cord][y_cord] = sn;
					
					return "Supernova";
				}
				else
				{
					System.out.println("White dwarf : " + s.getMass() + "(" + s.getX() + ", " + s.getY() + ")");
					WhiteDwarf wd = new WhiteDwarf();
					
					wd.setX(x_cord);
					wd.setY(y_cord);
					
					stars[x_cord][y_cord] = wd;
					
					return "WhiteDwarf";
				}
			}

			if( s.toString().equals("W") )
			{
				Nebula n = new Nebula();
				
				n.setX(x_cord);
				n.setY(y_cord);
				
				stars[x_cord][y_cord] = n;
				
				return "Nebula";
			}

			if( s.toString().equals("S") )
			{
				System.out.println("Supernova burst..(" + s.getX() + ", " + s.getY() + ")");
				BlackHole bh = new BlackHole();
				
				bh.setX(x_cord);
				bh.setY(y_cord);
				
				stars[x_cord][y_cord] = bh;
				
				return "BlackHole";
			}
			
			if( s.toString().equals("B") )
			{
				System.out.println("Black hole dying..(" + s.getX() + ", " + s.getY() + ")");
				Nebula n = new Nebula();
				
				n.setX(x_cord);
				n.setY(y_cord);
				
				stars[x_cord][y_cord] = n;
				
				return "Nebula";
			}
		}
		
		return "";
	}
	// end evolveStar
	
	// begin getSpaceAge
	public int getSpaceAge()
	{
		return this.space_age;
	}
	// end getSpaceAge
	
	// increment number of tours by 1
	public void incrementAge()
	{
		this.space_age++;
	}
	// end incrementAge
	
	public int getWidth()
	{
		return this.columns;
	}
	
	public int getHeight()
	{
		return this.rows;
	}
	
	public Star getStarAt(int x, int y)
	{
		return this.stars[x][y];
	}

	public boolean killStarAt(int x, int y)
	{
		this.stars[x][y] = new Nebula();
		
		return true;
	}
}

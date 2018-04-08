/*
 * Authors : Cire Diallo
 * 		Demba Cisse
 * 		Harry Jandu
 */

package gorgoneyez.game_of_life;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application implements Runnable
{
	private volatile int ageOfUniverse;
	private volatile boolean running = true;
	private volatile boolean paused = false;
	private final Object threadLock = new Object();
	
	private BufferedReader bufferedReader;
	private String readInput;
	
	private World world;
	
	public Application()
	{
		System.out.print("Enter row-column figure : ");
		
		this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		this.ageOfUniverse = 0;
		
		try
		{
			this.readInput = this.bufferedReader.readLine();
		}
		catch (IOException e1)
		{}
		
		int row_cols = 0;
		
		try
		{
			row_cols = Integer.parseInt(this.readInput);
		}
		catch( NumberFormatException e )
		{}
		catch( NullPointerException e )
		{}
		
		if( row_cols < 10 )
		{
			row_cols = 10;
		}

		world = World.getInstance(row_cols, row_cols);
		
		System.out.println("Choose from the following setups. Default setup glider");
		System.out.println("1. Glider");
		System.out.println("2. Small Exploder");
		System.out.println("3. Exploder");
		System.out.println("4. 10 Cell Row");
		System.out.println("5. Light Weight Spaceship");
		System.out.println("6. Tumbler");
		// System.out.println("7. Gosper Glider gun");	
		
		try
		{
			this.readInput = this.bufferedReader.readLine();
		}
		catch( IOException e )
		{}
		
		switch( this.readInput.charAt(0) )
		{
			case '1':
			{
				world.clear();
				world.glider();
				break;
			}
			case '2':
			{
				world.clear();
				world.smallExploder();
				break;
			}
			case '3':
			{
				world.clear();
				world.exploder();
				break;
			}
			case '4':
			{
				world.clear();
				world.tenCellRow();
				break;
			}
			case '5':
			{
				world.clear();
				world.lightWeightSpaceship();
				break;
			}
			case '6':
			{
				world.clear();
				world.tumbler();
				break;
			}
			default:
			{
				world.clear();
				world.glider();
				break;
			}
		}
		
	}
	
	public static void main(String[] args)
	{
		Application app = new Application();
		
		app.run();
	}

	@Override
	public void run()
	{
		while( running )
		{
			System.out.println("t = " + this.ageOfUniverse);
			world.displayWorld();
			world.nextGeneration();
			
			try
			{
				if( this.bufferedReader.ready() )
				{
					this.readInput = this.bufferedReader.readLine();
					
					if( this.readInput.length() > 0 && this.readInput.charAt(0) == 's' )
					{
						System.out.println("pausing thread...");
						this.pause();
					}
				}
			}
			catch (IOException e1)
			{}
			
			synchronized( threadLock )
			{
				if( !running )
				{
					break;
				}
				
				while( paused )
				{
					System.out.println("waiting..press `r` for resume");
					try
					{
						this.readInput = this.bufferedReader.readLine();
						
						System.out.println(this.readInput);
						if( this.readInput.length() > 0 && this.readInput.charAt(0) == 'r' )
						{
							System.out.println("paused here = " + paused);
							this.resume();
						}
					}
					catch (IOException e1)
					{}
				}
			}
			
			try
			{
				Thread.sleep(1200);
				this.ageOfUniverse++;
			}
			catch( InterruptedException e )
			{}
		}
	}
	
	public void stop()
	{
		running = false;
		
		resume();
	}
	
	public void pause()
	{
		paused = true;
	}
	
	public void resume()
	{
		synchronized( Application.class )
		{
			paused = false;
			threadLock.notifyAll();
		}
	}
}

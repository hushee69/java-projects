package gorgoneyez.game_of_life;

public class Cell
{
	// x et y de la cellule
	private int xPos, yPos;
	private boolean state;
	
	public Cell(int x, int y, boolean state)
	{
		this.xPos = x;
		this.yPos = y;
		this.state = state;
	}
	
	public int[] getNeighbors()
	{
		int[] retVal = new int[16];
		
		int localX = this.getX();
		int localY = this.getY();
		
		int voisinX, voisinY;
		
		int positionTab = 0;
		
		for( int i = -1; i <= 1; ++i )
		{
			for( int j = -1; j <= 1; ++j )
			{
				voisinX = localX + i;
				voisinY = localY + j;
				
				if( voisinX != localX || voisinY != localY )
				{
					retVal[positionTab] = voisinX;
					positionTab++;
					
					retVal[positionTab] = voisinY;
					positionTab++;
				}
			}
		}
		
		return retVal;
	}
	
	public int getX()
	{
		return this.xPos;
	}
	
	public void setX(int x)
	{
		this.xPos = x;
	}
	
	public int getY()
	{
		return this.yPos;
	}
	
	public void setY(int y)
	{
		this.yPos = y;
	}
	
	public boolean getState()
	{
		return this.state;
	}
	
	public void setState(boolean state)
	{
		this.state = state;
	}
}

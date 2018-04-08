package gorgoneyez.star.simulation;

public class BlackHole extends Star
{
	public BlackHole()
	{
		super("B");
		this.setRemainingLife(2);
	}
	
	public int[] neighboringStars()
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
}

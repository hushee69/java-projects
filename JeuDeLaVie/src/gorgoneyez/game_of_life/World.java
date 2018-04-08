package gorgoneyez.game_of_life;

import java.util.Arrays;

public class World
{
	private static World worldInstance;
	
	private int rows, columns;
	private Cell[][] cells;
	private Cell[][] tempCopy;
	
	private int midCols, midRows;
	
	private World(int rows, int cols)
	{
		this.rows = rows;
		this.columns = cols;
		
		this.midCols = this.columns / 2;
		this.midRows = this.rows / 2;
		
		this.cells = new Cell[this.rows][this.columns];
		this.tempCopy = new Cell[this.rows][this.columns];
		
		for( int i = 0; i < this.rows; ++i )
		{
			for( int j = 0; j < this.columns; ++j )
			{
				this.cells[i][j] = new Cell(i, j, false);
				this.tempCopy[i][j] = new Cell(i, j, false);
			}
		}
	}
	
	public static World getInstance(int rows, int cols)
	{
		if( worldInstance == null )
		{
			worldInstance = new World(rows, cols);
		}
		
		return worldInstance;
	}
	
	public void displayWorld()
	{		
		for( int i = 0; i < this.rows; ++i )
		{
			for( int j = 0; j < this.columns; ++j )
			{
				System.out.print((this.cells[i][j].getState()) ? "   #   " : "   .   ");
			}
			System.out.println();
			System.out.println();
		}
	}
	
	public void clear()
	{
		for( int i = 0; i < this.rows; ++i )
		{
			for( int j = 0; j < this.columns; ++j )
			{
				this.cells[i][j].setState(false);
				this.tempCopy[i][j].setState(false);
			}
		}
	}
	
	public void glider()
	{
		this.bogusStates(midRows, midCols);
		this.bogusStates(midRows + 1, midCols + 1);
		this.bogusStates(midRows + 2, midCols + 1);
		this.bogusStates(midRows + 2, midCols);
		this.bogusStates(midRows + 2, midCols - 1);
	}
	
	public void smallExploder()
	{
		this.bogusStates(midRows, midCols);
		this.bogusStates(midRows + 1, midCols - 1);
		this.bogusStates(midRows + 1, midCols);
		this.bogusStates(midRows + 1, midCols + 1);
		this.bogusStates(midRows + 2, midCols - 1);
		this.bogusStates(midRows + 2, midCols + 1);
		this.bogusStates(midRows + 3, midCols);
	}
	
	public void exploder()
	{
		this.bogusStates(midRows, midCols);
		
		for( int i = 0; i < 5; ++i )
		{
			this.bogusStates(midRows + i, midCols - 2);
			this.bogusStates(midRows + i, midCols + 2);
		}
		
		this.bogusStates(midRows + 4, midCols);
	}
	
	public void tenCellRow()
	{
		for( int i = -5; i < 5; ++i )
		{
			this.bogusStates(midRows, midCols + i);
		}
	}
	
	public void lightWeightSpaceship()
	{
		for( int i = -2; i < 2; ++i )
		{
			this.bogusStates(midRows, midCols + i);
		}
		for( int i = 0; i < 3; ++i )
		{
			this.bogusStates(midRows + i, midCols + 1);
		}
		
		this.bogusStates(midRows + 3, midCols);
		this.bogusStates(midRows + 3, midCols - 3);
		this.bogusStates(midRows + 1, midCols - 3);
	}
	
	public void tumbler()
	{
		for( int i = -3; i < 2; ++i )
		{
			this.bogusStates(midRows + i, midCols - 1);
			this.bogusStates(midRows + i, midCols + 1);
		}
		
		this.bogusStates(midRows - 3, midCols - 2);
		this.bogusStates(midRows - 3, midCols + 2);

		this.bogusStates(midRows - 2, midCols - 2);
		this.bogusStates(midRows - 2, midCols + 2);
		
		this.bogusStates(midRows + 2, midCols - 2);
		this.bogusStates(midRows + 2, midCols + 2);

		this.bogusStates(midRows + 2, midCols - 3);
		this.bogusStates(midRows + 2, midCols + 3);

		this.bogusStates(midRows + 1, midCols - 3);
		this.bogusStates(midRows + 1, midCols + 3);

		this.bogusStates(midRows, midCols - 3);
		this.bogusStates(midRows, midCols + 3);
	}
	
	// debug function for states and checking neighbors
	public void bogusStates(int x, int y)
	{
		this.cells[x][y].setState(true);
	}
	// end bogusStates
	
	// debug function displayNeighbors
	public void displayNeighbors(int x, int y)
	{
		int[] neighbors = this.cells[x][y].getNeighbors();
		
		System.out.println(Arrays.toString(neighbors));
	}
	// end displayNeighbors
	
	public void nextGeneration()
	{
		this.tempCopy = this.deepCopyCells(this.cells);
		
		for( int i = 0; i < this.rows; ++i )
		{
			for( int j = 0; j < this.columns; ++j )
			{
				this.findNextStep(this.cells[i][j].getNeighbors(), i, j);
			}
		}
		
		this.cells = this.deepCopyCells(this.tempCopy);
	}

	private void findNextStep(int[] neighbors, int x, int y)
	{
		int localX, localY;
		int neighborCount = 0;
		
		for( int i = 0; i < neighbors.length; ++i )
		{
			if( i % 2 == 0 )
			{
				localX = neighbors[i];
				localY = neighbors[i + 1];
				
				if( localX < 0 )
				{
					localX = this.rows - 1;
				}
				else if( localX >= this.rows )
				{
					localX = 0;
				}
				
				if( localY < 0 )
				{
					localY = this.rows - 1;
				}
				else if( localY >= this.columns )
				{
					localY = 0;
				}
				
				// neighborCount
				if( this.cells[localX][localY].getState() )
				{
					neighborCount++;
				}
			}
		}
		
		// each cell with one or no neighbors dies by loneliness :(
		if( neighborCount <= 1 || neighborCount >= 4 )
		{
//			System.out.println("Entering (" + x + ", " + y + ") " + neighborCount);
			tempCopy[x][y].setState(false);
//			System.out.println("state : " + tempCopy[x][y].getState());
		}
		if( neighborCount == 3 )
		{
			tempCopy[x][y].setState(true);
		}
	}
	
	public Cell[][] deepCopyCells(Cell[][] src)
	{
		Cell[][] retVal = new Cell[this.rows][this.columns];
		
		for( int i = 0; i < this.rows; ++i )
		{
			for( int j = 0; j < this.columns; ++j )
			{
				retVal[i][j] = new Cell(i, j, false);
				retVal[i][j].setState(src[i][j].getState());
			}
		}
		
		return retVal;
	}
}

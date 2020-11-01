/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplex;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author Gabriel and Emanuel
 */
public class Simplex
{
	static int rows = 1;
	static int collumns = 1;
	static float[][] simplex;
	static float[][] restricoes;
	static int[] dominio;
	static int[] dominioDual;
	static float[][] restricoesDual;
	static float[] choosePivot;
	static int[] PivotPos;
	static float[] divLines;
	static int type; //Type 0 for min 1 for max
	static int numvarZ;
	static int Yn;
	static int pointerOnOne = 0;
	static int numrest;
	static float[] varZ;
	static float[] varG;
	static float[][] formap;
	//static float simplex[][]=new float[rows][collumns];
	//static float choosePivot[]=new float[rows-1];
	//static ArrayList<float[]> divLines = new ArrayList<float[rows]>();
	//static float divLines[]=new float[rows];
	//static int PivotPos[]=new int[rows-1];
	static int negPos = 0;
	static int posMinFinal = 0;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		// TODO code application logic here
		Scanner reader = new Scanner(System.in);
		System.out.println("Inserir 0 para Minimizacao ou 1 para Maximizacao");
		type = reader.nextInt();
		System.out.println("Inserir o numero de variaveis na funcao objetivo");
		numvarZ = reader.nextInt();
		varZ = new float[numvarZ];
		for (int i = 1; i <= numvarZ; i++)
		{
			System.out.println("Inserir valor da variavel X" + i);
			varZ[i - 1] = reader.nextInt();
		}
		System.out.println("Inserir numero de restrições");
		numrest = reader.nextInt();
		Yn = numrest;
		restricoes = new float[numrest][numvarZ + 2];
		formap = new float[numrest][numvarZ + 2];
		for (int i = 1; i <= numrest; i++)
		{
			for (int j = 1; j <= numvarZ + 2; j++)
			{
				if (j == numvarZ + 1)
				{
					System.out.println("Escolher sinal da limitação: 0 para <= ou  1 para >= ou 2 para =");
					restricoes[i - 1][j - 1] = reader.nextInt();

				}
				else if (j == numvarZ + 2)
				{
					System.out.println("Inserir valor da limitação");
					restricoes[i - 1][j - 1] = reader.nextInt();
				}
				else
				{
					System.out.println("Inserir valor da variavel X" + j + " da restrição " + i);
					restricoes[i - 1][j - 1] = reader.nextInt();
				}
			}
		}

		dominio = new int[numvarZ];
		System.out.println("Escolher o dominio das variaveis: 0 para <=0 ; 1 para >=0 ; 2 para Livre");
		for (int i = 1; i <= numvarZ; i++)
		{
			System.out.print("x" + i + ": ");
			dominio[i - 1] = reader.nextInt();
		}


		//Mostrar a função objetivo as restrições e o dominio
		Print();

		// Quando o exercicio é de minimizar
		if (type == 0)
		{
			forma_padrao();

			rows = numrest + 1;

			collumns = Yn + numvarZ + 1;

			//teste
			System.out.println(varZ[0]);

			choosePivot = new float[rows - 1];
			simplex = new float[numrest + 1][Yn + numvarZ + 1];
			PivotPos = new int[rows - 1];
			divLines = new float[rows];


			for (int i = 0; i < numrest + 1; i++)
			{
				if (i == numrest)
				{
					for (int j = 0; j < numvarZ + 1 + Yn; j++)
					{
						if (j == 0)
							simplex[i][j] = 0;
						if (j > 0 && j < numvarZ + 1)
						{
							simplex[i][j] = varZ[j - 1] * -1;
						}
						else
							simplex[i][j] = 0;

					}
				}
				else
				{
					for (int j = 0; j < numvarZ + 2; j++)
					{
						if (j == 0)
							simplex[i][j] = formap[i][numvarZ + 1];
						else
							simplex[i][j] = formap[i][j - 1];
					}
				}

			}

			for (int i = 0; i < numrest + 1; i++)
			{

				for (int j = numvarZ + 1; j < numvarZ + 1 + Yn; j++)
				{
					if (pointerOnOne + numvarZ + 1 == j)
					{
						simplex[i][j] = 1;
					}
					else
					{
						simplex[i][j] = 0;
					}
				}
				pointerOnOne++;
			}


			Simplex();


			/*
    //Temporarily declaring restrictions
    simplex[0][0]=120;
    simplex[0][1]=3;
    simplex[0][2]=10;
    simplex[0][3]=5;
    simplex[0][4]=1;
    simplex[0][5]=0;
    simplex[0][6]=0;
    
    simplex[1][0]=6;
    simplex[1][1]=5;
    simplex[1][2]=2;
    simplex[1][3]=8;
    simplex[1][4]=0;
    simplex[1][5]=1;
    simplex[1][6]=0;
    
    simplex[2][0]=105;
    simplex[2][1]=8;
    simplex[2][2]=10;
    simplex[2][3]=3;
    simplex[2][4]=0;
    simplex[2][5]=0;
    simplex[2][6]=1;
    
    simplex[3][0]=0;
    simplex[3][1]=-3;
    simplex[3][2]=-4;
    simplex[3][3]=-1;
    simplex[3][4]=0;
    simplex[3][5]=0;
    simplex[3][6]=0;
*/

			/*
    //Show Simplex table
    for (int i=0;i<rows;i++)
    {
      System.out.print("\n ");
      for(int j=0;j<collumns;j++)
       System.out.print(simplex[i][j]+" ");
    }
    
    Simplex();
*/


			/*
			//If lowest value is negative then we will repeat last methods
			if(simplex[simplex.length-1][posMinFinal]<0)
			{
			  System.out.println("\nRepete a parte de cima");
			  Simplex();
			}*/


		}
		// Quando o exercicio é de maximixar
		else
		{
			Dual();


			System.out.println("Funcao G: " + varG[0] + "w1 + " + varG[1] + "w2");
			System.out.println("Dominio G: " + dominioDual[0] + " " + dominioDual[1]);

			System.out.print("\n\nRestrições:\n  --");
			for (int i = 1; i <= numvarZ; i++)
			{
				System.out.print("\n | ");
				for (int j = 1; j <= numrest + 2; j++)
				{
					if (j == numrest + 1 && restricoesDual[i - 1][j - 1] == 0)
					{
						System.out.print("<= ");
					}
					else if (j == numrest + 1 && restricoesDual[i - 1][j - 1] == 1)
					{
						System.out.print(">= ");
					}
					else if (j == numrest + 1 && restricoesDual[i - 1][j - 1] == 2)
					{
						System.out.print("= ");
					}
					else if (j == numrest + 2)
					{
						System.out.print(restricoesDual[i - 1][j - 1] + " ");
					}
					else
					{
						System.out.print(restricoesDual[i - 1][j - 1] + "x" + j + " ");
					}
				}
			}
		}

	}


	public static void forma_padrao()
	{
		for (int i = 0; i < numrest; i++)
		{
			for (int j = 0; j < numvarZ + 2; j++)
			{
				if (j == numvarZ)
				{
					if (restricoes[i][numvarZ] == 2)
						formap[i][j] = 0;
					else if (restricoes[i][numvarZ] == 1)
						formap[i][j] = -1;
					else
						formap[i][j] = 1;
				}
				else
					formap[i][j] = restricoes[i][j];

				System.out.println(formap[i][j]);
			}
		}

		for (int i = 0; i < numrest; i++)
		{
			System.out.println("pos:" + i + " " + formap[i][numvarZ] + " " + formap[i][numvarZ + 1]);
			if (formap[i][numvarZ] < 0 && formap[i][numvarZ + 1] > 0)
			{
				System.out.println("Impossivel mimi ");
				System.exit(1);
			}
			else
			if (formap[i][numvarZ] > 0 && formap[i][numvarZ + 1] < 0)
			{
				System.out.println("Impossivel mimi ");
				System.exit(1);
			}
			else if (formap[i][numvarZ + 1] < 0)
			{
				for (int j = 0; j < numvarZ + 2; j++)
				{
					formap[i][j] = formap[i][j] * -1;
				}
			}

		}
		for (int i = 0; i < numvarZ; i++)
		{
			if (dominio[i] == 0)
			{
				System.out.println("Impossivel dominio ");
				System.exit(1);
			}
		}

		//temp
		for (int i = 0; i < numrest; i++)
		{
			for (int j = 0; j < numvarZ + 2; j++)
			{
				//System.out.println(formap[i][j]);
			}
		}

		for (int i = 0; i < numrest; i++)
		{
			if (formap[i][numvarZ] == 0)
				Yn--;
			System.out.println("Yn: " + Yn);
		}


	}
	public static void Dual()
	{
		varG = new float[numrest];
		restricoesDual = new float[numvarZ][numrest + 2];
		dominioDual = new int[numrest];
		for (int i = 0; i < numrest; i++)
		{
			varG[i] = restricoes[i][numvarZ + 1];
		}
		for (int i = 0; i < numrest; i++)
		{
			if (restricoes[i][numvarZ] == 0)
			{
				dominioDual[i] = 1;
			}
			else if (restricoes[i][numvarZ] == 1)
			{
				dominioDual[i] = 0;
			}
			else if (restricoes[i][numvarZ] == 2)
			{
				dominioDual[i] = 2;
			}
		}
		for (int i = 0; i < numvarZ; i++)
		{
			for (int j = 0; j < numrest + 2; j++)
			{
				if (j < numrest)
				{
					restricoesDual[i][j] = restricoes[j][i];
				}
				else if (j == numrest)
				{
					if (dominio[i] == 0)
					{
						restricoesDual[i][numrest] = 0;
					}
					else if (dominio[i] == 1)
					{
						restricoesDual[i][numrest] = 1;
					}
					else if (dominio[i] == 2)
					{
						restricoesDual[i][numrest] = 2;
					}
				}
				else if (j == numrest + 1)
				{
					restricoesDual[i][numrest + 1] = varZ[i];
				}
			}
		}
	}

	public static void Simplex()
	{
		//Find min in z
		float min = simplex[rows - 1][0];
		int pos = 0;
		for (int j = simplex[rows - 1].length - 1; j > -1; j--)
		{
			System.out.println("\nposis:" + simplex[rows - 1][j] + " ");
			if (simplex[rows - 1][j] > min)
			{
				min = simplex[rows - 1][j];
				pos = j;
			}
		}
		System.out.println("\nmax:" + min + " pos: " + pos);
		if (min > 0)
		{

			//Finding pivot (< first collumn / min value in z)
			for (int i = 0; i < rows - 1; i++)
			{
				if (simplex[i][0] == 0)
					choosePivot[i] = 0;
				else
				{
					choosePivot[i] = simplex[i][0] / simplex[i][pos];
					PivotPos[i] = i;
				}

				System.out.println(simplex[i][0] / simplex[i][pos]);
			}

			//Saving both the Pivot value and position
			float minPivot = choosePivot[0];
			int posPivot = 0;
			for (int j = 0; j < choosePivot.length; j++)
			{
				//System.out.println(" "+choosePivot[j]);
				if (choosePivot[j] < minPivot && choosePivot[j] > 0)
				{
					System.out.println(choosePivot[j]);
					minPivot = choosePivot[j];
					posPivot = j;
				}
			}
			System.out.println("\nmax:" + minPivot + " pos: " + PivotPos[posPivot]);

			//Gauss law (finding by which ammount i should divide/multiply by, and saving them in an array)
			int x = 0;
			for (int i = 0; i < simplex.length; i++)
			{
				if (i != PivotPos[posPivot])
				{

					divLines[x] = -1 * (simplex[i][pos]) / simplex[PivotPos[posPivot]][pos];
					System.out.println(divLines[x]);
					x++;
				}
			}
			divLines[x] = 1 / simplex[PivotPos[posPivot]][pos];

			//Gauss law (Using previous array to execute law)
			for (int i = 0; i < simplex[0].length; i++)
			{
				System.out.print("\n");
				for (int j = 0; j < simplex.length; j++)
				{
					if (j != PivotPos[posPivot])
					{
						if (j == 0)
							simplex[j][i] = simplex[j][i] + divLines[j] * simplex[PivotPos[posPivot]][i];
						else
							simplex[j][i] = simplex[j][i] + divLines[j - 1] * simplex[PivotPos[posPivot]][i];

					}

				}
			}

			//Fixing pivot to 1 (and his row)
			float temp = (1 / simplex[PivotPos[posPivot]][pos]); //Had to do this here, wasn't working when i made the calculation all toghether down there (classic java hehe)
			for (int i = 0; i < simplex[PivotPos[posPivot]].length; i++)
			{
				simplex[PivotPos[posPivot]][i] = temp * simplex[PivotPos[posPivot]][i];
			}

			//Show Simplex table
			for (int i = 0; i < rows; i++)
			{
				System.out.print("\n ");
				for (int j = 0; j < collumns; j++)
					System.out.print(simplex[i][j] + " ");
			}

			//Finding the lowest value (only used if it's negative)
			float minFinal = simplex[simplex.length - 1][0];
			for (int i = 0; i < simplex[simplex.length - 1].length; i++)
			{
				if (simplex[simplex.length - 1][i] > minFinal)
				{

					posMinFinal = i;
					minFinal = simplex[simplex.length - 1][i];

				}
			}


			if (simplex[simplex.length - 1][posMinFinal] < 0)
			{
				System.out.println("\nRepete a parte de cima");
				Simplex();
			}
		}
		else
		{
			for (int i = 0; i < rows; i++)
			{
				System.out.print("\n ");
				for (int j = 0; j < collumns; j++)
					System.out.print(simplex[i][j] + " ");
			}
		}


	}
	static void Print()
	{
		//Mostrar função objetivo
		System.out.println("\nFunção Objetivo");
		System.out.print("Z= ");
		for (int i = 1; i <= numvarZ; i++)
		{
			System.out.print(varZ[i - 1] + "x" + i + "+ ");
		}
		//teste mostrar as restricoes
		System.out.print("\n\nRestrições:\n  --");
		for (int i = 1; i <= numrest; i++)
		{
			System.out.print("\n | ");
			for (int j = 1; j <= numvarZ + 2; j++)
			{
				if (j == numvarZ + 1 && restricoes[i - 1][j - 1] == 0)
				{
					System.out.print("<= ");
				}
				else if (j == numvarZ + 1 && restricoes[i - 1][j - 1] == 1)
				{
					System.out.print(">= ");
				}
				else if (j == numvarZ + 1 && restricoes[i - 1][j - 1] == 2)
				{
					System.out.print("= ");
				}
				else if (j == numvarZ + 2)
				{
					System.out.print(restricoes[i - 1][j - 1] + " ");
				}
				else
				{
					System.out.print(restricoes[i - 1][j - 1] + "x" + j + " ");
				}
			}
		}
		//teste Mostrar o dominio
		System.out.println("");
		for (int j = 0; j < 3; j++)
		{
			for (int i = 1; i <= numvarZ; i++)
			{
				if (j == 0)
				{
					if (dominio[i - 1] == 0)
					{
						System.out.print("x" + i + ", ");
					}
				}
				else if (j == 1)
				{
					if (dominio[i - 1] == 1)
					{
						System.out.print("x" + i + ", ");
					}
				}
				else if (j == 2)
				{
					if (dominio[i - 1] == 2)
					{
						System.out.print("x" + i + ", ");
					}
				}
			}
			if (j == 0)
			{
				System.out.print("<=0 ");
			}
			else if (j == 1)
			{
				System.out.print(">=0 ");
			}
			else if (j == 2)
			{
				System.out.print("Livre ");
			}
		}
		System.out.println("\n  --");
	}

}

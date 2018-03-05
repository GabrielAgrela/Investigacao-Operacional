import java.util.Arrays;
import java.util.Collections;

//Uncomment Println's to help understand the code

//-----TODO-----
//Add dynamism (float[3][5] is static for now for example)
//Test this better
//This is for Max function only, implement Min
//Pos-Analise (change function values w/o re-doing it all again)
//--------------
class Main 
{
  
    static float simplex[][]=new float[3][5];
    static float choosePivot[]=new float[2];
    static float divLines[]=new float[3];
    static int PivotPos[]=new int[2];
    static int negPos=0;
    static int posMinFinal = 0;

  public static void Simplex() 
  {
    //Find min in z
    float min = simplex[2][0];
    int pos=0;
    for (int j=simplex.length-1; j > -1; j--) 
    {
        if (simplex[2][j] < min) 
        { 
            min = simplex[2][j];
            pos=j;
        }
    }
    //System.out.println("\nmin:"+min+" pos: "+pos);
    
    //Finding pivot (< first collumn / min value in z)
    for (int i=0;i<2;i++)
    {
      choosePivot[i]=simplex[i][0]/simplex[i][pos];
      PivotPos[i]=i;
      //System.out.println(simplex[i][0]/simplex[i][pos]);
    }
    
    //Saving both the Pivot value and position
    float minPivot = choosePivot[0];
    int posPivot=0;
    for (int j=0; j < choosePivot.length; j++)
    {
      
        if (choosePivot[j] < minPivot) 
        {  
          //System.out.println(choosePivot[j]);
            minPivot = choosePivot[j];
            posPivot=j;
        }
    }
    //System.out.println("\nmin:"+minPivot+" pos: "+PivotPos[posPivot]);
    
    //Gauss law (finding by which ammount i should divide/multiply by, and saving them in an array)
    int x=0;
    for (int i=0;i<simplex.length;i++)
    {
      if (i != PivotPos[posPivot])
        {
          
          divLines[x]=-1*(simplex[i][pos])/simplex[PivotPos[posPivot]][pos];
          //System.out.println(divLines[x]);
          x++;
        }
    }
    divLines[x]=1/simplex[PivotPos[posPivot]][pos];
    
    //Gauss law (Using previous array to execute law)
    for (int i=0;i<simplex[0].length;i++)
    {
      System.out.print("\n");
      for (int j=0;j<simplex.length;j++)
      {
        if (j != PivotPos[posPivot])
        {
          if(j==0)
            simplex[j][i]=simplex[j][i]+divLines[j]*simplex[PivotPos[posPivot]][i];
            else
              simplex[j][i]=simplex[j][i]+divLines[j-1]*simplex[PivotPos[posPivot]][i];

        }
          
      }
    }
    
    //Fixing pivot to 1 (and his row)
    float temp=(1/simplex[PivotPos[posPivot]][pos]);//Had to do this here, wasn't working when i made the calculation all toghether down there (classic java hehe)
    for (int i=0;i<simplex[PivotPos[posPivot]].length;i++)
    {
      simplex[PivotPos[posPivot]][i]=temp*simplex[PivotPos[posPivot]][i];
    }
    
    //Show Simplex table
    for (int i=0;i<3;i++)
    {
      System.out.print("\n ");
      for(int j=0;j<5;j++)
       System.out.print(simplex[i][j]+" ");
    }
    
    //Finding the lowest value (only used if it's negative)
    float minFinal = simplex[simplex.length-1][0];
    for (int i=0;i<simplex[simplex.length-1].length;i++)
    {
      if (simplex[simplex.length-1][i] < minFinal) 
        {  
          
            posMinFinal=i;
            minFinal = simplex[simplex.length-1][i];
  
        }
    }
    
  }
  public static void main(String[] args) 
  {
    //Temporarily declaring restrictions
    simplex[0][0]=20;
    simplex[0][1]=2;
    simplex[0][2]=4;
    simplex[0][3]=1;
    simplex[0][4]=0;
    simplex[1][0]=45;
    simplex[1][1]=5;
    simplex[1][2]=2;
    simplex[1][3]=0;
    simplex[1][4]=1;
    simplex[2][0]=0;
    simplex[2][1]=-2;
    simplex[2][2]=-2;
    simplex[2][3]=0;
    simplex[2][4]=0;
    
    //Show Simplex table
    for (int i=0;i<3;i++)
    {
      System.out.print("\n ");
      for(int j=0;j<5;j++)
       System.out.print(simplex[i][j]+" ");
    }
    
    Simplex();
    

    //If lowest value is negative then we will repeat last methods
    if(simplex[simplex.length-1][posMinFinal]<0)
    {
      System.out.println("\nRepete a parte de cima");
      Simplex();
    }
    
  }
}

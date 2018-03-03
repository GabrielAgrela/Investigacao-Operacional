import java.util.Arrays;
import java.util.Collections;

class Main 
{
  
    static float simpelx[][]=new float[3][5];
    static float choosePivot[]=new float[2];
    static float divLines[]=new float[3];
    static int PivotPos[]=new int[2];
    static int negPos=0;
    static int posMinFinal = 0;

  
  public static void Simplex() 
  {
    //Find min in z
    float min = simpelx[2][0];
    int pos=0;
    for (int j=simpelx.length-1; j > -1; j--) 
    {
        if (simpelx[2][j] < min) 
        { 
            min = simpelx[2][j];
            pos=j;
        }
    }
    //System.out.println("\nmin:"+min+" pos: "+pos);
    
    //Finding pivot (< first collumn / min value in z)
    for (int i=0;i<2;i++)
    {
      choosePivot[i]=simpelx[i][0]/simpelx[i][pos];
      PivotPos[i]=i;
      //System.out.println(simpelx[i][0]/simpelx[i][pos]);
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
    for (int i=0;i<simpelx.length;i++)
    {
      if (i != PivotPos[posPivot])
        {
          
          divLines[x]=-1*(simpelx[i][pos])/simpelx[PivotPos[posPivot]][pos];
          //System.out.println(divLines[x]);
          x++;
        }
    }
    divLines[x]=1/simpelx[PivotPos[posPivot]][pos];
    
    //Gauss law (Using previous array to execute law)
    for (int i=0;i<simpelx[0].length;i++)
    {
      System.out.print("\n");
      for (int j=0;j<simpelx.length;j++)
      {
        if (j != PivotPos[posPivot])
        {
          if(j==0)
            simpelx[j][i]=simpelx[j][i]+divLines[j]*simpelx[PivotPos[posPivot]][i];
            else
              simpelx[j][i]=simpelx[j][i]+divLines[j-1]*simpelx[PivotPos[posPivot]][i];

        }
          
      }
    }
    
    //Fixing pivot to 1 (and his row)
    float temp=(1/simpelx[PivotPos[posPivot]][pos]);//Had to do this here, wasn't working when i made the calculation all toghether down there (classic java hehe)
    for (int i=0;i<simpelx[PivotPos[posPivot]].length;i++)
    {
      simpelx[PivotPos[posPivot]][i]=temp*simpelx[PivotPos[posPivot]][i];
    }
    
    //Show Simplex table
    for (int i=0;i<3;i++)
    {
      System.out.print("\n ");
      for(int j=0;j<5;j++)
       System.out.print(simpelx[i][j]+" ");
    }
    
    //Finding the lowest value (only used if it's negative)

    float minFinal = simpelx[simpelx.length-1][0];
    for (int i=0;i<simpelx[simpelx.length-1].length;i++)
    {
      if (simpelx[simpelx.length-1][i] < minFinal) 
        {  
          
            posMinFinal=i;
            minFinal = simpelx[simpelx.length-1][i];
  
        }
    }
    
  }
  public static void main(String[] args) 
  {
    //Temporarily declaring restrictions
    simpelx[0][0]=20;
    simpelx[0][1]=2;
    simpelx[0][2]=4;
    simpelx[0][3]=1;
    simpelx[0][4]=0;
    simpelx[1][0]=45;
    simpelx[1][1]=5;
    simpelx[1][2]=2;
    simpelx[1][3]=0;
    simpelx[1][4]=1;
    simpelx[2][0]=0;
    simpelx[2][1]=-2;
    simpelx[2][2]=-2;
    simpelx[2][3]=0;
    simpelx[2][4]=0;
    Simplex();
    //If lowest value is negative then we will repeat last methods
    if(simpelx[simpelx.length-1][posMinFinal]<0)
    {
      System.out.println("\nRepete a parte de cima");
      Simplex();
    }
    

    
    
}
  
}

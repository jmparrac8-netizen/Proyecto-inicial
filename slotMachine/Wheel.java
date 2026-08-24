
/**
 * Its a wheel with symbols, part of the slotMachine
 * 
 * @author Tomas Arevalo-Jose Parra 
 * @version 1.0
 */
import java.util.ArrayList;
public class Wheel
{
    private int xPosition;
    private int yPosition;
    private ArrayList<Symbol> symbols;
    private boolean isVisible;
    

    /**
     * Constructor for objects of class Wheel
     */
    public Wheel(int xpos,int ypos)
    {
        symbols=new ArrayList<>();
        isVisible =false;
        this.xPosition=xpos;
        this.yPosition=ypos;
        
        
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
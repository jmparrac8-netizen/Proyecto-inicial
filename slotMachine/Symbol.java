
/**
 * A symbol will call the circle class, to take it for his own, the symbol is just
 * a circle that changes its color, its position is determined by the wheel
 * 
 * @author Tomas Arevalo-Jose Parra 
 * @version 1.0
 */
public class Symbol
{
    private Circle symbol;
    private String actualColor;
    /**
     * Constructor for objects of class Symbol
     */
    public Symbol(String color,int x,int y)
    {
        symbol=new Circle();
        symbol.changeColor(color);
        symbol.moveVertical(y);
        symbol.moveHorizontal(x);
    }

    /**
     *changeColor es un metodo que cambia el color del simbolo por uno diferente
     * 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black"
     */
    public void changeColor(String color)
    {
        this.actualColor=color;
        symbol.changeColor(color);
    }
    /**
     *changeColor es un metodo que cambia el color del simbolo por uno diferente
     * 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black"
     * @return
     */
    public String getColor()
    {
        return actualColor;
    }
    
}
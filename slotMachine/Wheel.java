
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
     * Crea una rueda
     */
    public Wheel(int xpos,int ypos)
    {
        symbols=new ArrayList<>();
        isVisible =false;
        this.xPosition=xpos;
        this.yPosition=ypos;
    }

    /**
     * Añade un symbolo a la rueda
     */
    public void addSymbols(String color)
    {
        Symbol symbol=new Symbol(color,xPosition,yPosition);
        symbols.add(symbol);
    }
    /**
     * Elimina el primer symbolo que encuentre con un determinado color
     */
    public void delSymbols(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                symbols.remove(i);
                return;
            }
        }
    }
    /**
     * Ubica en la rueda el simbolo del color indicado
     */
    public void placeSymbols(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            Symbol symbol=symbols.get(i);
            symbol.makeInVisibleS();
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                symbol=symbols.get(i);
                symbol.makeVisibleS();
                return;
            }
        }
    }
    /**
     * Hace girar a la rueda
     */
    public void spin()
    {
        if (symbols.isEmpty()){
            return;
        }
        int newIndex = (int) (Math.random() * symbols.size());
        String findcolor=symbols.get(newIndex).getColor();
        placeSymbols(findcolor);
        }
    public String[] getSymbols()
    {
        String[] colors = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            colors[i] = symbols.get(i).getColor();
        }
        return colors;
    }
    public String getVisibleSymbols()
    {
        String color;
        for (int i = 0; i < symbols.size(); i++)
        {
            Symbol symbol=symbols.get(i);
            boolean visible=symbol.getvisibility();
            if (visible = true){
                return color=symbols.get(i).getColor();
            }
        }
        return "None";
    }
}
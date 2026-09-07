import java.util.ArrayList;

/**
 * Wheel representa una rueda de la maquina tragamonedas. Una rueda
 * puede tener varios simbolos, pero solo muestra uno a la vez.
 *
 * @author Tomas Arevalo - Jose Parra
 * @version 1.0
 */
public class Wheel
{
    private int xPosition;
    private int yPosition;
    private ArrayList<Symbol> symbols;
    private int currentIndex;
    private boolean visible;

    /**
     * Crea una rueda vacia, ubicada en la posicion (xPos, yPos) del canvas.
     */
    public Wheel(int xPos, int yPos)
    {
        xPosition = xPos;
        yPosition = yPos;
        symbols = new ArrayList<>();
        currentIndex = -1;
        visible = true;
    }

    /**
     * Adiciona un simbolo del color dado a la rueda. Si es el primer
     * simbolo de la rueda, queda ubicado (visible) automaticamente.
     *
     * @param color color del nuevo simbolo
     */
    public void addSymbol(String color)
    {
        symbols.add(new Symbol(color, xPosition, yPosition));
        if (currentIndex == -1) {
            placeSymbol(color);
        }
    }

    /**
     * Elimina el primer simbolo que tenga el color indicado.
     *
     * @param color color del simbolo a eliminar
     */
    public void delSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                symbols.get(i).hide();
                symbols.remove(i);
                if (i == currentIndex) {
                    currentIndex = -1;
                }
                return;
            }
        }
    }

    /**
     * Ubica en la rueda el simbolo del color indicado, ocultando el
     * que estaba visible anteriormente.
     *
     * @param color color del simbolo a ubicar
     */
    public void placeSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                hideCurrent();
                currentIndex = i;
                if (visible) {
                    symbols.get(i).show();
                }
                return;
            }
        }
    }

    /**
     * Hace girar la rueda, ubicando al azar uno de sus simbolos.
     */
    public void spin()
    {
        if (symbols.isEmpty()) {
            return;
        }
        int randomIndex = (int) (Math.random() * symbols.size());
        placeSymbol(symbols.get(randomIndex).getColor());
    }

    /**
     * Devuelve los colores de los simbolos de la rueda, en el orden
     * en que fueron adicionados.
     *
     * @return arreglo con los colores de los simbolos
     */
    public String[] getSymbols()
    {
        String[] colors = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            colors[i] = symbols.get(i).getColor();
        }
        return colors;
    }

    /**
     * Devuelve el color del simbolo actualmente ubicado en la rueda.
     *
     * @return color visible, o "none" si no hay ninguno ubicado
     */
    public String getVisibleSymbol()
    {
        if (currentIndex == -1) {
            return "none";
        }
        return symbols.get(currentIndex).getColor();
    }

    /**
     * Hace visible el simbolo actualmente ubicado en la rueda.
     */
    public void makeVisible()
    {
        visible = true;
        if (currentIndex != -1) {
            symbols.get(currentIndex).show();
        }
    }

    /**
     * Oculta el simbolo actualmente ubicado en la rueda.
     */
    public void makeInvisible()
    {
        visible = false;
        hideCurrent();
    }

    /**
     * Oculta el simbolo que esta actualmente visible, si existe.
     */
    private void hideCurrent()
    {
        if (currentIndex != -1) {
            symbols.get(currentIndex).hide();
        }
    }
}

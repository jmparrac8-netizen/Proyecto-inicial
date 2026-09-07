/**
 * Symbol representa un simbolo de la maquina: un circulo de un color
 * determinado, ubicado en una posicion fija del canvas. Un Symbol
 * reutiliza la clase Circle del paquete shapes para dibujarse.
 *
 * @author Tomas Arevalo - Jose Parra
 * @version 1.0
 */
public class Symbol
{
    private Circle circle;
    private String color;

    /**
     * Crea un simbolo del color dado, ubicado en la posicion (x, y)
     * del canvas.
     *
     * @param color color del simbolo (nombre de color CSS)
     * @param x posicion horizontal en el canvas
     * @param y posicion vertical en el canvas
     */
    public Symbol(String color, int x, int y)
    {
        this.color = color;
        circle = new Circle();
        circle.changeColor(color);
        // Circle nace en la posicion (20, 15): se mueve a (x, y).
        circle.moveHorizontal(x - 20);
        circle.moveVertical(y - 15);
    }

    /**
     * Devuelve el color del simbolo.
     *
     * @return el color del simbolo
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Muestra el simbolo en el canvas.
     */
    public void show()
    {
        circle.makeVisible();
    }

    /**
     * Oculta el simbolo del canvas.
     */
    public void hide()
    {
        circle.makeInvisible();
    }
}

PROYECTO: slotMachine
AUTORES: Arevalo-Parra
CURSO: Desarrollo Orientado por Objetos (DOPO-POOB) - Proyecto inicial 2026-2

Simulador de una maquina tragamonedas inspirado en el Problem I
de la maraton 2025 (Slot Machine). Ciclos 1 a 4.

CLASES PROPIAS
  SlotMachine        : clase principal. Maneja las ruedas y responde ok().
  Wheel              : rueda (abstracta). Guarda varios simbolos y muestra uno a la vez.
    NormalWheel      : la rueda de siempre.
    LeftyWheel       : al girar copia a la rueda de su izquierda.
    RebelWheel       : no se deja fijar, intercambiar ni eliminar.
    RightyWheel      : tipo nuevo. Al girar copia a la rueda de su derecha.
  Symbol             : simbolo (abstracto) con un color.
    NormalSymbol     : circulo.
    EphemeralSymbol  : triangulo que se achica en cada giro hasta ser un punto.
    ShySymbol        : cuadrado que alterna visible / invisible al ser seleccionado.
  SlotMachineContest : resuelve el problema de la maraton (solve y simulate).

CLASES REUTILIZADAS DEL PROYECTO shapes (sin modificar)
  Canvas, Circle, Rectangle, Triangle

COMO SE VE
  - Cada rueda es un cuadro negro (Rectangle) con un circulo de color
    adentro (Circle): el simbolo que esta ubicado en ese momento.
  - Cuando las ruedas coinciden, aparece un triangulo magenta (Triangle)
    encima de cada rueda: asi la maquina luce distinta al ganar.
  - Si una accion no se puede realizar se muestra un JOptionPane,
    solo cuando el simulador esta visible.

COLORES VALIDOS
  red, blue, green, yellow, magenta, black
  (son los que el Canvas de shapes sabe pintar)
  Por eso SlotMachine(n) acepta n entre 2 y 6.

CICLO 3
  SlotMachine(n)                crea n ruedas con los mismos n simbolos en el
                                mismo orden, cada una girada al azar. Nace
                                invisible y nunca en premio.
  SlotMachineContest.solve(n)   devuelve las acciones {rueda, pasos} que ganan.
                                Trabaja siempre con la maquina invisible.
  SlotMachineContest.simulate(n) resuelve y despues muestra la solucion.
  Lo unico que consulta el solver es distinctSymbols(); nunca lee un color.

CICLO 4
  addWheel(type, pos)           tipos: normal, lefty, rebel, righty.
  addSymbol(type, pos, color)   tipos: normal, ephemeral, shy.
  addWheel(pos) y addSymbol(pos, color) siguen creando elementos normales.
  Un tipo que no existe deja ok() en false.
  Como se distinguen (usabilidad):
    ruedas   : barra de color bajo el cuadro. normal negra, lefty azul,
               rebel roja, righty verde.
    simbolos : forma. normal circulo, ephemeral triangulo, shy cuadrado.
  Un shy invisible sigue contando con su color en configuration() e isJackpot().
  El solver trabaja solo con ruedas y simbolos normales.

PRUEBAS
  SlotMachineC2Test       : pruebas de unidad del ciclo 2 (modo invisible).
  SlotMachineCC2Test      : pruebas compartidas del ciclo 2.
  SlotMachineContestTest  : pruebas de unidad del ciclo 3.
  SlotMachineContestCTest : pruebas compartidas del ciclo 3.
  SlotMachineC4Test       : pruebas de unidad del ciclo 4 (tipos de ruedas y simbolos).
  SlotMachineCC4Test      : pruebas compartidas del ciclo 4.

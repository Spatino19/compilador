package tal;

import java.io.*;
import static tal.Token.Type.*;

/**
 * Analizador léxico implementado mediante un
 * autómata finito determinista.
 * <p>Cada estado del autómata se implementa con un objeto Runnable.
 */
public class AFD extends ALex
{
/**
 * Construye el autómata.
 * @param fichero Fichero de texto que se debe analizar.
 * @throws IOException
 */
public AFD(String fichero) throws IOException
{
    super(fichero);
    setStart(this::inicio);
}

private void inicio()
{
    if(isDigitChar())
        state(this::intval);
    else if (isChar('e'))
        state(this::entero);
    else if(isSpaceChar())
        restart();
    else if(isEofChar())
        token(EOF);
    else
        error();
}

private void id() {
    if (isIdChar())
        state(this::id);
    else
        token(ID);
}


private void entero() {
    if(isChar('n'))
        state(this::entero2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void entero2() {
    if(isChar('t'))
        state(this::entero3);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void entero3() {
    if(isChar('e'))
        state(this::entero4);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void entero4() {
    if(isChar('r'))
        state(this::entero5);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void entero5() {
    if(isChar('o'))
        state(this::entero6);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void entero6() {
    if(isIdChar())
        state(this::id);
    else
        token(ENTERO);
}

private void intval()
{
    if(isDigitChar())
        state(this::intval);
    else if(isIdChar())
        error();
    else
        token(INTVAL);
}

} // AFD

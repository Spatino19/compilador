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
        state(() -> PalabraEntero.e(this));
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

private static class PalabraEntero {
    static void e(AFD afd) {
        if (afd.isChar('n'))
            afd.state(() -> n(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    static void n(AFD afd) {
        if (afd.isChar('t'))
            afd.state(() -> t(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    static void t(AFD afd) {
        if (afd.isChar('e'))
            afd.state(() -> e2(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    static void e2(AFD afd) {
        if (afd.isChar('r'))
            afd.state(() -> r(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    static void r(AFD afd) {
        if (afd.isChar('o'))
            afd.state(() -> fin(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    static void fin(AFD afd) {
        if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ENTERO);
    }
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

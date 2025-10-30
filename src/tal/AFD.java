package tal;

import java.io.*;

import tal.palabras.*;

import static tal.Token.Type.*;

/**
 * Analizador léxico implementado mediante un
 * autómata finito determinista.
 * <p>
 * Cada estado del autómata se implementa con un objeto Runnable.
 */
public class AFD extends ALex {
    /**
     * Construye el autómata.
     * 
     * @param fichero Fichero de texto que se debe analizar.
     * @throws IOException
     */
    public AFD(String fichero) throws IOException {
        super(fichero);
        setStart(this::inicio);
    }

    private void inicio() {
        if (isDigitChar())
            state(this::intval);
        else if (isChar('e'))
            state(() -> Entero.e(this));
        else if (isChar('c'))
            state(() -> Cadena.c(this));
        else if (isChar('s'))
            state(() -> Si.s(this));
        else if (isChar('m'))
            state(() -> Mientras.m(this));
        else if (isChar('f'))
            state(() -> Fin.f(this));
        else if (isChar('i'))
            state(() -> Imprimir.i(this));

        else if (isSpaceChar())
            restart();
        else if (isChar('"'))
            stateNoChar(this::strVal);
        else if (isEofChar())
            token(EOF);
        else
            error();
    }

    public void id() {
        if (isIdChar())
            state(this::id);
        else
            token(ID);
    }

    private void intval() {
        if (isDigitChar())
            state(this::intval);
        else if (isIdChar())
            error();
        else
            token(INTVAL);
    }

    private void strVal() {
        if (isChar('"'))
            stateNoChar(() -> token(STRVAL));
        else if (isEofChar())
            error();
        else
            state(this::strVal);
    }

} // AFD

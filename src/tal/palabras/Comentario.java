package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Comentario {
    
    public static void diagonal(AFD afd) {
        if (afd.isChar('/'))
            afd.stateNoChar(() -> linea(afd));
        else if (afd.isChar('*'))
            afd.stateNoChar(() -> bloque(afd));
        else
            afd.token(MUL);
    }

    private static void linea(AFD afd) {
        if (afd.isChar('\n'))
            afd.restart();
        else if (afd.isEofChar())
            afd.token(EOF);
        else
            afd.stateNoChar(() -> linea(afd));
    }

    private static void bloque(AFD afd) {
        if (afd.isChar('*'))
            afd.stateNoChar(() -> finBloque(afd));
        else if (afd.isEofChar())
            afd.error();
        else
            afd.stateNoChar(() -> bloque(afd));
    }

    private static void finBloque(AFD afd) {
        if (afd.isChar('/'))
            afd.stateNoChar(afd::restart);
        else if (afd.isChar('*'))
            afd.stateNoChar(() -> finBloque(afd));
        else
            afd.stateNoChar(() -> bloque(afd));
    }
}

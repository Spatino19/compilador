package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Imprimir {
    public static void i(AFD afd) {
        if (afd.isChar('m'))
            afd.state(() -> m(afd));
        else if (afd.isChar('t'))
            afd.state(() -> Iterar.t(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void m(AFD afd) {
        if (afd.isChar('p'))
            afd.state(() -> p(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void p(AFD afd) {
        if (afd.isChar('r'))
            afd.state(() -> r(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void r(AFD afd) {
        if (afd.isChar('i'))
            afd.state(() -> i2(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void i2(AFD afd) {
        if (afd.isChar('m'))
            afd.state(() -> m2(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void m2(AFD afd) {
        if (afd.isChar('i'))
            afd.state(() -> i3(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void i3(AFD afd) {
        if (afd.isChar('r'))
            afd.state(() -> fin(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void fin(AFD afd) {
        if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(IMPRIMIR);
    }
}

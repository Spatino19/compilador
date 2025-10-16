package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Entero {
    public static void e(AFD afd) {
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
        else if (afd.isChar('o'))
            afd.state(() -> Entonces.o(afd));
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

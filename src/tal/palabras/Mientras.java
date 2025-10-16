package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Mientras {
    public static void m(AFD afd) {
        if (afd.isChar('i'))
            afd.state(() -> i(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void i(AFD afd) {
        if (afd.isChar('e'))
            afd.state(() -> e(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void e(AFD afd) {
        if (afd.isChar('n'))
            afd.state(() -> n(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void n(AFD afd) {
        if (afd.isChar('t'))
            afd.state(() -> t(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void t(AFD afd) {
        if (afd.isChar('r'))
            afd.state(() -> r(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void r(AFD afd) {
        if (afd.isChar('a'))
            afd.state(() -> a(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void a(AFD afd) {
        if (afd.isChar('s'))
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
            afd.token(MIENTRAS);
    }
}

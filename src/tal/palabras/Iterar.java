package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Iterar {
    public static void i(AFD afd) {
        if (afd.isChar('t'))
            afd.state(() -> t(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    public static void t(AFD afd) {
        if (afd.isChar('e'))
            afd.state(() -> e(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void e(AFD afd) {
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
            afd.token(ITERAR);
    }
}

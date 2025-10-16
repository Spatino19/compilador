package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Cadena {
    public static void c(AFD afd) {
        if (afd.isChar('a'))
            afd.state(() -> a(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void a(AFD afd) {
        if (afd.isChar('d'))
            afd.state(() -> d(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void d(AFD afd) {
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
        if (afd.isChar('a'))
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
            afd.token(CADENA);
    }
}

package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Fin {
    public static void f(AFD afd) {
        if (afd.isChar('i'))
            afd.state(() -> i(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    private static void i(AFD afd) {
        if (afd.isChar('n'))
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
            afd.token(FIN);
    }
}

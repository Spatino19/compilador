package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Sino {

    public static void s(AFD afd) {
        if (afd.isChar('i'))
            afd.state(() -> i(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }
    private static void i(AFD afd) {
        if (afd.isChar('n'))
            afd.state(() -> n(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(SI);
    }

    static void n(AFD afd) {
        if (afd.isChar('o'))
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
            afd.token(SINO);
    }
}

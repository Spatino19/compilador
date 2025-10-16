package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Si {
    public static void s(AFD afd) {
        if (afd.isChar('i'))
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
            afd.token(SI);
    }
}
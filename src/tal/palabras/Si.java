package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public final class Si {
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
            afd.state(() -> Sino.n(afd));
         else
        if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(SI);
    }
}
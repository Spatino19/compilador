package tal.palabras;

import static tal.Token.Type.*;
import tal.AFD;

public final class Entonces {
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
        if (afd.isChar('o'))
            afd.state(() -> o(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    static void o(AFD afd) {
        if (afd.isChar('n'))
            afd.state(() -> n2(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    static void n2(AFD afd) {
        if (afd.isChar('c'))
            afd.state(() -> c(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    static void c(AFD afd) {
        if (afd.isChar('e'))
            afd.state(() -> e2(afd));
        else if (afd.isIdChar())
            afd.state(afd::id);
        else
            afd.token(ID);
    }

    static void e2(AFD afd) {
        if (afd.isChar('s'))
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
            afd.token(ENTONCES);
    }
    
}

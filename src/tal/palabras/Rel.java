package tal.palabras;

import static tal.Token.Type.*;

import tal.AFD;

public  final class Rel {
    
    public static void igual(AFD afd) {
        if (afd.isChar('='))
            afd.state(() -> afd.token(REL));
        else
            Asign.asign(afd);
    }

    public static void menor(AFD afd) {
        if (afd.isChar('>'))
            afd.state(() -> afd.token(REL));
        else if (afd.isChar('='))
            afd.state(() -> afd.token(REL));
        else
            afd.token(REL);
    }

    public static void mayor(AFD afd) {
        if (afd.isChar('='))
            afd.state(() -> afd.token(REL));
        else
            afd.token(REL);
    }
    
}

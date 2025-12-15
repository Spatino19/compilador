package tal;

import static tal.Token.Type.*;

/**
 * Analizador sintáctico implementado mediante el método
 * descendente recursivo.
 * <p>En esta clase se debe implementar la gramática del lenguaje.
 */
public class ADR extends ASin {
    /**
     * Construye un analizador descendente recursivo.
     * @param lex Analizador léxico.
     */
    public ADR(ALex lex) {
        super(lex);
    }

/**
 * Símbolo inicial de la gramática.
 */
public void programa()
{
    declaracion();
    bloque();
    tokenRead(EOF);
}


    private void declaracion() {
    switch(tokenType()) {
        case CADENA:
            tokenRead(CADENA);
            tokenRead(ID);
            declaracion();
            break;

        case ENTERO:
            tokenRead(ENTERO);
            tokenRead(ID);
            declaracion();
            break;
        default:
            break;
    }
}

    private void bloque() {
        while (true) {
            switch (tokenType()) {
                case ID:
                    asignacion();
                    break;
                case IMPRIMIR:
                    impresion();
                    break;
                case SI:
                    condicion();
                    break;
                case MIENTRAS:
                    iteracion();
                    break;
                default:
                    return;
            }
        }
    }

    private void asignacion() {
        tokenRead(ID);
        tokenRead(ASIGN);
        expresion();
    }

    private void impresion() {
        tokenRead(IMPRIMIR);
        expresion();
    }


    private void condicion() {
        tokenRead(SI);
        expresion();
        tokenRead(ENTONCES);
        bloque();
        sino();
        tokenRead(FIN);
    }


    private void sino() {
        if (tokenType() == SINO) {
            tokenRead(SINO);
            bloque();
        }
    }

    /**
     * iteración → MIENTRAS expresión ITERAR bloque FIN
     */
    private void iteracion() {
        tokenRead(MIENTRAS);
        expresion();
        tokenRead(ITERAR);
        bloque();
        tokenRead(FIN);
    }


    private void expresion() {
        orExpr();
    }

    /**
     * orExpr -> andExpr orTail
     * orTail -> OR andExpr orTail | ε
     * Implementación recursiva (no iterativa) para orTail.
     */
    private void orExpr() {
        andExpr();
        orTail();
    }

    private void orTail() {
        if (tokenType() == OR) {
            tokenRead(OR);
            andExpr();
            orTail();
        }
        // ε -> nada más que hacer
    }


    private void andExpr() {
        relacional();
        while (tokenType() == AND) {
            tokenRead(AND);
            relacional();
        }
    }

    private void relacional() {
        aditiva();
        while (tokenType() == REL) {
            tokenRead(REL);
            aditiva();
        }
    }

    private void aditiva() {
        multiplicativa();
        while (tokenType() == SUM) {
            tokenRead(SUM);
            multiplicativa();
        }
    }


    private void multiplicativa() {
        unaria();
        while (tokenType() == MUL) {
            tokenRead(MUL);
            unaria();
        }
    }

    private void unaria() {
        while (tokenType() == NEG) {
            tokenRead(NEG);
        }
        primaria();
    }


    private void primaria() {
        switch (tokenType()) {
            case ID:
                tokenRead(ID);
                break;
            case INTVAL:
                tokenRead(INTVAL);
                break;
            case STRVAL:
                tokenRead(STRVAL);
                break;
            case IPAR:
                tokenRead(IPAR);
                expresion();
                tokenRead(DPAR);
                break;
            default:
                throw new RuntimeException(
                    "Error (" + tokenName());
        }
    }
} // ADR

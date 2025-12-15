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
    public void programa() {
        declaracion();
        bloque();
        tokenRead(EOF);
    }

    private void declaracion() {
        switch (tokenType()) {
            case CADENA:
                tokenRead(CADENA);
                tokenRead(ID);
                codeVariableString();
                declaracion();
                break;
            case ENTERO:
                tokenRead(ENTERO);
                tokenRead(ID);
                codeVariableInteger();
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
        codeVariableAssignment();
        tokenRead(ASIGN);
        expresion();
        codeAssignment();
    }

    private void impresion() {
        tokenRead(IMPRIMIR);
        expresion();
        codePrint();
    }

    private void condicion() {
        tokenRead(SI);
        expresion();
        codeIf();
        tokenRead(ENTONCES);
        bloque();
        sino();
        codeEnd();
        tokenRead(FIN);
    }

    private void sino() {
        if (tokenType() == SINO) {
            codeElse();
            tokenRead(SINO);
            bloque();
        }
    }

    /**
     * iteración → MIENTRAS expresión ITERAR bloque FIN
     */
    private void iteracion() {
        tokenRead(MIENTRAS);
        codeWhile();
        expresion();
        codeIf();
        tokenRead(ITERAR);
        bloque();
        codeEnd();
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
            codeOperator("||");
            orTail();
        }
    }

    private void andExpr() {
        relacional();
        while (tokenType() == AND) {
            tokenRead(AND);
            relacional();
            codeOperator("&&");
        }
    }

    private void relacional() {
        aditiva();
        while (tokenType() == REL) {
            String op = tokenName();
            tokenRead(REL);
            aditiva();
            codeOperator(op);
        }
    }

    private void aditiva() {
        multiplicativa();
        while (tokenType() == SUM) {
            String op = tokenName();
            tokenRead(SUM);
            multiplicativa();
            codeOperator(op);
        }
    }

    private void multiplicativa() {
        unaria();
        while (tokenType() == MUL) {
            String op = tokenName();
            tokenRead(MUL);
            unaria();
            codeOperator(op);
        }
    }

    private void unaria() {
        if (tokenType() == NEG) {
            tokenRead(NEG);
            unaria();
            codeOperator("!");
        } else {
            primaria();
        }
    }

    private void primaria() {
        switch (tokenType()) {
            case ID:
                tokenRead(ID);
                codeVariableExpression();
                break;
            case INTVAL:
                tokenRead(INTVAL);
                codeInteger();
                break;
            case STRVAL:
                tokenRead(STRVAL);
                codeString();
                break;
            case IPAR:
                tokenRead(IPAR);
                expresion();
                tokenRead(DPAR);
                break;
            default:
                throw new RuntimeException("Error (" + tokenName() + "): Expresión esperada");
        }
    }
} // ADR

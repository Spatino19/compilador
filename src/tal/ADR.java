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

    /**
     * declaracion -> (CADENA|ENTERO) ID declaracion | λ
     */
    private void declaracion() {
        switch (tokenType()) {
            case CADENA:
                tokenRead(CADENA);
                tokenRead(ID);
                codeVariableString(); // reserva var string
                declaracion();        // sigue buscando más
                break;
            case ENTERO:
                tokenRead(ENTERO);
                tokenRead(ID);
                codeVariableInteger(); // reserva var int
                declaracion();         // sigue buscando más
                break;
            default:
                break; // ε
        }
    }

    /**
     * bloque -> { asignacion | impresion | condicion | iteracion | λ}*
     */
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
                    return; // fin del bloque (λ)
            }
        }
    }

    /**
     * asignacion -> ID = expresion
     */
    private void asignacion() {
        tokenRead(ID);
        codeVariableAssignment(); // apilar destino
        tokenRead(ASIGN);
        expresion();
        codeAssignment();         // generar "="
    }

    /**
     * impresion -> IMPRIMIR expresion
     */
    private void impresion() {
        tokenRead(IMPRIMIR);
        expresion();
        codePrint();
    }

    /**
     * condicion -> SI expresion ENTONCES bloque (sino)? FIN
     */
    private void condicion() {
        tokenRead(SI);
        expresion();
        codeIf();          // salto si falso
        tokenRead(ENTONCES);
        bloque();          // rama then
        sino();            // rama else opcional
        codeEnd();         // cierre if/else
        tokenRead(FIN);
    }

    /**
     * sino -> SINO bloque | ε
     */
    private void sino() {
        if (tokenType() == SINO) {
            codeElse();    // salto al else
            tokenRead(SINO);
            bloque();
        }
    }

    /**
     * iteración → MIENTRAS expresión ITERAR bloque FIN
     */
    private void iteracion() {
        tokenRead(MIENTRAS);
        codeWhile();   // marca inicio del while
        expresion();
        codeIf();      // salto si falso
        tokenRead(ITERAR);
        bloque();      // cuerpo
        codeEnd();     // salto de iteración / fin while
        tokenRead(FIN);
    }

    /**
     * expresion (nivel más alto)
     */
    private void expresion() {
        orExpr();
    }

    /**
     * orExpr -> andExpr orTail
     */
    private void orExpr() {
        andExpr();
        orTail();
    }

    /**
     * orTail -> (|| andExpr)*
     */
    private void orTail() {
        if (tokenType() == OR) {
            tokenRead(OR);
            andExpr();
            codeOperator("||");
            orTail(); // recursivo para más ||
        }
    }

    /**
     * andExpr -> relacional (&& relacional)*
     */
    private void andExpr() {
        relacional();
        while (tokenType() == AND) {
            tokenRead(AND);
            relacional();
            codeOperator("&&");
        }
    }

    /**
     * relacional -> aditiva (REL aditiva)*
     */
    private void relacional() {
        aditiva();
        while (tokenType() == REL) {
            String op = tokenName(); // guarda ==, <>, <, >, <=, >=
            tokenRead(REL);
            aditiva();
            codeOperator(op);
        }
    }

    /**
     * aditiva -> multiplicativa ((+|-) multiplicativa)*
     */
    private void aditiva() {
        multiplicativa();
        while (tokenType() == SUM) {
            String op = tokenName(); // + o -
            tokenRead(SUM);
            multiplicativa();
            codeOperator(op);
        }
    }

    /**
     * multiplicativa -> unaria ((*|/) unaria)*
     */
    private void multiplicativa() {
        unaria();
        while (tokenType() == MUL) {
            String op = tokenName(); // * o /
            tokenRead(MUL);
            unaria();
            codeOperator(op);
        }
    }

    /**
     * unaria -> ! unaria | primaria
     */
    private void unaria() {
        if (tokenType() == NEG) {
            tokenRead(NEG);
            unaria();
            codeOperator("!");
        } else {
            primaria();
        }
    }

    /**
     * primaria -> ID | INTVAL | STRVAL | ( expresion )
     */
    private void primaria() {
        switch (tokenType()) {
            case ID:
                tokenRead(ID);
                codeVariableExpression(); // carga var
                break;
            case INTVAL:
                tokenRead(INTVAL);
                codeInteger();            // push entero
                break;
            case STRVAL:
                tokenRead(STRVAL);
                codeString();             // push cadena
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

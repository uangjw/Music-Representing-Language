import java.io.*;
import java_cup.runtime.*;
import exceptions.*;

%%
%class Scanner
%public
%cup
%unicode
%line
%column
%yylexthrow LexicalException
%type java_cup.runtime.Symbol
%eofval{
	return symbol(Symbols.EOF);
%eofval}

%{
    StringBuffer string = new StringBuffer();
    int get_line(){
        return yyline;
    }
    int get_column(){
        return yycolumn;
    }
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

Comment = "//" {InputCharacter}* {LineTerminator}?

//String   = "\"" [^\"] ~"\"" | "\"" + "\""

Identifier = [:lowercase:] [[:letter:]||[:digit:]]*

Integer = 0 | [1-9][0-9]*

NoteName = "C"|"D"|"E"|"F"|"G"|"A"|"B"

//IntConfig = "BPM" | "INSTRUMENT" | "CUR_OCTAVE"

//Punctuation = ";" | "|" | "=" | ":" | "+" | "-" | "#" | "{" | "}"


%%

<YYINITIAL>
{
    {WhiteSpace}    { /* ignore */ }
    {Comment}       { /* ignore */ }

    "MUSIC"         { return symbol(Symbols.MUSIC); }
    "BPM"           { return symbol(Symbols.BPM); }
    "INSTRUMENT"    { return symbol(Symbols.INSTR); }
    "CUR_OCTAVE"    { return symbol(Symbols.CUROC); }
    "VELOCITY"      { return symbol(Symbols.VELOC); }
    "TEMPO"         { return symbol(Symbols.TEMPO); }
    "SET_OCTAVE"    { return symbol(Symbols.SETOCT); }
    "SEGMENT"       { return symbol(Symbols.SEGMENT); }

    "int"           { return symbol(Symbols.INT); }
    "if"            { return symbol(Symbols.IF); }
    "else"          { return symbol(Symbols.ELSE); }

    ";"             { return symbol(Symbols.SEMI); }
    "|"             { return symbol(Symbols.BAR); }
    "="             { return symbol(Symbols.EQUAL); }
    ":"             { return symbol(Symbols.COLON); }
    "+"             { return symbol(Symbols.PLUS); }
    "-"             { return symbol(Symbols.MINUS); }
    "*"             { return symbol(Symbols.TIMES); }
    "/"             { return symbol(Symbols.DIVIDE); }
    "=="            { return symbol(Symbols.EQUIV); }
    ">"             { return symbol(Symbols.GREAT); }
    "<"             { return symbol(Symbols.LESS); }
    ">="            { return symbol(Symbols.GEQ); }
    "<="            { return symbol(Symbols.LEQ); }
    "!="            { return symbol(Symbols.NEQ); }
    "#"             { return symbol(Symbols.SHARP); }
    "{"             { return symbol(Symbols.LCURLY); }
    "}"             { return symbol(Symbols.RCURLY); }
    "."             { return symbol(Symbols.DOT); }
    "%"             { return symbol(Symbols.PERCENT); }
    "("             { return symbol(Symbols.LPAREN); }
    ")"             { return symbol(Symbols.RPAREN); }

    {Integer}       { 
                        if (yylength() > 12) {
                            throw new LexicalException(yyline, yycolumn, "Integer");
                        }
                        else {
                            return symbol(Symbols.INTEGER, yytext());
                        }
                    }

    {NoteName}      { return symbol(Symbols.NOTENAME, yytext()); }

    {Identifier}    { 
                        if (yylength() > 24) {
                            throw new LexicalException(yyline, yycolumn, "Identifier");
                        }
                        else {
                            return symbol(Symbols.IDENTIFIER, yytext());
                        }
                    }

}



/* error fallback */
[^]                              { throw new LexicalException(yyline, yycolumn, yytext()); }
grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';




//--- PARSER: ---
//stylesheet: EOF;
stylesheet: object+;
selector: LOWER_IDENT | CLASS_IDENT | ID_IDENT;
object: selector OPEN_BRACE decleration+ CLOSE_BRACE;

key: LOWER_IDENT COLON;
value: pixelLiteral | colorLiteral;
decleration: key value SEMICOLON;

pixelLiteral: PIXELSIZE;
colorLiteral: COLOR;


//stylesheet: object+;
//object: selector OPEN_BRACE decleration+ CLOSE_BRACE;
//selector: LOWER_IDENT | CLASS_IDENT | ID_IDENT;
//
//decleration: key value SEMICOLON;
//key: LOWER_IDENT COLON;
//value: PIXELSIZE | COLOR;





//grammar ICSS;
//
//properties: property+;
//property: ID '=' value;
//value:  INT | STRING;
//
//ID : [a-zA-Z]+ ;
//STRING : '"' .*? '"';
//INT : [0-9]+ ;
//WS : [ \t\r\n]+ -> skip ;



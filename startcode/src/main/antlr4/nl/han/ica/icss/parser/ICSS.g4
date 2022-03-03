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
stylesheet: variable* object*;
selector: LOWER_IDENT | CLASS_IDENT | ID_IDENT;
object: selector OPEN_BRACE decleration+ CLOSE_BRACE;

key: LOWER_IDENT COLON;
value: pixelLiteral | colorLiteral | booleanLiteral | variableName;
decleration: key value SEMICOLON;

//variableLiteral: CAPITAL_IDENT;
booleanLiteral: TRUE | FALSE;
pixelLiteral: PIXELSIZE;
colorLiteral: COLOR;

variableName: CAPITAL_IDENT;
variableValue: value;
variable: variableName ASSIGNMENT_OPERATOR variableValue SEMICOLON;


//LinkColor := #ff0000;
//ParWidth := 500px;
//AdjustColor := TRUE;
//UseLinkColor := FALSE;
//
//p {
//	background-color: #ffffff;
//	width: ParWidth;
//}
//
//a {
//	color: LinkColor;
//}
//
//#menu {
//	width: 520px;
//}
//
//.menu {
//	color: #000000;
//}




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



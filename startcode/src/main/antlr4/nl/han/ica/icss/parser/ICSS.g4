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
object: selector OPEN_BRACE objectItem* CLOSE_BRACE;

objectItem: decleration | ifStatement | variable; // | ifElseStatement ;
key: LOWER_IDENT COLON;
value: pixelLiteral | colorLiteral | booleanLiteral | variableName | scalarLiteral | percentageLiteral;
decleration: key (value | expression) SEMICOLON;

//ifElseStatement: ifStatement elseStatement; // https://theantlrguy.atlassian.net/wiki/spaces/ANTLR3/pages/2687036/ANTLR+Cheat+Sheet
ifStatement: IF BOX_BRACKET_OPEN (variableName | booleanLiteral) BOX_BRACKET_CLOSE OPEN_BRACE objectItem* CLOSE_BRACE elseStatement?;
elseStatement: ELSE OPEN_BRACE objectItem* CLOSE_BRACE;

//variableLiteral: CAPITAL_IDENT;
booleanLiteral: TRUE | FALSE;
pixelLiteral: PIXELSIZE;
colorLiteral: COLOR;
scalarLiteral: SCALAR;
percentageLiteral: PERCENTAGE;


variableName: CAPITAL_IDENT;
//variableValue: value;
variable: variableName ASSIGNMENT_OPERATOR expression SEMICOLON;

multiplyOperation: MUL;
addOperation: PLUS;
subtractOperation: MIN;
expression: value | expression multiplyOperation expression | expression (addOperation | subtractOperation) expression; //doet multiply eerst // (value operation value) | (operation value);


//ParWidth := 10px;
//a {
//    width: ParWidth + 2 * 10px;
//}










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



; ************************************************************************************************* ;
; Program Name:		HexBinDec Converter (HexBinDec Converter.asm) 													;
; Program Description: 	This program is an integer Hex/Binary/Decimal converter. There is a repeat ;
;                        option.														;
; Author:				Adam Stancil													;
; Course/Section/Title:	CSC2025 Assembly/Architecture										;
; Creation Date:		4/11/25														;
; Date Last Modified:	4/13/25														;
; ************************************************************************************************* ;

.386
.model flat,stdcall
.stack 4096
ExitProcess PROTO, dwExitCode:DWORD

INCLUDE Irvine32.inc
INCLUDELIB Irvine32.lib
.data

binMsg BYTE "Binary",0													; binary string
decMsg BYTE "Decimal",0													; decimal string
hexMsg BYTE "Hexadecimal",0												; hexadecimal string
valMsg BYTE " Value: ",0												     ; value string
exitMsg BYTE "Exit",0	     											; exit string

titleMsg BYTE "--- Integer Converter ---",0					                    ; title string
menuMsg BYTE "Please choose one of the menu options: ",0					     ; menu string
errorMsg BYTE "Invalid Entry. Please retry...",0            				     ; error string
entryMsg1 BYTE "Please enter a 32-bit ",0	               				     ; 1st entry string
entryMsg2 BYTE " integer: ",0	               				                    ; 2nd entry string
resultMsg BYTE "Conversion Results: ",0					                         ; results string

intVal SDWORD (0)                                                                    ; 32-bit signed integer value

binVal BYTE 32 DUP(?)                                                                ; user input string
byteCount DWORD ?                                                                    ; holds counter

menuAnswer BYTE ?,0                                                                  ; menu choice  

; ************************************************************************************************* ;
; Functional description of the main program												;
;	Inputs -	a 32-bit integer in binary, decimal, or hex format							;
;	Outputs -	*   																	;
;	Registers used / purpose of each													;
;	     eax, ebx - input, output, math / ecx - string input / edx - output   	                    ;
;	Functional details - This program is an integer Hex/Binary/Decimal converter. There is a repeat;
;                         option.                                                                   ;
; ************************************************************************************************* ;

.code
main PROC

L1:

     mov  intVal,0                                     ; clear integer value
     
     call Clrscr                                       ; clear the screen

     call ShowMenu                                     ; print the main menu
     call ReadChar                                     ; get user choice
     call WriteChar                                    ; echo user choice
     mov  menuAnswer,al                                ; store user choice
     call Crlf                                         ; line feed

     cmp  menuAnswer,49                                ; compare user choice to menu options
     jl   E1                                           ; jump if not valid entry
     
     cmp  menuAnswer,52                                ; compare user choice to menu options
     jg   E1                                           ; jump if not valid entry

     jmp  E2                                           ; jump past menu choice error loop
     
E1:

     mov  edx,OFFSET errorMsg                          ; load prompt into register
     call WriteString                                  ; write to screen
     
     call ReadChar                                     ; wait for user input
     call Crlf                                         ; line feed
     
     jmp  L1                                           ; jump back to menu choice     

E2:

     mov  edi,0                                        ; reset invalid input flag

     cmp  menuAnswer,49                                ; compare user choice to menu options
     jne  L2                                           ; jump past binary input

     mov  edx,OFFSET entryMsg1                         ; load prompt into register
     call WriteString                                  ; write to screen

     mov  edx,OFFSET binMsg                            ; load prompt into register
     call WriteString                                  ; write to screen
     
     mov  edx,OFFSET entryMsg2                         ; load prompt into register
     call WriteString                                  ; write to screen
     
     call GetBinary                                    ; get binary input
     
     cmp  edi,1                                        ; check if binary input value was invalid
     je   E2                                           ; jump to binary input if invalid

L2:        

     cmp  menuAnswer,50                                ; compare user choice to menu options
     jne  L3                                           ; jump past decimal input

     mov  edx,OFFSET entryMsg1                         ; load prompt into register
     call WriteString                                  ; write to screen

     mov  edx,OFFSET decMsg                            ; load prompt into register
     call WriteString                                  ; write to screen
     
     mov  edx,OFFSET entryMsg2                         ; load prompt into register
     call WriteString                                  ; write to screen
     
     call ReadInt                                      ; get decimal input
     mov  intVal,eax                                   ; store decimal value

     jo   L2                                           ; jump to decimal input if invalid input

L3:

     mov  edi,0                                        ; reset invalid input flag

     cmp  menuAnswer,51                                ; compare user choice to menu options
     jne  L4                                           ; jump past hex input

     mov  edx,OFFSET entryMsg1                         ; load prompt into register
     call WriteString                                  ; write to screen

     mov  edx,OFFSET hexMsg                            ; load prompt into register
     call WriteString                                  ; write to screen
     
     mov  edx,OFFSET entryMsg2                         ; load prompt into register
     call WriteString                                  ; write to screen
     
     call GetHex                                       ; get hex input

     cmp  edi,1                                        ; check if hex input value was invalid
     je   L3                                           ; jump to hexinput if invalid

L4:

     cmp  menuAnswer,52                                ; compare user choice to menu options
     je   L5                                           ; jump past output

     call Crlf                                         ; line feed
     call PrintResults                                 ; write all values
     call ReadChar                                     ; wait for user input

L5:

     cmp  menuAnswer,52                                ; compare user choice to menu options
     jne  L1                                           ; jump to loop start

     INVOKE ExitProcess,0

main ENDP

; ************************************************************************************************* ;
; Function -   ShowMenu                                  									;
;	Inputs -	                                                                                     ;
;	Outputs -	Menu choices   														;
;	Registers used / purpose of each													;
;	     ecx - string input / edx - output                                                         ;
;	Functional details - Prints out menu and gets user's choice                                    ;
; ************************************************************************************************* ;
 
ShowMenu PROC uses edx
     
     mov  edx,OFFSET titleMsg                          ; load prompt into register
     call WriteString                                  ; write to screen
     call Crlf                                         ; line feed

     mov  bl,'1'                                       ; load menu number into register
     call MenuNumber                                   ; write menu number

     mov  edx,OFFSET binMsg                            ; load prompt into register
     call WriteString                                  ; write to screen
     call Crlf                                         ; line feed
     
     mov  bl,'2'                                       ; load menu number into register
     call MenuNumber                                   ; write menu number

     mov  edx,OFFSET decMsg                            ; load prompt into register
     call WriteString                                  ; write to screen
     call Crlf                                         ; line feed

     mov  bl,'3'                                       ; load menu number into register
     call MenuNumber                                   ; write menu number

     mov  edx,OFFSET hexMsg                            ; load prompt into register
     call WriteString                                  ; write to screen
     call Crlf                                         ; line feed

     mov  bl,'4'                                       ; load menu number into register
     call MenuNumber                                   ; write menu number

     mov  edx,OFFSET exitMsg                           ; load prompt into register
     call WriteString                                  ; write to screen
     call Crlf                                         ; line feed

     call Crlf                                         ; line feed
     mov  edx,OFFSET menuMsg                           ; load prompt into register
     call WriteString                                  ; write to screen
     
     ret
ShowMenu ENDP

; ************************************************************************************************* ;
; Function -   MenuNumber                                  									;
;	Inputs -	                                                                                     ;
;	Outputs -	   														               ;
;	Registers used / purpose of each													;
;	     eax - / edx - output                                                                      ;
;	Functional details - Prints out a number for menu choice                                       ;
; ************************************************************************************************* ;
 
MenuNumber PROC uses eax

     mov  al,'('                                       ; load character into register
     call WriteChar                                    ; write to screen
     
     mov  al,bl                                        ; load menu number into register
     call WriteChar                                    ; write to screen
         
     mov  al,')'                                       ; load character into register
     call WriteChar                                    ; write to screen

     mov  al,' '                                       ; load character into register
     call WriteChar                                    ; write to screen

     ret
MenuNumber ENDP

; ************************************************************************************************* ;
; Function -   PrintResults								                                   ;
;	Inputs -	 				                                   						;
;	Outputs -	The 32-bit integer in binary, decimal, and hex								;
;	Registers used / purpose of each													;
;	     eax, ebx - output, math / edx - output                                                    ;
;	Functional details - Prints the input integer in all 3 formats							;
; ************************************************************************************************* ;

PrintResults PROC USES eax edx

     mov  eax,intVal                                   ; load integer value into register

     mov  edx,OFFSET resultMsg                         ; load prompt into register
     call WriteString                                  ; write to screen
     call Crlf                                         ; line feed

     mov  edx,OFFSET binMsg                            ; load prompt into register
     call WriteString                                  ; write to screen

     mov  edx,OFFSET valMsg                            ; load prompt into register
     call WriteString                                  ; write to screen

     call WriteBin                                     ; write binary value
     call Crlf                                         ; line feed

     mov  edx,OFFSET decMsg                            ; load prompt into register
     call WriteString                                  ; write to screen

     mov  edx,OFFSET valMsg                            ; load prompt into register
     call WriteString                                  ; write to screen

     call WriteInt                                     ; write decimal value
     call Crlf                                         ; line feed

     mov  edx,OFFSET hexMsg                            ; load prompt into register
     call WriteString                                  ; write to screen
     
     mov  edx,OFFSET valMsg                            ; load prompt into register
     call WriteString                                  ; write to screen
     
     call WriteHex                                     ; write hex value
     call Crlf                                         ; line feed
    
     ret
PrintResults ENDP

; ************************************************************************************************* ;
; Function -   GetBinary									                                   ;
;	Inputs -	32-bit integer in binary format                          						;
;	Outputs -	  																	;
;	Registers used / purpose of each													;
;	     eax, ebx - output, math / edx - output                                                    ;
;	Functional details - gets integer from user											;
; ************************************************************************************************* ;

GetBinary PROC USES eax ebx edx
    
     mov  edx,OFFSET binVal                            ; point to the buffer
     mov  ecx,SIZEOF binVal                            ; specify max characters
     call ReadString                                   ; input the string
     mov  byteCount,eax                                ; store number of characters
     mov  ecx,LENGTHOF binVal                          ; set loop counter 

     mov  esi,0                                        ; clear array pointer
     mov  ebx,0                                        ; clear register

B1:                                                    ; binary input loop

     cmp  binVal[esi],0                                ; check binary input for null
     je   B4                                           ; jump if null

     cmp  binVal[esi],48                               ; check binary input for 0 or 1
     je   BE                                           ; jump if 0

     cmp  binVal[esi],49                               ; check binary input for 0 or 1
     je   BO                                           ; jump if 1
     
     mov  edx,OFFSET errorMsg                          ; load error message into register
     call WriteString                                  ; write to screen
     call Crlf                                         ; line feed

     mov  edi,1                                        ; set invalid binary input flag
     ret

BE:

     clc                                               ; clear carry flag
     jmp  BS                                           ; jump 

BO:

     stc                                               ; set carry flag

BS:

     rcl  ebx,1                                        ; store binary digit into integer value
     mov  intVal,ebx                                   ; store converted value

     jmp  B3                                           ; jump

B3:

     inc  esi                                          ; increment array pointer

     dec  ecx                                          ; decrement loop counter
     cmp  ecx,0                                        ; compare to 0
     jne  B1                                           ; jump to loop start if not 0

B4:

     ret
GetBinary ENDP

; ************************************************************************************************* ;
; Function -   GetHex									                                   ;
;	Inputs -	32-bit integer in hex format                           						;
;	Outputs -	  																	;
;	Registers used / purpose of each													;
;	     eax, ebx - output, math / edx - output                                                    ;
;	Functional details - gets integer from user											;
; ************************************************************************************************* ;

GetHex PROC USES eax ebx edx

     mov  edx,OFFSET binVal                            ; point to the buffer
     mov  ecx,SIZEOF binVal                            ; specify max characters
     call ReadString                                   ; input the string
     mov  byteCount,eax                                ; store number of characters
     mov  ecx,LENGTHOF binVal                          ; set loop counter 

     mov  esi,0                                        ; clear array pointer
     mov  ebx,0                                        ; clear register

H1:                                                    ; binary input loop

     cmp  binVal[esi],0                                ; check binary input for null
     je   HX                                           ; jump if null

     movsx  ebx,binVal[esi]                            ; load input string character to register

     cmp  binVal[esi],48                               ; check input for digit
     jl   HE                                           ; jump if not

     cmp  binVal[esi],58                               ; check input for digit
     jl   HD                                           ; jump if so

     cmp  binVal[esi],65                               ; check input for A-F
     jl   HE                                           ; jump if not

     cmp  binVal[esi],70                               ; check input for A-F
     jg   HE                                           ; jump if not

     cmp  esi,0                                        ; check if first pass through loop
     jg   HU1                                          ; jump if not

     sub  ebx,55                                       ; convert A-F ASCII into number
     mov  intVal,ebx                                   ; store converted number

     jmp  H4

HU1:

     shl  intVal,4                                     ; shift value by half byte

     sub  ebx,55                                       ; convert A-F ASCII into number
     or   intVal,ebx                                   ; store converted number

     jmp  H4                                           ; jump to loop end

HD:

     cmp  esi,0                                        ; check if first pass through loop
     jg   HD1                                          ; jump if not

     sub  ebx,48                                       ; convert digit ASCII into number
     mov  intVal,ebx                                   ; store converted number

     jmp  H4                                           ; jump to loop end

HD1:

     shl  intVal,4                                     ; shift value by half byte                                     

     sub  ebx,48                                       ; convert digit ASCII into number
     or   intVal,ebx                                   ; store converted number       

     jmp  H4                                           ; jump to loop end          

HE:

     mov  edx,OFFSET errorMsg                          ; load error message into register
     call WriteString                                  ; write to screen
     call Crlf                                         ; line feed

     mov  edi,1                                        ; set invalid binary input flag
     ret

H4:

     inc  esi                                          ; increment array pointer

     dec  ecx                                          ; decrement loop counter
     cmp  ecx,0                                        ; compare to 0          
     jne  H1                                           ; jump to loop start if not 0

HX:

     ret
GetHex ENDP


END main
; ************************************************************************************************* ;
; Program Name:		16-bit Calc (16-bit Calc.asm)													;
; Program Description: 	This program acts as an integer calculater. It allows 16 bit addition,     ;
;                        subtraction, multiplication, and division. There is a repeat option.       ;
; Author:				Adam Stancil													;
; Course/Section/Title:	CSC2025 Assembly/Architecture										;
; Creation Date:		4/5/25														;
; Date Last Modified:	4/6/25														;
; ************************************************************************************************* ;

.386
.model flat,stdcall
.stack 4096
ExitProcess PROTO, dwExitCode:DWORD

INCLUDE Irvine32.inc
INCLUDELIB Irvine32.lib
.data
intPrompt1 BYTE "Please enter an integer value up to 16 bits in size: ",0       ; 1st integer prompt string
intPrompt2 BYTE "Please enter another integer value up to 16 bits in size: ",0  ; 2ns integer prompt string
menuPrompt BYTE "Please choose one of the menu options: ",0                     ; menu prompt string
contPrompt BYTE "Press <ENTER> to continue...",0                                ; continue prompt string

menu1 BYTE "(1) Addition",0                                                     ; addition prompt string
menu2 BYTE "(2) Subtraction",0                                                  ; subtraction prompt string
menu3 BYTE "(3) Multiplication",0                                               ; multiplication prompt string
menu4 BYTE "(4) Division",0                                                     ; division prompt string
menu5 BYTE "(5) Exit",0                                                         ; exit prompt string

resultMessage BYTE "Computation Result: ",0                                     ; result string message
sumMessage BYTE "The sum of ",0                                                 ; sum string message
diffMessage BYTE "The difference of ",0                                         ; difference string message
multMessage BYTE "The product of ",0                                            ; multiply string message
divMessage1 BYTE "The quotient of ",0                                           ; 1st divide string message
divMessage2 BYTE " divided by ",0                                               ; 2nd divide string message
divMessage3 BYTE " with a remainder of ",0                                      ; 3rd divide string message
andMessage BYTE " and ",0                                                       ; and string message
isMessage BYTE " is ",0                                                         ; is string message

input1 WORD (0)                                                                 ; 1st integer variable
input2 WORD (0)                                                                 ; 2nd integer variable
result DWORD (0)                                                                ; result variable
remainder DWORD (0)                                                             ; division remainder variable

menuAnswer BYTE ?,0                                                             ; menu choice variable 

; ************************************************************************************************* ;
; Functional description of the main program												;
;	Inputs -	2 integers for computation, menu choices, prompts                                    ;
;	Outputs -	the computation result   												;
;	Registers used / purpose of each													;
;	     eax, ebx - input, output, math / ecx - string input / edx - output   	                    ;
;	Functional details - This program acts as an integer calculater. It allows 16 bit addition,    ;
;                         subtraction, multiplication, and division. There is an option to repeat.	;
; ************************************************************************************************* ;

.code
main PROC

L1:

     mov  eax,0                                        ; clear EAX register
     mov  edx,0                                        ; clear EDX register
     
     mov  input1,0                                     ; clear 1st integer input every loop
     mov  input2,0                                     ; clear 2nd integer input every loop
     mov  menuAnswer,0                                 ; clear menu input every loop
     
     call Menu                                         ; call function to write menu

     cmp  menuAnswer,53                                ; compare menu choice with menu options
     je   L6                                           ; jump to program exit if End

     cmp  menuAnswer,49                                ; compare menu choice with menu options
     jne  L2                                           ; jump if choice not Add
     call PlusFunc                                     ; call function to write menu

L2:
     
     cmp  menuAnswer,50                                ; compare menu choice with menu options
     jne  L3                                           ; jump if choice not Subtract
     call SubtractFunc                                 ; call function to write menu

L3:

     cmp  menuAnswer,51                                ; compare menu choice with menu options
     jne  L4                                           ; jump if choice not Multiply
     call MultiplyFunc                                 ; call function to write menu

L4:

     cmp  menuAnswer,52                                ; compare menu choice with menu options
     jne  L5                                           ; jump if choice not Divide
     call DivideFunc                                   ; call function to write menu

L5:

     call Crlf                                         ; line feed
     mov  edx,OFFSET contPrompt                        ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call ReadInt                                      ; read user input
 
     call Clrscr                                       ; clear the screen

     jmp  L1                                           ; loop to program start (Input!=5) 

L6:

    INVOKE ExitProcess,0

main ENDP

; ************************************************************************************************* ;
; Function -   Menu                                  									     ;
;	Inputs -	2 integers for computation, menu choices                                             ;
;	Outputs -	Menu choices   														;
;	Registers used / purpose of each													;
;	     ecx - string input / edx - output                                                         ;
;	Functional details - Prints out menu and gets user's choice                                    ;
; ************************************************************************************************* ;
 
Menu PROC  

     call GetInts
     
     mov  edx,OFFSET menu1                             ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call Crlf                                         ; line feed

     mov  edx,OFFSET menu2                             ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call Crlf                                         ; line feed

     mov  edx,OFFSET menu3                             ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call Crlf                                         ; line feed

     mov  edx,OFFSET menu4                             ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call Crlf                                         ; line feed
     
     mov  edx,OFFSET menu5                             ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call Crlf                                         ; line feed

     call Crlf                                         ; line feed
     mov  edx,OFFSET menuPrompt                        ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     
     mov  edx,OFFSET menuAnswer                        ; point to the buffer
     mov  ecx,SIZEOF menuAnswer                        ; specify max characters
     call ReadString                                   ; input the string

     call Crlf                                         ; line feed

     ret
Menu ENDP

; ************************************************************************************************* ;
; Function -   GetInts                                  									;
;	Inputs -	2 16-bit integers                                                                    ;
;	Outputs -	Input prompts	                                        						;
;	Registers used / purpose of each													;
;	     eax - input / edx - output                                                                ;
;	Functional details - Get 2 integers for computation from user                                  ;
; ************************************************************************************************* ;
 
GetInts PROC 

     mov  edx,OFFSET intPrompt1                        ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call ReadInt                                      ; read user input
     mov  input1,ax                                    ; save user input to input1 variable
     call Crlf                                         ; line feed

     mov  edx,OFFSET intPrompt2                        ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call ReadInt                                      ; read user input
     mov  input2,ax                                    ; save user input to input2 variable
     call Crlf                                         ; line feed

     ret
GetInts ENDP

; ************************************************************************************************* ;
; PlusFunc			                              									;
;	Inputs -	None 				                                   					;
;	Outputs -	Computation result  													;
;	Registers used / purpose of each													;
;	     eax, ebx - output, math / edx - output                                                    ;
;	Functional details - Adds 2 integers together and displays the result                          ;
; ************************************************************************************************* ;

PlusFunc PROC USES eax ebx edx
     
     mov  eax,0                                        ; clear register
     mov  ebx,0                                        ; clear register
     mov  edx,0                                        ; clear register
     
     mov  bx,input1                                    ; load first integer into register
     add  bx,input2                                    ; add second integer to first

     mov  edx,OFFSET resultMessage                     ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call Crlf                                         ; line feed
     
     mov  edx,OFFSET sumMessage                        ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  ax,input1                                    ; load value into register
     call WriteDec                                     ; write value to screen
     
     mov  edx,OFFSET andMessage                        ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  ax,input2                                    ; load value into register
     call WriteDec                                     ; write value to screen

     mov  edx,OFFSET isMessage                         ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  eax,ebx                                      ; load result into register
     call WriteDec                                     ; write result to screen

     call Crlf                                         ; line feed

     ret
PlusFunc ENDP

; ************************************************************************************************* ;
; SubtractFunc												                              ;
;	Inputs -	None 				                                   					;
;	Outputs -	Computation result  													;
;	Registers used / purpose of each													;
;	     eax, ebx - output, math / edx - output                                                    ;
;	Functional details - Subtracts 2 integers and displays the result                              ;
; ************************************************************************************************* ;

SubtractFunc PROC USES eax ebx edx
     
     mov  eax,0                                        ; clear register
     mov  ebx,0                                        ; clear register
     mov  edx,0                                        ; clear register
   
     mov  bx,input1                                    ; load first integer into register
     sub  bx,input2                                    ; subtract second integer from first

     mov  edx,OFFSET resultMessage                     ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call Crlf                                         ; line feed
     
     mov  edx,OFFSET diffMessage                       ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  ax,input1                                    ; load value into register
     call WriteDec                                     ; write value to screen
     
     mov  edx,OFFSET andMessage                        ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  ax,input2                                    ; load value into register
     call WriteDec                                     ; write value to screen

     mov  edx,OFFSET isMessage                         ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  eax,ebx                                      ; load result into register
     call WriteDec                                     ; write result to screen

     call Crlf                                         ; line feed

     ret
SubtractFunc ENDP

; ************************************************************************************************* ;
; MultiplyFunc												                              ;
;	Inputs -	None 				                                   					;
;	Outputs -	Computation result  													;
;	Registers used / purpose of each													;
;	     eax, ebx - output, math / edx - output                                                    ;
;	Functional details - Multiplies 2 integers and displays the result                             ;
; ************************************************************************************************* ;

MultiplyFunc PROC USES eax ebx edx
     
     mov  eax,0                                        ; clear register
     mov  ebx,0                                        ; clear register
     mov  edx,0                                        ; clear register
   
     mov  ax,input1                                    ; load first integer into register
     mov  bx,input2                                    ; load second integer into register
     mul  bx                                           ; multiply integers together

     shl  edx, 16                                      ; move top 16 bits into place
     mov  dx, ax                                       ; move bottom 16 bits into place

     mov  result,edx                                   ; move combined product into result variable

     mov  edx,OFFSET resultMessage                     ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call Crlf                                         ; line feed
     
     mov  edx,OFFSET multMessage                       ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  ax,input1                                    ; load value into register
     call WriteDec                                     ; write value to screen
     
     mov  edx,OFFSET andMessage                        ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  ax,input2                                    ; load value into register
     call WriteDec                                     ; write value to screen

     mov  edx,OFFSET isMessage                         ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  eax,result                                   ; load result into register
     call WriteDec                                     ; write result to screen

     call Crlf                                         ; line feed

     ret
MultiplyFunc ENDP

; ************************************************************************************************* ;
; DivideFunc												                              ;
;	Inputs -	None 				                                   					;
;	Outputs -	Computation result  													;
;	Registers used / purpose of each													;
;	     eax, ebx - output, math / edx - output                                                    ;
;	Functional details - Divides 2 integers and displays the quotient and remainder                ;
; ************************************************************************************************* ;

DivideFunc PROC USES eax ebx edx
     
     mov  eax,0                                        ; clear register
     mov  ebx,0                                        ; clear register
     mov  edx,0                                        ; clear register

     mov  ax,input1                                    ; load first integer into register
     mov  bx,input2                                    ; load second integer into register

     cmp  bx,0                                         ; check for 0 divisor
     je   D1                                           ; jump past division if 0 divisor

     div  bx                                           ; divide integers

     movsx ebx,ax                                      ; extend quotient to 32 bit
     mov  result,ebx                                   ; move quotient to result variable
     movsx ebx,dx                                      ; extend remainder to 32 bit
     mov  remainder,ebx                                ; move remainder to remainder variable

D1:

     mov  edx,OFFSET resultMessage                     ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen
     call Crlf                                         ; line feed
     
     mov  edx,OFFSET divMessage1                       ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  ax,input1                                    ; load value into register
     call WriteDec                                     ; write value to screen
     
     mov  edx,OFFSET divMessage2                       ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  ax,input2                                    ; load value into register
     call WriteDec                                     ; write value to screen

     mov  edx,OFFSET isMessage                         ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  eax,result                                   ; load result into register
     call WriteDec                                     ; write result to screen

     mov  edx,OFFSET divMessage3                       ; load prompt into EDX register
     call WriteString                                  ; write prompt to screen

     mov  eax,remainder                                ; load remainder into register
     call WriteDec                                     ; write remainder to screen

     call Crlf                                         ; line feed

     ret
DivideFunc ENDP

END main
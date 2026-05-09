; ************************************************************************************************* ;
; Program Name:		Number Guess (Number Guess.asm) 												;
; Program Description: 	This is a random number guessing game. It generates a random number between;
;                        1-50 and gives the user 10 attempts to guess it, with high/low responses   ;
;                        each guess. There is a repeat option.						          ;
; Author:				Adam Stancil													;
; Course/Section/Title:	CSC2025 Assembly/Architecture										;
; Creation Date:		4/25/25														;
; Date Last Modified:	4/26/25														;
; ************************************************************************************************* ;

.386
.model flat,stdcall
.stack 4096
ExitProcess PROTO, dwExitCode:DWORD

INCLUDE Irvine32.inc
INCLUDE Macros.inc
INCLUDELIB Irvine32.lib
.data

entryMsg BYTE "Please guess a number between 1-50: ",0		                         ; entry prompt string
exitMsg BYTE "Press 0 To Exit Or Any Other Key To Repeat...",0					; exit string
errorMsg BYTE "Invalid Entry.",0            				                         ; error string

numRand WORD (0)                                                                     ; random number
numGuess WORD (0)                                                                    ; user's guess
countGuess WORD (0)                                                                  ; guess counter

; ************************************************************************************************* ;
; Functional description of the main program												;
;	Inputs -	A number guess between 1-50 and whether to repeat after each game                    ;
;	Outputs -	The result of each guess and the correct number if it isn't guessed after 10 attempts;
;	Registers used / purpose of each													;
;	     eax, ebx - input, output, math / ecx - string input / edx - output   	                    ;
;	Functional details - This is a random number guessing game. It generates a random number       ;
;                        between 1-50 and gives the user 10 attempts to guess it, with high/low     ;
;                        responses each guess. There is a repeat option.                            ;
; ************************************************************************************************* ;

.code
main PROC
     
L0:                                                    ; full program loop

     mov  countGuess,10                                ; reset guess counter
     mov  eax,0                                        ; clear register

     call Clrscr                                       ; clear screen
     mWrite "---Random Number Guessing Game---"        ; program title
     call Crlf                                         ; line feed

     call GenRand                                      ; generate a random number 1-50

L1:                                                    ; single game loop (10 attempts)

     call GetGuess                                     ; ask user to guess number
     movsx eax,numRand                                 ; store random number in register

     cmp  numGuess,ax                                  ; compare guess to random number
     jl   LL                                           ; jump if less
     jg   LG                                           ; jump if greater

     mWrite "You guessed the correct number!"          ; correct guess message
     call Crlf                                         ; line feed
     jmp  L9                                           ; jump to end of game loop

LL:
     
     mWrite "You guessed too low!"                     ; low guess message
     call Crlf                                         ; line feed
     jmp  L2

LG:
     
     mWrite "You guessed too high!"                    ; high guess message
     call Crlf                                         ; line feed
     jmp  L2


L2:

     dec  countGuess                                   ; decrement guess counter     

     cmp  countGuess,0                                 ; check guess counter for 0
     jne  L1                                           ; jump to loop start

     mWrite "You are out of guesses."                  ; display guess fail message
     call Crlf                                         ; line feed

     mWrite "The correct answer was: "                 ; display correct number prompt
     call writeDec                                     ; display random number
     call Crlf                                         ; line feed

L9:

     mWriteString exitMsg                              ; print game repeat prompt
     call ReadChar                                     ; get user's repeat selection

     cmp  al,48                                        ; check user input for 0
     jne  L0                                           ; jump to loop start

     INVOKE ExitProcess,0

main ENDP

; ************************************************************************************************* ;
; Function -   GenRand                                  									;
;	Inputs -	None                                                                                 ;
;	Outputs -	None                                                                                 ;
;	Registers used / purpose of each													;
;	     eax - random number generation                                                            ;
;	Functional details - Generates a random number between 1-50 and stores it in numRand.          ;
; ************************************************************************************************* ;
 
GenRand PROC uses eax
     
     mov  eax,50                                       ; load 50 as random seed

     call RandomRange                                  ; get random number 0-49

     inc  eax                                          ; adjust to 1-50

     mov  numRand,ax                                   ; store random number
     
     ret
GenRand ENDP

; ************************************************************************************************* ;
; Function -   GetGuess                                  									;
;	Inputs -	A number 1-50                                                                        ;
;	Outputs -	Attempts remaining and user prompt for guess 1-50      			               ;
;	Registers used / purpose of each													;
;	     eax - input, output                                                                       ;
;	Functional details - Prompts user for a number between 1-50                                    ;
; ************************************************************************************************* ;
 
GetGuess PROC

     mWrite "Attempts Left: "                          ; write prompt to screen
     
     mov  ax,countGuess                                ; load guess attempts to register
     call writeDec                                     ; write to screen

     call Crlf                                         ; line feed

     mWriteString entryMsg                             ; prompt user for guess

     call ReadDec                                      ; get user's guess
     
     mov  numGuess,ax                                  ; store user's guess

     ret
GetGuess ENDP

END main
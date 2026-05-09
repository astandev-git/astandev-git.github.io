; ************************************************************************************************* ;
; Program Name:		Hangman (Hangman.asm) 												;
; Program Description: 	This is a hangman guessing game. It chooses from a list of 10 words,       ;
;                        gives the user 6 failures to guess all the letters in the word, comparing  ;
;                        each letter chosen to letters in the word. After each letter pick, the     ;
;                        position is revealed if in the word or a part of the hangman is applied if ;
;                        not. A repeat option is given.                                             ;
; Author:				Adam Stancil													;
; Course/Section/Title:	CSC2025 Assembly/Architecture										;
; Creation Date:		4/26/25														;
; Date Last Modified:	5/2/25														;
; ************************************************************************************************* ;

.386
.model flat,stdcall
.stack 4096
ExitProcess PROTO, dwExitCode:DWORD

INCLUDE Irvine32.inc
INCLUDE Macros.inc
INCLUDELIB Irvine32.lib
.data

word1 BYTE "mustard",0                                                               ; first word
word2 BYTE "burgers",0                                                               ; second word
word3 BYTE "brisket",0                                                               ; third word
word4 BYTE "burrito",0                                                               ; fourth word
word5 BYTE "calzone",0                                                               ; fifth word
word6 BYTE "taquito",0                                                               ; sixth word
word7 BYTE "lasagna",0                                                               ; seventh word
word8 BYTE "sausage",0                                                               ; eight word
word9 BYTE "eggroll",0                                                               ; ninth word
word10 BYTE "wontons",0                                                              ; tenth word

wordPick BYTE "9999999",0                                                            ; chosen word
wordReset BYTE "9999999",0                                                           ; array to reset others
flagArray BYTE "9999999",0                                                           ; array to mark found letters

exitMsg BYTE "Press 0 To Exit Or Any Other Key To Repeat...",0					; exit message

numRand WORD (0)                                                                     ; random number

userGuess WORD (0)                                                                   ; user's guess
guessLeft WORD (0)                                                                   ; attempts remaining
guessCount WORD (0)                                                                  ; total guesses
charLeft WORD (0)                                                                    ; characters unguessed

; ************************************************************************************************* ;
; Functional description of the main program												;
;	Inputs -	One letter at a time                                                                 ;
;	Outputs -	The number of guess attempts, the results of the guess, including the word and       ;
;              hangman parts.                                                                       ;
;	Registers used / purpose of each													;
;	     eax, ebx - input, output, math / ecx - string input / edx - output   	                    ;
;	Functional details - This is a hangman guessing game. It chooses from a list of 10 words,      ;
;                         gives the user 6 failures to guess all the letters in the word, comparing ;
;                         each letter chosen to letters in the word. After each letter pick, the    ;
;                         position is revealed if in the word or a part of the hangman is applied if;
;                         not. A repeat option is given.                                            ;
; ************************************************************************************************* ;

.code
main PROC
     
L0:                                                    ; full program loop

     call Clrscr                                       ; clear screen
     mWrite "--- Yummy Hangman ---"                    ; program title
     call Crlf                                         ; line feed
     call Crlf                                         ; line feed

     INVOKE Str_copy,ADDR wordReset,ADDR wordPick      ; reset chosen word
     INVOKE Str_copy,ADDR wordReset,ADDR flagArray     ; reset flagged array
     mov  guessLeft,6                                  ; reset guess counter
     mov  charLeft,7                                   ; reset characters unguessed
     mov  guessCount,0                                 ; reset total guesses
     mov  eax,0                                        ; clear register

     call PickWord                                     ; generate a random number 1-10

     call PrintAll                                     ; print word matches and hangman results

L1:                                                    ; single game loop (6 failed guesses)

     call GetGuess                                     ; ask user to guess letter

     call CheckGuess                                   ; compare guess to word

     call PrintAll                                     ; print word matches and hangman results

     cmp  charLeft,0                                   ; check if all letters have been guessed
     je   L9                                           ; jump to end if so

     cmp  guessLeft,0                                  ; compare user guesses remaining to 0
     jne  L1                                           ; jump to loop start if not

L9:

     mWriteString exitMsg                              ; print game repeat prompt
     call ReadChar                                     ; get user's repeat selection

     cmp  al,48                                        ; check user input for 0
     jne  L0                                           ; jump to loop start

     INVOKE ExitProcess,0

main ENDP

; ************************************************************************************************* ;
; Function -   PickWord                                  									;
;	Inputs -	None                                                                                 ;
;	Outputs -	None                                                                                 ;
;	Registers used / purpose of each													;
;	     eax - random number generation                                                            ;
;	Functional details - Gets a random number between 1-10 and assigns a word to chosen word       ;
; ************************************************************************************************* ;
 
PickWord PROC uses eax
     
     call Randomize                                    ; reseed random number generator

     mov  eax,10                                       ; load 10 as random seed

     call RandomRange                                  ; get random number 0-9
     
     cmp  eax,0                                        ; compare random number to 0
     jg   P1                                           ; jump if so

     INVOKE Str_copy,ADDR word1,ADDR wordPick          ; copy random word to chosen word variable
     jmp  PE                                           ; jump to end

P1:

     cmp  eax,1                                        ; compare random number to 1
     jg   P2                                           ; jump if so

     INVOKE Str_copy,ADDR word2,ADDR wordPick          ; copy random word to chosen word variable         
     jmp  PE                                           ; jump to end

P2:

     cmp  eax,2                                        ; compare random number to 2
     jg   P3                                           ; jump if so

     INVOKE Str_copy,ADDR word3,ADDR wordPick          ; copy random word to chosen word variable          
     jmp  PE                                           ; jump to end

P3:

     cmp  eax,3                                        ; compare random number to 3
     jg   P4                                           ; jump if so

     INVOKE Str_copy,ADDR word4,ADDR wordPick          ; copy random word to chosen word variable          
     jmp  PE                                           ; jump to end

P4:

     cmp  eax,4                                        ; compare random number to 4
     jg   P5                                           ; jump if so

     INVOKE Str_copy,ADDR word5,ADDR wordPick          ; copy random word to chosen word variable         
     jmp  PE                                           ; jump to end

P5:

     cmp  eax,5                                        ; compare random number to 5
     jg   P6                                           ; jump if so

     INVOKE Str_copy,ADDR word6,ADDR wordPick          ; copy random word to chosen word variable          
     jmp  PE                                           ; jump to end

P6:

     cmp  eax,6                                        ; compare random number to 6
     jg   P7                                           ; jump if so

     INVOKE Str_copy,ADDR word7,ADDR wordPick          ; copy random word to chosen word variable          
     jmp  PE                                           ; jump to end

P7:

     cmp  eax,7                                        ; compare random number to 7
     jg   P8                                           ; jump if so

     INVOKE Str_copy,ADDR word8,ADDR wordPick          ; copy random word to chosen word variable         
     jmp  PE                                           ; jump to end

P8:

     cmp  eax,8                                        ; compare random number to 8
     jg   P9                                           ; jump if so

     INVOKE Str_copy,ADDR word9,ADDR wordPick          ; copy random word to chosen word variable         
     jmp  PE                                           ; jump to end

P9:

     INVOKE Str_copy,ADDR word10,ADDR wordPick         ; copy random word to chosen word variable          

PE:

     ret
PickWord ENDP

; ************************************************************************************************* ;
; Function -   GetGuess                                  									;
;	Inputs -	A letter, either upper or lowercase                                                  ;
;	Outputs -	None                                                                                 ;
;	Registers used / purpose of each													;
;	     eax - input, output                                                                       ;
;	Functional details - Prompts user for a letter guess, ensures a letter input, converts to      ;
;                         lowercase, and saves to variable.                                         ;
; ************************************************************************************************* ;
 
GetGuess PROC uses eax

     mWrite "Attempts Left: "                          ; write prompt to screen
     
     mov  ax,guessLeft                                 ; load guess attempts to register
     call writeDec                                     ; write to screen

     call Crlf                                         ; line feed

     mov  eax,0                                        ; clear register

G0:
     
     mWrite "Please guess a letter: "                  ; prompt user for guess
     call ReadChar                                     ; get user's guess
     call WriteChar                                    ; echo user's input
     call Crlf                                         ; line feed

     cmp  al,65                                        ; check if character is less than 'A'
     jl   G1                                           ; jump if less
     cmp  al,90                                        ; check if character is greater than 'Z'
     jg   G1                                           ; jump if greater

     add  al,32                                        ; convert uppercase letter to lowercase
     jmp  G3                                           ; jump to end

G1:

     cmp  al,97                                        ; check if character is less than 'a'
     jl   G2                                           ; jump if less
     cmp  al,122                                       ; check if character is greater than 'z'
     jg   G2                                           ; jump if greater
     
     jmp  G3                                           ; jump to end

G2:

     mWrite "Non-Letter entered. Try again."           ; error message
     call Crlf                                         ; line feed
     jmp  G0                                           ; jump to user entry

G3:

     mov  userGuess,ax                                ; store user's guess

     ret
GetGuess ENDP

; ************************************************************************************************* ;
; Function -   CheckGuess                                  									;
;	Inputs -	None                                                                                 ;
;	Outputs -	None                                                                                 ;
;	Registers used / purpose of each													;
;	     eax, ebx - input, output, math / ecx - loop counter / edx - string length math            ;
;         esi, edi - flag, pointer                                                                  ;
;	Functional details - Compares user's letter guess against each letter in chosen word. Sets     ;
;                         found flag if found and decrements characters left. Decrements guesses    ;
;                         left if not.                                                              ;
; ************************************************************************************************* ;
 
CheckGuess PROC uses eax ebx ecx esi edi

     mov  esi,0                                        ; clear array pointer
     mov  edi,0                                        ; clear found flag               
    
     mov  edx,OFFSET wordPick                          ; load word into register
     call Strlength                                    ; find word length
     mov  ecx,eax                                      ; store word length as loop counter

     mov  ebx,0                                        ; clear register
     mov  bx,userGuess                                 ; load user's letter guess to register

C1:
         
     mov  eax,0                                        ; clear register
     movsx ax,wordPick[esi]                            ; load letter at pointer location to register
 
     cmp  al,bl                                        ; compare guess to current letter in word
     jne  C2                                           ; jump if guess doesn't match current letter

     dec  charLeft                                     ; decrement characters unguessed
     mov  edi,1                                        ; set found flag for guess attempt
     mov  flagArray[esi],"1"                           ; set location flag in flagArray

C2:

     inc  esi                                          ; increment array pointer
     loop C1                                           ; jump to loop start

     cmp  edi,1                                        ; check if found flag was set
     je   C3                                           ; jump if letter was found

     dec  guessLeft                                    ; decrement guesses left     

C3:

     ret
CheckGuess ENDP

; ************************************************************************************************* ;
; Function -   PrintAll                                  									;
;	Inputs -	None                                                                                 ;
;	Outputs -	Guesses remaining, letters found, hangman parts applied, win/lose                    ;
;	Registers used / purpose of each													;
;	     eax, ebx - input, output, math / ecx - loop counter / edx - string length math            ;
;         esi - pointer                                                                             ;
;	Functional details - If all letters have been guessed, print win message. Print number of      ;
;                         unguessed letters left, the word with any guessed letters filled in. Print;
;                         hangman parts depending on how many guess attempts used.                  ;      
; ************************************************************************************************* ;
 
PrintAll PROC uses esi eax ebx ecx edx

     mov  esi,0                                        ; clear array pointer

     cmp  charLeft,0                                   ; check if all letters have been guessed
     jne  P0                                           ; jump if some letters left

     call Crlf                                         ; line feed
     mWrite "You WIN!"                                 ; print win message
     call Crlf                                         ; line feed
     call Crlf                                         ; line feed

P0:

     movsx eax,charLeft                                ; load number of unguessed letters left to register
     call WriteDec                                     ; print to screen
     mWrite " letters left to find..."                 ; print letters remaining message to screen
     call Crlf                                         ; line feed

     mWrite "Word: "                                   ; print word section title

     mov  edx,0                                        ; clear register
     mov  edx,OFFSET wordPick                          ; load chosen word to register
     call Strlength                                    ; find word length
     mov  ecx,eax                                      ; store word length as loop counter

P1:                                                    ; print found letter loop

     movsx ebx,flagArray[esi]                          ; load flagArray value to register

     cmp  ebx,"1"                                      ; check if letter location was found
     jne  P2                                           ; jump if it wasn't

     mov  al,wordPick[esi]                             ; load current letter to register
     call WriteChar                                    ; print to screen
     jmp  P3                                           ; jump past blank letter substitution

P2:

     mov  eax,"-"                                      ; load dash to register
     call WriteChar                                    ; print to screen
     
P3:

     inc  esi                                          ; increment array pointer
     loop P1                                           ; jumpt to loop start

     call Crlf                                         ; line feed
     mWrite "Hangman: "                                ; print hangman section title
     call Crlf                                         ; line feed

H1:

     cmp  guessLeft,6                                  ; check if 6 guesses left
     je   H0                                           ; jump to end if so

     mWrite "Head"                                     ; print hangman part to screen
     call Crlf                                         ; line feed

     cmp  guessLeft,5                                  ; check if 5 guesses left
     je   H0                                           ; jump to end if so

     mWrite "Body"                                     ; print hangman part to screen
     call Crlf                                         ; line feed

     cmp  guessLeft,4                                  ; check if 4 guesses left
     je   H0                                           ; jump to end if so

     mWrite "Left Arm"                                 ; print hangman part to screen
     call Crlf                                         ; line feed

     cmp  guessLeft,3                                  ; check if 3 guesses left
     je   H0                                           ; jump to end if so

     mWrite "Right Arm"                                ; print hangman part to screen
     call Crlf                                         ; line feed

     cmp  guessLeft,2                                  ; check if 2 guesses left
     je   H0                                           ; jump to end if so

     mWrite "Left Leg"                                 ; print hangman part to screen
     call Crlf                                         ; line feed

     cmp  guessLeft,1                                  ; check if 1 guesses left
     je   H0                                           ; jump to end if so

     mWrite "Right Leg"                                ; print hangman part to screen                                
     call Crlf                                         ; line feed

     call Crlf                                         ; line feed     
     mWrite "YOU LOSE!"                                ; print loss message to screen
     call Crlf                                         ; line feed
     
     mWrite "The word was "                            ; print chosen word message
     mWriteString wordPick                             ; print chosen word
     call Crlf                                         ; line feed
     
H0:

     call Crlf                                         ; line feed

     ret
PrintAll ENDP

END main
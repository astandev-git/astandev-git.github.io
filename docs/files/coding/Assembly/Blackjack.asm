; ************************************************************************************************* ;
; Program Name:		Blackjack (Blackjack.asm)     									;
; Program Description: 	This is a blackjack game. It deals two cards to the player, shows their    ;
;                        combined value, then asks the player to hit or stand. If hit is chosen,    ;
;                        another card is dealt and the value is added to the total. This is repeated;
;                        until the user stands or goes over 21. The house then does the same,       ;
;                        having to hit if under 17. The scores are compared and a winner is         ;
;                        declared. A repeat option is given.                                        ;
; Author:				Adam Stancil													;
; Course/Section/Title:	CSC2025 Assembly/Architecture										;
; Creation Date:		5/3/25														;
; Date Last Modified:	5/6/25														;
; ************************************************************************************************* ;

.386
.model flat,stdcall
.stack 4096
ExitProcess PROTO, dwExitCode:DWORD

INCLUDE Irvine32.inc
INCLUDE Macros.inc
INCLUDELIB Irvine32.lib
.data

playerTotal WORD (0)                                                         ; player's hand value
houseTotal WORD (0)                                                          ; house's hand value

exitMsg BYTE "Press 0 To Exit Or Any Other Key To Repeat ",0			  ; exit message

aceFlag BYTE (0)                                                             ; ace card counter

; ************************************************************************************************* ;
; Functional description of the main program												;
;	Inputs -	Choices of whether to hit or stand and whether to play again.                        ;
;	Outputs -	A card with value and suit. Also, win or loss message depending which hand total is  ;
;              higher between player and house, or a tie message if they are equal.                 ;
;	Registers used / purpose of each													;
;	     eax, ebx - input, output, math                                                            ;
;	Functional details - This is a blackjack game. It deals two cards to the player, shows their   ;
;                         combined value, then asks the player to hit or stand. If hit is chosen,   ;
;                         another card is dealt and the value is added to the total. This is        ;     
;                         repeated until the user stands or goes over 21. The house then does the   ;
;                         same, having to hit if under 17. The scores are compared and a winner is  ;
;                         declared. A repeat option is given.                                       ;
; ************************************************************************************************* ;

.code
main PROC
     
L0:                                                    ; full program loop

     call Clrscr                                       ; clear screen
     mWrite "--- Blackjack ---"                        ; program title
     call Crlf                                         ; line feed
     call Crlf                                         ; line feed

     mov  eax,0                                        ; clear register
     mov  playerTotal,0                                ; reset player hand value
     mov  houseTotal,0                                 ; reset house hand value
     mov  aceFlag,0                                    ; reset ace card counter
  
     mWrite "Player Cards: "                           ; print message
     call Crlf                                         ; line feed
     call Crlf                                         ; line feed
     
     call DealCard                                     ; deal a card
     add  playerTotal,bx                               ; add card value to player's hand total

L1:                                                    ; user play loop 

     call DealCard                                     ; deal a card       
     add  playerTotal,bx                               ; add card value to player's hand total

     cmp  playerTotal,22                               ; compare hand value to 22
     jl   L2                                           ; jump if less

     movsx eax,aceFlag                                 ; load ace card counter value to register
     cmp  eax,0                                        ; check for aces
     jne  L3                                           ; jump if aces present

     mWrite "You Busted!"                              ; print loss message
     call Crlf                                         ; line feed

     mWrite "Hand Total: "                             ; print message
     movsx eax,playerTotal                             ; load hand value to register
     call WriteDec                                     ; print hand value
     call Crlf                                         ; line feed
     call Crlf                                         ; line feed

     jmp  L9                                           ; jump to end

L3:

     sub  playerTotal,10                               ; change ace value from 11 to 1
     dec  aceFlag                                      ; decrement ace card counter

L2:
        
     mWrite "Hand Total: "                             ; print message

     movsx eax,playerTotal                             ; load hand value to register                             
     call WriteDec                                     ; print hand value
     call Crlf                                         ; line feed
     call Crlf                                         ; line feed

     mWrite "Press 0 To Hit Or Any Other Key To Stand "; print message
     call ReadChar                                     ; get user's repeat selection

     call Crlf                                         ; line feed

     cmp  al,48                                        ; check user input for 0
     je   L1                                           ; jump to user hand loop start

     mov  aceFlag,0                                    ; reset ace card counter

     call Crlf                                         ; line feed
     mWrite "House Cards: "                            ; print message
     call Crlf                                         ; line feed
     call Crlf                                         ; line feed

     call DealCard                                     ; deal a card
     add  houseTotal,bx                                ; add card value to houses's hand total

L4:

     call DealCard                                     ; deal a card
     add  houseTotal,bx                                ; add card value to houses's hand total

     cmp  houseTotal,22                                ; compare hand value to 22
     jl   L5                                           ; jump if less

     movsx eax,aceFlag                                 ; load ace card counter value to register
     cmp  eax,0                                        ; check for aces
     jne  L6                                           ; jump if aces present

     mWrite "The House Busted!"                        ; print win message
     call Crlf                                         ; line feed

     mWrite "Hand Total: "                             ; print message
     movsx eax,houseTotal                              ; load hand value to register
     call WriteDec                                     ; print hand value
     call Crlf                                         ; line feed
     call Crlf                                         ; line feed

     jmp L9                                            ; jump to end

L6:

     sub  houseTotal,10                                ; change ace value from 11 to 1
     dec  aceFlag                                      ; decrement ace card counter

L5:
     
     mWrite "Hand Total: "                             ; print message

     movsx eax,houseTotal                              ; load hand value to register
     call WriteDec                                     ; print hand value
     call Crlf                                         ; line feed
     call Crlf                                         ; line feed
     
     cmp  houseTotal,17                                ; check for house hit condition
     jl   L4                                           ; jump to loop start if so 

     call PrintWinner                                  ; determine and print winner

L9:

     mWriteString exitMsg                              ; print game repeat prompt
     call ReadChar                                     ; get user's repeat selection

     cmp  al,48                                        ; check user input for 0
     jne  L0                                           ; jump to loop start

     call Crlf                                         ; line feed
     mWrite "Thanks for playing!"                      ; print exit message
     call Crlf                                         ; line feed

     INVOKE ExitProcess,0

main ENDP

; ************************************************************************************************* ;
; Function -   DealCard                                  									;
;	Inputs -	None                                                                                 ;
;	Outputs -	A card with value and suit                                                           ;
;	Registers used / purpose of each													;
;	     eax - random number generation / ebx - returns card value                                 ;
;	Functional details - Gets a random playing card suit and value, prints both, and returns the   ;
;                         value in ebx register.                                                    ;
; ************************************************************************************************* ;
 
DealCard PROC uses eax
     
     mov  ebx,0                                        ; clear register
     mov  eax,200                                      ; load 20 ms delay
     call Delay                                        ; call time delay to ensure different card dealt

     call Randomize                                    ; reseed random number generator

     mov  eax,13                                       ; load 13 as suit random seed

     call RandomRange                                  ; get random number 0-12
     
     cmp  eax,0                                        ; compare random number to 0
     jg   V1                                           ; jump if so

     mWrite "Ace of "                                  ; print card value
     mov  ebx,11                                       ; move card value to register
     inc  aceFlag                                      ; increment ace card counter
     jmp  V0                                           ; jump to end

V1:

     cmp  eax,1                                        ; compare random number to 1
     jg   V2                                           ; jump if so

     mWrite "Two of "                                  ; print card value
     mov  ebx,2                                        ; move card value to register
     jmp  V0                                           ; jump to end

V2:

     cmp  eax,2                                        ; compare random number to 2
     jg   V3                                           ; jump if so

     mWrite "Three of "                                ; print card value
     mov  ebx,3                                        ; move card value to register
     jmp  V0                                           ; jump to end

V3:

     cmp  eax,3                                        ; compare random number to 3
     jg   V4                                           ; jump if so

     mWrite "Four of "                                 ; print card value
     mov  ebx,4                                        ; move card value to register
     jmp  V0                                           ; jump to end

V4:

     cmp  eax,4                                        ; compare random number to 4
     jg   V5                                           ; jump if so

     mWrite "Five of "                                 ; print card value
     mov  ebx,5                                        ; move card value to register
     jmp  V0                                           ; jump to end

V5:

     cmp  eax,5                                        ; compare random number to 5
     jg   V6                                           ; jump if so

     mWrite "Six of "                                  ; print card value
     mov  ebx,6                                        ; move card value to register
     jmp  V0                                           ; jump to end

V6:

     cmp  eax,6                                        ; compare random number to 6
     jg   V7                                           ; jump if so

     mWrite "Seven of "                                ; print card value
     mov  ebx,7                                        ; move card value to register
     jmp  V0                                           ; jump to end

V7:

     cmp  eax,7                                        ; compare random number to 7
     jg   V8                                           ; jump if so

     mWrite "Eight of "                                ; print card value
     mov  ebx,8                                        ; move card value to register
     jmp  V0                                           ; jump to end

V8:

     cmp  eax,8                                        ; compare random number to 8
     jg   V9                                           ; jump if so

     mWrite "Nine of "                                 ; print card value
     mov  ebx,9                                        ; move card value to register
     jmp  V0                                           ; jump to end

V9:

     cmp  eax,9                                        ; compare random number to 9
     jg   V10                                          ; jump if so

     mWrite "Ten of "                                  ; print card value
     mov  ebx,10                                       ; move card value to register
     jmp  V0                                           ; jump to end

V10:

     cmp  eax,10                                       ; compare random number to 10
     jg   V11                                          ; jump if so

     mWrite "Jack of "                                 ; print card value
     mov  ebx,10                                       ; move card value to register
     jmp  V0                                           ; jump to end

V11:

     cmp  eax,11                                       ; compare random number to 11
     jg   V12                                          ; jump if so

     mWrite "Queen of "                                ; print card value
     mov  ebx,10                                       ; move card value to register
     jmp  V0                                           ; jump to end

V12:

     mWrite "King of "                                 ; print card value
     mov  ebx,10                                       ; move card value to register

V0:

     call Randomize                                    ; reseed random number generator

     mov  eax,4                                        ; load 4 as suit random seed

     call RandomRange                                  ; get random number 0-3
     
     cmp  eax,0                                        ; compare random number to 0
     jg   S1                                           ; jump if so

     mWrite "Clubs"                                    ; print card suit
     jmp  S0                                           ; jump to end

S1:

     cmp  eax,1                                        ; compare random number to 1
     jg   S2                                           ; jump if so

     mWrite "Spades"                                   ; print card suit
     jmp  S0                                           ; jump to end

S2:

     cmp  eax,2                                        ; compare random number to 2
     jg   S3                                           ; jump if so

     mWrite "Hearts"                                   ; print card suit
     jmp  S0                                           ; jump to end

S3:

     mWrite "Diamonds"                                 ; print card suit

S0:

     call Crlf                                         ; line feed

     ret
DealCard ENDP

; ************************************************************************************************* ;
; Function -   PrintWinner                                  								;
;	Inputs -	None                                                                                 ;
;	Outputs -	Win or loss message depending which hand total is higher between player and house, or;
;              a tie message if they are equal.                                                     ;
;	Registers used / purpose of each													;
;	     eax - comparison math                                                                     ;
;	Functional details - Compare player's hand total to house's hand total and print out a message.;
; ************************************************************************************************* ;
 
PrintWinner PROC uses eax

     mov  ax,playerTotal                               ; load player hand value to register
     cmp  ax,houseTotal                                ; compare player and house hands

     jg   P1                                           ; jump if greater
     jl   P2                                           ; jump if less
     
     mWrite "Hand values are equal, game is a PUSH."   ; print tie game message
     call Crlf                                         ; line feed
     jmp  P0                                           ; jump to end

P1:

     mWrite "The Player WINS!"                         ; print win message
     call Crlf                                         ; line feed
     jmp  P0                                           ; jump to end

P2:

     mWrite "The House WINS!"                          ; print loss message
     call Crlf                                         ; line feed

P0:     

     call Crlf                                         ; line feed

     ret
PrintWinner ENDP

END main
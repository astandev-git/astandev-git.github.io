# Adam Stancil - 5/5/24
# This program is a number guessing game. It generates a random integer between 1 and 100 then prompts the user to
# guess a number until they guess the number correct. Then they are asked if they want to play again.

# Import random class for math calculations
import random

# Create list variable for names
game_history_names = []

# Create list variable for guesses
game_history_guesses = []   

# Main function
def main():

    # Print initial score history
    display_scores()

    # Get user's name
    name = get_player_name()

    # Add user's name to file
    game_history_names.append(name)

    # Create menu loop end flag
    menuChoice = True

    # Main program loop - call repeat_game() which is main user menu loop, then calls store_name() which writes data
    # to the text file when done.
    while menuChoice:

        # Call repeat_game(), resulting in a succesful guess and returning a value representing whether they want to
        # continue playing
        menuChoice = repeat_game()

        # Write user's name and number of guess attempts to history.txt
        store_name(name, game_history_guesses[len(game_history_guesses)-1])
 
# Function read_history - Opens, reads, and outputs winner data from the file history.txt. The file contains the
# user name and number of guesses for the game they won. 
def read_history():

    # Open file for reading
    userFile = open("history.txt", "r")

    # Read first line
    currentLine = userFile.readline()

    # Loop to read and print each line (user's name and number of guesses)
    while currentLine != "":
        userName = currentLine.rstrip("\n")
        game_history_names.append(userName)
        print(f"{userName:<20}", end="")
        numGuesses = userFile.readline().rstrip("\n")
        game_history_guesses.append(numGuesses)
        print(f"{numGuesses:>20}")
        currentLine = userFile.readline()

    # Close file
    userFile.close()
   
# Function display_scores - Outputs header for history list, and calls read_history() for list data.
def display_scores():
    print(f"{'Name':<20}{'Guesses':>20}")
    print(f"{'-'*40}")
    read_history()

# Function get_player_name - Gets/returns users name, to be stored at end of game in the history file.
def get_player_name():
    return input("\nPlease enter your name: ")

# Function repeat_game - Generates a random number between 1-100, gets a guess from the user, tracks how many
# guesses have been made, determines whether the guess was correct or too low/high and displays a related messsage.
# It then asks the user if they want to continue the game and returns a Boolean value representing that answer. 
def repeat_game():
    # Create counter for guesses    
    guessCount = 1

    # Generate random number for user to guess
    targetNum = random.randint(1,100)

    # Set initial loop condition
    winFlag = False

    # Loop to guess number and display appropriate message until correct
    while not winFlag:
        userGuess = int(input(f"#{guessCount} Guess an integer number between 1 and 100: "))
        while userGuess != targetNum:
            guessCount += 1
            if userGuess < targetNum:
                print(f"Your guess is too low")
                userGuess = int(input(f"#{guessCount} Guess an integer number between 1 and 100: "))
            elif userGuess > targetNum:
                print(f"Your guess is too high")
                userGuess = int(input(f"#{guessCount} Guess an integer number between 1 and 100: "))
        winFlag = True

    # Display win message
    print(f"Congratulations - you are right!")

    # Save number of guesses to guess list
    game_history_guesses.append(guessCount)

    # Reset guess count for next play
    guessCount = 1

    # Set initial loop condition
    loopChoice = "x"

    # Loop to ask user to continue playing game
    while not(loopChoice == "n") and not(loopChoice == "N") and not(loopChoice == "y") and not(loopChoice == "Y"):
        loopChoice = input("Would you like to play again (y/n)?: ")
        if loopChoice == "y" or loopChoice == "Y":
            return True
        elif loopChoice == "n" or loopChoice == "N":
            return False
        else:
            print(f"Not a valid entry (Y/y or N/n)")
        
# Function store_name - takes the user's name and their guess and appends those to the file history.txt.
def store_name(name,guess):
    # Open file for writing
    userFile = open("history.txt", "a")

    # Write user's name to file
    userFile.write(f"\n{name}\n")

    # Write user's number of guesses to file
    userFile.write(f"{guess}")

    # Close file
    userFile.close()

# Call the main function
main()

# Adam Stancil - 4/7/24
# This generates 100 random numbers (1-100) and tracks and displays how many of them were even vs odd.

def main():
    import random

    # Set loop counter max constant
    LOOPMAX = 100

    # Set number range max constant
    NUMMAX = 100    

    # Initialize loop counter variable
    counter = 1

    # Initialize odd counter variable
    odd = 0

    # Initialize even counter variable
    even = 0

    # Loop to generate 100 random numbers, determine if each is odd or even, and track that with a counter
    for counter in range(LOOPMAX):
        if (oddEven(random.randint(1, NUMMAX)) == 1):
            odd += 1
        else:
            even += 1

    # Display even/odd totals
    print(f'Out of {odd + even} random 1-100 rolls, there were {even} even rolls and {odd} odd rolls.')

# Function to determine odd vs even
def oddEven(num):
    if ((num % 2) == 1):
        return 1
    else:
        return 2

# Call the main function
main()


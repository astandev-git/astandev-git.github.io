# Adam Stancil - 4/21/24
# This program reads a series of integers from a file named numbers.txt and calculates and displays their
# average.

def main():

    # Open file
    file1 = open('numbers.txt','r')

    # Initialize total variable
    total = 0   

    # Initialize loop counter variable
    counter = 0

    # Loop to read the file's contents 
    for line in file1:
        total = total + int(line)
        counter = counter + 1

    # Display the average of the numbers in the file
    print('The average of the numbers in the file is:', (total / counter))

# Call the main function
main()

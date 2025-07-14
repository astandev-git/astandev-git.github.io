# Adam Stancil - 2/11/24
# This program is designed to convert a temperature input in Celsius and output it in Fahrenheit.

inputTemp = input('What is the temperature in Celsius? ')  # Ask user for temperature
temp = float(inputTemp)                                    # Assign input to temp 

newTemp = ( (9/5) * temp ) + 32                            # Calculate new temp in Fahrenheit

print("")                                                  # Print buffer line
print("Tempearature in Fahrenheit: ", newTemp)             # Print new temp in Fahrenheit

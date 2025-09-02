const textInput = document.getElementById("text-input");
const checkButton = document.getElementById("check-btn");
const result = document.getElementById("result");

checkButton.addEventListener('click', () => {
    const inputValue = textInput.value;

    if (inputValue === '') {
        alert('Please input a value');
        return;
    }

    const cleanedString = inputValue.toLowerCase().replace(/[^a-z0-9]/g, '');
    const reversedString = cleanedString.split('').reverse().join('');

    if (cleanedString === reversedString) {
        result.textContent = `${inputValue} is a palindrome`;
    } else {
        result.textContent = `${inputValue} is not a palindrome`;
    }
});
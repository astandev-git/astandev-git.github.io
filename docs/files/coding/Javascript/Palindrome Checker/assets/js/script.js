const textInput1 = document.querySelector("#text-input");
const textInput2 = document.getElementById("text-input");

const result = document.querySelector("#result");
const checkButton = document.getElementById("check-btn");

let userInput1 = textInput1.value;
let userInput2 = textInput2.value;

console.log("1 "+textInput1);
console.log("2 "+userInput1);
console.log("3 "+textInput2);
console.log("4 "+userInput2);
console.log("5 "+textInput1.value);
console.log("6 "+textInput2.value);

//result.innerText = `x ${userInput1}  y ${userInput2} z ${textInput1.value} a ${textInput2.value}`;
result.innerText = "test";

if (userInput1===null){
console.log("userInput1 null");
result.innerText="userInput1 null";
}

if (userInput1===""){
console.log("userInput1 blank");
result.innerText="userInput1 blank";
}

if (userInput2===null){
console.log("userInput2 null");
result.innerText="userInput2 null";
}

if (userInput2===""){
console.log("userInput2 blank");
result.innerText="userInput2 blank";
}


const checkInput = () => {

  console.log("Test");
console.log(userInput1);
console.log(userInput2);
result.innerText = "test";

if (userInput1==="") {
       alert("Please input a value1");
       console.log("1x");
  }

  if (userInput2==="") {
       alert("Please input a value2");
       console.log("2x");
  }     
  
  if (textInput1==="") {
       alert("Please input a value3");
       console.log("3x");
  }

  if (textInput2==="") {
       alert("Please input a value4");
       console.log("4x");
  } 

console.log(userInput1);
console.log(userInput2);

let newResult = removeNonAlphanumeric(userInput1);
result.innerText = newResult.toLowerCase();
}

const removeNonAlphanumeric = (inputString) => {
  // The regular expression [^a-zA-Z0-9] matches any character that is NOT an uppercase letter (A-Z), a lowercase letter (a-z), or a digit (0-9). The 'g' flag ensures that ALL occurrences are replaced, not just the first.

  return inputString.replace(/[^a-zA-Z0-9]/g, ''); 
}

checkButton.addEventListener("click",checkInput);
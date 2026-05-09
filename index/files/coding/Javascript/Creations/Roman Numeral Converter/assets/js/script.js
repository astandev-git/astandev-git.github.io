const numberInput = document.getElementById("number");
const convertBtn = document.getElementById("convert-btn");
const output = document.getElementById("output");
 
const checkUserInput = () => {
  
  if (numberInput.value===""){
    output.innerText = "Please enter a valid number";    
    return;
  }  

  let inputInt = parseInt(numberInput.value);

  if (isNaN(inputInt) || inputInt < 1) {
    output.innerText =  "Please enter a number greater than or equal to 1";    
    return;    
  } 

  if (inputInt>=4000) {
    output.innerText =  "Please enter a number less than or equal to 3999";    
    return;    
  }  

output.innerText = "";

  while (inputInt>0) {
console.log("Start "+inputInt);    
    if (inputInt>=1000) {
      output.innerText += "M";
      inputInt-=1000;    
    } else if (inputInt>=900) {
      output.innerText += "CM";
      inputInt-=900;   
    } else if (inputInt>=500) {
      output.innerText += "D";
      inputInt-=500;    
    } else if (inputInt>=400) {
      output.innerText += "CD";
      inputInt-=400;      
    } else if (inputInt>=100) {
      output.innerText += "C";
      inputInt-=100;  
    } else if (inputInt>=90) {
      output.innerText += "XC";
      inputInt-=90;        
    } else if (inputInt>=50) {
      output.innerText += "L";
      inputInt-=50;    
    } else if (inputInt>=40) {
      output.innerText += "XL";
      inputInt-=40;       
    } else if (inputInt>=10) {
      output.innerText += "X";
      inputInt-=10;   
    } else if (inputInt>=9) {
      output.innerText += "IX";
      inputInt-=9;       
    } else if (inputInt>=5) {
      output.innerText += "V";
      inputInt-=5;     
    } else if (inputInt===4) {
      output.innerText += "IV";
      inputInt-=4;      
    } else if (inputInt>0) {
      output.innerText += "I";
      inputInt-=1;    
    } 
    console.log("Finish "+inputInt);
  } 
};

convertBtn.addEventListener("click", checkUserInput);

numberInput.addEventListener("keydown", (e) => {
  if (e.key === "Enter") {
    checkUserInput();
  }
});



/*
const result = document.getElementById("result");
const animationContainer = document.getElementById("animation-container");
const animationData = [
  {
    inputVal: 5,
    addElDelay: 1000,
    msg: 'decimalToBinary(5) returns "10" + 1 (5 % 2). Then it pops off the stack.',
    showMsgDelay: 15000,
    removeElDelay: 20000,
  },
  {
    inputVal: 2,
    addElDelay: 1500,
    msg: 'decimalToBinary(2) returns "1" + 0 (2 % 2) and gives that value to the stack below. Then it pops off the stack.',
    showMsgDelay: 10000,
    removeElDelay: 15000,
  },
  {
    inputVal: 1,
    addElDelay: 2000,
    msg: "decimalToBinary(1) returns '1' (base case) and gives that value to the stack below. Then it pops off the stack.",
    showMsgDelay: 5000,
    removeElDelay: 10000,
  }
];

const decimalToBinary = (input) => {
  if (input === 0 || input === 1) {
    return String(input);
  } else {
    return decimalToBinary(Math.floor(input / 2)) + (input % 2);
  }
};

const showAnimation = () => {
  result.innerText = "Call Stack Animation";

  animationData.forEach((obj) => {
    setTimeout(() => {
      animationContainer.innerHTML += `
        <p id="${obj.inputVal}" class="animation-frame">
          decimalToBinary(${obj.inputVal})
        </p>
      `;
    }, obj.addElDelay);

    setTimeout(() => {
      document.getElementById(obj.inputVal).textContent = obj.msg;
    }, obj.showMsgDelay);

    setTimeout(() => {
      document.getElementById(obj.inputVal).remove();
    }, obj.removeElDelay);
  });

  setTimeout(() => {
result.textContent = decimalToBinary(5);
  }, 20000);
};
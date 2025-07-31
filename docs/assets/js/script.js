/**/ 
function checkPersonal() {
  let inputPwd = document.getElementById("personalInput").value;
  let correctPwd = "stangit";
  if (inputPwd === correctPwd) {
    window.open("./personal/personal.html", "_blank", rel="noopener noreferrer")
  } else {
    alert("Incorrect password!");
  }
}

/**/ 
function checkTesting() {
  let inputPwd = document.getElementById("testingInput").value;
  let correctPwd = "standev";
  if (inputPwd === correctPwd) {
    window.open("./assets/testing.html", "_blank", rel="noopener noreferrer")
  } else {
    alert("Incorrect password!");
  }
}

/**/
function show() {
  var x = document.getElementById("test2");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}


/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~TEST AREA~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */ 
function showList(list) {
  document.getElementById(${list}).classList.toggle("show");
}

function showList2(list) {
  document.getElementById(list).classList.toggle("show");
}




/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~OLD UNUSED CODE~~~~~~~~~~~~~~~~~~~~~~~~~~~~
function showList3(list) {
  var x = document.getElementById(list);
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}





<li id="song-${song.id}" class="playlist-song">
      <button class="playlist-song-info" onclick="playSong(${song.id})">
list.classList.remove("

---

// Get the button, and when the user clicks on it, execute myFunction
document.getElementById("myBtn").onclick = function() {myFunction()};

// myFunction toggles between adding and removing the show class, which is used to hide and show the dropdown content 
function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}
      
---

const listBtn = document.querySelectorAll(.listBtn);
listBtn.addEventListener("click",()=>{
});

---

function download(filename, text) {
  var pom = document.createElement('a');
  pom.setAttribute('href', 'data:text/plain;charset=utf-8,' + 

  encodeURIComponent(text));
  pom.setAttribute('download', filename);

  pom.style.display = 'none';
  document.body.appendChild(pom);

  pom.click();

  document.body.removeChild(pom);
}

---

function addTextTXT()
{
    document.addtext.name.value = document.addtext.name.value + ".txt"
}

---

document.getElementById("container").style.display = "block";
document.getElementById("pwd").style.display = "none";

---

const myElement = document.getElementsByClassName("listBtn");

// Toggles the 'active' class on myElement
myElement.classList.toggle('active');

---

const buttons = document.querySelectorAll('.listBtn');

buttons.forEach(button => {
  button.addEventListener('click', () => {
    // Remove 'active' class from all buttons
    // buttons.forEach(button => button.classList.remove('active'));

    // Add 'active' class to the clicked button
    // button.classList.add('active');
    button.classList.toggle('active');
  });
});

---

$('ul li').click( function () {
    if($(this).find('ul').hasClass('active'))
    {
        $(this).find('ul').removeClass('active');
    }
    else{
        $(this).find('ul').addClass('active');
    }
});

---

// Get the textbox element
const form = document.getElementById('myForm');
const inputform = document.getElementById('inputform');

// Add an event listener for the 'keydown' event
inputform.addEventListener('keydown', function(event) {
 
  // Check if the ENTER key (key code 13) is pressed
  if (event.key === 'Enter') {
 
  // Prevent the default action (e.g., new line in a textarea)
  event.preventDefault();

  // Submit the form
  document.getElementById('myForm').submit();
  }

});  
  
*/

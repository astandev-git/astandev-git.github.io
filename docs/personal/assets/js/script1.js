/* Working */

/**/ 
function showList(list) {
  document.getElementById(list).classList.toggle("hiddenList");
}

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

// Get the textbox element
const form = document.getElementById('myForm');
//const inputform = document.getElementById('inputform');

// Add an event listener for the 'keydown' event
form.addEventListener('keydown', function(event) {
 
  // Check if the ENTER key (key code 13) is pressed
  if (event.key === 'Enter') {
 
  // Prevent the default action (e.g., new line in a textarea)
  event.preventDefault();

  // Submit the form
  document.getElementById('myForm').submit();
  }

});  

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~TEST AREA~~~~~~~~~~~~~~~~~~~~~~~~~~~~  */




/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~OLD UNUSED CODE~~~~~~~~~~~~~~~~~~~~~~~~~~~~


---

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
 
*/

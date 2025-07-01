const lessonBtn = document.getElementById("lessonBtn");
const creationBtn = document.getElementById("creationBtn");
const lessonList = document.getElementById("lessonList");
const creationList = document.getElementById("creationList");


lessonBtn.addEventListener("click", function() {
    if (lessonList.style.display === "none") {
        lessonList.style.display = "block";
    } else {
        lessonList.style.display = "none";
    }

creationBtn.addEventListener("click", function() {
    if (creationList.style.display === "none") {
        creationList.style.display = "block";
    } else {
        creationList.style.display = "none";
    }
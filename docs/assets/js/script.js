/*
$('ul li').click( function () {
    if($(this).find('ul').hasClass('active'))
    {
        $(this).find('ul').removeClass('active');
    }
    else{
        $(this).find('ul').addClass('active');
    }
});
*/

// Get the button, and when the user clicks on it, execute myFunction
document.getElementById("myBtn").onclick = function() {myFunction()};

/* myFunction toggles between adding and removing the show class, which is used to hide and show the dropdown content */
function myFunction() {
  document.getElementById("myDropdown").classList.toggle("show");
}

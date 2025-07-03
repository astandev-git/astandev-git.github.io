c:\Users\Adam\Desktop\Html\`Portfolio Page\docs\assets\js\script.js$('ul li').click( function () {
    if($(this).find('ul').hasClass('active'))
    {
        $(this).find('ul').removeClass('active');
    }
    else{
        $(this).find('ul').addClass('active');
    }
});
var __showMessageBox = function (theHtmlMessage) {
    var __theDivMessageBox = $("#divMessageBox");
    __theDivMessageBox.html(theHtmlMessage);
    __showHideDiv(__theDivMessageBox);
};

var __showHideDiv = function (theDiv) {
    theDiv.fadeIn(400).delay(5000).slideUp(500);
    //$( "div.first" ).slideUp( 300 ).delay( 800 ).fadeIn( 400 );
};




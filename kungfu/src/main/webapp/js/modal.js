// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
//btn.onclick = function() {
//    modal.style.display = "block";
//}

// When the user clicks on <span> (x), close the modal
//span.onclick = function() {
//    modal.style.display = "none";
//}


// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function setNumeroDataBuild(idBotaoBuild, dataBuild) {
	var numeroBuild = idBotaoBuild.innerHTML;
	var url = "http://sigprowiki.fla.serpro/publicacoes/builds/sigpro-login-2.0.0-SNAPSHOT-bXXXX.war";
	var urlComNumeroBuild = url.replace("XXXX", numeroBuild);
	document.getElementById('download-login').href = urlComNumeroBuild;	
	var url = "http://sigprowiki.fla.serpro/publicacoes/builds/sigpro2016-2.0.0-SNAPSHOT-DDDD-bXXXX.war";
	var urlComNumeroDataBuild = url.replace("XXXX", numeroBuild);
	urlComNumeroDataBuild = urlComNumeroDataBuild.replace("DDDD", dataBuild);
	document.getElementById('download-sigpro2016').href = urlComNumeroBuild;
	var titulo = "Clique para download da Build [XXXX]:";
	var tituloComNumeroBuild = titulo.replace("XXXX", numeroBuild);
	document.getElementById('tituloModal').innerHTML = tituloComNumeroBuild;
}


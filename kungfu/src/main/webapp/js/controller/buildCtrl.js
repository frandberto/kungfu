app.controller("buildCtrl", function($scope, $http) {

	$http.get("api/publicacao/listarUltimasPublicacoes").then(function (response) {
        $scope.ultimasPublicacoes = response.data;
    });
	
	$http.get("api/publicacao/listarPublicacoes").then(function (response) {
        $scope.publicacoes = response.data;
    });

});

app.controller("accordionUICtrl", function ($scope, $http) {
	$http.get("api/publicacao/listarAmbientes").then(function (response) {
        $scope.ambientes = response.data;
    });
	
	$http.get("api/publicacao/listarNumerosBuilds").then(function (response) {
        $scope.numerosBuilds = response.data;
    });
});

function publicar(nrBuildObject, ambienteObject) {
	nrBuild = nrBuildObject.value;
	ambiente = ambienteObject.value;
	if (nrBuild == "" || ambiente == "") {
		alert('Informe um número de build e ambiente');
		return;
	}
	document.location.assign('api/publicacao/' + nrBuild + '/' + ambiente);
}
angular.module('market').controller('registrationController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.functionRegistration = function (){
        $http.post(contextPath + 'registration', $scope.reguser).then(function (response) {
            if (response.data.token) {
                $http.default.headers.common.Authorization = 'Bearer' + response.data.token;
                $localStorage.marketUser = {username: $scope.reguser.username, token: response.data.token};
                $localStorage.reguser = null;
                $location.path("/");
            }
        });
    }
});
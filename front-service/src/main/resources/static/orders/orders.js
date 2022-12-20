angular.module('market').controller('orderController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/core/'
    $scope.loadOrders = function (){
        $http.get(contextPath + 'api/v1/orders').then(function (response){
            $scope.orders = response.data;
        })
    }

    $scope.loadOrders();
});
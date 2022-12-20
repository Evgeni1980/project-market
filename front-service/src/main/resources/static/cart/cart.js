angular.module('market').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/cart/';
    const coreContextPath = 'http://localhost:5555/core/';

    $scope.loadCart = function () {
        $http.get(contextPath + 'api/v1/cart/'+ $localStorage.marketGuestCartId).then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.clearCart = function () {
        $http.get(contextPath + 'api/v1/cart/' + $localStorage.marketGuestCartId + '/clear').then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.removeFromCart = function (productId) {
        $http.get(contextPath + 'api/v1/cart/' + $localStorage.marketGuestCartId + '/remove/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.addOrder = function (){
        $http.post(coreContextPath + 'api/v1/orders').then(function (response){
            alert('Заказ оформлен');
            $scope.clearCart();
        });
    }

    // $scope.createOrder = function () {
    //     $http.post(coreContextPath + 'api/v1/orders').then(function (response) {
    //         alert('Заказ оформлен');
    //             $scope.loadCart();
    //         });
    // }

    $scope.loadCart();
});
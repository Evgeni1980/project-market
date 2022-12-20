angular.module('market').controller('storeController', function ($scope, $http, $location,  $localStorage) {
    const contextPath = 'http://localhost:5555/core/';
    const cartContextPath = 'http://localhost:5555/cart/';

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                p: pageIndex,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.productList = response.data().item;
            $scope.generatePagesList($scope.productsPage.totalPages);
        });
    };

    $scope.showProductInfo = function (productId) {
        $http.get(contextPath + 'api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + 'api/v1/products/'+ productId).then(function (response){
            $scope.loadProducts();
        })
    }

    $scope.addToCart = function (productId) {
        $http.get(cartContextPath + 'api/v1/cart/' + $localStorage.marketGuestCartId + '/add/' + productId).then(function (response) {
        });
    }

    $scope.generatePagesList = function (totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }

    $scope.loadProducts();
});
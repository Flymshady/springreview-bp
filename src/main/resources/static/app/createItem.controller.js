var app = angular.module('app', []);

app.controller('itemController', ['$scope', '$http', function($scope, $http) {


    $scope.originalItem= {
        name:'Nazev',
        author:'Autor',
        type:'Album',
        genre:'rock',
        year:'2019',
        textShort:'',
        textLong:''
    };
    $scope.item = angular.copy($scope.originalItem);

    $scope.submitItemForm = function () {

        $http({
            method:'POST', url:'/api/items/admin/create', data:$scope.item,
            headers:{'Content-Type':'application/json'}
        }).then(successCallback, errorCallback);


        function successCallback(response){
            $scope.item = angular.copy($scope.originalItem);
            alert('Item saved successfully.');
        }
        function errorCallback(error){
            alert('Error occurred');
        }
    };
    $scope.resetForm = function () {
        $scope.item = angular.copy($scope.originalItem);
    }

}]);
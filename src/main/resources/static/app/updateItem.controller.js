var app = angular.module('app', []);

app.controller('itemUpdateController', ['$scope', '$http', '$window', function($scope, $http, $window) {

    var id= document.getElementById("itemId").textContent;
    var vm=this;
    vm.itemData={};
    vm.getById=getById;
    init();
    function init() {
        var id= document.getElementById("itemId").textContent;
        getById(id);

    };

    function getById(id) {
        var url="/api/items/detail/"+id;
        var itemDataPromise = $http.get(url);
        itemDataPromise.then(function (response) {
            vm.itemData=response;
            setToTheFields();

        });
    };

    function setToTheFields(){
        $scope.originalItem= {
            name: vm.itemData.data.name,
            author: vm.itemData.data.author,
            type:vm.itemData.data.type,
            genre:vm.itemData.data.genre,
            year:vm.itemData.data.year,
            textShort:vm.itemData.data.textShort,
            textLong:vm.itemData.data.textLong
        };

        $scope.item = angular.copy($scope.originalItem);
    };


    $scope.submitItemForm = function () {

        $http({
            method:'PUT', url:'/api/items/admin/update/'+id, data:$scope.item,
            headers:{'Content-Type':'application/json'}
        }).then(successCallback, errorCallback);


        function successCallback(response){
            $scope.item = angular.copy($scope.originalItem);
            alert('Item updated successfully.');
            $window.location.href='/admin/item/'+id;
        }
        function errorCallback(error){
            alert('Error occurred');
        }
    };
    $scope.resetForm = function () {
        $scope.item = angular.copy($scope.originalItem);
    }

}]);

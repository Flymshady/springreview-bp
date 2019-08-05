var app = angular.module('app', []);

app.controller('reviewController', ['$scope', '$http', function($scope, $http) {

    var id= document.getElementById("itemId").textContent;

    $scope.originalReview= {
        textShort:'',
        textLong:'',
        itemId:id
    };
    $scope.review = angular.copy($scope.originalReview);

    $scope.submitItemForm = function () {

        $http({
            method:'POST', url:'/api/reviews/create/'+$scope.review.itemId, data:$scope.review,
            headers:{'Content-Type':'application/json'}
        }).then(successCallback, errorCallback);


        function successCallback(response){
            $scope.review = angular.copy($scope.originalReview);
            alert('Review saved successfully.');
        }
        function errorCallback(error){
            alert('Error occurred');
        }
    };
    $scope.resetForm = function () {
        $scope.review = angular.copy($scope.originalReview);
    }

}]);
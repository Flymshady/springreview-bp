var app = angular.module('app', []);

app.controller('reviewUpdateController', ['$scope', '$http', '$window', function($scope, $http, $window) {

    var id= document.getElementById("reviewId").textContent;
    var vm=this;
    vm.reviewData={};
    vm.getById=getById;
    init();
    function init() {
        var id= document.getElementById("reviewId").textContent;
        getById(id);
    };

    function getById(id) {
        var url="/api/reviews/detail/"+id;
        var reviewDataPromise = $http.get(url);
        reviewDataPromise.then(function (response) {
            vm.reviewData=response;
            setToTheFields();
        });
    };

    function setToTheFields(){
        $scope.originalReview= {
            textShort:vm.reviewData.data.textShort,
            textLong:vm.reviewData.data.textLong,
            item:vm.reviewData.data.item
        };

        $scope.review = angular.copy($scope.originalReview);
    };


    $scope.submitReviewForm = function () {

        $http({
            method:'PUT', url:'/api/reviews/update/'+id+'/items/'+$scope.review.item.id, data:$scope.review,
            headers:{'Content-Type':'application/json'}
        }).then(successCallback, errorCallback);


        function successCallback(response){
            $scope.review = angular.copy($scope.originalReview);
            alert('Review updated successfully.');
            $window.location.href='/item/'+$scope.review.item.id+'/reviews';
        }
        function errorCallback(error){
            alert('Error occurred: '+error.data.message);
        }
    };
    $scope.resetForm = function () {
        $scope.review = angular.copy($scope.originalReview);
    }

}]);

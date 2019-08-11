(function () {
    'use strict';
    angular
        .module('app', [])
        .controller('ReviewsController', ReviewsController);
    ReviewsController.$inject=['$http'];

    function ReviewsController($http) {
        var vm=this;

        vm.reviews=[];
        vm.item;
        vm.getByItemId=getByItemId;
        vm.getItem=getItem;
        vm.deleteReview=deleteReview;

        init();
        function init() {
            var id= document.getElementById("itemId").textContent;
            getItem(id);
            getByItemId(id);
        }
        function getItem(id) {
            var url = "/api/items/detail/"+id;
            var itemsPromise = $http.get(url);
            itemsPromise.then(function (response) {
                vm.item=response.data;
            });
        }

        function getByItemId(id) {
            var url = "/api/reviews/item/"+id;
            var itemsPromise = $http.get(url);
            itemsPromise.then(function (response) {
                vm.reviews=response.data;
            });
        }
        function deleteReview(reviewId) {
            var itemId= document.getElementById("itemId").textContent;
            var url="/api/reviews/remove/"+reviewId+"/items/"+itemId;
            $http.delete(url).then(function (response) {
                vm.reviews=response.data;
            }).then(successCallback, errorCallback);

        }
        function successCallback(response) {
            alert('Review deleted successfully.');
        }
        function errorCallback(error) {
            alert('Error occurred: '+error.data.message);
        }
    }
})();
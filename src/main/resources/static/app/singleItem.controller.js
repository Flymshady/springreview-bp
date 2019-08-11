(function () {
    'use strict';
    angular
        .module('app', [])
        .controller('SingleItemController', SingleItemController);
    SingleItemController.$inject=['$http'];

    function SingleItemController($http) {
        var vm=this;
        vm.itemData={};
        vm.getById=getById;

        init();
        function init() {
            var id= document.getElementById("itemId").textContent;
            getById(id);
        }

        function getById(id) {
            var url="/api/items/detail/"+id;
            var itemDataPromise = $http.get(url);
            itemDataPromise.then(function (response) {
               vm.itemData=response;
            });
        }
    }
})();
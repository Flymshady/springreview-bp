(function () {
    'use strict';
    angular
        .module('app', [])
        .controller('ItemsController', ItemsController);
    ItemsController.$inject=['$http'];

    function ItemsController($http) {
        var vm=this;

        vm.items=[];
        vm.getAll=getAll;
        vm.getGenreRock=getGenreRock;
        vm.getGenrePop=getGenrePop;
        vm.getGenreMetal=getGenreMetal;
        vm.getGenreClassical=getGenreClassical;
        vm.getGenreHipHop=getGenreHipHop;
        vm.deleteItem = deleteItem;
        vm.getById=getById;

        init();
        function init() {
           getAll();

        }

        function getAll() {
            var url = "/api/items/all";
            var itemsPromise = $http.get(url);
            itemsPromise.then(function (response) {
                vm.items=response.data;
            });
        }

        function getGenreRock() {
            var url = "/api/items/genre/"+"rock";
            var itemsPromise = $http.get(url);
            itemsPromise.then(function (response) {
                vm.items=response.data;
            });
        }
        function getGenreMetal() {
            var url = "/api/items/genre/"+"metal";
            var itemsPromise = $http.get(url);
            itemsPromise.then(function (response) {
                vm.items=response.data;
            });
        }
        function getGenrePop() {
            var url = "/api/items/genre/"+"pop";
            var itemsPromise = $http.get(url);
            itemsPromise.then(function (response) {
                vm.items=response.data;
            });
        }
        function getGenreClassical() {
            var url = "/api/items/genre/"+"classical";
            var itemsPromise = $http.get(url);
            itemsPromise.then(function (response) {
                vm.items=response.data;
            });
        }
        function getGenreHipHop() {
            var url = "/api/items/genre/"+"hiphop";
            var itemsPromise = $http.get(url);
            itemsPromise.then(function (response) {
                vm.items=response.data;
            });
        }

        function deleteItem(id) {
            var url="/api/items/admin/remove/"+id;
            $http.delete(url).then(function (response) {
               vm.items=response.data;
            });

        }

        function getById(id) {
            var url="/api/items/detail/"+id;
            var itemsPromise = $http.get(url);
            itemsPromise.then(function (response) {
                vm.items=response.data;
            });
        }

    }
})();

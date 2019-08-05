var app = angular.module('app', []);

app.controller('personController', ['$scope', '$http', '$window', function($scope, $http, $window) {

    $scope.originalPerson= {
        name:'',
        username:'',
        password:''
    };
    $scope.person = angular.copy($scope.originalPerson);



    $scope.submitPersonForm = function () {
        console.log($scope.person.username);
        $http({
            method:'POST', url:'/api/persons/username', data:$scope.person.username,
            headers:{'Content-Type':'application/json'}
        }).then(successCallback1, errorCallback1);

        function successCallback1(response){
            console.log(response);
            if(response.data ==false){

                $http({
                    method:'POST', url:'/api/persons/create', data:$scope.person,
                    headers:{'Content-Type':'application/json'}
                }).then(successCallback, errorCallback);


                function successCallback(response){
                    $scope.person = angular.copy($scope.originalPerson);
                    alert('Person registered successfully.');
                    $window.location.href='/';
                }
                function errorCallback(error){
                    alert('Error occurred');
                }

            }else {
                alert('Username exists already');
            }
        }
        function errorCallback1(error){
            alert('Error occurred');
        }

    };
    $scope.resetForm = function () {
        $scope.person = angular.copy($scope.originalPerson);
    }

}]);
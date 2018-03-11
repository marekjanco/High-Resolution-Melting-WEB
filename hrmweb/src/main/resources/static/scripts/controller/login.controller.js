'use strict';

angular.module('hrm').controller('LoginController', function ($scope, $rootScope, $window, AuthService,
                                                                 $timeout, $http, $location) {
    var vm = this;

    vm.username = undefined;
    vm.password = undefined;
    vm.error = false;
    vm.logout = false;
    vm.errorMessage = 'Authentication failed. Please try again.';
    vm.logoutMessage = 'You have been successfully logged out.';

    vm.login = function () {
        $rootScope.loading = true;
        AuthService.login(vm.username, vm.password).then(
            function success(response) {
                $timeout(function () {
                    $location.path('/');
                    $scope.$emit('reinitialize', {});
                    $rootScope.loading = false;
                    }, 1500);
            });
    };

    vm.init = function () {
        if($location.$$search.logout){
            vm.logout = true;
        }
        if($location.$$search.error){
            vm.error = true;
        }
    };

    vm.init();

});
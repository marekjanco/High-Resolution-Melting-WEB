'use strict';

angular.module('hrm').controller('LoginController', function ($scope, $rootScope, $window, AuthService,
                                                              $timeout, $http, $location) {
    var vm = this;

    vm.username = undefined;
    vm.password = undefined;

    vm.login = function () {
        $rootScope.loading = true;
        AuthService.login(vm.username, vm.password).then(
            function success(response) {
                $timeout(function () {
                    $location.path('/');
                    $scope.$emit('reinitialize', {});
                    $rootScope.loading = false;
                }, 1500);
            }).finally(function () {
            $rootScope.loading = false;
        });
    };

    vm.init = function () {
    };

    vm.init();

});
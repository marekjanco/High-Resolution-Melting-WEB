'use strict';

angular.module('hrm')
    .controller('IndexController', function (AuthService, $location, $scope, $rootScope) {
        var vm = this;

        vm.username = undefined;
        vm.authenticated = false;

        vm.isAuth = function () {
            AuthService.getAccount().then(function (account) {
                vm.authenticated = (account.username !== null);
                vm.username = account.username;
            });
        };

        $scope.$on('reinitialize', function () {
            vm.isAuth();
            $rootScope.loading = false;
        });

        vm.logout = function () {
            AuthService.logout().then(function (response) {
                if (response.status === 200) {
                    vm.authenticated = false;
                    vm.username = undefined;
                }
                $location.path('/login').search('logout');
            });
        };

        vm.init = function () {
            $scope.$emit('reinitialize', {});
        };

        vm.init();
        return vm;
    });
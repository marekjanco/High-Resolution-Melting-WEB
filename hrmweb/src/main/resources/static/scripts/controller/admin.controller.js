'use strict';

angular.module('hrm')
    .controller('AdminController', function (AdminService) {
        var vm = this;
        vm.datasets = [];

        vm.getAll = function () {
            AdminService.getAll().then(function (data) {
                vm.datasets = data;
            });
        };

        vm.delete = function (name) {
            vm.delete = name;
            //AdminService.delete(vm.delete);
        };

        vm.init = function () {
            vm.getAll();
        };

        vm.init();
        return vm;
    });
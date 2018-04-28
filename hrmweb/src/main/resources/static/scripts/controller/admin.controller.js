'use strict';

angular.module('hrm')
    .controller('AdminController', function (AdminService, ValuesService, $location, $window) {
        var vm = this;
        vm.refCurves = [];
        vm.temperature = undefined;
        vm.success = false;
        vm.xAxis = undefined;
        vm.successMessage = "New dataset was successfully added to DB";

        vm.getAll = function () {
            AdminService.getAll().then(function (data) {
                vm.refCurves = data;
            });
        };

        vm.getTemperature = function () {
            ValuesService.getTemperature().then(function (data) {
                vm.temperature = data;
            });
        };

        vm.delete = function (name) {
            var result = confirm("Are you sure you want to delete reference curve: '" + name + "' ?");
            if (result) {
                AdminService.delete({name: name}).then(
                    function success(response) {
                        $window.location.reload();
                    });
            }
        };

        vm.init = function () {
            if ($location.$$search.success) {
                vm.successMessage = "New dataset was successfully added to DB";
                vm.success = true;
            }
            if ($location.$$search.updated) {
                vm.successMessage = "Dataset was successfully updated";
                vm.success = true;
            }
            vm.getAll();
            vm.getTemperature();
        };

        vm.init();
        return vm;
    });
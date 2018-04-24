'use strict';

angular.module('hrm')
    .controller('UpdateController', function (ValuesService, AdminService, $location) {
        var vm = this;
        vm.name = undefined;
        vm.error = false;
        vm.errorMessage = "Format of data is not OK - format of data should be - e.g., '-1.01 0 1.01 2.01...'";

        $('.ui.form')
            .form({
                fields: {
                    name: ['minLength[1]', 'empty'],
                    values: ['minLength[1]', 'empty']
                }
            }).submit(function () {
            vm.update();
        });

        vm.update = function () {
            if (vm.name == null || vm.name === "" || vm.values.length === 0) {
                return;
            }
            var data = ValuesService.parseInput(vm.values);
            var formatOK = ValuesService.checkFormatOfDataset(data);
            if (!formatOK) {
                vm.error = true;
                vm.errorMessage = "Format of data is not OK - format of data should be - e.g., '-1.01 0 1.01 2.01...'";
                return;
            }
            AdminService.update({name: vm.name, acronym: undefined, note: undefined, data: data}).then(
                function success(response) {
                    $location.path('/admin/datasets').search('updated');
                });
        };

        vm.init = function () {
            if ($location.$$search.name != null) {
                vm.name = $location.$$search.name;
            }
        };

        vm.init();
        return vm;
    });
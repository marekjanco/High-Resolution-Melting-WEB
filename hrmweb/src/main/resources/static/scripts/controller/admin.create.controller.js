'use strict';

angular.module('hrm')
    .controller('CreateController', function (ValuesService, AdminService, $location) {
        var vm = this;

        vm.values = [];
        vm.name = undefined;
        vm.error = false;
        vm.errorMessage = "Format of data is not OK - format of data should be - e.g., '-1.01 0 1.01 2.01...'";
        vm.names = [];

        $('.ui.form')
            .form({
                fields: {
                    name: ['minLength[1]', 'empty'],
                    values: ['minLength[1]', 'empty']
                }
            }).submit(function () {
            vm.addToDb();
        });

        vm.addToDb = function () {
            if (vm.name == null || vm.name === "" || vm.values.length === 0) {
                return;
            }
            var data = ValuesService.parseInput(vm.values);
            var formatOK = ValuesService.checkFormatOfNumberArray(data);
            if (!formatOK) {
                vm.error = true;
                vm.errorMessage = "Format of data is not OK - format of data should be - e.g., '-1.01 0 1.01 2.01...'";
                return;
            }
            if( vm.names.includes(vm.name)){
                vm.error = true;
                vm.errorMessage = "Dataset with name "+vm.name+" already exists";
                return;
            }
            AdminService.create({name: vm.name, data: data}).then(
                function success(response) {
                    $location.path('/admin/datasets').search('success');
                });
        };

        vm.init = function () {
            ValuesService.getAllNames().then(function (data) {
                vm.names = data;
            });
        };

        vm.init();

        return vm;
    });
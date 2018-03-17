'use strict';

angular.module('hrm')
    .controller('AdminController', function (AdminService, $location, $window) {
        var vm = this;
        vm.datasets = [];
        vm.success = false;
        vm.xAxis = undefined;
        vm.successMessage = "New dataset was successfully added to DB";

        vm.getAll = function () {
            AdminService.getAll().then(function (data) {
                for (var i = 0; i < data.length; ++i) {
                    if(data[i].name === "X-axis"){
                        vm.xAxis = data[i];
                    }else{
                        vm.datasets.push(data[i]);
                    }
                }
            });
        };

        vm.copyToClipboard = function (dataset) {
            var data = dataset.data;
            var text = "";
            for (var i = 0; i < data.length - 1; ++i) {
                text = text + data[i] + " ";
            }
            text = text + data[data.length - 1];
            var dummy = document.createElement("input");
            document.body.appendChild(dummy);
            dummy.setAttribute('value', text);
            dummy.select();
            document.execCommand("copy");
            document.body.removeChild(dummy);
        };

        vm.delete = function (name) {
            var result = confirm("Are you sure you want to delete '" + name + "' dataset?");
            if (result) {
                AdminService.delete({name: name}).then(
                    function success(response) {
                        $window.location.reload();
                    });
            }
        };

        vm.update = function (dataset) {
            $location.path('/admin/datasets/update').search({name: dataset.name});
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
        };

        vm.init();
        return vm;
    });
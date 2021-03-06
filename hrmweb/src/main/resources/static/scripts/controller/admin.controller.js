'use strict';

angular.module('hrm')
    .controller('AdminController', function (AdminService, ValuesService, FileService, $location, $window, FileSaver, Blob, $rootScope) {
        var vm = this;
        vm.refCurves = [];
        vm.temperature = undefined;
        vm.success = false;
        vm.successMessage = "New dataset was successfully added to DB";
        vm.newDataField = false;


        //checks if document is loaded
        $(document).ready(function () {
            $('form input').change(function () {
                var filename = document.getElementById('admin_excel_file').files[0].name;
                $('form p').text(filename);
            });
        });

        $('.large.question.circle.outline.icon')
            .popup({
                inline     : true,
                hoverable  : true,
            });

        vm.downloadData = function () {
            $rootScope.loading = true;
            FileService.generateFileOfRefCurves().then(function (response) {
                var header = response.headers('Content-Disposition');
                var fileName = header.split("=")[1].replace(/\"/gi, '');
                console.log('download', fileName);
                var blob = new Blob([response.data],
                    {type: 'application/vnd.openxmlformats-officedocument.presentationml.presentation'});
                FileSaver.saveAs(blob, fileName);
            }).finally(function () {
                $rootScope.loading = false;
            });
        };

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

        vm.addData = function () {
            vm.newDataField = !vm.newDataField;
        };

        vm.uploadFile = function () {
            var file = document.getElementById('admin_excel_file').files[0];
            if(file === undefined){
                $rootScope.showError = true;
                $rootScope.errorMessage = "There is no file to load";
                return;
            }
            $rootScope.loading = true;
            AdminService.uploadFileAndSave(file).then(function (data) {
                $window.location.reload();
                $rootScope.showSuccess = true;
                $rootScope.successMessage = "New reference curves were successfully added";
            },
            function(data) {
                $rootScope.showError = true;
                $rootScope.errorMessage = data.data.message;
            }).finally(function () {
                $rootScope.loading = false;
            });

        };

        vm.init = function () {
            vm.getAll();
            vm.getTemperature();
        };

        vm.init();
        return vm;
    });
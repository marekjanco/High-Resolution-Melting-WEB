'use strict';

angular.module('hrm')
    .controller('HomeController', function ($scope, ValuesService, ComputationService, FileService, $rootScope) {
        var vm = this;

        vm.names = [];
        vm.selectedNames = [];
        vm.curvesNumber = 0;
        vm.parsedData = undefined;
        vm.userDataLoaded = false;
        vm.result = undefined;

        // 'JQuery'
        //checks if document is loaded
        $(document).ready(function () {
            $('form input').change(function () {
                var filename = document.getElementById('excel_file').files[0].name;
                $('form p').text(filename);
            });
        });

        // dropdown action
        $('.dropdown')
            .dropdown({
                action: 'combo'
            })
        ;
        // end of 'JQuery'

        vm.viewUserDataInGraph = function () {
            vm.clearGraph();
            var parsedData = vm.parsedData;
            for (var i = 0; i < parsedData.length; ++i) {
                vm.addDataToGraph(parsedData[i].values, parsedData[i].name);
            }
        };

        vm.compute = function () {
            $rootScope.loading = true;
            ComputationService.compute(vm.parsedData).then(function (data) {
                vm.result = data;
            }).finally(function () {
                $rootScope.loading = false;
            });
        };

        vm.getCurve = function (name) {
            ValuesService.findByName(name).then(function (data) {
                $scope.data[vm.curvesNumber] = data;
                $scope.series[vm.curvesNumber] = name;
                vm.curvesNumber++;
            });
        };

        vm.addDataToGraph = function (data, name) {
            $scope.data[vm.curvesNumber] = data;
            $scope.series[vm.curvesNumber] = name;
            vm.curvesNumber++;
        };

        vm.drawGraph = function () {
            var array = $('#names').dropdown('get value');
            array = array.split(",");
            //remove all that all already drawn
            for (var i = array.length - 1; i > -1; --i) {
                if ($scope.series.indexOf(array[i]) > -1) {
                    array.splice(i, 1);
                }
            }
            for (var i = 0; i < array.length; ++i) {
                vm.getCurve(array[i]);
            }
        };

        vm.uploadFile = function () {
            var file = document.getElementById('excel_file').files[0];
            $rootScope.loading = true;
            FileService.uploadFile(file).then(function (data) {
                vm.parsedData = data;
                vm.userDataLoaded = true;
                console.log("parsedData: ",vm.parsedData);
            }).finally(function () {
                $rootScope.loading = false;
            });
        };

        vm.clearGraph = function () {
            vm.selectedNames = [];
            vm.curvesNumber = 0;
            $scope.data = [""];
            $scope.series = [];
            $('#names').dropdown('restore defaults');
        };

        vm.getLabels = function () {
            ValuesService.getTemperature().then(function (data) {
                $scope.labels = data.values;
                console.log($scope.labels);
            });
        };

        vm.getNames = function () {
            ValuesService.getAllNames().then(function (data) {
                $scope.names = data;
                console.log("names: ",$scope.names);
            });
        };

        vm.init = function () {
            vm.getLabels(); //get temperature - x axis
            vm.getNames(); // names and acronyms of reference curves to dropdown
            vm.clearGraph();
            vm.userDataLoaded = false;
        };

        vm.init();

        return vm;
    });
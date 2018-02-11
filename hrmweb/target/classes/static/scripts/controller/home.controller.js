'use strict';

angular.module('hrm')
    .controller('HomeController', function ($scope, ValuesService) {
        var vm = this;

        vm.names = [];
        vm.selectedNames = [];

        vm.labels = [];
        vm.data = [];
        vm.series = [];
        vm.numberOfDatasets = 0;

        $scope.labels = [];
        $scope.data = [];
        $scope.series = [];

        $('.dropdown')
            .dropdown({
                action: 'combo'
            })
        ;

        /*
        vm.inserts = [];
        vm.name = undefined;

        vm.createInsert = function () {
            var array = vm.inserts.split(" ");
            for(var i = 0; i < array.length; ++i){
                array[i] = array[i].replace(',', '.');
            }
            console.log("Insert into NUMBER_ARRAY (ID, NAME, NUMBERS) values (1, '"+vm.name+"', '"+array.toString()+"');");

        };
        */

        vm.getLabels = function () {
            ValuesService.findByName('X-axis').then(function (data) {
                vm.labels = data;
                $scope.labels = vm.labels;
            });
        };

        vm.addDataToGraph = function () {
            var array = vm.data.split(" ");
            vm.data = $scope.data;
            for (var i = 0; i < array.length; ++i) {
                array[i] = array[i].replace(',', '.');
            }
            vm.data[vm.numberOfDatasets] = array;
            $scope.data[vm.numberOfDatasets] = array;
            $scope.addedDataSet = vm.numberOfDatasets;
            vm.series[vm.numberOfDatasets] = "raw data";
            $scope.series = vm.series;
            vm.numberOfDatasets++;
            vm.computeDifference();
        };

        vm.getDataSet = function (name) {
            ValuesService.findByName(name).then(function (data) {
                vm.data[vm.numberOfDatasets] = data;
                $scope.data[vm.numberOfDatasets] = vm.data[vm.numberOfDatasets];
                vm.series[vm.numberOfDatasets] = name;
                $scope.series = vm.series;
                vm.numberOfDatasets++;
            });
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
                vm.getDataSet(array[i]);
            }
        };

        vm.clearGraph = function () {
            vm.data = [];
            vm.selectedNames = [];
            $scope.data = [];
            vm.series = [];
            $scope.series = [];
            vm.numberOfDatasets = 0;
            $scope.matchDataSet = undefined;
            $scope.distance = undefined;
            $('#names').dropdown('restore defaults');
        };

        vm.getNames = function () {
            ValuesService.getAllNames().then(function (data) {
                vm.names = data;
                $scope.names = vm.names;
            });
        };

        vm.computeDifference = function () {
            ValuesService.compute(vm.data[$scope.addedDataSet]).then(function (data) {
                $scope.matchDataSet = data[1].name;
                $scope.distance = data[0];
                vm.getDataSet($scope.matchDataSet);
            });
        };

        vm.init = function () {
            vm.getLabels();
            vm.getNames();
            $scope.data = vm.data;
            $scope.series = vm.series;
        };

        vm.init();

        return vm;
    });
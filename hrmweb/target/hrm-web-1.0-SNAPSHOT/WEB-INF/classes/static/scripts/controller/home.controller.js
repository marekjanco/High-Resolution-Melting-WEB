'use strict';

angular.module('hrm')
    .controller('HomeController', function ($scope) {
        var vm = this;
        vm.labels = [];
        vm.numberOfDatasets = 0;
        vm.data = [];

        $scope.labels = [];
        $scope.series = ['Series A', 'Series B'];
        $scope.data = [];

        vm.addLabels = function () {
            var array = vm.labels.split(" ");
            for(var i = 0; i < array.length; ++i){
                array[i] = array[i].replace(',', '.');
            }
            $scope.labels = array;
        };

        vm.addData = function () {
            var array = vm.data.split(" ");
            for(var i = 0; i < array.length; ++i){
                array[i] = array[i].replace(',', '.');
            }
            $scope.data[vm.numberOfDatasets++] = array;
            console.log(array);
        };

        return vm;
    });
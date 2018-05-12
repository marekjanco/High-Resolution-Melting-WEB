'use strict';

angular.module('hrm')
    .controller('HomeController', function ($scope, ValuesService,
                                            ComputationService, FileService, $rootScope) {
        var vm = this;

        vm.names = [];
        vm.selectedNames = [];
        vm.curvesNumber = 0;
        vm.parsedData = undefined;
        vm.userDataLoaded = false;
        vm.result = undefined;
        vm.userDataField = false;

        // 'JQuery'
        //checks if document is loaded
        $(document).ready(function () {
            $('form input').change(function () {
                var filename = document.getElementById('excel_file').files[0].name;
                $('form p').text(filename);
            });
        });

        //show popup
        $('.large.question.circle.outline.icon')
            .popup({
                inline     : true,
                hoverable  : true,
            });

        // dropdown action
        $('.dropdown')
            .dropdown({
                action: 'combo'
            })
        ;
        // end of 'JQuery'

        vm.viewUserDataInGraph = function () {
            if(vm.parsedData === undefined){
                vm.showError();
                return;
            }
            vm.clearGraph();
            var parsedData = vm.parsedData;
            for (var i = 0; i < parsedData.length; ++i) {
                vm.addDataToGraph(parsedData[i].values, parsedData[i].name);
            }
        };

        vm.compute = function () {
            if(vm.parsedData === undefined){
                vm.showError();
                return;
            }
            $rootScope.loading = true;
            ComputationService.compute(vm.parsedData).then(function (data) {
                vm.result = data;
                vm.showResultInGraph();
            }).finally(function () {
                $rootScope.loading = false;
            });
        };

        vm.loadNewData = function () {
            vm.clearGraph();
            vm.closeResult();
            vm.parsedData = undefined;
            vm.userDataLoaded = false;
        };

        vm.showAverageCurve = function(){
            if(vm.parsedData === undefined){
                vm.showError();
                return;
            }
            $rootScope.loading = true;
            ComputationService.getAverageCurve(vm.parsedData).then(function (data) {
                vm.addDataToGraph(data.values, data.name);
            }).finally(function () {
                $rootScope.loading = false;
            });
        };

        vm.getCurve = function (name) {
            if(name === undefined || name === ""){
                return;
            }
            ValuesService.findByName(name).then(function (data) {
                vm.addDataToGraph(data.values, name);
            });
        };


        vm.getCurveWithInterval = function (name) {
            ValuesService.findByName(name).then(function (data) {
                vm.addDataToGraph(data.values, name);
                vm.addIntervalToGraph(data.values, data.errorMargin.values, name);
            });
        };

        vm.addIntervalToGraph = function(data, margin, name){
            var above = [];
            var below = [];
            for(var i = 0; i < data.length; ++i){
                above.push(data[i] + margin[i]);
                below.push(data[i] - margin[i]);
            }
            vm.addDataToGraph(above, name+"+");
            vm.addDataToGraph(below, name+"-");
        };

        vm.showResultInGraph = function () {
            if(vm.result === undefined){
                $rootScope.showError = true;
                $rootScope.errorMessage = "No result to show";
                return;
            }
            vm.clearGraph();
            vm.addDataToGraph(vm.result.matched.values, "matched");
            vm.addDataToGraph(vm.result.notMatched.values, "not_matched");
            vm.getCurveWithInterval(vm.result.refCurveName);
            vm.setResultColors();
        };

        vm.setResultColors = function(){
            $scope.colors =  ['#00FF00','#FF0000', '#0000ff','#05fce3','#05fce3', '#FF5252', '#3339FF', '#FF33FC', '#33FF33', '#33FFDA', '#FF8A80', '#FCFF33'];
        };

        vm.setDefaultColors = function () {
            $scope.colors =  ['#FF5252', '#3339FF', '#FF33FC', '#33FF33', '#33FFDA', '#FF8A80', '#FCFF33'];
        };

        vm.addDataToGraph = function (data, name) {
            if(name === 'temperature'){
                return;
            }
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
            console.log(array);
            for (var i = 0; i < array.length; ++i) {
                vm.getCurve(array[i]);
            }
        };

        vm.uploadFile = function () {
            var file = document.getElementById('excel_file').files[0];
            if(file === undefined){
                $rootScope.showError = true;
                $rootScope.errorMessage = "There is no file to load";
                return;
            }
            $rootScope.loading = true;
            FileService.uploadFile(file).then(function (data) {
                if(data === undefined){
                    return;
                }
                vm.parsedData = data;
                vm.userDataLoaded = true;
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

        vm.closeResult = function () {
            vm.result = undefined;
            vm.setDefaultColors();
            vm.clearGraph();
        };

        vm.showError = function () {
            $rootScope.showError = true;
            $rootScope.errorMessage = "User data are not loaded yet.";
        };

        vm.addUserDataField = function () {
          vm.userDataField = ! vm.userDataField;
        };

        vm.getLabels = function () {
            ValuesService.getTemperature().then(function (data) {
                $scope.labels = data.values;
            });
        };

        vm.getNames = function () {
            ValuesService.getAllNames().then(function (data) {
                $scope.names = data;
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
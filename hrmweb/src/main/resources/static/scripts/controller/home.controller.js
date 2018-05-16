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
        vm.confidenceIntervalInPerc = 95;
        vm.pointsShown = true;


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
                vm.addDataToGraph(parsedData[i].values, parsedData[i].name, false);
            }
        };

        vm.compute = function () {
            if(vm.parsedData === undefined){
                vm.showError();
                return;
            }
            if(vm.confidenceIntervalInPerc <= 75 || vm.confidenceIntervalInPerc >= 100){
                $rootScope.showError = true;
                $rootScope.errorMessage = "Confidence interval has to be bigger than 75 and less than 100";
                return;
            }
            $rootScope.loading = true;
            ComputationService.compute(vm.parsedData, vm.confidenceIntervalInPerc).then(function (data) {
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
                vm.addDataToGraph(data.values, data.name, false);
            }).finally(function () {
                $rootScope.loading = false;
            });
        };

        vm.getCurve = function (name) {
            if(name === undefined || name === ""){
                return;
            }
            ValuesService.findByName(name).then(function (data) {
                vm.addDataToGraph(data.values, name, false);
            });
        };


        vm.addCurveWithInterval = function (refCurve) {
            vm.addDataToGraph(refCurve.values, refCurve.name, false);
            vm.addIntervalToGraph(refCurve.values, refCurve.errorMargin.values, refCurve.name);

        };

        vm.addIntervalToGraph = function(data, margin, name){
            var above = [];
            var below = [];
            for(var i = 0; i < data.length; ++i){
                above.push(data[i] + margin[i]);
                below.push(data[i] - margin[i]);
            }
            vm.addDataToGraph(above, name+"+", false);
            vm.addDataToGraph(below, name+"-", true);
        };

        vm.showResultInGraph = function () {
            if(vm.result === undefined){
                $rootScope.showError = true;
                $rootScope.errorMessage = "No result to show";
                return;
            }
            vm.clearGraph();
            vm.addDataToGraph(vm.result.matched.values, "matched", false);
            vm.addDataToGraph(vm.result.notMatched.values, "not_matched", false);
            vm.addCurveWithInterval(vm.result.matchedRefCurve);
        };

        vm.addDataToGraph = function (data, name, lastResultCurve) {
            if (name === 'temperature') {
                return;
            }
            if(vm.curvesNumber === 0){
                $scope.colors = [];
            }
            if(!lastResultCurve) {
                var color = vm.colors[vm.curvesNumber % vm.colors.length];
                $scope.colors.push(color);
            }else{
                $scope.colors = [];
                $scope.colors.push(vm.colors[5]);
                $scope.colors.push(vm.colors[0]);
                $scope.colors.push(vm.colors[vm.colors.length - 1]);
                $scope.colors.push(vm.colors[vm.colors.length - 1]);
                $scope.colors.push(vm.colors[vm.colors.length - 1]);
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
            $scope.colors = [];
            $('#names').dropdown('restore defaults');
        };

        vm.closeResult = function () {
            vm.result = undefined;
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

        vm.showPoints = function () {
            if(!vm.pointsShown) {
                $scope.options = {
                    responsive: true,
                    elements: {
                        point: {
                            radius: 0
                        }
                    },
                    labels:{
                        display: true
                    }
                };
            }else{
                $scope.options = {
                    responsive: true,
                    elements: {
                        point: {
                            radius: 2
                        }
                    },labels:{
                        display: true
                    }
                };
            }
        };

        vm.init = function () {
            vm.getLabels(); //get temperature - x axis
            vm.getNames(); // names and acronyms of reference curves to dropdown
            vm.clearGraph();
            vm.showPoints();
            vm.userDataLoaded = false;
        };

        vm.init();

        vm.colors = [{
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FF5252',
            borderColor: '#FF5252',
            pointBorderColor: '#FF5252',
        },{
            backgroundColor: 'transparent',
            pointBackgroundColor: '#33FFDA',
            borderColor: '#33FFDA',
            pointBorderColor: '#33FFDA',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#3339FF',
            borderColor: '#3339FF',
            pointBorderColor: '#3339FF',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FCFF33',
            borderColor: '#FCFF33',
            pointBorderColor: '#FCFF33',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FF33FC',
            borderColor: '#FF33FC',
            pointBorderColor: '#FF33FC',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#33FF33',
            borderColor: '#33FF33',
            pointBorderColor: '#33FF33',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FF8A80',
            borderColor: '#FF8A80',
            pointBorderColor: '#FF8A80',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FCFF33',
            borderColor: '#FCFF33',
            pointBorderColor: '#FCFF33',
        },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#333300',
                borderColor: '#333300',
                pointBorderColor: '#333300',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#993333',
                borderColor: '#993333',
                pointBorderColor: '#993333',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#ff9900',
                borderColor: '#ff9900',
                pointBorderColor: '#ff9900',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#cccc00',
                borderColor: '#cccc00',
                pointBorderColor: '#cccc00',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#666699',
                borderColor: '#666699',
                pointBorderColor: '#666699',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#999966',
                borderColor: '#999966',
                pointBorderColor: '#999966',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#f2ceff',
                borderColor: '#f2ceff',
                pointBorderColor: '#f2ceff',
            }
        ];

        return vm;
    });
'use strict';

angular.module('hrm')
    .controller('HomeController', function ($scope, ValuesService) {
        var vm = this;

        vm.names = [];
        vm.selectedNames = [];
        vm.numberOfDatasets = 0;

        // 'JQuery'
        // add inputs dynamically
        $(document).ready(function() {
            var max_fields      = 6;
            var wrapper         = $(".input_fields_wrap");
            var add_button      = $(".add_field_button");

            var x = 1;
            $(add_button).click(function(e){
                e.preventDefault();
                if(x < max_fields){
                    x++;
                    $(wrapper).append('<div class="ui input" style="display: block;"><input class="userDataInput" type="text" name="inputs[]">&nbsp;<a href="#" class="remove_field"><i class="small inverted circular red minus link icon" style="margin-top:10px"></i></a></div>'); //add input box
                }
            });

            $(wrapper).on("click",".remove_field", function(e){
                e.preventDefault(); $(this).parent('div').remove(); x--;
            })
        });

        // dropdown action
        $('.dropdown')
            .dropdown({
                action: 'combo'
            })
        ;
        // end of 'JQuery'

        vm.viewInGraph = function () {
            vm.clearGraph();
            var values = vm.getValuesFromInputs();
            for(var i = 0; i < values.length; ++i){
                vm.addDataToGraph(values[i], "input_"+i);
            }
        };

        vm.getLabels = function () {
            ValuesService.findByName('X-axis').then(function (data) {
                $scope.labels = data;
            });
        };

        vm.addDataToGraph = function (data, name) {
            $scope.data[vm.numberOfDatasets] = data;
            $scope.series[vm.numberOfDatasets] = name;
            vm.numberOfDatasets++;
            //vm.computeDifference();
        };

        vm.getDataSet = function (name) {
            ValuesService.findByName(name).then(function (data) {
                $scope.data[vm.numberOfDatasets] = data;
                $scope.series[vm.numberOfDatasets] = name;
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

        vm.computeDifference = function () {
        /*    ValuesService.compute(vm.data[$scope.addedDataSet]).then(function (data) {
                $scope.matchDataSet = data[1].name;
                $scope.distance = data[0];
                vm.getDataSet($scope.matchDataSet);
            });
        */
        };

        vm.getValuesFromInputs = function () {
            var values = [];
            $("input[name='inputs[]']").each(function() {
                values.push($(this).val());
            });
            for(var i = 0; i < values.length; ++i){
                values[i] = ValuesService.parseInput(values[i]);
            }
            return values;
        };


        vm.clearGraph = function () {
            vm.selectedNames = [];
            vm.numberOfDatasets = 0;
            $scope.data = [""];
            $scope.series = [];
            $scope.matchDataSet = undefined;
            $scope.distance = undefined;
            $('#names').dropdown('restore defaults');
        };

        vm.getNames = function () {
            ValuesService.getAllNames().then(function (data) {
                $scope.names = data;
                vm.names = data;
            });
        };

        vm.init = function () {
            vm.getLabels();
            vm.getNames();
            $scope.data = [""];
            vm.selectedNames = [];
            vm.numberOfDatasets = 0;
            $scope.data = [""];
            $scope.series = [];
            $scope.matchDataSet = undefined;
            $scope.distance = undefined;
            $('#names').dropdown('restore defaults');
        };

        vm.init();

        return vm;
    });
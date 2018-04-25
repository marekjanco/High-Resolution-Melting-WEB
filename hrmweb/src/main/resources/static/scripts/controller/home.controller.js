'use strict';

angular.module('hrm')
    .controller('HomeController', function ($scope, ValuesService, FileService, $rootScope) {
        var vm = this;

        vm.names = [];
        vm.selectedNames = [];
        vm.numberOfDatasets = 0;

        // 'JQuery'
        /*
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
        */
        $(document).ready(function () {
            $('form input').change(function () {
                $('form p').text(this.files.length + " file(s) selected");
            });
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
            for (var i = 0; i < values.length; ++i) {
                vm.addDataToGraph(values[i], "input_" + i);
            }
        };

        vm.showMedianInGraph = function () {
            vm.clearGraph();
            var values = vm.getValuesFromInputs();
            var length = values[0].length;
            var ret = [];
            for (var i = 0; i < length; ++i) {
                var temp = [];
                for (var j = 0; j < values.length; ++j) {
                    if (values[j][i] != null) {
                        temp.push(values[j][i]);
                    }
                }
                ret.push(vm.median(temp));
            }
            vm.addDataToGraph(ret, "median of values");
        };

        vm.median = function (values) {
            if (values.length === 0) return 0;
            values.sort(function (a, b) {
                return a - b;
            });
            var half = Math.floor(values.length / 2);
            if (values.length % 2)
                return values[half];
            else
                return (Number(values[half - 1]) + Number(values[half])) / 2.0;
        };

        vm.getLabels = function () {
            ValuesService.findByName('X-axis').then(function (data) {
                $scope.labels = data;
            });
        };

        vm.getDataSet = function (name) {
            ValuesService.findByName(name).then(function (data) {
                $scope.data[vm.numberOfDatasets] = data;
                $scope.series[vm.numberOfDatasets] = name;
                vm.numberOfDatasets++;
            });
        };

        vm.addDataToGraph = function (data, name) {
            $scope.data[vm.numberOfDatasets] = data;
            $scope.series[vm.numberOfDatasets] = name;
            vm.numberOfDatasets++;
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

        vm.uploadFile = function () {
            var file = document.getElementById('excel_file').files[0];
            $rootScope.loading = true;
            FileService.uploadFile(file).then(function (data) {
                $rootScope.loading = false;
            });
        };

        vm.getValuesFromInputs = function () {
            var values = [];
            $("input[name='inputs[]']").each(function () {
                values.push($(this).val());
            });
            for (var i = 0; i < values.length; ++i) {
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
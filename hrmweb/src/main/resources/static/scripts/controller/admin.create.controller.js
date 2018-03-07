'use strict';

angular.module('hrm')
    .controller('CreateController', function (ValuesService, AdminService) {
        var vm = this;

        vm.values = [];
        vm.name = undefined;

        $('.ui.form')
            .form({
                fields: {
                    name     : ['minLength[1]', 'empty'],
                    values   : ['minLength[1]', 'empty']
                }
            }).submit(function() {
                vm.addToDb();
            })
        ;

        vm.addToDb = function (){
            if(vm.name == null || vm.name === "" || vm.values.length === 0){
                return;
            }
            var data = ValuesService.parseInput(vm.values);
            var formatOK = ValuesService.checkFormatOfNumberArray(data);
            if(formatOK){
                AdminService.create({name: vm.name, data: data});
            }else{
                //todo error message
            }
        };

        return vm;
    });
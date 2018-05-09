'use strict';

angular.module('hrm')
    .factory('ComputationService', function ($http, $rootScope) {
        return {
            compute: function (data) {
                var config = {
                    headers : {
                        'Content-Type': 'application/json'
                    }
                };
                return $http.post('/computation/compareData',data, config).then(
                    function (response) {
                    return response.data;
                },
                    function(data) {
                        $rootScope.showError = true;
                        $rootScope.errorMessage = 'error occured while computating ...' + data.data.message;
                    });
            },
            getAverageCurve: function (data) {
                var config = {
                    headers : {
                        'Content-Type': 'application/json'
                    }
                };
                return $http.post('/computation/getAverageCurve',data, config).then(function (response) {
                    return response.data;
                })
            }
        }
    });
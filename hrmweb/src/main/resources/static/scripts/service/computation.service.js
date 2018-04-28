'use strict';

angular.module('hrm')
    .factory('ComputationService', function ($http) {
        return {
            compute: function (data) {
                console.log("comp data, ",data);
                var config = {
                    headers : {
                        'Content-Type': 'application/json',
                        'Accept': 'text/plain'
                    }
                };
                return $http.post('/computation/compareData',data, config).then(function (response) {
                    return response.data;
                })
            }
        }
    });
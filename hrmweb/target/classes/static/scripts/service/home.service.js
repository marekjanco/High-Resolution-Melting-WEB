'use strict';

angular.module('hrm')
    .factory('ValuesService', function ($http) {
        return {
            getAllNames: function () {
                return $http.get('/numberArray/getAllNames').then(function (response) {
                    return response.data;
                })
            },
            compute: function (data) {
                return $http.get('/numberArray/compute',{
                    params: {
                        data: data
                    }
                }).then(function (response) {
                    return response.data;
                })
            },
            findByName: function (name) {
                return $http.get('/numberArray/findByName',{
                    params: {
                        name: name
                    }
                }).then(function (response) {
                    return response.data;
                })
            }
        }
    });
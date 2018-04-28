'use strict';

angular.module('hrm')
    .factory('ValuesService', function ($http) {
        return {
            getAllNames: function () {
                return $http.get('/refCurve/getAllNames').then(function (response) {
                    return response.data;
                })
            },
            getTemperature: function () {
                return $http.get('/refCurve/getTemperature').then(function (response) {
                    return response.data;
                })
            },
            findByName: function (name) {
                return $http.get('/refCurve/findByName', {
                    params: {
                        name: name
                    }
                }).then(function (response) {
                    return response.data;
                })
            }
        }
    });
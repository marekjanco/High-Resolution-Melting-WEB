'use strict';

angular.module('hrm')
    .factory('ValuesService', function ($http) {
        return {
            getAllNames: function () {
                return $http.get('/dataset/getAllNames').then(function (response) {
                    return response.data;
                })
            },
            compute: function (data) {
                return $http.get('/dataset/compute', {
                    params: {
                        data: data
                    }
                }).then(function (response) {
                    return response.data;
                })
            },
            findByName: function (name) {
                return $http.get('/dataset/findByName', {
                    params: {
                        name: name
                    }
                }).then(function (response) {
                    return response.data;
                })
            },
            parseInput: function (data) {
                var array = data.split(" ");
                for (var i = 0; i < array.length; ++i) {
                    array[i] = array[i].replace(',', '.');
                }
                return array;
            },
            checkFormatOfdataset: function (data) {
                for (var i = 0; i < data.length; ++i) {
                    var n = parseFloat(data[i]);
                    if(n.toString() !== data[i]){
                        return false;
                    }
                    if (Number(n) === n) {
                        continue;
                    } else {
                        return false;
                    }
                }
                return true;
            }

        }
    });
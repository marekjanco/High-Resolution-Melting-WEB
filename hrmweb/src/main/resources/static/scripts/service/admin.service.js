'use strict';

angular.module('hrm')
    .factory('AdminService', function ($http) {
        return {
            getAll: function () {
                return $http.get('/admin/getAll').then(function (response) {
                    return response.data;
                })
            },
            create:  function (object) {
                return $http.put('/admin/create',object).then(function (response) {
                    return response.data;
                })
            },
            delete:  function (object) {
            return $http.put('/admin/delete',object).then(function (response) {
                return response.data;
            })
        }
        }
    });
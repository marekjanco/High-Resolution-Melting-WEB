'use strict';

angular.module('hrm')
    .factory('AdminService', function ($http) {
        return {
            getAll: function () {
                return $http.get('/admin/refCurve/getAll').then(function (response) {
                    return response.data;
                })
            },
            create: function (object) {
                return $http.put('/admin/refCurve/create', object).then(function (response) {
                    return response.data;
                })
            },
            delete: function (object) {
                return $http.put('/admin/refCurve/delete', object).then(function (response) {
                    return response.data;
                })
            }
        }
    });
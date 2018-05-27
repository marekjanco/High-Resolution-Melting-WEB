'use strict';

angular.module('hrm')
    .factory('AdminService', function ($http, $rootScope) {
        return {
            getAll: function () {
                return $http.get('/admin/refCurve/getAll').then(function (response) {
                    return response.data;
                })
            },
            delete: function (object) {
                return $http.put('/admin/refCurve/delete', object).then(function (response) {
                    $rootScope.showSuccess = true;
                    $rootScope.successMessage = 'Reference dataset was successfully deleted';
                    return response.data;
                })
            },uploadFileAndSave: function (file) {
                var fd = new FormData();
                fd.append('file', file);
                return $http.post('/admin/uploadExcelAndSave', fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(function (response) {
                    return response.data;
                });
            }
        }
    });
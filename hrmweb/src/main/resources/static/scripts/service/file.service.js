'use strict';

angular.module('hrm')
    .factory('FileService', function ($http, $rootScope) {
        return {
            uploadFile: function (file) {
                console.log('upload service', file);
                var fd = new FormData();
                fd.append('file', file);
                return $http.post('/file/uploadExcel', fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(
                    function (response) {
                        return response.data;
                },
                    function(data) {
                        $rootScope.showError = true;
                        $rootScope.errorMessage = data.data.message;
                    });
            },
            generateFileOfRefCurves: function () {
                return $http.get('/file/generateFileDbData',{headers: {'Content-type': 'application/json'},
                    responseType: "arraybuffer"}).then(function (response) {
                    return response;
                },
                    function(response) {
                        $rootScope.showError = true;
                        $rootScope.errorMessage = response.data.message;
                    });
            }
        }}
    );
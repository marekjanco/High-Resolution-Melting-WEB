'use strict';

angular.module('hrm')
    .factory('FileService', function ($http, $q) {
        return {
            uploadFile: function (file) {
                console.log('upload service', file);
                var fd = new FormData();
                fd.append('file', file);
                return $http.post('/file/uploadExcel', fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(function (response) {
                    return response.data;
                })
            },
            /*uploadFileToUrl: function (file, uploadUrl) {
                var deffered = $q.defer();
                var responseData;
                var fd = new FormData();
                fd.append('file', file);
                console.log('up file, ', file);
                return $http.post(uploadUrl, fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                })
                    .success(function (response) {
                        console.log(response);
                        responseData = response;
                        deffered.resolve(response);
                        return deffered.promise;
                    })
                    .error(function (error) {
                        deffered.reject(error);
                        return deffered.promise;
                    });
            }
            */
        }}
    );
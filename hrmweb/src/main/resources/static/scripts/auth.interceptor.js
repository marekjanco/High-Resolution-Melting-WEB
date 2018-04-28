angular.module('hrm').factory('AuthInterceptor',
    function AuthInterceptor($q, $rootScope, $location) {
        return {
            request: function (config) {
                config.headers['X-Requested-With'] = 'XMLHttpRequest';
                return config;
            },
            response: function (response) {
                return response;
            },
            responseError: function (response) {
                if (response.status === 401 || response.status === 403) {
                    $rootScope.loading = false;
                    $location.path('/login').search('error');
                }

                return $q.reject(response);
            }
        };
    });
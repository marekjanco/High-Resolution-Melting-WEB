'use strict';

angular.module('hrm')
    .factory('AuthService', function ($window, $http) {
        var credentials = {username: undefined, password: undefined};
        return {
            authenticate: function (credentials, callback) {
                var headers = credentials ? {
                    authorization: "Basic "
                    + btoa(credentials.username + ":" + credentials.password)
                } : {};
                return $http.get('user', {headers: headers}).then(function (response) {
                    callback && callback();
                });

            },
            login: function (username, password) {
                var config = {
                    params: {
                        username: credentials.username,
                        password: credentials.password
                    },
                    ignoreAuthModule: 'ignoreAuthModule',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                };
                config.headers['X-Requested-With'] = 'XMLHttpRequest';
                credentials.username = username;
                credentials.password = password;
                var response;
                $http.post('authenticate', '', config).then(
                    response = this.authenticate(credentials, function (response) {
                        return response;
                    })
                ).
                catch(function (err) {
                    this.logout();
                }.bind(this));
                return response;
            },
            logout: function () {
                return $http.post('logout', {}).then(
                    function (response) {
                        return response;
                    })
            },
            getAccount: function () {
                return $http.get('currentUser').
                then(function (response) {
                    return response.data;
                }).catch(   function(error) {
                    //anonymous user
                    return {username: null};
                });
            }

        };
    });
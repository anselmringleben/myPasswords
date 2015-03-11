var services = angular.module('myPassword.services', [ 'ngResource' ]);

services.factory('LoginService', function($resource) {
	return $resource('login', {}, {
		authenticate : {
			method : 'POST',
			params : {},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}
	});
});

services.factory('errorResponseInterceptor', [
		'$rootScope',
		'$q',
		'$location',
		function($rootScope, $q, $location) {
			var errorResponseInterceptor = {
				'response' : function(response) {
					return response;
				},
				'responseError' : function(response) {
					var status = response.status;
					var config = response.config;
					var method = config.method;
					var url = config.url;

					if (status == 401) {
						$location.path("/login");
					} else {
						$rootScope.error = method + " on " + url
								+ " failed with status " + status;
					}

					return $q.reject(response);
				}
			};
			return errorResponseInterceptor;
		} ]);
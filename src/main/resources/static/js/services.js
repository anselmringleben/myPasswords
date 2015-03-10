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
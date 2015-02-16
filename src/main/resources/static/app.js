/**
 * 
 */

'use strict';

angular.module('myPassword', [])
	.controller('PasswordCtrl', function($scope, $http){
		$http.get('./password/list').then(function(passwordResponse) {
			$scope.passwords = passwordResponse.data;
		});
	});
(function() {
	var app = angular.module('myPassword', [ 'ngRoute' ]);

	app.config([ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/table', {
			templateUrl : 'table.html'
		});

		$routeProvider.when('/login', {
			templateUrl : 'login.html',
			controller : 'LoginCtrl'
		});

		$routeProvider.otherwise({
			redirectTo : '/login'
		});

		// $locationProvider.hashPrefix('!');
		//
		// $httpProvider.interceptors.push(function($q, $rootScope,
		// $location) {
		// return {
		// 'responseError' : function(rejection) {
		// var status = rejection.status;
		// var config = rejection.config;
		// var method = config.method;
		// var url = config.url;
		//
		// if (status == 401) {
		// $location.path("/login");
		// } else {
		// $rootScope.error = method + " on " + url
		// + " failed with status " + status;
		// }
		//
		// return $q.reject(rejection);
		// }
		// };
		// });
	} ]);

	app.controller('PasswordCtrl', [ '$http', '$log', function($http, $log) {
		var store = this;
		store.passwords = [];
		$http.get('http://localhost:8080/passwords').success(function(data) {
			$log.debug('Retrieved: ' + data)
			store.passwords = data;
		}).error(function(data, status, headers) {
			$log.error(data + ' ' + status + ' ' + headers);
		});
	} ]);

	app.controller('PasswordInputCtrl', [
			'$http',
			'$log',
			'$scope',
			function($http, $log, $scope) {
				this.password = {};

				$scope.send = function(input) {
					$log.log(input);
					this.password = angular.copy(input);
					$http.post('http://localhost:8080/passwords', input)
							.success(
									function(data, status, headers, config) {
										$log.log('Successfully submitted: '
												+ data + ' ' + status);
									}).error(
									function(data, status, headers, config) {
										$log.error('Error submitting: ' + data
												+ ' ' + status);
									});
					$scope.password = {};
				};
			} ]);

	app.controller('LoginCtrl', [
			'$scope',
			'$rootScope',
			'$location',
			function($scope, $rootScope, $location) {

				$scope.login = function() {
					if ($scope.username === "test"
							&& $scope.password === "abc123") {
						delete $rootScope.error;
						$location.path('/table');
					} else {
						$rootScope.error = "Wrong Username/Password Combination!";
					}
				};
			} ]);
})();

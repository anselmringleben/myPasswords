(function() {
	var app = angular.module('myPassword', [ 'ngRoute', 'myPassword.services',
			'myPassword.config' ]);

	app.config(
			[
					'$routeProvider',
					'$locationProvider',
					'$httpProvider',
					'xAuthTokenHeaderName',
					function($routeProvider, $locationProvider, $httpProvider) {
						$routeProvider.when('/table', {
							templateUrl : 'partials/table.html'
						});

						$routeProvider.when('/login', {
							templateUrl : 'partials/login.html',
							controller : LoginController
						});

						$routeProvider.otherwise({
							redirectTo : '/login'
						});

						$locationProvider.hashPrefix('!');

						$httpProvider.interceptors
								.push('errorResponseInterceptor');

					} ]).run(
			function($rootScope, $http, $location, LoginService,
					xAuthTokenHeaderName) {
				$rootScope.$on('$viewContentLoaded', function() {
					delete $rootScope.error;
				});

				$rootScope.hasRole = function(role) {
					if ($rootScope.user === undefined) {
						return false;
					}

					if ($rootScope.user.roles[role] === undefined) {
						return false;
					}

					return $rootScope.user.roles[role];
				};

				$rootScope.logout = function(xAuthTokenHeaderName) {
					delete $rootScope.user;
					delete $http.defaults.headers.common[xAuthTokenHeaderName];
					$location.path("/login");
				};

				if ($rootScope.user === undefined) {
					$location.path("/login");
				}
			});

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

	function LoginController($scope, $rootScope, $location, $http,
			LoginService, xAuthTokenHeaderName) {
		$scope.login = function() {
			LoginService
					.authenticate(
							$.param({
								username : $scope.username,
								password : $scope.password
							}),
							function(user) {
								$rootScope.user = user;
								$http.defaults.headers.common[xAuthTokenHeaderName] = user.token;
								$location.path("/table");
							});
		};
	}
})();

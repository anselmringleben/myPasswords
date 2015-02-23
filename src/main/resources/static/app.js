(function() {
	var app = angular.module('myPassword', []);

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
})();

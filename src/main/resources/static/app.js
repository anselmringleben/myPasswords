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
})();

(function(angular) {
    'use strict';
    angular.module('angularExample', []).controller('UserController',
	    [ '$scope', '$http', function($scope, $http) {
		$scope.users = [ ]

		, $scope.usernew = {
			firstname:"",
			name: "",
			email:""
		},

		$scope.load = function() {
		    $http.get('pottmeier/userservice/users'
		     ).then(function successCallback(response) {
			// this callback will be called asynchronously
			// when the response is available
			$scope.users = response.data.user;
		    }, function errorCallback(response) {
			// called asynchronously if an error occurs
			// or server returns response with an error status.
			alert("An error occured:" + response.statusText);
		    });
		},

		$scope.deleteUser = function(id) {
		    $http.delete( 'pottmeier/userservice/users/' + id
		    ).then(function successCallback(response) {
			// this callback will be called asynchronously
			// when the response is available
			alert("Successful deleted");
			$scope.load();
		    }, function errorCallback(response) {
			// called asynchronously if an error occurs
			// or server returns response with an error status.
			alert("An error occured:" + response.statusText);
		    });
		}, $scope.createUser = function() {
		    var data={user: $scope.usernew  }; 
		    $http.post('pottmeier/userservice/users',data).then(function successCallback(response) {
			// this callback will be called asynchronously
			// when the response is available
			alert("Successful created");
			$scope.load();
		    }, function errorCallback(response) {
			// called asynchronously if an error occurs
			// or server returns response with an error status.
			alert("An error occured:" + response.statusText);
		    });
		}

	    } ]);
})(window.angular);
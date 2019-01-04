angular.module("e-lancer.map")
    .controller('MapCtrl', function ($state, $scope, $ionicLoading, ReverseGeocodingService,$state) {
        if(!$scope.initialPos)
            $scope.initialPos={
                lat:37.9433958291991,
                lng: 23.74944269657135         
            };
     console.log( $scope.restrictedLocation.restrictionChoice);
    $scope.restrictionChoice={test:'gg'};
        $scope.results = [];
        $scope.choice = {
            location: ''
        };
        $scope.currentPos;

        $scope.showMapLoading = function () {
            $ionicLoading.show({
                template: 'Loading google map...'
            });
        };
        $scope.hideMapLoading = function () {
            $ionicLoading.hide();
        };

        $scope.showPointLoading = function () {
            $ionicLoading.show({
                template: 'Loading point...'
            });
        };
        $scope.hidePointLoading = function () {
            $ionicLoading.hide();
        };

        var mapInit = function () {
            $scope.showMapLoading();
            $scope.currentPos = new google.maps.LatLng(parseFloat($scope.initialPos.lat), parseFloat($scope.initialPos.lng));
            ReverseGeocodingService.reverseCoords($scope.initialPos).then(function (results) {
                processResults(results);
            });
            var mapOptions = {
                center: $scope.currentPos,
                zoom: 5,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            $scope.map = new google.maps.Map(document.getElementById("map"),
                mapOptions);
            $scope.marker = new google.maps.Marker({
                position: $scope.currentPos,
                map: $scope.map,
                options: function () {
                    return {
                        draggable: true
                    }
                }
            });
            $scope.map.addListener('click', function (e) {
                placeMarkerAndPanTo(e.latLng);

            });
            $scope.map.addListener('tilesloaded', function () {
                $scope.hideMapLoading();
                isMapLoaderShown = false;
            });

            function placeMarkerAndPanTo(latLng) {
                $scope.currentPos = new google.maps.LatLng(latLng.lat(), latLng.lng());
                $scope.marker.setPosition($scope.currentPos);
                $scope.map.panTo($scope.currentPos);
                if (!isMapLoaderShown)
                    $scope.showPointLoading();
                ReverseGeocodingService.reverseCoords({
                    lat: latLng.lat(),
                    lng: latLng.lng()
                }).then(function (results) {
                    $scope.hidePointLoading();
                    processResults(results);
                });
            };
        };
        ionic.Platform.ready(mapInit);

        $scope.submitLocation = function () {
             $scope.restrictedLocation.restrictionChoice = _.findWhere($scope.results, {
                location: $scope.choice.location
            });
            console.log( $scope.restrictedLocation.restrictionChoice);
            $state.go($state.previous);
        };
       $scope.back = function () {
             $state.go($state.previous);
        };
        function processResults(results) {
            var country;
            var level4;
            var level3;
            var level2;

            var length_1 = 3
            if (results.length < 3)
                length_1 = results.length;
            for (var x = 0; x < length_1; x++) {
               // console.log("result " + x);
                for (var y = 0, length_2 = results[x].address_components.length; y < length_2; y++) {
                    for (var t = 0; t < results[x].address_components[y].types.length; t++) {
                        if (results[x].address_components[y].types[t] == 'country') {
                            country = results[x].address_components[y].long_name;
                        }
                        if (results[x].address_components[y].types[t] == 'administrative_area_level_4') {
                            level4 = results[x].address_components[y].long_name;
                        }
                        if (results[x].address_components[y].types[t] == 'administrative_area_level_3') {
                            level3 = results[x].address_components[y].long_name;
                        }
                        if (results[x].address_components[y].types[t] == 'administrative_area_level_2') {
                            level2 = results[x].address_components[y].long_name;
                        }
                       // console.log(results[x].address_components[y].types[t]);
                        if (country&&level4 && level3 && level2) {
                            break;
                        }
                    }
                    // console.log(results[x].address_components[y].long_name);
                }
            }
            $scope.results = [];

            $scope.results.push({
                country: country,
                location: ''
            });
            if (level2)
                $scope.results.push({
                    country: country,
                    location: level2
                });
            if (level3)
                $scope.results.push({
                    country: country,
                    location: level3
                });
            if (level4)
                $scope.results.push({
                    country: country,
                    location: level4
                });

        };
    });
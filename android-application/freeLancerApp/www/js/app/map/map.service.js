angular.module("e-lancer.map")
    .service("ReverseGeocodingService", function ($q) {
        return {
            reverseCoords: function (latLng) {
                var deferred = $q.defer();
                var geocoder = new google.maps.Geocoder;
                geocoder.geocode({
                    'location': latLng
                }, function (results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        return deferred.resolve(results);
                    }
                    return deferred.reject();
                });
                return deferred.promise;
            }
        };

    });
angular.module('e-lancer-notification')

.factory('GcmRegistrationService', function ($resource, SERVER_IP_ADDRESS) {
        return $resource('http://' + SERVER_IP_ADDRESS.ipAddress + '/competitions/gcmRegistrations', {}, {

            saveGcmRegistration: {
                method: 'POST',
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }
            }
        });
    })
    .factory('NotificationService', function ($resource, SERVER_IP_ADDRESS) {
        return $resource('http://' + SERVER_IP_ADDRESS.ipAddress + '/competitions/notifications', {}, {

            sendNotification: {
                method: 'POST',
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }
            }
        });
    })
    .factory('LocalNotificationService', function ($cordovaLocalNotification, $rootScope, GcmRegistrationService) {
        var addLocalNotification = function (notification) {
            var alarmTime = new Date();
            alarmTime.setMinutes(alarmTime.getMinutes());
            $cordovaLocalNotification.add({
                id: "1234",
                date: alarmTime,
                message: "You have a new notification",
                title: notification.message,
                autoCancel: true,
                sound: null
            }).then(function () {
                console.log("The notification has been set");
            });
        };
        return {
            addLocalNotification: addLocalNotification
        }
    })
    .factory('GcmPushRegistrationService', function ($resource, $cordovaPush, $rootScope, GcmRegistrationService, LocalNotificationService) {
        var androidConfig = {
            "senderID": "351121439772",
        };
        var pushRegister = function (userId) {
            $cordovaPush.register(androidConfig).then(function (deviceToken) {
            }, function (err) {
            });

            $rootScope.$on('$cordovaPush:notificationReceived', function (event, notification) {
                switch (notification.event) {
                    case 'registered':
                        if (notification.regid.length > 0) {
                            // alert('registration ID = ' + notification.regid);
                            var gcmRegistration = {};
                            gcmRegistration.user = {};
                            gcmRegistration.user.userId = userId;
                            gcmRegistration.registrationId = notification.regid;
                            GcmRegistrationService.saveGcmRegistration(gcmRegistration).$promise.then(function () {
                                console.log("y");

                            });
                        }
                        break;

                    case 'message':
                        // this is the actual push notification. its format depends on the data model from the push server
                        console.log('message = ' + notification.message + ' msgCount = ' + notification.msgcnt);
                        LocalNotificationService.addLocalNotification(notification);
                        break;

                    case 'error':
                        alert('GCM error = ' + notification.msg);
                        break;

                    default:
                        alert('An unknown GCM event has occurred');
                        break;
                }
            });
        };
        return {
            pushRegister: pushRegister
        }
    });
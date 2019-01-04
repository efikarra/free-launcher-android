 angular.module('e-lancer.bid')

 .controller('MyBidsCtrl', function ($scope, $ionicFilterBar, $ionicPopup, ExpertService, $ionicModal, ProjectService,_) {
         $scope.myBids = [];
         $scope.currentBid = {};
         $scope.currentBid.expert = {};
         $scope.currentBid.project = {};

         $scope.myBids = ExpertService.bids({
             isClosed: false
         }, {
             expId: $scope.userId
         }, function () {
             $scope.showFilterBar = function () {
                 var filterBarInstance = $ionicFilterBar.show({
                     items: $scope.myBids,
                     update: function (filteredItems, filterText) {

                         $scope.myBids = filteredItems;
                     },
                     expression: function (filterText, value, index, array) {
                         return value.project.shortDescr.toLowerCase().indexOf(filterText) !== -1 || value.project.title.toLowerCase().indexOf(filterText) !== -1;
                     }
                 });
             };
         });


         $scope.deleteBidFunc = function (id) {
             $scope.currentBid = _.findWhere($scope.myBids, {
                 bidId: id
             });
             var confirmPopup = $ionicPopup.confirm({
                 title: 'Delete bid',
                 template: 'Are you sure you want to permanently delete this bid?'
             });
             confirmPopup.then(function (res) {
                 if (res) {
                     ProjectService.deleteBid({}, {
                         id1: $scope.currentBid.project.projId,
                         id2: id
                     }, function () {
                         $scope.myBids=_.reject($scope.myBids, function (obj) {
                             return obj.bidId === id;
                         });
                     });
                 }
             });

         }
         $scope.editBidFunc = function (id) {
             $scope.currentBid = _.findWhere($scope.myBids, {
                 bidId: id
             });
             $ionicModal.fromTemplateUrl('templates/editBid.html', {
                 scope: $scope,
                 animation: 'slide-in-up'
             }).then(function (modal) {
                 $scope.modal = modal;
                 $scope.modal.show();
             });
             $scope.openModal = function () {
                 $scope.modal.show();
             };
             $scope.closeModal = function () {
                 $scope.modal.hide();
             };
             //Cleanup the modal when we're done with it!
             $scope.$on('$destroy', function () {
                 $scope.modal.remove();
             });

         };
     })
     .controller('EditBidCtrl', function ($scope, $stateParams, ProjectService, _) {
         $scope.editBid = {};
         _.extend($scope.editBid,$scope.currentBid);
         var datePickerCallback = function (val) {
             if (typeof (val) === 'undefined') {
                 console.log('No date selected');
             } else {
                 $scope.editBid.estTime = val.getTime();
                 $scope.datepickerObject.inputDate = val;
             }
         };
         $scope.datepickerObject = {
             titleLabel: 'Choose estimated date', //Optional
             todayLabel: 'Today', //Optional
             closeLabel: 'Close', //Optional
             setLabel: 'Set', //Optional
             errorMsgLabel: 'Please select time.', //Optional
             setButtonType: 'button-assertive', //Optional
             mondayFirst: true, //Optional
             templateType: 'popup', //Optional
             modalHeaderColor: 'bar-positive', //Optional
             modalFooterColor: 'bar-positive', //Optional
             from: new Date(new Date().getTime() + (24 * 60 * 60 * 1000)),
             inputDate: new Date($scope.editBid.estTime),
             callback: function (val) { //Mandatory
                 datePickerCallback(val);
             }
         };
         $scope.updateBid = function () {
             console.log("yeah");
             console.log($scope.editBid);
             if (!_.isEqual($scope.editBid, $scope.newBid)) {
                 ProjectService.updateBid({
                     id1: $scope.currentBid.project.projId
                 }, $scope.editBid, function () {
                     $scope.currentBid.estTime = $scope.editBid.estTime;
                     $scope.currentBid.price = $scope.editBid.price;
                     $scope.closeModal();
                 });
             }
         }

     });
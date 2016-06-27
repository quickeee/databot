
app.controller('JobBrowseCtrl', ['$scope', 'Job', function($scope, Job) {

    $scope.tableColumnDefs = [
        {field: "id", name: "ID", visible: false},
        {field: "title", name: "Title"},
        {field: "notes", name: "notes", visible: false},
        {field: "type", name: "Type"},
        {field: "enabled", name: "Enabled"},
        {field: "forceRun", name: "Force Run"}
    ];

    $scope.gridOptions = {
        enableFiltering: true,
        enableColumnResizing: true,
        paginationPageSizes: [15, 30, 90, 500],
        paginationPageSize: 30,
        enableGridMenu: true,
        exporterCsvFilename: 'exported_job_data.csv',
        enableRowSelection: true,
        enableRowHeaderSelection: false,
        multiSelect: false,
        modifierKeysToMultiSelect: false,
        noUnselect: true,
        columnDefs: $scope.tableColumnDefs,
        onRegisterApi: function(gridApi) {
            //$scope.gridApi = gridApi;

            gridApi.selection.on.rowSelectionChanged($scope, function(row) {

                //$scope.changeState("app.employeeview.detail", {id: row.entity.id});

            });

        }
    };


    $scope.getAllJobs = function() {

        Job.query({size:9999999}, function(result, headers) {

            console.log(result);
            $scope.gridOptions.totalItems = result.totalElements;
            $scope.gridOptions.data = result.content;
        });

    };


    $scope.getAllJobs();

}]);
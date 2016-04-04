## In Android M, the permissions should be request in code, so I build this project to make this process easier

##how to use :
1. make your Activity which need chek and request permissions extends `PermissionActivity`,then you can use the function `checkAndRequestPermission(Activity context, String[] permision, onRequestPermissionsCallback back)`
2. `PermissionActivity`is a direct Subclass of `AppCompatActivity`,so don't worry ~~
3. `checkAndRequestPermission(Activity context, String[] permision, onRequestPermissionsCallback back)`need a Activity context, the `String[]` is an array of the permissions in form `Manifest.permission.xxx`,there are to functions you must impliment for  `onRequestPermissionsCallback` interface, one is `success()`and the other one is `fail()`. I suppose you know the meaning.
4. if the request was delied ,there would be a dialog to lead users to check permission setting pages.

## In Android M, the permissions should be request in code, so I build this project to make this process easier

## How to use:
1. in maven`':<dependency>
                <groupId>com.blen.permission</groupId>
                <artifactId>permission</artifactId>
                <version>0.0.1</version>
                <type>pom</type>
              </dependency>`
2. with gradle `compile 'com.blen.permission:permission:0.0.1'`


## get start in your code:
1. make your Activity which need check and request permissions extends `PermissionActivity`,then you can use the function `checkAndRequestPermission(Activity context, String[] permision, onRequestPermissionsCallback back)`to check and request permissions.
2. `PermissionActivity`is a direct Subclass of `AppCompatActivity`,so don't worry ~~
3. `checkAndRequestPermission(Activity context, String[] permision, onRequestPermissionsCallback back)`need a Activity context, the `String[]` is an array of the permissions in form `Manifest.permission.xxx`,there are to functions you must impliment for  `onRequestPermissionsCallback` interface, one is `success()`and the other one is `fail()`. I suppose you know the meaning.
4. if the request was delied ,there would be a dialog to lead users to check permission setting pages.

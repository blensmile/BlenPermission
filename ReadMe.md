## Brief introduction 
In Android M, the permissions should be requested in code, so I build this project to make this process easier

## How to introduce to your project
- Download and use the code, strings are defined in simplified Chinese and English
- with maven:
>
	<dependency>    
		<groupId>com.blen.permission</groupId>    
        <artifactId>permission</artifactId>    
       	<version>0.0.2</version>    
        <type>pom</type>    
    </dependency>   

- with gradle 
>
	compile 'com.blen.permission:permission:0.0.2'

## How to use
>
	public xxx extends PermissionActivity ...{
		...
		public void xxx(...){
			...
			//here you would check the permissions
			checkAndRequestPermission(this, new String[]{Manifest.permission.xx,...}, 
					new onRequestPermissionsCallback(){
						@Override
						public void success(){
							//if the application has or successfully get the permissions
						}
						@Override
						public void fail(){
							//1.if the permission was refused, before fail() exucute 
							//  there'll show a dialog to lead users to setting page
							//2.if cancelled or was refused, what would you do?
						}
					});
			...
		}
		...
	}

## Some tips:
- 	Firstly,if your Activity need check and request permissions,the activity should extends	`PermissionActivity`  
	then you can use the function   
	`checkAndRequestPermission(Activity context, String[] permision, onRequestPermissionsCallback back)`  
	to check and request permissions.
- `PermissionActivity` is a direct subclass of `AppCompatActivity`  
	so don't worry the the `extends` operation would influence your activity or your project
- `checkAndRequestPermission(Activity context, String[] permision, onRequestPermissionsCallback back)`  
	need an Activity context,   
	the `String[]` is an array of the permissions in form of `Manifest.permission.xxx`  
	`onRequestPermissionsCallback` is an interface,you should implements to functions, one is `success()`and the other one is `fail()`. I suppose you know the meaning.
- if the request was delied ,there'll show a dialog to lead users to setting page.

##
<?php
require "conn.php";
$id=$_POST["id"];
$pass=$_POST["pass"];
$quary="select * from stu_details.faculty where co_id='$id' and pass='$pass';";
$result=mysqli_query($conn,$quary);
$error="{'error':'Wrong id or Password'}";
if(mysqli_num_rows($result)>0){
		while($row=mysqli_fetch_assoc($result)){
			$output=$row;
		}
		print(json_encode($output));
	}
	else
		print($error);
	
	mysqli_close($conn);
?>
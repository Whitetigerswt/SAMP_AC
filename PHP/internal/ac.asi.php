<?php
	require_once 'connect.php';

	function begin() {
		
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		if($result = $uConnect->query("SELECT `hash` FROM `AC_Hashes` WHERE `filename` = 'ac.asi' LIMIT 1")) {
			if($row = $result->fetch_assoc()) {
				echo $row['hash'];
			}
			$result->free();
		}
		if($result = $uConnect->query("SELECT `hash` FROM `AC_Hashes` WHERE `filename` = 'd3d9.dll' LIMIT 1")) {
			if($row = $result->fetch_assoc()) {
				echo "|" . $row['hash'];
			}
			$result->free();
		}
		$uConnect->close();
		exit();
	}
	
	begin();

?>	